package Results;

/**
 * Result to be returned by LoginService "Login" Method
 */
public class LoginResult extends Result
{

    /**
     * Login token
     */
    private String token;
    /**
     * logged in username
     */
    private String username;
    /**
     * Logged in PersonID
     */
    private String personID;

    /**
     * Parameterized constructor
     * @param message
     * @param success
     * @param token
     * @param username
     * @param personID
     */
    public LoginResult(String message, boolean success, String token, String username, String personID)
    {
        super(message, success);
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
