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
@Table(name = "korisnik3")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik3.findAll", query = "SELECT k FROM Korisnik3 k"),
    @NamedQuery(name = "Korisnik3.findByIDKor", query = "SELECT k FROM Korisnik3 k WHERE k.iDKor = :iDKor"),
    @NamedQuery(name = "Korisnik3.findByImeKor", query = "SELECT k FROM Korisnik3 k WHERE k.imeKor = :imeKor")})
public class Korisnik3 implements Serializable {

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
    private List<Pretplata> pretplataList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDKor")
    private List<Gledanje> gledanjeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korisnik3")
    private List<Ocena> ocenaList;

    public Korisnik3() {
    }

    public Korisnik3(Integer iDKor) {
        this.iDKor = iDKor;
    }

    public Korisnik3(Integer iDKor, String imeKor) {
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
    public List<Pretplata> getPretplataList() {
        return pretplataList;
    }

    public void setPretplataList(List<Pretplata> pretplataList) {
        this.pretplataList = pretplataList;
    }

    @XmlTransient
    public List<Gledanje> getGledanjeList() {
        return gledanjeList;
    }

    public void setGledanjeList(List<Gledanje> gledanjeList) {
        this.gledanjeList = gledanjeList;
    }

    @XmlTransient
    public List<Ocena> getOcenaList() {
        return ocenaList;
    }

    public void setOcenaList(List<Ocena> ocenaList) {
        this.ocenaList = ocenaList;
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
        if (!(object instanceof Korisnik3)) {
            return false;
        }
        Korisnik3 other = (Korisnik3) object;
        if ((this.iDKor == null && other.iDKor != null) || (this.iDKor != null && !this.iDKor.equals(other.iDKor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Korisnik3[ iDKor=" + iDKor + " ]";
    }
    
}
