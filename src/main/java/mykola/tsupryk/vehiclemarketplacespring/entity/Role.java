package mykola.tsupryk.vehiclemarketplacespring.entity;

import lombok.Getter;
import lombok.Setter;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.AppUserRole;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AppUserRole name;
}
