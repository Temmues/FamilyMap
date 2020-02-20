package DAO;

/**
 * Exception thrown when Data cannot be accessed
 */
public class DataAccessException extends Exception {
    DataAccessException(String message)
    {
        super(message);
    }

    DataAccessException()
    {
        super();
    }
}
