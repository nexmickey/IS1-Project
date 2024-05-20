/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Mihajlo
 */
public class Ocena implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer iDOc;
    private int ocena;
    private Date datumOcene;
    private Korisnik3 korisnik3;
    private Snimak3 iDSnim;

    public Ocena() {
    }

    public Ocena(Integer iDOc) {
        this.iDOc = iDOc;
    }

    public Ocena(Integer iDOc, int ocena, Date datumOcene) {
        this.iDOc = iDOc;
        this.ocena = ocena;
        this.datumOcene = datumOcene;
    }

    public Integer getIDOc() {
        return iDOc;
    }

    public void setIDOc(Integer iDOc) {
        this.iDOc = iDOc;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Date getDatumOcene() {
        return datumOcene;
    }

    public void setDatumOcene(Date datumOcene) {
        this.datumOcene = datumOcene;
    }

    public Korisnik3 getKorisnik3() {
        return korisnik3;
    }

    public void setKorisnik3(Korisnik3 korisnik3) {
        this.korisnik3 = korisnik3;
    }

    public Snimak3 getIDSnim() {
        return iDSnim;
    }

    public void setIDSnim(Snimak3 iDSnim) {
        this.iDSnim = iDSnim;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDOc != null ? iDOc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.iDOc == null && other.iDOc != null) || (this.iDOc != null && !this.iDOc.equals(other.iDOc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return korisnik3.getImeKor() + "  " + Integer.toString(ocena);
    }
}
