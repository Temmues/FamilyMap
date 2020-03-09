package Results;

/**
 * Result to be returned by RegisterService "register" Method
 */
public class RegisterResult extends Results.Result
{
    /**
     * authentication token
     */
    private String authToken;
    /**
     * Resulting username
     */
    private String userName;
    /**
     * Resulting person ID
     */
    private String personID;

    /**
     * @param authToken auth token
     * @param userName
     * @param personID
     *
     */
    public RegisterResult(String authToken, String userName, String personID, boolean success, String errorMessage)
    {
        super(errorMessage, success);
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public RegisterResult(boolean success, String errorMessage)
    {
        super(errorMessage, success);
    }

    public String getAuthToken()
    {
        return authToken;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPersonID()
    {
        return personID;
    }
}
