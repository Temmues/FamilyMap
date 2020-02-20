package Model;

/**
 * Class for holding and generating user authorization token
 */
public class AuthorizationToken
{
    /**
     * Token value
     */
    private String token;

    /**
     * Default Constructor
     */
    public AuthorizationToken()
    {
        token = "default_value";
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }

    /**
     * generates a randomized valid token for any given user
     */
    private void generateToken()
    {

    }

    //ToString and equals override
}
