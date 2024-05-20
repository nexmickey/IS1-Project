/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

public class Paket implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer iDPak;
    private String imePak;
    private int cenaPak;
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
        return imePak + "  " + Integer.toString(cenaPak);
    }
}
