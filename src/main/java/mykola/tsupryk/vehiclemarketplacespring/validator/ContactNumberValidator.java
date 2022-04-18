package mykola.tsupryk.vehiclemarketplacespring.validator;

import mykola.tsupryk.vehiclemarketplacespring.exception.InvalidContactNumberException;

public class ContactNumberValidator {

    public static boolean validatePhoneNumber(String phoneNo) throws InvalidContactNumberException {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{12}")) return true;
            //validating phone number with -, . or spaces "380 93 148 1369"
        else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{2}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\d{3}-\\(\\d{2}\\)-\\d{3}-\\d{4}")) return true;
            //return exception if nothing matches the input
        else throw new InvalidContactNumberException();

    }
}
