/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import zahtevi.ZahtevSP;
import entities.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;

@Path("serverR")
public class ServerR {
    @Resource(lookup="connFac")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup="resTopic")
    private Topic topic;
    
    @Resource(lookup="resQueue")
    private Queue queue;
    
    private static int ID_PODSISTEM1 = 1;
    private static int ID_PODSISTEM2 = 2;
    private static int ID_PODSISTEM3 = 3;
    
    private static ZahtevSP napraviZahtev(int tipZahtev, ArrayList<String> lista){
        ZahtevSP zahtev = new ZahtevSP();
        zahtev.dodajParametar(tipZahtev);
        if(lista != null){
            for (String parametar : lista)
                zahtev.dodajParametar(parametar);
        }
        return zahtev;
    }
    
    private static String pakovanjePoruke(Object vraceniObjekat){
        if(vraceniObjekat == null)
            return "";
        List<Object> listaVracenihObjekata = (List<Object>)vraceniObjekat;
        StringBuilder sb = new StringBuilder();
        for (Object o : listaVracenihObjekata) {
            sb.append(o.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private Response izvrsenjeZahteva(JMSContext context, JMSProducer producer, JMSConsumer consumer, int idPodsistema, ZahtevSP zahtev) throws JMSException{
        ObjectMessage porukaSaZahtevom = context.createObjectMessage(zahtev);
        porukaSaZahtevom.setIntProperty("podsistem", idPodsistema);
        
        producer.send(topic, porukaSaZahtevom);
            
        Message porukaOsn = consumer.receive();
        int rezultatIzv = porukaOsn.getIntProperty("result");
        
        if(porukaOsn instanceof TextMessage){
            TextMessage poruka = (TextMessage)porukaOsn;

            if(rezultatIzv == 0) return Response.status(Response.Status.OK).entity(poruka.getText()).build();
            else return Response.status(Response.Status.OK).entity("GRESKA: " + poruka.getText()).build();
        }
        else { //ObjectMessage
            ObjectMessage poruka = (ObjectMessage)porukaOsn;
            String pakPoruka = pakovanjePoruke(poruka.getObject());
            
            if(rezultatIzv == 0) return Response.status(Response.Status.OK).entity(pakPoruka).build();
            else return Response.status(Response.Status.OK).entity("GRESKA").build();
        }
    }
    
    @POST
    @Path("op1_KreiranjeMesta/{naziv}")
    public Response napraviMesto(@PathParam("naziv") String naziv){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(naziv);
            ZahtevSP zahtev = napraviZahtev(1, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM1, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op2_KreiranjeKorisnika/{ime}/{email}/{godiste}/{pol}/{nazivMesta}")
    public Response napraviKorisnika(@PathParam("ime") String ime, @PathParam("email") String email, @PathParam("godiste") String godiste , @PathParam("pol") String pol, @PathParam("nazivMesta") String nazivMesta) {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(ime); lista.add(email); lista.add(godiste); lista.add(pol); lista.add(nazivMesta);
            ZahtevSP zahtev = napraviZahtev(2, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM1, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op3_PromenaEmailAdrKor/{imeKor}/{email}")
    public Response promeniEmailAdrKor(@PathParam("imeKor") String ime, @PathParam("email") String email){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(ime); lista.add(email);
            ZahtevSP zahtev = napraviZahtev(3, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM1, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op4_PromenaMestoKor/{imeKor}/{imeMesta}")
    public Response promeniMestoKorisnika(@PathParam("imeKor") String ime, @PathParam("imeMesta") String imeMesta){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(ime); lista.add(imeMesta);
            ZahtevSP zahtev = napraviZahtev(4, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM1, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op5_KreiranjeKategorije/{nazivKat}")
    public Response napraviKategoriju(@PathParam("nazivKat") String nazivKat){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(nazivKat);
            ZahtevSP zahtev = napraviZahtev(5, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM2, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op6_KreiranjeVideoSnimka/{imeSnimka}/{trajanje}/{imeVlasnika}/{datumSnimanja}")
    public Response napraviVideoSnimak(@PathParam("imeSnimka") String imeSnimka, @PathParam("trajanje") String trajanje, @PathParam("imeVlasnika") String imeVlasnika, @PathParam("datumSnimanja") String datumSnimanja){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeSnimka); lista.add(trajanje); lista.add(imeVlasnika); lista.add(datumSnimanja);
            ZahtevSP zahtev = napraviZahtev(6, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM2, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op7_PromenaImenaSnimka/{imeSnimka}/{imeVlasnika}/{novoImeSnimka}")
    public Response promeniImeSnimka(@PathParam("imeSnimka") String imeSnimka, @PathParam("imeVlasnika") String imeVlasnika, @PathParam("novoImeSnimka") String novoImeSnimka){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeSnimka); lista.add(imeVlasnika); lista.add(novoImeSnimka);
            ZahtevSP zahtev = napraviZahtev(7, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM2, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op8_DodavanjeKategorije/{imeSnimka}/{imeVla}/{imeKategorije}")
    public Response napraviSnimak_Kategorija(@PathParam("imeSnimka") String imeSnimka, @PathParam("imeVla") String imeVla, @PathParam("imeKategorije") String imeKategorije){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeSnimka); lista.add(imeVla); lista.add(imeKategorije);
            ZahtevSP zahtev = napraviZahtev(8, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM2, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op9_KreiranjePaketa/{naziv}/{cena}")
    public Response napraviPaket(@PathParam("naziv") String naziv, @PathParam("cena") String cena){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(naziv); lista.add(cena);
            ZahtevSP zahtev = napraviZahtev(9, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM3, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op10_PromenaCenePaketa/{naziv}/{cena}")
    public Response promeniCenuPaketa(@PathParam("naziv") String ime, @PathParam("cena") String cena){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(ime); lista.add(cena);
            ZahtevSP zahtev = napraviZahtev(10, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM3, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op11_KreiranjePretplate/{imeKor}/{imePak}/{datum}")
    public Response napraviKategoriju(@PathParam("imeKor") String imeKor, @PathParam("imePak") String imePak, @PathParam("datum") String datum){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeKor); lista.add(imePak); lista.add(datum);
            ZahtevSP zahtev = napraviZahtev(11, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM3, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op12_KreiranjeGledanja/{imeKor}/{imeSnimka}/{datum}/{vremePocetka}/{vremeTrajanja}")
    public Response napraviOcenu(@PathParam("imeKor") String imeKor, @PathParam("imeSnimka") String imeSnimka, @PathParam("datum") String datum, @PathParam("vremePocetka") String vremePocetka, @PathParam("vremeTrajanja") String vremeTrajanja){
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeKor); lista.add(imeSnimka); lista.add(datum); lista.add(vremePocetka); lista.add(vremeTrajanja);
            ZahtevSP zahtev = napraviZahtev(12, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM3, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op13_KreiranjeOcene/{imeKor}/{imeSnimka}/{ocena}/{datum}")
    public Response napraviOcenu(@PathParam("imeKor") String imeKor, @PathParam("imeSnimka") String imeSnimka, @PathParam("ocena") String ocena, @PathParam("datum") String datum) {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeKor); lista.add(imeSnimka); lista.add(ocena); lista.add(datum);
            ZahtevSP zahtev = napraviZahtev(13, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM3, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @POST
    @Path("op14_PromenaOcene/{imeKor}/{imeSnimka}/{ocena}")
    public Response napraviOcenu(@PathParam("imeKor") String imeKor, @PathParam("imeSnimka") String imeSnimka, @PathParam("ocena") String ocena) {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeKor); lista.add(imeSnimka); lista.add(ocena);
            ZahtevSP zahtev = napraviZahtev(14, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM3, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @DELETE
    @Path("op15_BrisanjeOcene/{imeKor}/{imeSnimka}")
    public Response brisanjeOcene(@PathParam("imeKor") String imeKor, @PathParam("imeSnimka") String imeSnimka) {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeKor); lista.add(imeSnimka);
            ZahtevSP zahtev = napraviZahtev(15, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM3, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @DELETE
    @Path("op16_BrisanjeSnimka/{imeSnimka}/{imeVla}")
    public Response brisanjeSnimka(@PathParam("imeSnimka") String imeSnimka, @PathParam("imeVla") String imeVla) {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeSnimka); lista.add(imeVla);
            ZahtevSP zahtev = napraviZahtev(16, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM2, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @GET
    @Path("op17_DohvatanjeMesta")
    public Response dohvatiMesta() {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ZahtevSP zahtev = napraviZahtev(17, null);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM1, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @GET
    @Path("op18_DohvatanjeKorisnika")
    public Response dohvatiKorisnike() {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ZahtevSP zahtev = napraviZahtev(18, null);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM1, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @GET
    @Path("op19_DohvatanjeKategorija")
    public Response dohvatiKategorije() {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ZahtevSP zahtev = napraviZahtev(19, null);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM2, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @GET
    @Path("op20_DohvatanjeVideoSnimaka")
    public Response dohvatiVideoSnimke() {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ZahtevSP zahtev = napraviZahtev(20, null);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM2, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @GET
    @Path("op21_DohvatanjeKategorijaZaSnimak/{imeSnimka}")
    public Response dohvatiKategorijeVideoSnimka(@PathParam("imeSnimka") String imeSnimka) {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeSnimka);
            ZahtevSP zahtev = napraviZahtev(21, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM2, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @GET
    @Path("op22_DohvatanjePaketa")
    public Response dohvatiPakete() {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ZahtevSP zahtev = napraviZahtev(22, null);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM3, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @GET
    @Path("op23_DohvatanjePretplataKorisnika/{imeKorisnika}")
    public Response dohvatiPakete(@PathParam("imeKorisnika") String imeKor) {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeKor);
            ZahtevSP zahtev = napraviZahtev(23, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM3, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @GET
    @Path("op24_DohvatanjeGledanjaSnimka/{imeSnimka}")
    public Response dohvatiGledanjeSnimka(@PathParam("imeSnimka") String imeSnimka) {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeSnimka);
            ZahtevSP zahtev = napraviZahtev(24, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM3, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
    
    @GET
    @Path("op25_DohvatanjeOcenaSnimka/{imeSnimka}")
    public Response dohvatiOceneSnimka(@PathParam("imeSnimka") String imeSnimka) {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            ArrayList<String> lista = new ArrayList<String>(); lista.add(imeSnimka);
            ZahtevSP zahtev = napraviZahtev(25, lista);
            
            Response r = izvrsenjeZahteva(context, producer, consumer, ID_PODSISTEM3, zahtev);
            if(context != null){
                consumer.close();
                context.close();
            }
            return r;
        } catch (JMSException ex) {
            Logger.getLogger(ServerR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("GRESKA").build();
    }
}

/*
User/Pass za ucitavanje baze
user: root
pass: 123

--------------------------------------------------------------
-JMS connection factory
name: connFac
resource type: javax.jms.ConnectionFactory

-JMS destination resources
Queue
name: resQueue
physical destination name: resQueue
type:javax.jms.Queue

Topic
name: resTopic
physycal destination name: resTopic
type: javax.jms.Topic

--------------------------------------------------------------
-JDBC connection pools (ping!!!)
poolName: proPool
resourceType: javax.sql.DataSource
Database Driver Vendor: MySql
datasourceClassname: com.mysql.cj.jdbc.MysqlDataSource

addition properties:
	user: root (mozda drugacije!)
	password: 123 (mozda drugacije!)
	databaseName: podsistem1
	serverName: localhost
	portNumber: 3306
	useSSL: false
	allowPublicKeyRetrieval: true (mozda ne treba!)

-JDBC resources
jndi name: proResource
poolname: proPool

--------------------------------------------------------------
Nazivi persitentnih jedinica: 
PS1: PS1PU
PS2: PS22PU
PS3: PS33PU
Server: my_persistence_unit

--------------------------------------------------------------
PROMENE U PODSISTEMU!!!
1) glassfish
2) mysql-connector-java-8.0.31.jar i javaee-api-8.0.jar(Java EE 8 API Library)
	Za PS1, PS2, PS3
3) promeniti u persitentnoj jedinici password 1234->123!!!

--------------------------------------------------------------
PROVERITI ZA SERVER
Server->Properties->Run->Server => (Glassfish -> No Server -> Glassfish)

--------------------------------------------------------------
NE TREBA:
potencijalna promena jdk na jdk8

__ANT aplikacije__  =>  properties->libraries

__Maven__           =>  properties->build->compile
*/