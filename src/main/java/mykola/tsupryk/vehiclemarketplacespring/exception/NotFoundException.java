package mykola.tsupryk.vehiclemarketplacespring.exception;

public class NotFoundException extends Exception{

    public NotFoundException(String message) {
        super(message + " does not found");
    }
}
