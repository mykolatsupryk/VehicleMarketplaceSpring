package mykola.tsupryk.vehiclemarketplacespring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
@Getter @Setter
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public static final Path PATH = Paths.get("/home/mykolatsupryk/Desktop/KindGeek Project/"
                                                    +"VehicleMarketplaceSpring/src/main/java/mykola/tsupryk/"
                                                    +"vehiclemarketplacespring/photo/");
    @Column(length = 1000)
    private String photoUrl;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Vehicle vehicle;


}
