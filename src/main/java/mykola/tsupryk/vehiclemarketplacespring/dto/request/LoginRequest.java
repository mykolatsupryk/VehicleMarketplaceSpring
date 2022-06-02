package mykola.tsupryk.vehiclemarketplacespring.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

@ToString
@Getter @Setter
public class LoginRequest {

    private String email;
    private String password;

}

