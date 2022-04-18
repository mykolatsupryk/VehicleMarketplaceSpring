package mykola.tsupryk.vehiclemarketplacespring.enums;


import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;

public enum BodyType {

    SEDAN, VARIANT, HATCHBACK, SUV;

    public static BodyType checkBodyType (String b) throws UnreachebleTypeException {

        BodyType bodyType = null;

        for (BodyType iterate : BodyType.values()) {
            if (iterate.name().equalsIgnoreCase(b)) {
                bodyType = iterate;
                return bodyType;
            }
        }
        throw new UnreachebleTypeException("unreacheble bodyType name! Allowed bodyTypes: SEDAN, VARIANT, HATCHBACK, SUV;");
    }



}
