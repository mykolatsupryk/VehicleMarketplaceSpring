package mykola.tsupryk.vehiclemarketplacespring.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message + " does not found");
    }
}
