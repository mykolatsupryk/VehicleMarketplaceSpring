package mykola.tsupryk.vehiclemarketplacespring.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import mykola.tsupryk.vehiclemarketplacespring.entity.Role;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public class AppUserResponse {

    private Long id;
    private String name;
    private String email;
    private String city;
    private String contactNumber;
    private Integer money;
    private Set<String> roles = new HashSet<>();


}
