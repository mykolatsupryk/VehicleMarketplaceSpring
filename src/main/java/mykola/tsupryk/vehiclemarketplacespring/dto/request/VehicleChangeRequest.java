package mykola.tsupryk.vehiclemarketplacespring.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VehicleChangeRequest {

    private Integer enginePower;
    private String color;
    private Integer mileAge;
    private Integer price;


}
