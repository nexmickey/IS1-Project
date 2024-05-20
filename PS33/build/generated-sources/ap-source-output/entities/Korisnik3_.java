package entities;

import entities.Gledanje;
import entities.Ocena;
import entities.Pretplata;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-11T13:40:27")
@StaticMetamodel(Korisnik3.class)
public class Korisnik3_ { 

    public static volatile SingularAttribute<Korisnik3, Integer> iDKor;
    public static volatile ListAttribute<Korisnik3, Gledanje> gledanjeList;
    public static volatile SingularAttribute<Korisnik3, String> imeKor;
    public static volatile ListAttribute<Korisnik3, Pretplata> pretplataList;
    public static volatile ListAttribute<Korisnik3, Ocena> ocenaList;

}