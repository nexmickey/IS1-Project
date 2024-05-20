/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "korisnik2")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik2.findAll", query = "SELECT k FROM Korisnik2 k"),
    @NamedQuery(name = "Korisnik2.findByIDKor", query = "SELECT k FROM Korisnik2 k WHERE k.iDKor = :iDKor"),
    @NamedQuery(name = "Korisnik2.findByImeKor", query = "SELECT k FROM Korisnik2 k WHERE k.imeKor = :imeKor")})
public class Korisnik2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private Integer iDKor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "imeKor")
    private String imeKor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDKor")
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

    public Integer getIDKor() {
        return iDKor;
    }

    public void setIDKor(Integer iDKor) {
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
        return "entities.Korisnik2[ iDKor=" + iDKor + " ]";
    }
    
}
