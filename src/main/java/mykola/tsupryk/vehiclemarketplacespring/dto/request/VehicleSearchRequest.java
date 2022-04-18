package mykola.tsupryk.vehiclemarketplacespring.dto.request;


import lombok.Getter;
import lombok.Setter;




@Getter @Setter
public class VehicleSearchRequest {

    private String brand;
    private String model;
    private Integer yearOfManufactureTop;
    private Integer yearOfManufactureBottom;
    private String yearOfManufactureOperation;
    private String bodyType;
    private Integer enginePowerTop;
    private Integer enginePowerBottom;
    private String enginePowerOperation;
    private String color;
    private Integer mileAgeTop;
    private Integer mileAgeBottom;
    private String mileAgeOperation;
    private Integer priceTop;
    private Integer priceBottom;
    private String priceOperation;
}
