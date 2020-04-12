package bll.validators;

/**
 * Interface to validate fields of certain data base tables.
 * @param <T> table of the data base
 */
public interface Validator<T> {

    /**
     * Method to be implemented depending on the table and the field.
     * @param t table of the data base
     */
    public void validate(T t);
}

