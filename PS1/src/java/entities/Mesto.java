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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "mesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findByIDMesto", query = "SELECT m FROM Mesto m WHERE m.iDMesto = :iDMesto"),
    @NamedQuery(name = "Mesto.findByImeMes", query = "SELECT m FROM Mesto m WHERE m.imeMes = :imeMes")})
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDMesto")
    private Integer iDMesto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "imeMes")
    private String imeMes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDMesto")
    private List<Korisnik> korisnikList;

    public Mesto() {
    }

    public Mesto(Integer iDMesto) {
        this.iDMesto = iDMesto;
    }

    public Mesto(Integer iDMesto, String imeMes) {
        this.iDMesto = iDMesto;
        this.imeMes = imeMes;
    }

    public Integer getIDMesto() {
        return iDMesto;
    }

    public void setIDMesto(Integer iDMesto) {
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
        return "entities.Mesto[ iDMesto=" + iDMesto + " ]";
    }
    
}
