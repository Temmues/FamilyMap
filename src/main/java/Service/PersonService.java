package Service;

import DAO.*;
import Model.AuthorizationToken;
import Model.Person;
import Results.MultiplePersonResult;
import Results.PersonResult;
import Results.Result;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;

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
            Person person = accessPerson.find("personID", token.getPersonID());
            if(confirmOwnerShip(person, personID, accessPerson)) // Check to make sure person belongs to user
            {
                 // throws invalid personID
                data.close(false);
                PersonResult result = new PersonResult(null,true, person.getAssociatedUsername(), person.getPersonID(),
                        person.getFirstName(), person.getLastName(), person.getGender(), person.getFatherID(),person.getFatherID(), person.getSpouseID());
                return result;
            }
            else
            {
                data.close(false);
                Result result = new Result("ERROR (Person and Token do not match)", false);
                return result;
            }
        }
        catch(DataAccessException e)
        {
            try
            {
                data.close(false);
            }
            catch(DataAccessException error)
            {
                System.out.println("ERROR!");
            }
            Result failedResult = new Result("Internal Service Error",false);
            return failedResult;
        }
    }

    private boolean confirmOwnerShip(Person person, String personID, DAOPerson accessPerson) throws DataAccessException
    {
        String momID = person.getMotherID();
        String dadID = person.getFatherID();
        boolean searchMom = true;
        boolean searchDad = true;

        if(personID.equals(person.getPersonID()))
        {
            return true;
        }
        else if(momID != null)
        {
            if (confirmOwnerShip(accessPerson.find("personID", momID), personID, accessPerson))
            {
                return true;
            }
        }
        else if (dadID != null)
        {
            if (confirmOwnerShip(accessPerson.find("personID", dadID), personID, accessPerson))
            {
                return true;
            }
        }
        return false;
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
            Person person = accessPerson.find("personID", token.getPersonID());
            family.add(person);
            if(person.getSpouseID() != null)
            {
                Person spouse = accessPerson.find("personID", person.getSpouseID());
                family.add(spouse);
            }
            findRelatedData(person, accessPerson, family);

            Person[] familyList = new Person[family.size()];
            familyList = family.toArray(familyList);
            data.close(false);
            return new MultiplePersonResult(familyList, true);
        }
        catch(DataAccessException e)
        {
            try
            {
                data.close(false);
            }
            catch(DataAccessException err)
            {

            }
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
        Person dad = null;
        Person mom = null;
        if(dadID != null)
        {
            dad = personAccess.find("personID", dadID);
            family.add(dad);
        }
        if(momID != null)
        {
            mom = personAccess.find("personID", momID);
            family.add(mom);
        }
        if(dadID != null)
        {
            findRelatedData(dad, personAccess, family);
        }
        if(momID != null)
        {
            findRelatedData(mom, personAccess, family);
        }
    }
}