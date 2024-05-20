package entities;

import entities.Korisnik3;
import entities.Snimak3;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-11T13:40:27")
@StaticMetamodel(Ocena.class)
public class Ocena_ { 

    public static volatile SingularAttribute<Ocena, Korisnik3> korisnik3;
    public static volatile SingularAttribute<Ocena, Snimak3> iDSnim;
    public static volatile SingularAttribute<Ocena, Integer> ocena;
    public static volatile SingularAttribute<Ocena, Date> datumOcene;
    public static volatile SingularAttribute<Ocena, Integer> iDOc;

}