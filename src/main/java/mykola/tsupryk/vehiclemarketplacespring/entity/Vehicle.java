package mykola.tsupryk.vehiclemarketplacespring.entity;


import lombok.Getter;
import lombok.Setter;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.BodyType;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.Color;

import javax.persistence.*;
import java.util.Random;

@Entity
@Getter @Setter
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private final String vin = generateVIN();
    private Integer yearOfManufacture;
    @Enumerated(value = EnumType.STRING)
    private BodyType bodyType;
    private Integer enginePower;
    @Enumerated(value = EnumType.STRING)
    private Color color;
    private Integer mileAge;
    private Integer price;
    private Boolean isConfirm = false;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Owner owner;


    public String generateVIN() {

            Random random = new Random();
            StringBuilder engAndDig = new StringBuilder();
            engAndDig.append("abcdefghijklmnopqrstuvwxyz".toUpperCase()).append("0123456789");
            String vin = "";
            for (int i = 0; i < 17; i++) {
                vin += engAndDig.charAt(random.nextInt(engAndDig.length()));
            }
        return vin;
    }


}
