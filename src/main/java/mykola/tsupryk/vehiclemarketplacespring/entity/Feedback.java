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
    @ManyToOne(cascade = {CascadeType.ALL})
    private Owner user;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Owner comentator;


}
