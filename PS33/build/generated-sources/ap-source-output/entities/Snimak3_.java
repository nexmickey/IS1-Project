package entities;

import entities.Gledanje;
import entities.Ocena;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-02-11T13:40:27")
@StaticMetamodel(Snimak3.class)
public class Snimak3_ { 

    public static volatile ListAttribute<Snimak3, Gledanje> gledanjeList;
    public static volatile SingularAttribute<Snimak3, String> imeSnim;
    public static volatile ListAttribute<Snimak3, Ocena> ocenaList;
    public static volatile SingularAttribute<Snimak3, Integer> iDSnim;

}