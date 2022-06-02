package mykola.tsupryk.vehiclemarketplacespring.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    public static final Path PATH = Paths.get("/home/mykolatsupryk/Java education/KindGeek Project/"
                                                    +"VehicleMarketplaceSpring/src/main/java/mykola/tsupryk/"
                                                    +"vehiclemarketplacespring/photo/");
    @Column(length = 1000)
    private String photoUrl;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;


}
