package Model;

/**
 * Class for holding user data
 */
public class User
{
    /**
     * User's username
     */
    private String username;
    /**
     * User's password
     */
    private String password;
    /**
     * User's email
     */
    private String email;
    /**
     * User's firstName
     */
    private String firstName;
    /**
     * User's lastName
     */
    private String lastName;
    /**
     * PersonID associated with User
     */
    private String personID;
    /**
     * User Gender
     */
    private char gender;

    /**
     * Parameterized Constructor
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param personID
     * @param gender
     */
    public User(String username, String password, String email, String firstName, String lastName, String personID, char gender)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personID = personID;
        this.gender = gender;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getPersonID()
    {
        return personID;
    }

    public char getGender()
    {
        return gender;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    //toString and equals override
}
