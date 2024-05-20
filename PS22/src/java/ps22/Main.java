/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ps22;

import entities.Kategorija;
import entities.Korisnik2;
import entities.Snimak;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
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
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PS22PU");
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
    
    // -------------------------------  ZAHTEV #5  -------------------------------
    private static Message napraviKategoriju(ZahtevSP zahtev) throws JMSException{
        String nazivKategorije = (String)(zahtev.dohParametre().get(1));
        List<Kategorija> listaKategorije = em.createNamedQuery("Kategorija.findByImeKat", Kategorija.class).setParameter("imeKat", nazivKategorije).getResultList();
        if(!listaKategorije.isEmpty())
            return napraviTekstPoruka("Vec postoji kategorija sa istim imenom!", -1);
        
        Kategorija kat = new Kategorija();
        kat.setImeKat(nazivKategorije);
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            em.persist(kat);
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
        
        return napraviTekstPoruka("Napravljena kategorija: " + kat.getImeKat(), 0);
    }

    // -------------------------------  ZAHTEV #6  -------------------------------
    private static Message napraviSnimak(ZahtevSP zahtev) throws JMSException{
        String imeSnimka = (String)(zahtev.dohParametre().get(1));
        String trajanjeS = (String)(zahtev.dohParametre().get(2));
        String imeVlasnika = (String)(zahtev.dohParametre().get(3));
        String datumSnimanjaS = (String)(zahtev.dohParametre().get(4));
        int trajanje = Integer.parseInt(trajanjeS);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy,HH:mm:ss");
        Date datumSnimanja = null;
        try {
            datumSnimanja = sdf.parse(datumSnimanjaS);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<Snimak> listaSnimaka = em.createNamedQuery("Snimak.findByImeSnim", Snimak.class).setParameter("imeSnim", imeSnimka).getResultList();
        if(!listaSnimaka.isEmpty())
            return napraviTekstPoruka("Vec postoji snimak sa istim imenom!", -1);
        
        List<Korisnik2> listaKorisnika = em.createNamedQuery("Korisnik2.findByImeKor", Korisnik2.class).setParameter("imeKor", imeVlasnika).getResultList();
        if(listaKorisnika.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa datim imenom!", -2);
        
        Snimak snimak = new Snimak();
        snimak.setImeSnim(imeSnimka);
        snimak.setTrajanje(trajanje);
        snimak.setIDKor(listaKorisnika.get(0));
        snimak.setDatumSnim(datumSnimanja);
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            em.persist(snimak);
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
        
        Snimak snimakIzBaze = em.createNamedQuery("Snimak.findByImeSnim", Snimak.class).setParameter("imeSnim", imeSnimka).getResultList().get(0);
        String idSnimkaIzBazeS = snimakIzBaze.getIDSnim().toString();
        ArrayList<String> lista = new ArrayList<String>(); lista.add(idSnimkaIzBazeS); lista.add(imeSnimka);
        ZahtevSP zahtevZaPod3 = napraviZahtev(101, lista);
        int rezultatIzv_3 = izvrsenjeZahtevaZaPodsisteme(context, producer, consumer, ID_PODSISTEM3, zahtevZaPod3);
        
        return napraviTekstPoruka("Napravljena snimak: " + snimak.getImeSnim(), 0);
    }
    
    // -------------------------------  ZAHTEV #7  -------------------------------
    private static Message promeniNazivSnimka(ZahtevSP zahtev) throws JMSException{
        String imeSnimka = (String)(zahtev.dohParametre().get(1));
        String imeVlasnika = (String)(zahtev.dohParametre().get(2));
        String novoImeSnimka = (String)(zahtev.dohParametre().get(3));
        List<Snimak> listaSnimaka = em.createNamedQuery("Snimak.findByImeSnim", Snimak.class).setParameter("imeSnim", imeSnimka).getResultList();
        if(listaSnimaka.isEmpty())
            return napraviTekstPoruka("Ne postoji snimak sa unetim imenom!", -1);
        
        List<Korisnik2> listaKorisnika = em.createNamedQuery("Korisnik2.findByImeKor", Korisnik2.class).setParameter("imeKor", imeVlasnika).getResultList();
        if(listaKorisnika.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa unetim imenom!", -2);
        
        List<Snimak> listaSnimaka1 = em.createNamedQuery("Snimak.findByImeSnim", Snimak.class).setParameter("imeSnim", novoImeSnimka).getResultList();
        if(!listaSnimaka1.isEmpty())
            return napraviTekstPoruka("Vec postoji snimak sa unetim imenom!", -3);
        
        Snimak snimak = listaSnimaka.get(0);
        Korisnik2 korisnik = listaKorisnika.get(0);
        
        if(korisnik.getIDKor().intValue() != snimak.getIDKor().getIDKor().intValue())
            return napraviTekstPoruka("Korisnik " + korisnik.getImeKor() + " nije vlasnik " + snimak.getImeSnim(), -4);
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            snimak.setImeSnim(novoImeSnimka);
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
        
        Snimak snimakIzBaze = em.createNamedQuery("Snimak.findByImeSnim", Snimak.class).setParameter("imeSnim", novoImeSnimka).getResultList().get(0);
        String imeSnimkaIzBaze = snimakIzBaze.getImeSnim();
        ArrayList<String> lista = new ArrayList<String>(); lista.add(imeSnimka); lista.add(imeSnimkaIzBaze);
        ZahtevSP zahtevZaPod3 = napraviZahtev(102, lista);
        int rezultatIzv_3 = izvrsenjeZahtevaZaPodsisteme(context, producer, consumer, ID_PODSISTEM3, zahtevZaPod3);
        
        return napraviTekstPoruka("Nova ime snimka korisnika " + snimak.getIDKor().getImeKor() + " je " + snimak.getImeSnim(), 0);
    }
    
    // -------------------------------  ZAHTEV #8  -------------------------------
    private static Message dodavanjeKatSnimku(ZahtevSP zahtev) throws JMSException{
        String imeSnimka = (String)(zahtev.dohParametre().get(1));
        String imeVlasnika = (String)(zahtev.dohParametre().get(2));
        String imeKategorije = (String)(zahtev.dohParametre().get(3));
        List<Snimak> listaSnimaka = em.createNamedQuery("Snimak.findByImeSnim", Snimak.class).setParameter("imeSnim", imeSnimka).getResultList();
        if(listaSnimaka.isEmpty())
            return napraviTekstPoruka("Ne postoji snimak sa unetim imenom!", -1);
        
        List<Korisnik2> listaKorisnika = em.createNamedQuery("Korisnik2.findByImeKor", Korisnik2.class).setParameter("imeKor", imeVlasnika).getResultList();
        if(listaKorisnika.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa unetim imenom!", -2);
        
        Snimak snimak = listaSnimaka.get(0);
        Korisnik2 korisnik = listaKorisnika.get(0);
        if(korisnik.getIDKor().intValue() != snimak.getIDKor().getIDKor().intValue())
            return napraviTekstPoruka("Korisnik " + korisnik.getImeKor() + " nije vlasnik " + snimak.getImeSnim(), -3);
        
        List<Kategorija> listaKategorija = em.createNamedQuery("Kategorija.findByImeKat", Kategorija.class).setParameter("imeKat", imeKategorije).getResultList();
        if(listaKategorija.isEmpty())
            return napraviTekstPoruka("Ne postoji kategorija sa unetim imenom!", -4);
        
        List<Kategorija> novaListaK = snimak.getKategorijaList();
        
        for (Kategorija kat : novaListaK) {
            if(kat.getIDKat().intValue() == listaKategorija.get(0).getIDKat().intValue())
                return napraviTekstPoruka("Vec dodata kategorija " + imeKategorije + " snimku " + snimak.getImeSnim() + "!", -5);
        }
        
        novaListaK.add(listaKategorija.get(0));
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            snimak.setKategorijaList(novaListaK);
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
        
        return napraviTekstPoruka("Kategorija " + imeKategorije + " je dodata snimku " + snimak.getImeSnim(), 0);
    }
    
    // -------------------------------  ZAHTEV #16  -------------------------------
    private static Message brisanjeSnimka(ZahtevSP zahtev) throws JMSException{
        String imeSnimka = (String)(zahtev.dohParametre().get(1));
        String imeVlasnika = (String)(zahtev.dohParametre().get(2));
        
        List<Snimak> listaSnimaka = em.createNamedQuery("Snimak.findByImeSnim", Snimak.class).setParameter("imeSnim", imeSnimka).getResultList();
        if(listaSnimaka.isEmpty())
            return napraviTekstPoruka("Ne postoji snimak sa unetim imenom!", -1);
        
        List<Korisnik2> listaKorisnika = em.createNamedQuery("Korisnik2.findByImeKor", Korisnik2.class).setParameter("imeKor", imeVlasnika).getResultList();
        if(listaKorisnika.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa unetim imenom!", -2);
        
        Snimak snimak = listaSnimaka.get(0);
        Korisnik2 korisnik = listaKorisnika.get(0);
        if(korisnik.getIDKor().intValue() != snimak.getIDKor().getIDKor().intValue())
            return napraviTekstPoruka("Korisnik " + korisnik.getImeKor() + " nije vlasnik " + snimak.getImeSnim(), -3);
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            snimak.setKategorijaList(null);
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
        
        try{
            em.getTransaction().begin();
            em.remove(snimak);
            em.flush();
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
            return napraviTekstPoruka("GRESKA!!!", -11);
        }
        
        ArrayList<String> lista = new ArrayList<String>(); lista.add(imeSnimka);
        ZahtevSP zahtevZaPod3 = napraviZahtev(103, lista);
        int rezultatIzv_3 = izvrsenjeZahtevaZaPodsisteme(context, producer, consumer, ID_PODSISTEM3, zahtevZaPod3);
        
        return napraviTekstPoruka("Obrisan snimak " + imeSnimka, 0);
    }
    
    // -------------------------------  ZAHTEV #19  -------------------------------
    private static Message dohvatiKategorije(ZahtevSP zahtev) throws JMSException{
        List<Kategorija> listaKategorija = em.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
        
        return napraviObjectPoruka(0, (Serializable)listaKategorija);
    }
    
    // -------------------------------  ZAHTEV #20  -------------------------------
    private static Message dohvatiSnimke(ZahtevSP zahtev) throws JMSException{
        List<Snimak> listaSnimaka = em.createNamedQuery("Snimak.findAll", Snimak.class).getResultList();
        
        return napraviObjectPoruka(0, (Serializable)listaSnimaka);
    }
    
    // -------------------------------  ZAHTEV #21  -------------------------------
    private static Message dohvatiKategorijeZaSnimak(ZahtevSP zahtev) throws JMSException{
        String imeSnimka = (String)(zahtev.dohParametre().get(1));
        List<Snimak> listaSnimaka = em.createNamedQuery("Snimak.findByImeSnim", Snimak.class).setParameter("imeSnim", imeSnimka).getResultList();
        if(listaSnimaka.isEmpty())
            return napraviTekstPoruka("Ne postoji snimak sa unetim imenom!", -1);
        
        Snimak snimak = listaSnimaka.get(0);
        List<Kategorija> niz = snimak.getKategorijaList();
        if(niz != null && niz.isEmpty() == false){
            int g = niz.get(0).getIDKat();
        }
        return napraviObjectPoruka(0, (Serializable)niz);
    }
    
    // -------------------------------  ZAHTEV #100  -------------------------------
    private static Message dodavanjeKorisnika(ZahtevSP zahtev) throws JMSException{
        String idKorS = (String)(zahtev.dohParametre().get(1));
        String imeKor = (String)(zahtev.dohParametre().get(2));
        int idKor = Integer.parseInt(idKorS);
        
        Korisnik2 korisnik = new Korisnik2(idKor, imeKor);
        
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
            TextMessage poruka = context.createTextMessage("GRESKA!!! Pri pravljenju korisnik_2: " + korisnik.getImeKor());
            poruka.setIntProperty("result", -10);
            poruka.setIntProperty("podsistem", ID_PODSISTEM1);
            return poruka;
        }
        
        TextMessage poruka = context.createTextMessage("Napravljen korisnik_2: " + korisnik.getImeKor());
        poruka.setIntProperty("result", 0);
        poruka.setIntProperty("podsistem", ID_PODSISTEM1);
        return poruka;
    }
    
    
    public static void main(String[] args) {
        if(setup == false){
            setUpJMS();
            setup = true;
        }
        System.out.println("PODSISTEM2 aktivan...");System.out.println("");
        while(true){
            ObjectMessage om = (ObjectMessage) consumer.receive();
            Message porukaNazad = context.createTextMessage("Prazna poruka");
            int tipZahteva = -1;
            try {
                ZahtevSP zahtev = (ZahtevSP)om.getObject();
                tipZahteva = zahtev.dohTipZahteva();
                System.out.println("Primljen zahtev: " + tipZahteva);
                switch(tipZahteva){
                    case 5:
                        porukaNazad = napraviKategoriju(zahtev);
                        break;
                    case 6:
                        porukaNazad = napraviSnimak(zahtev);
                        break;
                    case 7:
                        porukaNazad = promeniNazivSnimka(zahtev);
                        break;
                    case 8:
                        porukaNazad = dodavanjeKatSnimku(zahtev);
                        break;
                    case 16:
                        porukaNazad = brisanjeSnimka(zahtev);
                        break;
                    case 19:
                        porukaNazad = dohvatiKategorije(zahtev);
                        break;
                    case 20:
                        porukaNazad = dohvatiSnimke(zahtev);
                        break;
                    case 21:
                        porukaNazad = dohvatiKategorijeZaSnimak(zahtev);
                        break;
                    case 100:
                        porukaNazad = dodavanjeKorisnika(zahtev);
                        break;
                }
                
                
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(tipZahteva != 100)
                producer.send(queue, porukaNazad);
            else
                producer.send(topic, porukaNazad);
            System.out.println("Podsistem2 zavrsio obradu");System.out.println("");
        }
    }
    
    private static void setUpJMS(){
        context = connectionFactory.createContext();
        context.setClientID("podsistem2");
        consumer = context.createDurableConsumer(topic, "sub2", "podsistem=2", false);
        producer = context.createProducer();
    } 
}
