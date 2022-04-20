package mykola.tsupryk.vehiclemarketplacespring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;

@Entity
@Getter @Setter
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public static final String PATH = "/home/mykolatsupryk/Desktop/KindGeek Project/"
                                        +"VehicleMarketplaceSpring/src/main/java/mykola/tsupryk/"
                                        +"vehiclemarketplacespring/photo/";
    private File photo;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Vehicle vehicle;


}
