package mykola.tsupryk.vehiclemarketplacespring.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bodyType")
@Getter @Setter
public class BodyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonBackReference
    @OneToMany(mappedBy = "bodyType", cascade = CascadeType.ALL)
    private List<Vehicle> vehicleList = new ArrayList<>();

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


