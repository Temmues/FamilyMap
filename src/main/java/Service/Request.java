package Service;

/**
 * General Request for the login in service
 * Serves as the basis for other requests
 */
public class Request
{
    /**
     * General request username
     */
    private String username;
    /**
     * General request password
     */
    private String password;

    /**
     * Parameterized Constructor
     * @param username
     * @param password
     */
    public Request(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}
