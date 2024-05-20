/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mihajlo
 */
public class Snimak3 implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer iDSnim;
    private String imeSnim;
    private List<Gledanje> gledanjeList;
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
        return iDSnim + "  " + imeSnim;
    }
}
