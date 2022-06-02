package mykola.tsupryk.vehiclemarketplacespring.exception;

import mykola.tsupryk.vehiclemarketplacespring.entity.Vehicle;

public class NoMoneyException extends RuntimeException{

    public NoMoneyException(Vehicle vehicle) {
        super("Owner " + vehicle.getOwner().getId() + ". " + vehicle.getOwner().getName()
                + " doesn't have money to create order with "
                + vehicle.getId() + ". " + vehicle.getBrand().getName() + " " + vehicle.getModel().getName());
    }
}
