/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ps1;

import entities.Korisnik;
import entities.Mesto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import zahtevi.ZahtevSP;

public class Main {
    
    @Resource(lookup="connFac")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup="resTopic")
    private static Topic topic;
    
    @Resource(lookup="resQueue")
    private static Queue queue;
    
    private static JMSContext context = null;
    private static JMSProducer producer = null;
    private static JMSConsumer consumer = null;
    private static Boolean setup = false;
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PS1PU");
    static EntityManager em = emf.createEntityManager();
    
    private static int ID_PODSISTEM1 = 1;
    private static int ID_PODSISTEM2 = 2;
    private static int ID_PODSISTEM3 = 3;
    
    private static TextMessage napraviTekstPoruka(String s, int kod) throws JMSException{
        TextMessage poruka = context.createTextMessage(s);
        poruka.setIntProperty("result", kod);
        return poruka;
    }
    private static ObjectMessage napraviObjectPoruka(int kod, Serializable ser) throws JMSException{
        ObjectMessage poruka = context.createObjectMessage();
        poruka.setObject(ser);
        poruka.setIntProperty("result", kod);
        return poruka;
    }
    
    private static ZahtevSP napraviZahtev(int tipZahtev, ArrayList<String> lista){
        ZahtevSP zahtev = new ZahtevSP();
        zahtev.dodajParametar(tipZahtev);
        if(lista != null){
            for (String parametar : lista)
                zahtev.dodajParametar(parametar);
        }
        return zahtev;
    }
    
    private static int izvrsenjeZahtevaZaPodsisteme(JMSContext context, JMSProducer producer, JMSConsumer consumer, int idPodsistema, ZahtevSP zahtev) throws JMSException{
        ObjectMessage porukaSaZahtevom = context.createObjectMessage(zahtev);
        porukaSaZahtevom.setIntProperty("podsistem", idPodsistema);
        
        producer.send(topic, porukaSaZahtevom);
            
        Message porukaOsn = consumer.receive();
        int rezultatIzv = porukaOsn.getIntProperty("result");
        return rezultatIzv;
    }
    
    // -------------------------------  ZAHTEV #1  -------------------------------
    private static Message napraviMesto(ZahtevSP zahtev) throws JMSException{
        String nazivMesta = (String)(zahtev.dohParametre().get(1));
        
        List<Mesto> listaMesta = em.createNamedQuery("Mesto.findByImeMes", Mesto.class).setParameter("imeMes", nazivMesta).getResultList();
        if(!listaMesta.isEmpty())
            return napraviTekstPoruka("Vec postoji mesto sa istim imenom!", -1);
        
        Mesto mesto = new Mesto();
        mesto.setImeMes(nazivMesta);
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            em.persist(mesto);
            em.getTransaction().commit();
        }
        catch(PersistenceException | NullPointerException pe){
            greskaPriTrans = true;
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        if(greskaPriTrans){
            return napraviTekstPoruka("GRESKA!!!", -10);
        }
        return napraviTekstPoruka("Napravljeno mesto: " + mesto.getImeMes(), 0);
    }
    
    // -------------------------------  ZAHTEV #2  -------------------------------
    private static Message napraviKorisnika(ZahtevSP zahtev) throws JMSException{
        String imeKor = (String)(zahtev.dohParametre().get(1));
        String emailKor = (String)(zahtev.dohParametre().get(2));
        String godisteKor = (String)(zahtev.dohParametre().get(3));
        String polKor = (String)(zahtev.dohParametre().get(4));
        String nazivMesta = (String)(zahtev.dohParametre().get(5));
        
        List<String> listaKorisnika = em.createNamedQuery("Korisnik.findByImeKor", String.class).setParameter("imeKor", imeKor).getResultList();
        if(!listaKorisnika.isEmpty())
            return napraviTekstPoruka("Vec postoji korisnik sa istim imenom-om!", -1);
        
        List<String> listaEmail = em.createNamedQuery("Korisnik.findByEmail", String.class).setParameter("email", emailKor).getResultList();
        if(!listaEmail.isEmpty())
            return napraviTekstPoruka("Vec postoji korisnik sa istim email-om!", -2);
        
        List<Mesto> listaMesta = em.createNamedQuery("Mesto.findByImeMes", Mesto.class).setParameter("imeMes", nazivMesta).getResultList();
        if(listaMesta.isEmpty())
            return napraviTekstPoruka("Ne postoji mesto sa unetim imenom!", -3);
        
        Korisnik korisnik = new Korisnik();
        korisnik.setImeKor(imeKor);
        korisnik.setEmail(emailKor);
        korisnik.setGodiste(Integer.parseInt(godisteKor));
        korisnik.setPol(polKor);
        korisnik.setIDMesto(listaMesta.get(0));
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            em.persist(korisnik);
            em.getTransaction().commit();
        }
        catch(PersistenceException | NullPointerException pe){
            greskaPriTrans = true;
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        if(greskaPriTrans){
            return napraviTekstPoruka("GRESKA!!!", -10);
        }
        
        Korisnik korisnikIzBaze = em.createNamedQuery("Korisnik.findByImeKor", Korisnik.class).setParameter("imeKor", imeKor).getResultList().get(0);
        String idKorIzBazeS = korisnikIzBaze.getIDKor().toString();
        ArrayList<String> lista = new ArrayList<String>(); lista.add(idKorIzBazeS); lista.add(imeKor);
        ZahtevSP zahtevZaPod2_i_3 = napraviZahtev(100, lista);
        int rezultatIzv_2 = izvrsenjeZahtevaZaPodsisteme(context, producer, consumer, ID_PODSISTEM2, zahtevZaPod2_i_3);
        int rezultatIzv_3 = izvrsenjeZahtevaZaPodsisteme(context, producer, consumer, ID_PODSISTEM3, zahtevZaPod2_i_3);
        
        return napraviTekstPoruka("Napravljen korisnik: " + korisnik.getImeKor(), 0);
    }
    
    // -------------------------------  ZAHTEV #3  -------------------------------
    private static Message promeniEmailKor(ZahtevSP zahtev) throws JMSException{
        String imeKor = (String)(zahtev.dohParametre().get(1));
        String noviEmailKor = (String)(zahtev.dohParametre().get(2));
        
        List<Korisnik> listaKor = em.createNamedQuery("Korisnik.findByImeKor", Korisnik.class).setParameter("imeKor", imeKor).getResultList();
        if(listaKor.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa unetim imenom!", -1);
        
        Korisnik k = listaKor.get(0);
        
        if(k.getEmail().equals(noviEmailKor))
            return napraviTekstPoruka("Uneti email je isti kao trenutni!", -2);
        
        List<Korisnik> listaKorSaDatimEmail = em.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", noviEmailKor).getResultList();
        if(!listaKorSaDatimEmail.isEmpty())
            return napraviTekstPoruka("Postoji korisnik sa unetim email-om!", -3);
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            k.setEmail(noviEmailKor);
            em.getTransaction().commit();
        }
        catch(PersistenceException | NullPointerException pe){
            greskaPriTrans = true;
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        if(greskaPriTrans){
            return napraviTekstPoruka("GRESKA!!!", -10);
        }
        
        return napraviTekstPoruka("Nova email adresa za korisnika " + k.getImeKor()+ " je " + k.getEmail(), 0);
    }
    
    // -------------------------------  ZAHTEV #4  -------------------------------
    private static Message promeniMestoKor(ZahtevSP zahtev) throws JMSException{
        String imeKor = (String)(zahtev.dohParametre().get(1));
        String imeMesta = (String)(zahtev.dohParametre().get(2));
        
        List<Korisnik> listaKor = em.createNamedQuery("Korisnik.findByImeKor", Korisnik.class).setParameter("imeKor", imeKor).getResultList();
        if(listaKor.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa unetim imenom!", -1);
        
        Korisnik k = listaKor.get(0);
        
        List<Mesto> listaMesta = em.createNamedQuery("Mesto.findByImeMes", Mesto.class).setParameter("imeMes", imeMesta).getResultList();
        if(listaMesta.isEmpty()){
            return napraviTekstPoruka("Ne postoji mesto sa unetim imenom!", -2);
        }
        Mesto mesto = listaMesta.get(0);
        
        if(k.getIDMesto().getIDMesto().intValue() == mesto.getIDMesto().intValue())
            return napraviTekstPoruka("Uneto mesto je isto kao trenutno!", -3);
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            k.setIDMesto(mesto);
            em.getTransaction().commit();
        }
        catch(PersistenceException | NullPointerException pe){
            greskaPriTrans = true;
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        if(greskaPriTrans){
            return napraviTekstPoruka("GRESKA!!!", -10);
        }
        
        return napraviTekstPoruka("Novo mesto za korisnika " + k.getImeKor()+ " je " + k.getIDMesto().getImeMes(), 0);
    }
    
    // -------------------------------  ZAHTEV #17  -------------------------------
    private static Message dohvatiMesta(ZahtevSP zahtev) throws JMSException{
        List<Mesto> listaMesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
        
        return napraviObjectPoruka(0, (Serializable)listaMesta);
    }
    
    // -------------------------------  ZAHTEV #18  -------------------------------
    private static Message dohvatiKorisnike(ZahtevSP zahtev) throws JMSException{
        List<Korisnik> listaKorisnika = em.createNamedQuery("Korisnik.findAll", Korisnik.class).getResultList();
        
        return napraviObjectPoruka(0, (Serializable)listaKorisnika);
    }
    
    public static void main(String[] args) {
        if(setup == false){
            setUpJMS();
            setup = true;
        }
        System.out.println("PODSISTEM1 aktivan...");System.out.println("");
        while(true){
            ObjectMessage om = (ObjectMessage) consumer.receive();
            Message porukaNazad = context.createTextMessage("Prazna poruka");
            try {
                ZahtevSP zahtev = (ZahtevSP)om.getObject();
                int tipZahteva = zahtev.dohTipZahteva();
                System.out.println("Primljen zahtev: " + tipZahteva);
                switch(tipZahteva){
                    case 1:
                        porukaNazad = napraviMesto(zahtev);
                        break;
                    case 2:
                        porukaNazad = napraviKorisnika(zahtev);
                        break;
                    case 3:
                        porukaNazad = promeniEmailKor(zahtev);
                        break;
                    case 4:
                        porukaNazad = promeniMestoKor(zahtev);
                        break;
                    case 17:
                        porukaNazad = dohvatiMesta(zahtev);
                        break;
                    case 18:
                        porukaNazad = dohvatiKorisnike(zahtev);
                        break;
                }
                
                
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            producer.send(queue, porukaNazad);
            System.out.println("Podsistem1 zavrsio obradu");System.out.println("");
        }
    }
    
    private static void setUpJMS(){
        context = connectionFactory.createContext();
        context.setClientID("podsistem1");
        consumer = context.createDurableConsumer(topic, "sub1", "podsistem=1", false);
        producer = context.createProducer();
    } 
    
}
