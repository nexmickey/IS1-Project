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
@Table(name = "snimak3")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Snimak3.findAll", query = "SELECT s FROM Snimak3 s"),
    @NamedQuery(name = "Snimak3.findByIDSnim", query = "SELECT s FROM Snimak3 s WHERE s.iDSnim = :iDSnim"),
    @NamedQuery(name = "Snimak3.findByImeSnim", query = "SELECT s FROM Snimak3 s WHERE s.imeSnim = :imeSnim")})
public class Snimak3 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDSnim")
    private Integer iDSnim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "imeSnim")
    private String imeSnim;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDSnim")
    private List<Gledanje> gledanjeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDSnim")
    private List<Ocena> ocenaList;

    public Snimak3() {
    }

    public Snimak3(Integer iDSnim) {
        this.iDSnim = iDSnim;
    }

    public Snimak3(Integer iDSnim, String imeSnim) {
        this.iDSnim = iDSnim;
        this.imeSnim = imeSnim;
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
        hash += (iDSnim != null ? iDSnim.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Snimak3)) {
            return false;
        }
        Snimak3 other = (Snimak3) object;
        if ((this.iDSnim == null && other.iDSnim != null) || (this.iDSnim != null && !this.iDSnim.equals(other.iDSnim))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Snimak3[ iDSnim=" + iDSnim + " ]";
    }
    
}
