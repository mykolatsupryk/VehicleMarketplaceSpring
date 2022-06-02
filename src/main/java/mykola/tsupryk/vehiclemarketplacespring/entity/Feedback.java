package mykola.tsupryk.vehiclemarketplacespring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AppUser user;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AppUser comentator;


}
