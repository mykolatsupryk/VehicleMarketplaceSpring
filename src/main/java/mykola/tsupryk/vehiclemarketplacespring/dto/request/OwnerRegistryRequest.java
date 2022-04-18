package mykola.tsupryk.vehiclemarketplacespring.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter @Setter
public class OwnerRegistryRequest {

    private String name;
    @Email
    private String email;
    private String password;



}
