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


    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

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

    @Override
    public String toString()
    {
        return "Request{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
