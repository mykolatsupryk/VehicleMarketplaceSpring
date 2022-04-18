package mykola.tsupryk.vehiclemarketplacespring.dto.request;


import lombok.Getter;
import lombok.Setter;
import mykola.tsupryk.vehiclemarketplacespring.enums.BodyType;
import mykola.tsupryk.vehiclemarketplacespring.enums.Color;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter @Setter
public class VehicleCreateRequest {

    private String brand;
    private String model;
    private Integer yearOfManufacture;
    private String bodyType;
    private Integer enginePower;
    private String color;
    private Integer mileAge;
    private Integer price;




}
