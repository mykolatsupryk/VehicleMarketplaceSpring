package mykola.tsupryk.vehiclemarketplacespring.exception;

public class InvalidEmailException extends Exception{

    public InvalidEmailException() {
        super("Invalid email adress");
    }
}
