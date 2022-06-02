package mykola.tsupryk.vehiclemarketplacespring.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.Color;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter @Setter
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonManagedReference
    @ManyToOne
    private Brand brand;
    @JsonManagedReference
    @ManyToOne
    private Model model;
    private final String vin = generateVIN();
    private Integer yearOfManufacture;
    @JsonManagedReference
    @ManyToOne
    private BodyType bodyType;
    private Integer enginePower;
    @Enumerated(value = EnumType.STRING)
    private Color color;
    private Integer mileAge;
    private Integer price;
    private Boolean isConfirm = false;
    @ManyToOne
    private AppUser owner;
    @JsonBackReference
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    List<Photo> photos = new ArrayList<>();


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
