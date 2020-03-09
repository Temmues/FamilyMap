package Results;

/**
 * Result to be returned by LoginService "Login" Method
 */
public class LoginResult extends Results.Result
{
    /**
     * Login token
     */
    private String authToken;

    /**
     * logged in username
     */
    private String userName;
    /**
     * Logged in PersonID
     */
    private String personID;

    /**
     * Parameterized constructor
     * @param success
     * @param authToken
     * @param userName
     * @param personID
     */
    public LoginResult(boolean success, String authToken, String userName, String personID)
    {
        super(null, success);
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public LoginResult()
    {
        super("default", true);
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

    @Override
    public String toString()
    {
        return "LoginResult{" +
                "token='" + authToken + '\'' +
                ", username='" + userName + '\'' +
                ", personID='" + personID + '\'' +
                '}';
    }
}
