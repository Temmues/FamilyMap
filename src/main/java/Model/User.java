package Model;

import java.util.Objects;

/**
 * Class for holding user data
 */
public class User
{
    /**
     * User's username
     */
    private String userName;
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
     * @param userName
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param personID
     * @param gender
     */
    public User(String userName, String password, String email, String firstName, String lastName, String personID, char gender)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personID = personID;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getGender() == user.getGender() &&
                getUserName().equals(user.getUserName()) &&
                getPassword().equals(user.getPassword()) &&
                getEmail().equals(user.getEmail()) &&
                getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getPersonID().equals(user.getPersonID());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getUserName(), getPassword(), getEmail(), getFirstName(), getLastName(), getPersonID(), getGender());
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }

    public void setGender(char gender)
    {
        this.gender = gender;
    }

    public User()
    {
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

    public String getUserName()
    {
        return userName;
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
