package entities;

import entities.Kategorija;
import entities.Korisnik2;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-11T13:40:25")
@StaticMetamodel(Snimak.class)
public class Snimak_ { 

    public static volatile SingularAttribute<Snimak, Date> datumSnim;
    public static volatile ListAttribute<Snimak, Kategorija> kategorijaList;
    public static volatile SingularAttribute<Snimak, Korisnik2> iDKor;
    public static volatile SingularAttribute<Snimak, String> imeSnim;
    public static volatile SingularAttribute<Snimak, Integer> trajanje;
    public static volatile SingularAttribute<Snimak, Integer> iDSnim;

}