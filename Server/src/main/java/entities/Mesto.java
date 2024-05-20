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
public class Mesto implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer iDMesto;
    private String imeMes;
    private List<Korisnik> korisnikList;

    public Integer getiDMesto() {
        return iDMesto;
    }

    public void setiDMesto(Integer iDMesto) {
        this.iDMesto = iDMesto;
    }

    public String getImeMes() {
        return imeMes;
    }

    public void setImeMes(String imeMes) {
        this.imeMes = imeMes;
    }
    
    @XmlTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDMesto != null ? iDMesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.iDMesto == null && other.iDMesto != null) || (this.iDMesto != null && !this.iDMesto.equals(other.iDMesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return imeMes;
    }
}
