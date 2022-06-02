package mykola.tsupryk.vehiclemarketplacespring.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VehicleCreateRequest {

    private Long brandId;
    private Long modelId;
    private Integer yearOfManufacture;
    private Long bodyTypeId;
    private Integer enginePower;
    private String color;
    private Integer mileAge;
    private Integer price;
    //image




}
