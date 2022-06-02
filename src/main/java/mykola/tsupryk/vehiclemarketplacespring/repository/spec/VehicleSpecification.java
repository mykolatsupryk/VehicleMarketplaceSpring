package mykola.tsupryk.vehiclemarketplacespring.repository.spec;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.VehicleSearchRequest;
import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class VehicleSpecification implements Specification<Vehicle> {

    private final String brandParameter = "brand";
    private String brand;
    private final String modelParameter = "model";
    private String model;
    private final String yearOfManufactureParameter = "yearOfManufacture";
    private Integer yearOfManufactureTop;
    private Integer yearOfManufactureBottom;
    private String yearOfManufactureOperation;
    private final String bodyTypeParameter = "bodyType";
    private String bodyType;
    private final String enginePowerParameter = "enginePower";
    private Integer enginePowerTop;
    private Integer enginePowerBottom;
    private String enginePowerOperation;
    private final String colorParameter = "color";
    private String color;
    private final String mileAgeParameter = "mileAge";
    private Integer mileAgeTop;
    private Integer mileAgeBottom;
    private String mileAgeOperation;
    private final String priceParameter = "price";
    private Integer priceTop;
    private Integer priceBottom;
    private String priceOperation;
    private final Boolean isConfirm = true;

    public VehicleSpecification(VehicleSearchRequest vehicleSearchRequest) {
        this.brand = vehicleSearchRequest.getBrand();
        this.model = vehicleSearchRequest.getModel();
        this.yearOfManufactureTop = vehicleSearchRequest.getYearOfManufactureTop();
        this.yearOfManufactureBottom = vehicleSearchRequest.getYearOfManufactureBottom();
        this.yearOfManufactureOperation = vehicleSearchRequest.getYearOfManufactureOperation();
        this.bodyType = vehicleSearchRequest.getBodyType();
        this.enginePowerTop = vehicleSearchRequest.getEnginePowerTop();
        this.enginePowerBottom = vehicleSearchRequest.getEnginePowerBottom();
        this.enginePowerOperation = vehicleSearchRequest.getEnginePowerOperation();
        this.color = vehicleSearchRequest.getColor();
        this.mileAgeTop = vehicleSearchRequest.getMileAgeTop();
        this.mileAgeBottom = vehicleSearchRequest.getMileAgeBottom();
        this.mileAgeOperation = vehicleSearchRequest.getMileAgeOperation();
        this.priceTop = vehicleSearchRequest.getPriceTop();
        this.priceBottom = vehicleSearchRequest.getPriceBottom();
        this.priceOperation = vehicleSearchRequest.getPriceOperation();
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("isConfirm"), isConfirm));
        predicateByParameter(root, criteriaBuilder, predicates, brandParameter, brand);
        predicateByParameter(root, criteriaBuilder, predicates, modelParameter, model);
        predicateByParameter(root, criteriaBuilder, predicates, yearOfManufactureParameter, yearOfManufactureTop, yearOfManufactureBottom, yearOfManufactureOperation);
        predicateByParameter(root, criteriaBuilder, predicates, bodyTypeParameter, bodyType);
        predicateByParameter(root, criteriaBuilder, predicates, enginePowerParameter, enginePowerTop, enginePowerBottom, enginePowerOperation);
        predicateByParameter(root, criteriaBuilder, predicates, colorParameter, color);
        predicateByParameter(root, criteriaBuilder, predicates, mileAgeParameter, mileAgeTop, mileAgeBottom, mileAgeOperation);
        predicateByParameter(root, criteriaBuilder, predicates, priceParameter, priceTop, priceBottom, priceOperation);


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


    private void predicateByParameter(Root root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates
                                        , String parameter, String value ) {
        if (!StringUtils.isBlank(value)) {
            predicates.add(criteriaBuilder.equal(root.get(parameter), value));
        }
    }

    private void predicateByParameter(Root root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates
                                        , String parameter, Integer topValue, Integer bottomValue, String operationValue) {

        Predicate byTopValue;
        Predicate byBottomValue;

        if (topValue != null && bottomValue != null) {
            if (topValue > bottomValue) {
                byTopValue = criteriaBuilder.lessThanOrEqualTo(root.get(parameter), topValue);
                byBottomValue = criteriaBuilder.greaterThanOrEqualTo(root.get(parameter), bottomValue);
            } else {
                byBottomValue = criteriaBuilder.lessThanOrEqualTo(root.get(parameter), bottomValue);
                byTopValue = criteriaBuilder.greaterThanOrEqualTo(root.get(parameter), topValue);
            }
            predicates.add(criteriaBuilder.and(byBottomValue, byTopValue));
        } else if (!StringUtils.isBlank(operationValue)) {
            if (operationValue.equals("greater")) {
                if (topValue != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(parameter), topValue));
                } else {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(parameter), bottomValue));
                }
            } else if (operationValue.equals("less")) {
                if (topValue != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(parameter), topValue));
                } else {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(parameter), bottomValue));
                }
            } else if (topValue != null) {
                predicates.add(criteriaBuilder.equal(root.get(parameter), topValue));
            } else {
                predicates.add(criteriaBuilder.equal(root.get(parameter), bottomValue));
            }
        } else if (topValue != null) {
            predicates.add(criteriaBuilder.equal(root.get(parameter), topValue));
        } else if (bottomValue != null){
            predicates.add(criteriaBuilder.equal(root.get(parameter), bottomValue));
        } else {
        }
    }



}
