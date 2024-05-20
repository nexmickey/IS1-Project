/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mihajlo
 */
@Entity
@Table(name = "gledanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gledanje.findAll", query = "SELECT g FROM Gledanje g"),
    @NamedQuery(name = "Gledanje.findByIDGled", query = "SELECT g FROM Gledanje g WHERE g.iDGled = :iDGled"),
    @NamedQuery(name = "Gledanje.findByDatumGled", query = "SELECT g FROM Gledanje g WHERE g.datumGled = :datumGled"),
    @NamedQuery(name = "Gledanje.findBySekPoc", query = "SELECT g FROM Gledanje g WHERE g.sekPoc = :sekPoc"),
    @NamedQuery(name = "Gledanje.findBySekOdgl", query = "SELECT g FROM Gledanje g WHERE g.sekOdgl = :sekOdgl")})
public class Gledanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDGled")
    private Integer iDGled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumGled")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumGled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sekPoc")
    private int sekPoc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sekOdgl")
    private int sekOdgl;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor")
    @ManyToOne(optional = false)
    private Korisnik3 iDKor;
    @JoinColumn(name = "IDSnim", referencedColumnName = "IDSnim")
    @ManyToOne(optional = false)
    private Snimak3 iDSnim;

    public Gledanje() {
    }

    public Gledanje(Integer iDGled) {
        this.iDGled = iDGled;
    }

    public Gledanje(Integer iDGled, Date datumGled, int sekPoc, int sekOdgl) {
        this.iDGled = iDGled;
        this.datumGled = datumGled;
        this.sekPoc = sekPoc;
        this.sekOdgl = sekOdgl;
    }

    public Integer getIDGled() {
        return iDGled;
    }

    public void setIDGled(Integer iDGled) {
        this.iDGled = iDGled;
    }

    public Date getDatumGled() {
        return datumGled;
    }

    public void setDatumGled(Date datumGled) {
        this.datumGled = datumGled;
    }

    public int getSekPoc() {
        return sekPoc;
    }

    public void setSekPoc(int sekPoc) {
        this.sekPoc = sekPoc;
    }

    public int getSekOdgl() {
        return sekOdgl;
    }

    public void setSekOdgl(int sekOdgl) {
        this.sekOdgl = sekOdgl;
    }

    public Korisnik3 getIDKor() {
        return iDKor;
    }

    public void setIDKor(Korisnik3 iDKor) {
        this.iDKor = iDKor;
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
        hash += (iDGled != null ? iDGled.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gledanje)) {
            return false;
        }
        Gledanje other = (Gledanje) object;
        if ((this.iDGled == null && other.iDGled != null) || (this.iDGled != null && !this.iDGled.equals(other.iDGled))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Gledanje[ iDGled=" + iDGled + " ]";
    }
    
}
