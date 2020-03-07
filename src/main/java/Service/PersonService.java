package Service;

import DAO.*;
import Model.AuthorizationToken;
import Model.Person;
import Results.MultiplePersonResult;
import Results.PersonResult;
import Results.Result;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Service to deal with Person Requests
 */
public class PersonService
{
    /**
     * Person ID
     */
    private String personID;

    /**
     * Parameterized Constructor
     * @param personID
     */
    public PersonService(String personID)
    {
        this.personID = personID;
    }

    /**
     * Returns the single Person object with the specified ID.
     * @return Person Result
     */
    public Result searchPerson(String authToken)
    {
        DataBase data = new DataBase();

        try
        {
            Connection conn = data.getConnect();
            DAOPerson accessPerson = new DAOPerson(conn);
            DAOToken accessToken = new DAOToken(conn);
            AuthorizationToken token = accessToken.find("token", authToken); // throws Invalid Auth Token
            if(token.getPersonID().equals(personID)) // Check to make sure person belongs to user
            {
                Person person = accessPerson.find(personID); // throws invalid personID
                PersonResult result = new PersonResult(null,true, person.getAssociatedUsername(), person.getPersonID(),
                        person.getFirstName(), person.getLastName(), person.getGender(), person.getFatherID(),person.getFatherID(), person.getSpouseID());
                return result;
            }
            else
            {
                Result result = new Result("Person and Token do not match", false);
                return result;

            }

        }
        catch(DataAccessException e)
        {
            Result failedResult = new Result("Internal Service Error",false);
            return failedResult;
        }
    }

    /**
     * Returns ALL family members of the current user.
     *
     * @return
     */
    public Result returnMembers(String authToken)
    {
        // invalid token validate token by getting id and then finding the person with that ID
        assert authToken != null;
        ArrayList<Person> family = new ArrayList<Person>();
        DataBase data = new DataBase();
        try
        {
            Connection conn = data.getConnect();
            DAOPerson accessPerson = new DAOPerson(conn);
            DAOToken accessToken = new DAOToken(conn);
            AuthorizationToken token = accessToken.find("token", authToken); // throws Invalid Auth Token
            Person person = accessPerson.find(token.getPersonID());
            findRelatedData(person, accessPerson, family);
            Person[] familyList = new Person[family.size()];
            familyList = family.toArray(familyList);
            return new MultiplePersonResult(familyList, true);
        }
        catch(DataAccessException e)
        {
            Result failedResult = new Result("Internal Service Error",false);
            return failedResult;
        }
    }

    private void findRelatedData(Person person, DAOPerson personAccess, ArrayList<Person> family) throws DataAccessException
    {
        assert personAccess != null;
        assert person != null;
        String momID = person.getMotherID();
        String dadID = person.getFatherID();
        if(momID != null)
        {
            Person mom = personAccess.find(momID);
            findRelatedData(mom, personAccess, family);
            family.add(mom);
        }
        if(dadID != null)
        {
            Person dad = personAccess.find(dadID);
            findRelatedData(dad, personAccess, family);
            family.add(dad);
        }
    }
}