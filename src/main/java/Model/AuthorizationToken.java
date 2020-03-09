package Model;

import java.util.Objects;
import java.util.Random;

/**
 * Class for holding and generating user authorization token
 */
public class AuthorizationToken
{
    /**
     * Token value
     */
    private String authToken;
    private String personID;

    /**
     * Default Constructor
     */
    public AuthorizationToken()
    {
        authToken = "default_value";
    }

    /**
     * Parameterized Constructor
     * @param personID
     */
    public AuthorizationToken(String personID)
    {
        this.personID = personID;
        this.authToken = generateToken(9);
    }
    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof AuthorizationToken)) return false;
        AuthorizationToken that = (AuthorizationToken) o;
        return getAuthToken().equals(that.getAuthToken()) &&
                getPersonID().equals(that.getPersonID());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getAuthToken(), getPersonID());
    }

    public String getAuthToken()
    {
        return authToken;
    }

    public String getPersonID(){return personID;}



    /**
     * generates a randomized valid token for any given user
     */
    public String generateToken(int size)
    {
       // how to generate a random character or number
        String alphabet = "0123456789qwertyuiopasdfghjklzxcvbnm";
        Random r = new Random();
        StringBuilder authToken = new StringBuilder();
        for (int i = 0; i < size; i++)
        {
            authToken.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return authToken.toString();
    }

    //ToString and equals override
}
