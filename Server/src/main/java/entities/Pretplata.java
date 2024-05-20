/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Pretplata implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer iDPret;
    private Date datumPret;
    private int cena;
    private Korisnik3 iDKor;
    private Paket iDPak;
    
    public Pretplata() {
    }

    public Pretplata(Integer iDPret) {
        this.iDPret = iDPret;
    }

    public Pretplata(Integer iDPret, Date datumPret, int cena) {
        this.iDPret = iDPret;
        this.datumPret = datumPret;
        this.cena = cena;
    }

    public Integer getIDPret() {
        return iDPret;
    }

    public void setIDPret(Integer iDPret) {
        this.iDPret = iDPret;
    }

    public Date getDatumPret() {
        return datumPret;
    }

    public void setDatumPret(Date datumPret) {
        this.datumPret = datumPret;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Korisnik3 getIDKor() {
        return iDKor;
    }

    public void setIDKor(Korisnik3 iDKor) {
        this.iDKor = iDKor;
    }

    public Paket getIDPak() {
        return iDPak;
    }

    public void setIDPak(Paket iDPak) {
        this.iDPak = iDPak;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPret != null ? iDPret.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.iDPret == null && other.iDPret != null) || (this.iDPret != null && !this.iDPret.equals(other.iDPret))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");
        Calendar novi = Calendar.getInstance();
        novi.setTime(datumPret);
        novi.add(Calendar.MONTH, 1);
        Date noviD = novi.getTime();
        return iDPak.getImePak() + "  " + dateFormat.format(datumPret) + " - " + dateFormat.format(noviD) + "  " + cena;
    }
}
