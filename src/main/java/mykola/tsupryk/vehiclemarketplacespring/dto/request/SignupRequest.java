package mykola.tsupryk.vehiclemarketplacespring.dto.request;


import lombok.Getter;
import lombok.Setter;
import mykola.tsupryk.vehiclemarketplacespring.entity.Role;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public class SignupRequest {

    private String name;
    @Email
    private String email;
    private String password;
    private String role;
}
