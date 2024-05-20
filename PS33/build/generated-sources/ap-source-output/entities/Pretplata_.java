package entities;

import entities.Korisnik3;
import entities.Paket;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-11T13:40:27")
@StaticMetamodel(Pretplata.class)
public class Pretplata_ { 

    public static volatile SingularAttribute<Pretplata, Korisnik3> iDKor;
    public static volatile SingularAttribute<Pretplata, Paket> iDPak;
    public static volatile SingularAttribute<Pretplata, Integer> cena;
    public static volatile SingularAttribute<Pretplata, Integer> iDPret;
    public static volatile SingularAttribute<Pretplata, Date> datumPret;

}