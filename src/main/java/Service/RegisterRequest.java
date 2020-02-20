package Service;

/**
 * Request to Register a new user
 */
public class RegisterRequest extends Request
{
    /**
     * register account email
     */
    private String email;
    /**
     * register account firstname
     */
    private String firstname;
    /**
     * register account lastname
     */
    private String lastname;
    /**
     * register account gender
     */
    private char gender;


    /**
     * Parameterized constructor
     * @param username
     * @param password
     * @param email
     * @param lastname
     * @param gender
     * @param firstname
     */
    public RegisterRequest(String username, String password, String email, String lastname, char gender, String firstname)
    {
        super(username,password);
        this.email = email;
        this.lastname = lastname;
        this.gender = gender;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public String getEmail()
    {
        return email;
    }

    public String getLastname()
    {
        return lastname;
    }

    public char getGender()
    {
        return gender;
    }
}
