/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mihajlo
 */
@Entity
@Table(name = "snimak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Snimak.findAll", query = "SELECT s FROM Snimak s"),
    @NamedQuery(name = "Snimak.findByIDSnim", query = "SELECT s FROM Snimak s WHERE s.iDSnim = :iDSnim"),
    @NamedQuery(name = "Snimak.findByImeSnim", query = "SELECT s FROM Snimak s WHERE s.imeSnim = :imeSnim"),
    @NamedQuery(name = "Snimak.findByTrajanje", query = "SELECT s FROM Snimak s WHERE s.trajanje = :trajanje"),
    @NamedQuery(name = "Snimak.findByDatumSnim", query = "SELECT s FROM Snimak s WHERE s.datumSnim = :datumSnim")})
public class Snimak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDSnim")
    private Integer iDSnim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "imeSnim")
    private String imeSnim;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumSnim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumSnim;
    @JoinTable(name = "sn_kat", joinColumns = {
        @JoinColumn(name = "IDSnim", referencedColumnName = "IDSnim")}, inverseJoinColumns = {
        @JoinColumn(name = "IDKat", referencedColumnName = "IDKat")})
    @ManyToMany
    private List<Kategorija> kategorijaList;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor")
    @ManyToOne(optional = false)
    private Korisnik2 iDKor;

    public Snimak() {
    }

    public Snimak(Integer iDSnim) {
        this.iDSnim = iDSnim;
    }

    public Snimak(Integer iDSnim, String imeSnim, int trajanje, Date datumSnim) {
        this.iDSnim = iDSnim;
        this.imeSnim = imeSnim;
        this.trajanje = trajanje;
        this.datumSnim = datumSnim;
    }

    public Integer getIDSnim() {
        return iDSnim;
    }

    public void setIDSnim(Integer iDSnim) {
        this.iDSnim = iDSnim;
    }

    public String getImeSnim() {
        return imeSnim;
    }

    public void setImeSnim(String imeSnim) {
        this.imeSnim = imeSnim;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumSnim() {
        return datumSnim;
    }

    public void setDatumSnim(Date datumSnim) {
        this.datumSnim = datumSnim;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    public Korisnik2 getIDKor() {
        return iDKor;
    }

    public void setIDKor(Korisnik2 iDKor) {
        this.iDKor = iDKor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDSnim != null ? iDSnim.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Snimak)) {
            return false;
        }
        Snimak other = (Snimak) object;
        if ((this.iDSnim == null && other.iDSnim != null) || (this.iDSnim != null && !this.iDSnim.equals(other.iDSnim))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Snimak[ iDSnim=" + iDSnim + " ]";
    }
    
}
