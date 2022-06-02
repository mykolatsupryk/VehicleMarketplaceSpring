package mykola.tsupryk.vehiclemarketplacespring.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VehicleResponse {

    private Long id;
    private String brand;
    private String model;
    private Integer yearOfManufacture;
    private String bodyType;
    private Integer enginePower;
    private String color;
    private Integer mileAge;
    private Integer price;
}
