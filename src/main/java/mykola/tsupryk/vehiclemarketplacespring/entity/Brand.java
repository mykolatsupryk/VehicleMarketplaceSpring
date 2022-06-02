package mykola.tsupryk.vehiclemarketplacespring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonBackReference
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Model> models = new ArrayList<>();
    @JsonBackReference
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Vehicle> vehicleList = new ArrayList<>();


}
