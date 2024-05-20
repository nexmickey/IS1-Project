/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zahtevi;

import java.io.Serializable;
import java.util.ArrayList;

//Zahtev server->podsistem
public class ZahtevSP implements Serializable {
    private ArrayList<Object> parametriZahteva = new ArrayList<Object>();
    
    public void dodajParametar(Object o){
        parametriZahteva.add(o);
    }
    
    public int dohBrojParametara(){
        return parametriZahteva.size();
    }
    
    public ArrayList<Object> dohParametre(){
        return parametriZahteva;
    }
    
    public int dohTipZahteva(){
        if(dohBrojParametara() == 0 || parametriZahteva == null)
            return -1;
        Integer brI = (Integer)(parametriZahteva.get(0));
        int br = brI.intValue();
        return br;
    }
    // tip zahteva je uvek prvi u listi!
    // return -1 => prazna lista parametara
}
