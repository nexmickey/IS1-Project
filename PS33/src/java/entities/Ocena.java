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
import javax.persistence.JoinColumns;
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
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o"),
    @NamedQuery(name = "Ocena.findByIDOc", query = "SELECT o FROM Ocena o WHERE o.iDOc = :iDOc"),
    @NamedQuery(name = "Ocena.findByOcena", query = "SELECT o FROM Ocena o WHERE o.ocena = :ocena"),
    @NamedQuery(name = "Ocena.findByDatumOcene", query = "SELECT o FROM Ocena o WHERE o.datumOcene = :datumOcene")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDOc")
    private Integer iDOc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ocena")
    private int ocena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumOcene")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumOcene;
    @JoinColumns({
        @JoinColumn(name = "IDKor", referencedColumnName = "IDKor"),
        @JoinColumn(name = "IDKor", referencedColumnName = "IDKor")})
    @ManyToOne(optional = false)
    private Korisnik3 korisnik3;
    @JoinColumn(name = "IDSnim", referencedColumnName = "IDSnim")
    @ManyToOne(optional = false)
    private Snimak3 iDSnim;

    public Ocena() {
    }

    public Ocena(Integer iDOc) {
        this.iDOc = iDOc;
    }

    public Ocena(Integer iDOc, int ocena, Date datumOcene) {
        this.iDOc = iDOc;
        this.ocena = ocena;
        this.datumOcene = datumOcene;
    }

    public Integer getIDOc() {
        return iDOc;
    }

    public void setIDOc(Integer iDOc) {
        this.iDOc = iDOc;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Date getDatumOcene() {
        return datumOcene;
    }

    public void setDatumOcene(Date datumOcene) {
        this.datumOcene = datumOcene;
    }

    public Korisnik3 getKorisnik3() {
        return korisnik3;
    }

    public void setKorisnik3(Korisnik3 korisnik3) {
        this.korisnik3 = korisnik3;
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
        hash += (iDOc != null ? iDOc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.iDOc == null && other.iDOc != null) || (this.iDOc != null && !this.iDOc.equals(other.iDOc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return korisnik3.getImeKor() + "  " + Integer.toString(ocena);
    }
    
}
