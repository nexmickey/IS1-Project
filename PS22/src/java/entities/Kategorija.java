/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mihajlo
 */
@Entity
@Table(name = "kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kategorija.findAll", query = "SELECT k FROM Kategorija k"),
    @NamedQuery(name = "Kategorija.findByIDKat", query = "SELECT k FROM Kategorija k WHERE k.iDKat = :iDKat"),
    @NamedQuery(name = "Kategorija.findByImeKat", query = "SELECT k FROM Kategorija k WHERE k.imeKat = :imeKat")})
public class Kategorija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDKat")
    private Integer iDKat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "imeKat")
    private String imeKat;
    @ManyToMany(mappedBy = "kategorijaList")
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

    public Integer getIDKat() {
        return iDKat;
    }

    public void setIDKat(Integer iDKat) {
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
        return "entities.Kategorija[ iDKat=" + iDKat + " ]";
    }
    
}
