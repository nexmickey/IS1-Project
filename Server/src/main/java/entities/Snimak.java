/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Mihajlo
 */
public class Snimak implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer iDSnim;
    private String imeSnim;
    private int trajanje;
    private Date datumSnim;
    private List<Kategorija> kategorijaList;
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

    public Integer getiDSnim() {
        return iDSnim;
    }

    public void setiDSnim(Integer iDSnim) {
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
    
    public Korisnik2 getiDKor() {
        return iDKor;
    }

    public void setiDKor(Korisnik2 iDKor) {
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
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");  
        return iDSnim + "  " + imeSnim + "  [" + iDKor.getImeKor() + "]  " + trajanje + "min  " + dateFormat.format(datumSnim);
    }
}
