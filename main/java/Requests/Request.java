package Requests;

/**
 * General Request for the login in service
 * Serves as the basis for other requests
 */
public class Request
{
    /**
     * General request username
     */
    private String userName;
    /**
     * General request password
     */
    private String password;

    /**
     * Parameterized Constructor
     * @param userName
     * @param password
     */
    public Request(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }
}
