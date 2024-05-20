/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mihajlo
 */
public class Korisnik2 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer iDKor;
    private String imeKor;
    private List<Snimak> snimakList;
    
    public Korisnik2() {
    }

    public Korisnik2(Integer iDKor) {
        this.iDKor = iDKor;
    }

    public Korisnik2(Integer iDKor, String imeKor) {
        this.iDKor = iDKor;
        this.imeKor = imeKor;
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

    @XmlTransient
    public List<Snimak> getSnimakList() {
        return snimakList;
    }

    public void setSnimakList(List<Snimak> snimakList) {
        this.snimakList = snimakList;
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
        if (!(object instanceof Korisnik2)) {
            return false;
        }
        Korisnik2 other = (Korisnik2) object;
        if ((this.iDKor == null && other.iDKor != null) || (this.iDKor != null && !this.iDKor.equals(other.iDKor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return iDKor + "  " + imeKor;
    }
}
