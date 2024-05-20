/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Klijent {
    static Scanner x = null;
    
    public static boolean proveraDatuma(String datum){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy,HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(datum); //datum.trim()
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static void pravljenjeZahteva() throws IOException{
        if(x == null)
            x = new Scanner(System.in);
        ispisIzboraFunkcija();
        System.out.println();
        Boolean radi = true;
        while(radi){
            System.out.println(" Izaberite opciju: ");
            int idOperacije = x.nextInt();
            x.nextLine();
            switch(idOperacije){
                case 1:
                    System.out.println("KREIRANJE MESTA");
                    System.out.println("===============");
                    System.out.println("Unesite ime mesta: ");
                    String nazivMesta11 = x.nextLine();
                    String nazivMesta1 = nazivMesta11.replaceAll(" ", "%20");
                    op1_KreiranjeMesta(nazivMesta1);
                    break;
                case 2:
                    System.out.println("KREIRANJE KORISNIKA");
                    System.out.println("===================");
                    System.out.println("Unesite ime: ");
                    String ime2 = x.nextLine();
                    System.out.println("Unesite email adresu: ");
                    String email2 = x.nextLine();
                    System.out.println("Unesite godinu rodjenja: ");
                    String godiste = x.nextLine();
                    System.out.println("Pol(M/Z): ");
                    String pol = null;
                    while(true){
                        pol = x.nextLine();
                        if(pol.equals("M") || pol.equals("m") || pol.equals("Z") || pol.equals("z"))
                            break;
                        System.out.println("Greska: niste uneli ispravan pol, unesite ponovo");
                    }
                    System.out.println("Unesite ime mesta: ");
                    String nazivMesta22 = x.nextLine();
                    String nazivMesta2 = nazivMesta22.replaceAll(" ", "%20");
                    op2_KreiranjeKorisnika(ime2, email2, godiste, pol, nazivMesta2);
                    break;
                case 3:
                    System.out.println("PROMENA EMAIL ADRESE ZA KORISNIKA");
                    System.out.println("=================================");
                    System.out.println("Unesite ime korisnika: ");
                    String ime3 = x.nextLine();
                    System.out.println("Unesite novu email adresu: ");
                    String email3 = x.nextLine();
                    op3_PromenaEmailAdrKor(ime3, email3);
                    break;
                case 4:
                    System.out.println("PROMENA MESTA ZA KORISNIKA");
                    System.out.println("==========================");
                    System.out.println("Unesite ime korisnika: ");
                    String ime4 = x.nextLine();
                    System.out.println("Unesite novo mesto: ");
                    String nazivMesta44 = x.nextLine();
                    String nazivMesta4 = nazivMesta44.replaceAll(" ", "%20");
                    op4_PromenaMestoKor(ime4, nazivMesta4);
                    break;
                case 5:
                    System.out.println("KREIRANJE KATEGORIJE");
                    System.out.println("====================");
                    System.out.println("Unesite ime kategorije: ");
                    String nazivKat55 = x.nextLine();
                    String nazivKat5 = nazivKat55.replaceAll(" ", "%20");
                    op5_KreiranjeKategorije(nazivKat5);
                    break;
                case 6:
                    System.out.println("KREIRANJE VIDEO SNIMKA");
                    System.out.println("======================");
                    System.out.println("Unesite ime video snimka: ");
                    String imeSnimka66 = x.nextLine();
                    String imeSnimka6 = imeSnimka66.replaceAll(" ", "%20");
                    System.out.println("Unesite ime vlasnika video snimka: ");
                    String imeVlasnika6 = x.nextLine();
                    System.out.println("Unesite trajanje video snimka: ");
                    String trajanje6 = x.nextLine();
                    
                    String datum_vreme_6 = null;
                    while(true){
                        System.out.println("Unesite datum postavljanja(format dd.mm.yyyy): ");
                        String datum6 = x.nextLine();
                        System.out.println("Unesite vreme postavljanja(format hh:mm:ss): ");
                        String vreme6 = x.nextLine();
                        datum_vreme_6 = datum6 + "," + vreme6;
                        if(proveraDatuma(datum_vreme_6))
                                break;
                            System.out.println("GRESKA: Niste dobro uneli datum i vreme!");
                    }
                    
                    op6_KreiranjeVideoSnimka(imeSnimka6, imeVlasnika6, trajanje6, datum_vreme_6);
                    break;
                case 7:
                    System.out.println("PROMENA IMENA VIDEO SNIMKA");
                    System.out.println("===========================");
                    System.out.println("Unesite ime video snimka: ");
                    String imeSnimka77 = x.nextLine();
                    String imeSnimka7 = imeSnimka77.replaceAll(" ", "%20");
                    System.out.println("Unesite ime vlasnika video snimka: ");
                    String imeVlasnika7 = x.nextLine();
                    System.out.println("Unesite novo ime video snimka: ");
                    String novoImeSnimka77 = x.nextLine();
                    String novoImeSnimka7 = novoImeSnimka77.replaceAll(" ", "%20");
                    op7_PromenaImenaSnimka(imeSnimka7, imeVlasnika7, novoImeSnimka7);
                    break;
                case 8:
                    System.out.println("DODAVANJE KATEGORIJE VIDEO SNIMKU");
                    System.out.println("=================================");
                    System.out.println("Unesite ime video snimka: ");
                    String imeSnimka88 = x.nextLine();
                    String imeSnimka8 = imeSnimka88.replaceAll(" ", "%20");
                    System.out.println("Unesite ime vlasnika video snimka: ");
                    String imeVlasnika8 = x.nextLine();
                    System.out.println("Unesite kategoriju: ");
                    String imeKategorije88 = x.nextLine();
                    String imeKategorije8 = imeKategorije88.replaceAll(" ", "%20");
                    op8_DodavanjeKategorije(imeSnimka8, imeVlasnika8, imeKategorije8);
                    break;
                case 9:
                    System.out.println("KREIRANJE PAKETA");
                    System.out.println("================");
                    System.out.println("Unesite ime paketa: ");
                    String imePaketa99 = x.nextLine();
                    String imePaketa9 = imePaketa99.replaceAll(" ", "%20");
                    System.out.println("Unesite cenu paketa: ");
                    String cenaPaketa9 = x.nextLine();
                    op9_KreiranjePaketa(imePaketa9, cenaPaketa9);
                    break;    
                case 10:
                    System.out.println("PROMENA MESECNE CENE ZA PAKET");
                    System.out.println("=============================");
                    System.out.println("Unesite ime paketa: ");
                    String imePaketa1010 = x.nextLine();
                    String imePaketa10 = imePaketa1010.replaceAll(" ", "%20");
                    System.out.println("Unesite cenu paketa: ");
                    String cenaPaketa10 = x.nextLine();
                    op10_PromenaCenePaketa(imePaketa10, cenaPaketa10);
                    break;
                case 11:
                    System.out.println("KREIRANJE PRETPLATE KORISNIKA NA PAKET");
                    System.out.println("======================================");
                    System.out.println("Unesite ime korisnika: ");
                    String imeKorisnika11 = x.nextLine();
                    System.out.println("Unesite ime paketa: ");
                    String imePaketa1111 = x.nextLine();
                    String imePaketa11 = imePaketa1111.replaceAll(" ", "%20");
                    
                    String datum_vreme_11 = null;
                    while(true){
                        System.out.println("Unesite datum kreiranja pretplate(format dd.mm.yyyy): ");
                        String datum11 = x.nextLine();
                        System.out.println("Unesite vreme kreiranja pretplate(format hh:mm:ss): ");
                        String vreme11 = x.nextLine();
                        datum_vreme_11 = datum11 + "," + vreme11;
                        if(proveraDatuma(datum_vreme_11))
                            break;
                        System.out.println("GRESKA: Niste dobro uneli datum i vreme!");
                    }
                    
                    op11_KreiranjePretplate(imeKorisnika11, imePaketa11, datum_vreme_11);
                    break;
                case 12:
                    System.out.println("KREIRANJE GLEDANJA VIDEO SNIMKA OD STRANE KORISNIKA");
                    System.out.println("===================================================");
                    System.out.println("Unesite ime korisnika: ");
                    String imeKorisnika12 = x.nextLine();
                    System.out.println("Unesite ime video snimka: ");
                    String imeSnimka1212 = x.nextLine();
                    String imeSnimka12 = imeSnimka1212.replaceAll(" ", "%20");
                    
                    String datum_vreme_12 = null;
                    while(true){
                        System.out.println("Unesite datum gledanja snimka(format dd.mm.yyyy): ");
                        String datum12 = x.nextLine();
                        System.out.println("Unesite vreme gledanja snimka(format hh:mm:ss): ");
                        String vreme12 = x.nextLine();
                        datum_vreme_12 = datum12 + "," + vreme12;
                        if(proveraDatuma(datum_vreme_12))
                            break;
                        System.out.println("GRESKA: Niste dobro uneli datum i vreme!");
                    }
                    
                    System.out.println("Unesite vreme pocetka gledanja(u sekundama): ");
                    String vremePocetka12 = x.nextLine();
                    System.out.println("Unesite vreme koliko je odgledano(u sekundama): ");
                    String vremeGledanja12 = x.nextLine();
                    op12_KreiranjeGledanja(imeKorisnika12, imeSnimka12, datum_vreme_12, vremePocetka12, vremeGledanja12);
                    break;
                case 13:
                    System.out.println("KREIRANJE OCENE KORISNIKA ZA VIDEO SNIMAK");
                    System.out.println("=========================================");
                    System.out.println("Unesite ime korisnika: ");
                    String imeKorisnika13 = x.nextLine();
                    System.out.println("Unesite ime video snimka: ");
                    String imeSnimka1313 = x.nextLine();
                    String imeSnimka13 = imeSnimka1313.replaceAll(" ", "%20");
                    System.out.println("Unesite ocenu video snimka: ");
                    String ocenaSnimka13 = null;
                    while(true){
                        ocenaSnimka13 = x.nextLine();
                        if(ocenaSnimka13.equals("1") || ocenaSnimka13.equals("2") || ocenaSnimka13.equals("3") || ocenaSnimka13.equals("4") || ocenaSnimka13.equals("5"))
                            break;
                        System.out.println("Greska: niste uneli ispravnu ocenu, unesite ponovo");
                    }
                    
                    String datum_vreme_13 = null;
                    while(true){
                        System.out.println("Unesite datum kreiranja ocene(format dd.mm.yyyy): ");
                        String datum13 = x.nextLine();
                        System.out.println("Unesite vreme kreiranja ocene(format hh:mm:ss): ");
                        String vreme13 = x.nextLine();
                        datum_vreme_13 = datum13 + "," + vreme13;
                        if(proveraDatuma(datum_vreme_13))
                            break;
                        System.out.println("GRESKA: Niste dobro uneli datum i vreme!");
                    }
                    
                    op13_KreiranjeOcene(imeKorisnika13, imeSnimka13, ocenaSnimka13, datum_vreme_13);
                    break;
                case 14:
                    System.out.println("MENJANJE OCENE KORISNIKA ZA VIDEO SNIMAK");
                    System.out.println("========================================");
                    System.out.println("Unesite ime korisnika: ");
                    String imeKorisnika14 = x.nextLine();
                    System.out.println("Unesite ime video snimka: ");
                    String imeSnimka1414 = x.nextLine();
                    String imeSnimka14 = imeSnimka1414.replaceAll(" ", "%20");
                    System.out.println("Unesite novu ocenu video snimka: ");
                    String ocenaSnimka14 = null;
                    while(true){
                        ocenaSnimka14 = x.nextLine();
                        if(ocenaSnimka14.equals("1") || ocenaSnimka14.equals("2") || ocenaSnimka14.equals("3") || ocenaSnimka14.equals("4") || ocenaSnimka14.equals("5"))
                            break;
                        System.out.println("Greska: niste uneli ispravnu ocenu, unesite ponovo");
                    }
                    op14_PromenaOcene(imeKorisnika14, imeSnimka14, ocenaSnimka14);
                    break;
                case 15:
                    System.out.println("BRISANJE OCENE KORISNIKA ZA VIDEO SNIMAK");
                    System.out.println("========================================");
                    System.out.println("Unesite ime korisnika: ");
                    String imeKorisnika15 = x.nextLine();
                    System.out.println("Unesite ime video snimka: ");
                    String imeSnimka1515 = x.nextLine();
                    String imeSnimka15 = imeSnimka1515.replaceAll(" ", "%20");
                    op15_BrisanjeOcene(imeKorisnika15, imeSnimka15);
                    break;
                case 16:
                    System.out.println("BRISANJE VIDEO SNIMKA OD STRANE VLASNIKA");
                    System.out.println("============================================");
                    System.out.println("Unesite ime video snimka: ");
                    String imeSnimka116 = x.nextLine();
                    String imeSnimka16 = imeSnimka116.replaceAll(" ", "%20");
                    System.out.println("Unesite ime vlasnika: ");
                    String imeKorisnika16 = x.nextLine();
                    op16_BrisanjeSnimka(imeSnimka16, imeKorisnika16);
                    break;    
                case 17:
                    System.out.println("LISTA SVIH MESTA");
                    System.out.println("================");
                    op17_DohvatanjeMesta();
                    break;
                case 18:
                    System.out.println("LISTA SVIH KORISNIKA");
                    System.out.println("====================");
                    op18_DohvatanjeKorisnika();
                    break;
                case 19:
                    System.out.println("LISTA SVIH KATEGORIJA");
                    System.out.println("====================");
                    op19_DohvatanjeKategorija();
                    break;
                case 20:
                    System.out.println("LISTA SVIH VIDEO SNIMAKA");
                    System.out.println("========================");
                    op20_DohvatanjeVideoSnimaka();
                    break;
                case 21:
                    System.out.println("LISTA KATEGORIJA ZA VIDEO SNIMAK");
                    System.out.println("================================");
                    System.out.println("Unesite ime video snimka: ");
                    String imeSnimka221 = x.nextLine();
                    String imeSnimka21 = imeSnimka221.replaceAll(" ", "%20");
                    op21_DohvatanjeKategorijaZaSnimak(imeSnimka21);
                    break;
                case 22:
                    System.out.println("LISTA SVIH PAKETA");
                    System.out.println("=================");
                    op22_DohvatanjePaketa();
                    break;
                case 23:
                    System.out.println("LISTA SVIH PRETPLATA ZA KORISNIKA");
                    System.out.println("=================================");
                    System.out.println("Unesite ime korisnika: ");
                    String imeKorisnika23 = x.nextLine();
                    op23_DohvatanjePretplataKorisnika(imeKorisnika23);
                    break;
                case 24:
                    System.out.println("LISTA SVIH GLEDANJA ZA VIDEO SNIMAK");
                    System.out.println("===================================");
                    System.out.println("Unesite ime video snimka: ");
                    String imeSnimka2424 = x.nextLine();
                    String imeSnimka24 = imeSnimka2424.replaceAll(" ", "%20");
                    op24_DohvatanjeGledanjaSnimka(imeSnimka24);
                    break;
                case 25:
                    System.out.println("LISTA SVIH OCENA ZA VIDEO SNIMAK");
                    System.out.println("================================");
                    System.out.println("Unesite ime video snimka: ");
                    String imeSnimka2525 = x.nextLine();
                    String imeSnimka25 = imeSnimka2525.replaceAll(" ", "%20");
                    op25_DohvatanjeOcenaSnimka(imeSnimka25);
                    break;
                case 26:
                    radi = false;
                    break;
                default:

            }
            System.out.println();
        }
    }
    
    public static void ispisIzboraFunkcija(){
        System.out.println(" 1. KREIRANJE MESTA");
        System.out.println(" 2. KREIRANJE KORISNIKA");
        System.out.println(" 3. PROMENA EMAIL ADRESE ZA KORISNIKA");
        System.out.println(" 4. PROMENA MESTA ZA KORISNIKA");
        System.out.println(" 5. KREIRANJE KATEGORIJE");
        System.out.println(" 6. KREIRANJE VIDEO SNIMKA");
        System.out.println(" 7. PROMENA NAZIVA VIDEO SNIMKA");
        System.out.println(" 8. DODAVANJE KATEGORIJE VIDEO SNIMKU");
        System.out.println(" 9. KREIRANJE PAKETA");
        System.out.println("10. PROMENA MESECNE CENE ZA PAKET");
        System.out.println("11. KREIRANJE PRETPLATE KORISNIKA NA PAKET");
        System.out.println("12. KREIRANJE GLEDANJA VIDEO SNIMKA OD STRANE KORISNIKA");
        System.out.println("13. KREIRANJE OCENE KORISNIKA ZA VIDEO SNIMAK");
        System.out.println("14. MENJANJE OCENE KORISNIKA ZA VIDEO SNIMAK");
        System.out.println("15. BRISANJE OCENE KORISNIKA ZA VIDEO SNIMAK");
        System.out.println("16. BRISANJE VIDEO SNIMKA OD STRANE VLASNIKA");
        System.out.println("17. DOHVATANJE SVIH MESTA");
        System.out.println("18. DOHVATANJE SVIH KORISNIKA");
        System.out.println("19. DOHVATANJE SVIH KATEGORIJA");
        System.out.println("20. DOHVATANJE SVIH VIDEO SNIMAKA");
        System.out.println("21. DOHVATANJE KATEGORIJA ZA ODREDJENI VIDEO SNIMAK");
        System.out.println("22. DOHVATANJE SVIH PAKETA");
        System.out.println("23. DOHVATANJE SVIH PRETPLATA ZA KORISNIKA");
        System.out.println("24. DOHVATANJE SVIH GLEDANJA ZA VIDEO SNIMAK");
        System.out.println("25. DOHVATANJE SVIH OCENA ZA VIDEO SNIMAK");
        System.out.println("26. KRAJ");
    }

    public static void main(String[] args) {        
        System.out.println("======================================");
        System.out.println("   SISTEM ZA GLEDANJE VIDEO SNIMAKA");
        System.out.println("======================================");
        System.out.println();
       
        try {
            pravljenjeZahteva();
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static void ispis_dohvatanje(HttpURLConnection konekcija) throws IOException{
        System.out.println("--- Poslat zahtev ---");
        int res = konekcija.getResponseCode();
        System.out.println("--- Izvrsen zahtev | HTTP = " + res + " ---");
        
        //v1
        Scanner skener = new Scanner(konekcija.getInputStream());
        String rez = skener.useDelimiter("\\A").next();
        System.out.println(rez);
        skener.close();
        
        //v2
        /*String linija;
        StringBuilder builder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(konekcija.getInputStream()));
        while ((linija = in.readLine()) != null) {
            builder.append(linija);
            builder.append(System.getProperty("line.separator"));
        }
        in.close();
        System.out.println(builder.toString());*/
    }

    public static void op1_KreiranjeMesta(String nazivMesta) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op1_KreiranjeMesta" + "/" + nazivMesta;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");

        ispis_dohvatanje(konekcija);
    }
    
    public static void op2_KreiranjeKorisnika(String ime, String email, String godiste, String pol, String nazivMesta) throws IOException {
        String putanja = "http://localhost:8080/Server/resources/serverR/op2_KreiranjeKorisnika" + "/" + ime + "/" + email + "/" + godiste + "/" + pol + "/" + nazivMesta;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op3_PromenaEmailAdrKor(String ime, String email) throws IOException {
        String putanja = "http://localhost:8080/Server/resources/serverR/op3_PromenaEmailAdrKor" + "/" + ime + "/" + email;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op4_PromenaMestoKor(String ime, String imeMesta) throws IOException {
        String putanja = "http://localhost:8080/Server/resources/serverR/op4_PromenaMestoKor" + "/" + ime + "/" + imeMesta;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op5_KreiranjeKategorije(String nazivKat) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op5_KreiranjeKategorije" + "/" + nazivKat;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");

        ispis_dohvatanje(konekcija);
    }
    
    public static void op6_KreiranjeVideoSnimka(String imeSnimka, String imeVlasnika, String trajanje, String datum) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op6_KreiranjeVideoSnimka" + "/" + imeSnimka + "/" + trajanje + "/" + imeVlasnika + "/" + datum;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");

        ispis_dohvatanje(konekcija);
    }
    
    public static void op7_PromenaImenaSnimka(String imeSnimka, String vlasnik, String novoImeSnimka) throws IOException {
        String putanja = "http://localhost:8080/Server/resources/serverR/op7_PromenaImenaSnimka" + "/" + imeSnimka + "/" + vlasnik + "/" + novoImeSnimka;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op8_DodavanjeKategorije(String imeSnimka, String imeVlasnika, String imeKategorije) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op8_DodavanjeKategorije" + "/" + imeSnimka + "/" + imeVlasnika + "/" + imeKategorije;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");

        ispis_dohvatanje(konekcija);
    }
    
    public static void op9_KreiranjePaketa(String nazivPaketa, String cenaPaketa) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op9_KreiranjePaketa" + "/" + nazivPaketa + "/" + cenaPaketa;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");

        ispis_dohvatanje(konekcija);
    }
    
    public static void op10_PromenaCenePaketa(String ime, String cena) throws IOException {
        String putanja = "http://localhost:8080/Server/resources/serverR/op10_PromenaCenePaketa" + "/" + ime + "/" + cena;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op11_KreiranjePretplate(String imeKorisnika, String imePaketa, String datum) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op11_KreiranjePretplate" + "/" + imeKorisnika + "/" + imePaketa + "/" + datum;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");

        ispis_dohvatanje(konekcija);
    }
    
    public static void op12_KreiranjeGledanja(String imeKorisnika, String imeSnimka, String datum, String vremePocetka, String vremeGledanja) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op12_KreiranjeGledanja" + "/" + imeKorisnika + "/" + imeSnimka + "/" + datum + "/" + vremePocetka + "/" + vremeGledanja;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");

        ispis_dohvatanje(konekcija);
    }
    
    public static void op13_KreiranjeOcene(String imeKorisnika, String imeSnimka, String ocena, String datum) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op13_KreiranjeOcene" + "/" + imeKorisnika + "/" + imeSnimka + "/" + ocena + "/" + datum;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");

        ispis_dohvatanje(konekcija);
    }
    
    public static void op14_PromenaOcene(String imeKorisnika, String imeSnimka, String ocena) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op14_PromenaOcene" + "/" + imeKorisnika + "/" + imeSnimka + "/" + ocena;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("POST");

        ispis_dohvatanje(konekcija);
    }
    
    public static void op15_BrisanjeOcene(String imeSnimka, String imeVlasnika) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op15_BrisanjeOcene" + "/" + imeSnimka + "/" + imeVlasnika;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("DELETE");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op16_BrisanjeSnimka(String imeSnimka, String imeVlasnika) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op16_BrisanjeSnimka" + "/" + imeSnimka + "/" + imeVlasnika;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("DELETE");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op17_DohvatanjeMesta() throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op17_DohvatanjeMesta";
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("GET");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op18_DohvatanjeKorisnika() throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op18_DohvatanjeKorisnika";
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("GET");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op19_DohvatanjeKategorija() throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op19_DohvatanjeKategorija";
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("GET");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op20_DohvatanjeVideoSnimaka() throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op20_DohvatanjeVideoSnimaka";
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("GET");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op21_DohvatanjeKategorijaZaSnimak(String imeSnimka) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op21_DohvatanjeKategorijaZaSnimak" + "/" + imeSnimka;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("GET");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op22_DohvatanjePaketa() throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op22_DohvatanjePaketa";
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("GET");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op23_DohvatanjePretplataKorisnika(String imeKorisnika) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op23_DohvatanjePretplataKorisnika" + "/" + imeKorisnika;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("GET");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op24_DohvatanjeGledanjaSnimka(String imeSnimka) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op24_DohvatanjeGledanjaSnimka" + "/" + imeSnimka;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("GET");
        
        ispis_dohvatanje(konekcija);
    }
    
    public static void op25_DohvatanjeOcenaSnimka(String imeSnimka) throws IOException{
        String putanja = "http://localhost:8080/Server/resources/serverR/op25_DohvatanjeOcenaSnimka" + "/" + imeSnimka;
            
        URL url = new URL(putanja);
        HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
        konekcija.setRequestMethod("GET");
        
        ispis_dohvatanje(konekcija);
    }
}
