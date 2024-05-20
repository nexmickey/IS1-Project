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
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p"),
    @NamedQuery(name = "Pretplata.findByIDPret", query = "SELECT p FROM Pretplata p WHERE p.iDPret = :iDPret"),
    @NamedQuery(name = "Pretplata.findByDatumPret", query = "SELECT p FROM Pretplata p WHERE p.datumPret = :datumPret"),
    @NamedQuery(name = "Pretplata.findByCena", query = "SELECT p FROM Pretplata p WHERE p.cena = :cena")})
public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPret")
    private Integer iDPret;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumPret")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumPret;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private int cena;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor")
    @ManyToOne(optional = false)
    private Korisnik3 iDKor;
    @JoinColumn(name = "IDPak", referencedColumnName = "IDPak")
    @ManyToOne(optional = false)
    private Paket iDPak;

    public Pretplata() {
    }

    public Pretplata(Integer iDPret) {
        this.iDPret = iDPret;
    }

    public Pretplata(Integer iDPret, Date datumPret, int cena) {
        this.iDPret = iDPret;
        this.datumPret = datumPret;
        this.cena = cena;
    }

    public Integer getIDPret() {
        return iDPret;
    }

    public void setIDPret(Integer iDPret) {
        this.iDPret = iDPret;
    }

    public Date getDatumPret() {
        return datumPret;
    }

    public void setDatumPret(Date datumPret) {
        this.datumPret = datumPret;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Korisnik3 getIDKor() {
        return iDKor;
    }

    public void setIDKor(Korisnik3 iDKor) {
        this.iDKor = iDKor;
    }

    public Paket getIDPak() {
        return iDPak;
    }

    public void setIDPak(Paket iDPak) {
        this.iDPak = iDPak;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPret != null ? iDPret.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.iDPret == null && other.iDPret != null) || (this.iDPret != null && !this.iDPret.equals(other.iDPret))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pretplata[ iDPret=" + iDPret + " ]";
    }
    
}
