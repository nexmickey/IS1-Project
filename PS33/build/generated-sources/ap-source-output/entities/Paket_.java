package entities;

import entities.Pretplata;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-11T13:40:27")
@StaticMetamodel(Paket.class)
public class Paket_ { 

    public static volatile SingularAttribute<Paket, String> imePak;
    public static volatile ListAttribute<Paket, Pretplata> pretplataList;
    public static volatile SingularAttribute<Paket, Integer> iDPak;
    public static volatile SingularAttribute<Paket, Integer> cenaPak;

}