package entities;

import entities.Korisnik3;
import entities.Snimak3;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-11T13:40:27")
@StaticMetamodel(Gledanje.class)
public class Gledanje_ { 

    public static volatile SingularAttribute<Gledanje, Integer> iDGled;
    public static volatile SingularAttribute<Gledanje, Korisnik3> iDKor;
    public static volatile SingularAttribute<Gledanje, Integer> sekPoc;
    public static volatile SingularAttribute<Gledanje, Snimak3> iDSnim;
    public static volatile SingularAttribute<Gledanje, Date> datumGled;
    public static volatile SingularAttribute<Gledanje, Integer> sekOdgl;

}