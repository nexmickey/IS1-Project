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
public class Kategorija implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Integer iDKat;
    private String imeKat;
    private List<Snimak> snimakList;

    public Kategorija() {
        
    }
    
    public Kategorija(Integer iDKat) {
        this.iDKat = iDKat;
    }

    public Kategorija(Integer iDKat, String imeKat) {
        this.iDKat = iDKat;
        this.imeKat = imeKat;
    }

    public Integer getiDKat() {
        return iDKat;
    }

    public void setiDKat(Integer iDKat) {
        this.iDKat = iDKat;
    }

    public String getImeKat() {
        return imeKat;
    }

    public void setImeKat(String imeKat) {
        this.imeKat = imeKat;
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
        hash += (iDKat != null ? iDKat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorija)) {
            return false;
        }
        Kategorija other = (Kategorija) object;
        if ((this.iDKat == null && other.iDKat != null) || (this.iDKat != null && !this.iDKat.equals(other.iDKat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return imeKat;
    }
}
