package mykola.tsupryk.vehiclemarketplacespring.exception;

public class InvalidContactNumberException extends Exception{

    public InvalidContactNumberException(String phoneNumber) {
        super("Invalid contact number: " + phoneNumber);
    }
}
