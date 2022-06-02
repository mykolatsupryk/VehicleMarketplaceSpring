package mykola.tsupryk.vehiclemarketplacespring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table (name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer rating;
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser owner;



}
