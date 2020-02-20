package Results;

/**
 * Result to be returned by RegisterService "register" Method
 */
public class RegisterResult extends Result
{
    /**
     * authentication token
     */
    private String token;
    /**
     * Resulting username
     */
    private String username;
    /**
     * Resulting person ID
     */
    private String personID;
    /**
     * Successfully executed flag
     */
    private boolean success;

    /**
     * @param token auth token
     * @param username
     * @param personID
     *
     */
    public RegisterResult(String token, String username, String personID, boolean success, String errorMessage)
    {
        super(errorMessage,success);
        this.token = token;
        this.username = username;
        this.personID = personID;
    }

    public String getToken()
    {
        return token;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPersonID()
    {
        return personID;
    }
}
