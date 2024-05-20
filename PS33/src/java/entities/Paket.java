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
@Table(name = "paket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paket.findAll", query = "SELECT p FROM Paket p"),
    @NamedQuery(name = "Paket.findByIDPak", query = "SELECT p FROM Paket p WHERE p.iDPak = :iDPak"),
    @NamedQuery(name = "Paket.findByImePak", query = "SELECT p FROM Paket p WHERE p.imePak = :imePak"),
    @NamedQuery(name = "Paket.findByCenaPak", query = "SELECT p FROM Paket p WHERE p.cenaPak = :cenaPak"),
    @NamedQuery(name = "Paket.findAllSorted", query = "SELECT p FROM Paket p ORDER BY p.cenaPak DESC")})
public class Paket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPak")
    private Integer iDPak;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "imePak")
    private String imePak;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cenaPak")
    private int cenaPak;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDPak")
    private List<Pretplata> pretplataList;

    public Paket() {
    }

    public Paket(Integer iDPak) {
        this.iDPak = iDPak;
    }

    public Paket(Integer iDPak, String imePak, int cenaPak) {
        this.iDPak = iDPak;
        this.imePak = imePak;
        this.cenaPak = cenaPak;
    }

    public Integer getIDPak() {
        return iDPak;
    }

    public void setIDPak(Integer iDPak) {
        this.iDPak = iDPak;
    }

    public String getImePak() {
        return imePak;
    }

    public void setImePak(String imePak) {
        this.imePak = imePak;
    }

    public int getCenaPak() {
        return cenaPak;
    }

    public void setCenaPak(int cenaPak) {
        this.cenaPak = cenaPak;
    }

    @XmlTransient
    public List<Pretplata> getPretplataList() {
        return pretplataList;
    }

    public void setPretplataList(List<Pretplata> pretplataList) {
        this.pretplataList = pretplataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPak != null ? iDPak.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paket)) {
            return false;
        }
        Paket other = (Paket) object;
        if ((this.iDPak == null && other.iDPak != null) || (this.iDPak != null && !this.iDPak.equals(other.iDPak))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Paket[ iDPak=" + iDPak + " ]";
    }
    
}
