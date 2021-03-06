package mykola.tsupryk.vehiclemarketplacespring.validator;

import mykola.tsupryk.vehiclemarketplacespring.exception.InvalidEmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN =
            "^(?=.{1,64}@)[a-z0-9_-]+(\\.[a-z0-9_-]+)*@"
                    + "[^-][a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValid(final String email) throws InvalidEmailException {
        Matcher matcher = pattern.matcher(email);
        boolean result = matcher.matches();
        if (result)
            return true;
        else throw new InvalidEmailException();

    }

}
