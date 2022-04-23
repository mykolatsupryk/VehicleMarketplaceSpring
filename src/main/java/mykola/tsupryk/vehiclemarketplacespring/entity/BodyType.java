package mykola.tsupryk.vehiclemarketplacespring.entity;


import lombok.Getter;
import lombok.Setter;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;

import javax.persistence.*;

@Entity
@Table(name = "bodyType")
@Getter @Setter
public class BodyType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bodyType;

//    public static BodyType checkBodyType (String b) throws UnreachebleTypeException {
//
//        BodyType bodyType = new BodyType();
//
//        for (BodyType iterate : BodyType.values()) {
//            if (iterate.name().equalsIgnoreCase(b)) {
//                bodyType = iterate;
//                return bodyType;
//            }
//        }
//        throw new UnreachebleTypeException("unreacheble bodyType name! Allowed bodyTypes: SEDAN, VARIANT, HATCHBACK, SUV;");
//    }



}


