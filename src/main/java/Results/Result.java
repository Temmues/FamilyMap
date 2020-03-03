package Results;

/**
 * General result to be returned by ClearService, PersonService, FillService, and LoadService's methods
 */
public class Result
{
    /**
     * Result message
     */
    private String message;
    /**
     * Success message
     */
    private boolean success;

    public Result() {}

    /**
     * Parameterized Constructor
     * @param message
     * @param success
     */
    public Result(String message, boolean success)
    {
        this.message = message;
        this.success = success;
    }

    public String getMessage()
    {
        return message;
    }

    public boolean isSuccess()
    {
        return success;
    }

    @Override
    public String toString()
    {
        return "Result{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
