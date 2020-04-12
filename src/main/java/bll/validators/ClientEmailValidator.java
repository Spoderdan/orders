package bll.validators;

import model.Client;
import java.util.regex.Pattern;

/**
 * Class that implements validator to check if a client email address is valid.
 */
public class ClientEmailValidator implements Validator<Client>{

    /**
     * Regular expression to match an email address
     */
    private static final String EMAIL_PATTERN = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";

    /**
     * Validate the email of a client.
     * @param client client
     * @throws IllegalArgumentException if the email does not match the regex.
     */
    @Override
    public void validate(Client client) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (!pattern.matcher(client.getClient_email()).matches()) {
            throw new IllegalArgumentException("Email is not a valid email!");
        }
    }

}
