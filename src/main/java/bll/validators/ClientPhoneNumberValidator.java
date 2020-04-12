package bll.validators;

import model.Client;
import java.util.regex.Pattern;

/**
 * Class that implements validator to check if a client phone number is valid.
 */
public class ClientPhoneNumberValidator implements Validator<Client>{

    /**
     * Regular expression to match a romanian phone number.
     */
    private static final String PHONE_NUMBER_PATTERN = "^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}$";

    /**
     * Validate the phone number of a client.
     * @param client client
     * @throws IllegalArgumentException ff the given phone number does not match the regex.
     */
    @Override
    public void validate(Client client) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        if (!pattern.matcher(client.getClient_phone_number()).matches()) {
            throw new IllegalArgumentException("Phone number is not a valid Phone number!");
        }
    }

}
