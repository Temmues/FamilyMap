package Service;

import Results.PersonResult;
import Results.Result;

/**
 * Service to deal with Person Requests
 */
public class PersonService
{
    /**
     * Person ID
     */
    private String PersonID;

    /**
     * Parameterized Constructor
     * @param personID
     */
    public PersonService(String personID)
    {
        PersonID = personID;
    }

    /**
     * Returns the single Person object with the specified ID.
     * @return Person Result
     */
    public PersonResult searchPerson()
    {
        return null;
    }

    /**
     * Returns ALL family members of the current user.
     *
     * @return
     */
    public Result returnMembers()
    {
        return null;
    }
}