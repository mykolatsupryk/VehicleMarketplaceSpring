package mykola.tsupryk.vehiclemarketplacespring.enums;


import mykola.tsupryk.vehiclemarketplacespring.exception.UnreachebleTypeException;

public enum Color {

    WHITE, RED, BLACK, BLUE, GRAY, GREEN, BROWN;

    public static Color checkColor (String c) throws UnreachebleTypeException {

        Color color = null;

        for (Color iterate : Color.values()) {
            if (iterate.name().equalsIgnoreCase(c)) {
                color = iterate;
                return color;
            }
        }
        throw new UnreachebleTypeException("unreacheble color name! Allowed colors: WHITE, RED, BLACK, BLUE, GRAY, GREEN, BROWN;");
    }


}
