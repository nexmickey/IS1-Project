/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ps33;

import entities.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PS33PU");
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
    
    
    // -------------------------------  ZAHTEV #9  -------------------------------
    private static Message napraviPaket(ZahtevSP zahtev) throws JMSException{
        String nazivPaketa = (String)(zahtev.dohParametre().get(1));
        String cenaPaketaS = (String)(zahtev.dohParametre().get(2));
        List<Paket> listaPaketa = em.createNamedQuery("Paket.findByImePak", Paket.class).setParameter("imePak", nazivPaketa).getResultList();
        if(!listaPaketa.isEmpty())
            return napraviTekstPoruka("Vec postoji paket sa istim imenom!", -1);
        
        Paket paket = new Paket();
        paket.setImePak(nazivPaketa);
        int cena = Integer.parseInt(cenaPaketaS);
        paket.setCenaPak(cena);
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            em.persist(paket);
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
        
        return napraviTekstPoruka("Napravljen paket: " + paket.getImePak(), 0);
    }
    
    // -------------------------------  ZAHTEV #10  -------------------------------
    private static Message promeniCenuPaketa(ZahtevSP zahtev) throws JMSException{
        String imePak = (String)(zahtev.dohParametre().get(1));
        String cenaS = (String)(zahtev.dohParametre().get(2));
        
        List<Paket> listaPaketa = em.createNamedQuery("Paket.findByImePak", Paket.class).setParameter("imePak", imePak).getResultList();
        if(listaPaketa.isEmpty())
            return napraviTekstPoruka("Ne postoji paket sa unetim imenom!", -1);
        
        Paket paket = listaPaketa.get(0);
        int cena = Integer.parseInt(cenaS);
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            paket.setCenaPak(cena);
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
        
        return napraviTekstPoruka("Nova cena paketa " + paket.getImePak() + " je " + paket.getCenaPak(), 0);
    }
    
    // -------------------------------  ZAHTEV #11  -------------------------------
    private static Message napraviPretplatu(ZahtevSP zahtev) throws JMSException{
        String imeKor = (String)(zahtev.dohParametre().get(1));
        String imePaketa = (String)(zahtev.dohParametre().get(2));
        String datumS = (String)(zahtev.dohParametre().get(3));
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy,HH:mm:ss");
        Date datum = null;
        try {
            datum = sdf.parse(datumS);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Calendar datumCA = Calendar.getInstance();
        datumCA.setTime(datum);
        
        List<Korisnik3> listaKorisnika = em.createNamedQuery("Korisnik3.findByImeKor", Korisnik3.class).setParameter("imeKor", imeKor).getResultList();
        if(listaKorisnika.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa unetim imenom!", -1);
        
        Korisnik3 kor = listaKorisnika.get(0);
        
        List<Paket> listaPaketa = em.createNamedQuery("Paket.findByImePak", Paket.class).setParameter("imePak", imePaketa).getResultList();
        if(listaPaketa.isEmpty())
            return napraviTekstPoruka("Ne postoji paket sa unetim imenom!", -2);
        
        Paket paket = listaPaketa.get(0);
        
        List<Pretplata> listaPretplata = em.createNamedQuery("Pretplata.findAll", Pretplata.class).getResultList();
        boolean imaPretplataPosle = false;
        boolean preklapanjePretplata = false;
        if(listaPretplata != null){
            for (Pretplata pretplata : listaPretplata) {
                Calendar pretplataPre = Calendar.getInstance();
                Calendar pretplataPosle = Calendar.getInstance();
                pretplataPre.setTime(pretplata.getDatumPret());
                pretplataPosle.setTime(pretplata.getDatumPret());
                pretplataPosle.add(Calendar.MONTH, 1);
                if(pretplata.getIDKor().getIDKor().intValue() == kor.getIDKor().intValue() && datumCA.before(pretplataPre)){
                    imaPretplataPosle = true;
                    break;
                }
                
                if(preklapanjePretplata == false && pretplata.getIDKor().getIDKor().intValue() == kor.getIDKor().intValue() && pretplataPre.before(datumCA) && pretplataPosle.after(datumCA)){
                    preklapanjePretplata = true;
                }
            }
        }
        if(imaPretplataPosle)
            return napraviTekstPoruka("Postoji novija pretplata!", -3);
        if(preklapanjePretplata)
            return napraviTekstPoruka("Trenutna pretplata nije istekla!", -4);
        
        Pretplata pretplata = new Pretplata();
        pretplata.setDatumPret(datum);
        pretplata.setIDKor(kor);
        pretplata.setIDPak(paket);
        pretplata.setCena(paket.getCenaPak());
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            em.persist(pretplata);
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
        
        return napraviTekstPoruka("Kreirana pretplata za korisnika " + pretplata.getIDKor().getImeKor() + " na paket " +  pretplata.getIDPak().getImePak(), 0);
    }
    
    // -------------------------------  ZAHTEV #12  -------------------------------
    private static Message napraviGledanje(ZahtevSP zahtev) throws JMSException{
        String imeKor = (String)(zahtev.dohParametre().get(1));
        String imeSnimka = (String)(zahtev.dohParametre().get(2));
        String datumS = (String)(zahtev.dohParametre().get(3));
        String vremePocetkaS = (String)(zahtev.dohParametre().get(4));
        String vremeTrajanjaS = (String)(zahtev.dohParametre().get(5));
        int vremePocetka = Integer.parseInt(vremePocetkaS);
        int vremeTrajanja = Integer.parseInt(vremeTrajanjaS);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy,HH:mm:ss");
        Date datum = null;
        try {
            datum = sdf.parse(datumS);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<Korisnik3> listaKorisnika = em.createNamedQuery("Korisnik3.findByImeKor", Korisnik3.class).setParameter("imeKor", imeKor).getResultList();
        if(listaKorisnika.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa unetim imenom!", -1);
        
        Korisnik3 kor = listaKorisnika.get(0);
        
        List<Snimak3> listaSnimaka = em.createNamedQuery("Snimak3.findByImeSnim", Snimak3.class).setParameter("imeSnim", imeSnimka).getResultList();
        if(listaSnimaka.isEmpty())
            return napraviTekstPoruka("Ne postoji snimak sa unetim imenom!", -2);
        
        Snimak3 snimak = listaSnimaka.get(0);
        
        Gledanje gledanje = new Gledanje();
        gledanje.setDatumGled(datum);
        gledanje.setIDKor(kor);
        gledanje.setIDSnim(snimak);
        gledanje.setSekPoc(vremePocetka);
        gledanje.setSekOdgl(vremeTrajanja);
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            em.persist(gledanje);
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
            return napraviTekstPoruka("GRESKA!!!", -10);
        }
        
        return napraviTekstPoruka("Kreirano gledanje snimka " + gledanje.getIDSnim().getImeSnim() + " za korisnika " + gledanje.getIDKor().getImeKor(), 0);
    }
    
    // -------------------------------  ZAHTEV #13  -------------------------------
    private static Message napraviOcenu(ZahtevSP zahtev) throws JMSException{
        String imeKor = (String)(zahtev.dohParametre().get(1));
        String imeSnimka = (String)(zahtev.dohParametre().get(2));
        String ocenaS = (String)(zahtev.dohParametre().get(3));
        String datumS = (String)(zahtev.dohParametre().get(4));
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy,HH:mm:ss");
        Date datum = null;
        try {
            datum = sdf.parse(datumS);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<Korisnik3> listaKorisnika = em.createNamedQuery("Korisnik3.findByImeKor", Korisnik3.class).setParameter("imeKor", imeKor).getResultList();
        if(listaKorisnika.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa unetim imenom!", -1);
        
        Korisnik3 kor = listaKorisnika.get(0);
        
        List<Snimak3> listaSnimaka = em.createNamedQuery("Snimak3.findByImeSnim", Snimak3.class).setParameter("imeSnim", imeSnimka).getResultList();
        if(listaSnimaka.isEmpty())
            return napraviTekstPoruka("Ne postoji snimak sa unetim imenom!", -2);
        
        Snimak3 snimak = listaSnimaka.get(0);
        
        List<Ocena> listaOcena = em.createNamedQuery("Ocena.findAll", Ocena.class).getResultList();
        if(listaOcena != null){
            boolean pronadjen = false;
            for (Ocena ocena : listaOcena) {
                if(ocena.getIDSnim().getIDSnim().intValue() == snimak.getIDSnim().intValue() && ocena.getKorisnik3().getIDKor().intValue() == kor.getIDKor().intValue())
                    pronadjen = true;
            }
            if(pronadjen)
                return napraviTekstPoruka("Vec ste ocenili snimak! Mozete promeniti ocenu snimka, opcija 14!", -3);
        }
        
        Ocena ocena = new Ocena();
        ocena.setKorisnik3(kor);
        ocena.setIDSnim(snimak);
        ocena.setDatumOcene(datum);
        int cena = Integer.parseInt(ocenaS);
        ocena.setOcena(cena);
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            em.persist(ocena);
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
        
        return napraviTekstPoruka("Snimak: " + ocena.getIDSnim().getImeSnim() + "  korisnik: " + ocena.getKorisnik3().getImeKor() + "  ocena: " + ocena.getOcena(), 0);
    }
    
    // -------------------------------  ZAHTEV #14  -------------------------------
    private static Message promeniOcene(ZahtevSP zahtev) throws JMSException{
        String imeKor = (String)(zahtev.dohParametre().get(1));
        String imeSnimka = (String)(zahtev.dohParametre().get(2));
        String ocenaS = (String)(zahtev.dohParametre().get(3));
        int ocenaI = Integer.parseInt(ocenaS);
        
        List<Korisnik3> listaKorisnika = em.createNamedQuery("Korisnik3.findByImeKor", Korisnik3.class).setParameter("imeKor", imeKor).getResultList();
        if(listaKorisnika.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa unetim imenom!", -1);
        
        Korisnik3 kor = listaKorisnika.get(0);
        
        List<Snimak3> listaSnimaka = em.createNamedQuery("Snimak3.findByImeSnim", Snimak3.class).setParameter("imeSnim", imeSnimka).getResultList();
        if(listaSnimaka.isEmpty())
            return napraviTekstPoruka("Ne postoji snimak sa unetim imenom!", -2);
        
        Snimak3 snimak = listaSnimaka.get(0);
        
        Ocena ocenaZaMenjanje = null;
        boolean pronadjen = false;
        List<Ocena> listaOcena = em.createNamedQuery("Ocena.findAll", Ocena.class).getResultList();
        
        if(listaOcena != null){
            for (Ocena ocena : listaOcena)
                if(ocena.getIDSnim().getIDSnim().intValue() == snimak.getIDSnim().intValue() && ocena.getKorisnik3().getIDKor().intValue() == kor.getIDKor().intValue()){
                    ocenaZaMenjanje = ocena;
                    pronadjen = true;
                    break;
                }
        }
        if(pronadjen == false || ocenaZaMenjanje == null){
            return napraviTekstPoruka("Uneti korisnik nije ocenio dati snimak!", -3);
        }
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            ocenaZaMenjanje.setOcena(ocenaI);
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
        
        return napraviTekstPoruka("Uspesno promenjena ocena korisnika " + ocenaZaMenjanje.getKorisnik3().getImeKor() + " za snimak " + ocenaZaMenjanje.getIDSnim().getImeSnim() + "!", 0);
    }
    
    // -------------------------------  ZAHTEV #15  -------------------------------
    private static Message brisanjeOcene(ZahtevSP zahtev) throws JMSException{
        String imeKor = (String)(zahtev.dohParametre().get(1));
        String imeSnimka = (String)(zahtev.dohParametre().get(2));
        List<Korisnik3> listaKorisnika = em.createNamedQuery("Korisnik3.findByImeKor", Korisnik3.class).setParameter("imeKor", imeKor).getResultList();
        if(listaKorisnika.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa unetim imenom!", -1);
        
        Korisnik3 kor = listaKorisnika.get(0);
        
        List<Snimak3> listaSnimaka = em.createNamedQuery("Snimak3.findByImeSnim", Snimak3.class).setParameter("imeSnim", imeSnimka).getResultList();
        if(listaSnimaka.isEmpty())
            return napraviTekstPoruka("Ne postoji snimak sa unetim imenom!", -2);
        
        Snimak3 snimak = listaSnimaka.get(0);
        
        Ocena ocenaZaBrisanje = null;
        boolean pronadjen = false;
        List<Ocena> listaOcena = em.createNamedQuery("Ocena.findAll", Ocena.class).getResultList();
        
        if(listaOcena != null){
            for (Ocena ocena : listaOcena)
                if(ocena.getIDSnim().getIDSnim().intValue() == snimak.getIDSnim().intValue() && ocena.getKorisnik3().getIDKor().intValue() == kor.getIDKor().intValue()){
                    ocenaZaBrisanje = ocena;
                    pronadjen = true;
                    break;
                }
        }
        if(pronadjen == false || ocenaZaBrisanje == null){
            return napraviTekstPoruka("Uneti korisnik nije ocenio dati snimak!", -3);
        }
        
        boolean greskaPriTrans = false;
        try{
            em.getTransaction().begin();
            em.remove(ocenaZaBrisanje);
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
            return napraviTekstPoruka("GRESKA!!!", -10);
        }
        
        return napraviTekstPoruka("Uspesno obrisana ocena korisnika " + kor.getImeKor() + " za snimak " + snimak.getImeSnim() + "!", 0);
        
        
    }
    
    // -------------------------------  ZAHTEV #22  -------------------------------
    private static Message dohvatiPakete(ZahtevSP zahtev) throws JMSException{        
        List<Paket> listaPaketa = em.createNamedQuery("Paket.findAllSorted", Paket.class).getResultList();
        
        return napraviObjectPoruka(0, (Serializable)listaPaketa);
    }
    
    // -------------------------------  ZAHTEV #23  -------------------------------
    private static Message dohvatiPretplateKorisnika(ZahtevSP zahtev) throws JMSException{
        String imeKor = (String)(zahtev.dohParametre().get(1));
        
        List<Korisnik3> listaKorisnika = em.createNamedQuery("Korisnik3.findByImeKor", Korisnik3.class).setParameter("imeKor", imeKor).getResultList();
        if(listaKorisnika.isEmpty())
            return napraviTekstPoruka("Ne postoji korisnik sa unetim imenom!", -1);
        
        Korisnik3 korisnik = listaKorisnika.get(0);
        List<Pretplata> niz = em.createNamedQuery("Pretplata.findAll", Pretplata.class).getResultList();
        List<Pretplata> nizPretplate = new ArrayList();
        for (Pretplata pretplataPro : niz) {
            if(pretplataPro.getIDKor().getIDKor().intValue() == korisnik.getIDKor().intValue())
                nizPretplate.add(pretplataPro);
        }
        
        return napraviObjectPoruka(0, (Serializable)nizPretplate);
    }
    
    // -------------------------------  ZAHTEV #24  -------------------------------
    private static Message dohvatiGledanjeZaSnimak(ZahtevSP zahtev) throws JMSException{
        String imeSnimka = (String)(zahtev.dohParametre().get(1));
        
        List<Snimak3> listaSnimaka = em.createNamedQuery("Snimak3.findByImeSnim", Snimak3.class).setParameter("imeSnim", imeSnimka).getResultList();
        if(listaSnimaka.isEmpty())
            return napraviTekstPoruka("Ne postoji snimak sa unetim imenom!", -2);
        
        Snimak3 snimak = listaSnimaka.get(0);
        List<Gledanje> niz = em.createNamedQuery("Gledanje.findAll", Gledanje.class).getResultList();
        List<Gledanje> nizGledanje = new ArrayList();
        for (Gledanje gledanjePro : niz) {
            if(gledanjePro.getIDSnim().getIDSnim().intValue() == snimak.getIDSnim().intValue())
                nizGledanje.add(gledanjePro);
        }
        
        return napraviObjectPoruka(0, (Serializable)nizGledanje);
    }
    
    // -------------------------------  ZAHTEV #25  -------------------------------
    private static Message dohvatiOceneZaSnimak(ZahtevSP zahtev) throws JMSException{
        String imeSnimka = (String)(zahtev.dohParametre().get(1));
        
        List<Snimak3> listaSnimaka = em.createNamedQuery("Snimak3.findByImeSnim", Snimak3.class).setParameter("imeSnim", imeSnimka).getResultList();
        if(listaSnimaka.isEmpty())
            return napraviTekstPoruka("Ne postoji snimak sa unetim imenom!", -2);
        
        Snimak3 snimak = listaSnimaka.get(0);
        List<Ocena> niz = em.createNamedQuery("Ocena.findAll", Ocena.class).getResultList();
        List<Ocena> nizOcena = new ArrayList();
        for (Ocena ocenaPro : niz) {
            if(ocenaPro.getIDSnim().getIDSnim().intValue() == snimak.getIDSnim().intValue())
                nizOcena.add(ocenaPro);
        }
        
        return napraviObjectPoruka(0, (Serializable)nizOcena);
    }
    
    
    
    
    
    // -------------------------------  ZAHTEV #100  -------------------------------
    private static Message dodavanjeKorisnika(ZahtevSP zahtev) throws JMSException{
        String idKorS = (String)(zahtev.dohParametre().get(1));
        String imeKor = (String)(zahtev.dohParametre().get(2));
        int idKor = Integer.parseInt(idKorS);
        
        Korisnik3 korisnik = new Korisnik3(idKor, imeKor);
        
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
            TextMessage poruka = context.createTextMessage("GRESKA!!! Pri kreiranju korisnik_3: " + korisnik.getImeKor());
            poruka.setIntProperty("result", -10);
            poruka.setIntProperty("podsistem", ID_PODSISTEM1);
            return poruka;
        }
        
        TextMessage poruka = context.createTextMessage("Napravljen korisnik_3: " + korisnik.getImeKor());
        poruka.setIntProperty("result", 0);
        poruka.setIntProperty("podsistem", ID_PODSISTEM1);
        return poruka;
    }
    
    // -------------------------------  ZAHTEV #101  -------------------------------
    private static Message dodavanjeSnimka(ZahtevSP zahtev) throws JMSException{
        String idSnimkaS = (String)(zahtev.dohParametre().get(1));
        String imeSnimka = (String)(zahtev.dohParametre().get(2));
        int idSnimka = Integer.parseInt(idSnimkaS);
        
        Snimak3 snimak = new Snimak3(idSnimka, imeSnimka);
        
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
            TextMessage poruka = context.createTextMessage("GRESKA!!! Pri kreiranju snimak_3: " + snimak.getImeSnim());
            poruka.setIntProperty("result", -10);
            poruka.setIntProperty("podsistem", ID_PODSISTEM2);
            return poruka;
        }
        
        TextMessage poruka = context.createTextMessage("Napravljen snimak_3: " + snimak.getImeSnim());
        poruka.setIntProperty("result", 0);
        poruka.setIntProperty("podsistem", ID_PODSISTEM2);
        return poruka;
    }
    
    // -------------------------------  ZAHTEV #102  -------------------------------
    private static Message promeniNazivSnimka(ZahtevSP zahtev) throws JMSException{
        String imeSnimka = (String)(zahtev.dohParametre().get(1));
        String novoImeSnimka = (String)(zahtev.dohParametre().get(2));
        List<Snimak3> listaSnimaka = em.createNamedQuery("Snimak3.findByImeSnim", Snimak3.class).setParameter("imeSnim", imeSnimka).getResultList();
        Snimak3 snimak = listaSnimaka.get(0);
        
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
            TextMessage poruka = context.createTextMessage("GRESKA!!! Pri promeni imena snimka korisnika je " + snimak.getImeSnim());
            poruka.setIntProperty("result", -10);
            poruka.setIntProperty("podsistem", ID_PODSISTEM2);
            return poruka;
        }
        
        TextMessage poruka = context.createTextMessage("Nova ime snimka korisnika je " + snimak.getImeSnim());
        poruka.setIntProperty("result", 0);
        poruka.setIntProperty("podsistem", ID_PODSISTEM2);
        return poruka;
    }
    
    // -------------------------------  ZAHTEV #103  -------------------------------
    private static Message brisanjeSnimka(ZahtevSP zahtev) throws JMSException{
        String imeSnimka = (String)(zahtev.dohParametre().get(1));
        List<Snimak3> listaSnimaka = em.createNamedQuery("Snimak3.findByImeSnim", Snimak3.class).setParameter("imeSnim", imeSnimka).getResultList();
        Snimak3 snimak = listaSnimaka.get(0);
        
        List<Ocena> nizSveOcene = em.createNamedQuery("Ocena.findAll", Ocena.class).getResultList();
        List<Ocena> listaOcena = new ArrayList();
        for (Ocena ocenaPro : nizSveOcene) {
            if(ocenaPro.getIDSnim().getIDSnim().intValue() == snimak.getIDSnim().intValue())
                listaOcena.add(ocenaPro);
        }
        if(listaOcena != null){
            for(int i = 0; i < listaOcena.size(); i++){
                try{
                    em.getTransaction().begin();
                    em.remove(listaOcena.get(i));
                    em.flush();
                    em.getTransaction().commit();
                }
                finally{
                    if(em.getTransaction().isActive())
                        em.getTransaction().rollback();
                }
            }
        }
        
        List<Gledanje> nizSvaGledanja = em.createNamedQuery("Gledanje.findAll", Gledanje.class).getResultList();
        List<Gledanje> listaGledanja = new ArrayList();
        for (Gledanje gledanjePro : nizSvaGledanja) {
            if(gledanjePro.getIDSnim().getIDSnim().intValue() == snimak.getIDSnim().intValue())
                listaGledanja.add(gledanjePro);
        }
        if(listaGledanja != null){
            for(int i = 0; i < listaGledanja.size(); i++){
                try{
                    em.getTransaction().begin();
                    em.remove(listaGledanja.get(i));
                    em.flush();
                    em.getTransaction().commit();
                }
                finally{
                    if(em.getTransaction().isActive())
                        em.getTransaction().rollback();
                }
            }
        }
        
        boolean greskaPriTrans = false;
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
            TextMessage poruka = context.createTextMessage("GRESKA!!! Pri brisanju snimak " + imeSnimka);
            poruka.setIntProperty("result", -11);
            poruka.setIntProperty("podsistem", ID_PODSISTEM2);
            return poruka;
        }
        
        TextMessage poruka = context.createTextMessage("Obrisan snimak " + imeSnimka);
        poruka.setIntProperty("result", 0);
        poruka.setIntProperty("podsistem", ID_PODSISTEM2);
        return poruka;
    }
    
    
    public static void main(String[] args) {
        if(setup == false){
            setUpJMS();
            setup = true;
        }
        System.out.println("PODSISTEM3 aktivan...");System.out.println("");
        while(true){
            ObjectMessage om = (ObjectMessage) consumer.receive();
            Message porukaNazad = context.createTextMessage("Prazna poruka");
            int tipZahteva = -1;
            try {
                ZahtevSP zahtev = (ZahtevSP)om.getObject();
                tipZahteva = zahtev.dohTipZahteva();
                System.out.println("Primljen zahtev: " + tipZahteva);
                switch(tipZahteva){
                    case 9:
                        porukaNazad = napraviPaket(zahtev);
                        break;
                    case 10:
                        porukaNazad = promeniCenuPaketa(zahtev);
                        break;
                    case 11:
                        porukaNazad = napraviPretplatu(zahtev);
                        break;
                    case 12:
                        porukaNazad = napraviGledanje(zahtev);
                        break;
                    case 13:
                        porukaNazad = napraviOcenu(zahtev);
                        break;
                    case 14:
                        porukaNazad = promeniOcene(zahtev);
                        break;
                    case 15:
                        porukaNazad = brisanjeOcene(zahtev);
                        break;
                    case 22:
                        porukaNazad = dohvatiPakete(zahtev);
                        break;
                    case 23:
                        porukaNazad = dohvatiPretplateKorisnika(zahtev);
                        break;
                    case 24:
                        porukaNazad = dohvatiGledanjeZaSnimak(zahtev);
                        break;
                    case 25:
                        porukaNazad = dohvatiOceneZaSnimak(zahtev);
                        break;
                    case 100:
                        porukaNazad = dodavanjeKorisnika(zahtev);
                        break;
                    case 101:
                        porukaNazad = dodavanjeSnimka(zahtev);
                        break;
                    case 102:
                        porukaNazad = promeniNazivSnimka(zahtev);
                        break;
                    case 103:
                        porukaNazad = brisanjeSnimka(zahtev);
                        break;
                }
                
                
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(tipZahteva != 100 && tipZahteva != 101 && tipZahteva != 102 && tipZahteva != 103)
                producer.send(queue, porukaNazad);
            else
                producer.send(topic, porukaNazad);
            System.out.println("Podsistem3 zavrsio obradu");System.out.println("");
        }
    }
    
    private static void setUpJMS(){
        context = connectionFactory.createContext();
        context.setClientID("podsistem3");
        consumer = context.createDurableConsumer(topic, "sub3", "podsistem=3", false);
        producer = context.createProducer();
    }
}
