/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Mihajlo
 */
public class Korisnik implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer iDKor;
    private String imeKor;
    private String email;
    private int godiste;
    private String pol;
    private Mesto iDMesto;

    public Korisnik() {
    }

    public Korisnik(Integer iDKor) {
        this.iDKor = iDKor;
    }

    public Korisnik(Integer iDKor, String imeKor, String email, int godiste, String pol, Mesto iDMesto) {
        this.iDKor = iDKor;
        this.imeKor = imeKor;
        this.email = email;
        this.godiste = godiste;
        this.pol = pol;
        this.iDMesto = iDMesto;
    }

    public Integer getiDKor() {
        return iDKor;
    }

    public void setiDKor(Integer iDKor) {
        this.iDKor = iDKor;
    }

    public String getImeKor() {
        return imeKor;
    }

    public void setImeKor(String imeKor) {
        this.imeKor = imeKor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public Mesto getiDMesto() {
        return iDMesto;
    }

    public void setiDMesto(Mesto iDMesto) {
        this.iDMesto = iDMesto;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDKor != null ? iDKor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.iDKor == null && other.iDKor != null) || (this.iDKor != null && !this.iDKor.equals(other.iDKor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return iDKor + "  " + imeKor + "  " + email + "  " + godiste + "  " + pol + "  [" + iDMesto.getImeMes() + "]";
    }
}
