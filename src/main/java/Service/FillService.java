package Service;

import DAO.*;
import Model.AuthorizationToken;
import Model.Event;
import Model.Person;
import Model.User;
import Results.Result;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;

/**
 * Service to deal with Fill request
 */
public class FillService
{
    private int genCount;
    private int partCount;
    private int addNumPerson;
    private int addNumEvent;
    private JsonInfo parentInfo;
    private String startID = null;
    /**
     * Populates the server's database with generated data for the specified user name.
     * @return Result
     */
    public Result fill(String userName, int genCount)
    {
        assert userName != null;
        assert genCount > 0;

        try
        {
            addNumEvent = 0;
            addNumPerson = 0;
            // Authenticate person
            parentInfo = new JsonInfo();
            Person startPerson = null;
            try
            {
                startPerson = removePreviousData(userName);
            }
            catch(DataAccessException e)
            {

            }
            GenerateAncestorData gen = new GenerateAncestorData();
            this.genCount = genCount;
            this.partCount = partCount;

            ArrayList<Person> ancestors = new ArrayList<Person>();
            DataBase data = new DataBase();
            Person person = fillSingle(userName, data);
            ancestors.add(person);
            for (int i = 0; i < genCount; i++)
            {
                ArrayList<Person> newGeneration = new ArrayList<Person>();
                for (int j = 0; j < ancestors.size(); j++)
                {
                    gen.generateParents(ancestors.get(j), newGeneration, data);
                }
                ancestors = newGeneration;
            }
            data.close(true);
            String message = "Successfully added " + gen.getAddNumPerson() + 1  +
                    " persons and " + gen.getAddNumEvent() + 1 + " events to the database.";
            return new Result(message, true);
        }
        catch(DataAccessException e)
        {
            return new Result("ERROR (Fill failed)", false);
        }
    }

    public Person fillSingle(String userName, DataBase data) throws DataAccessException
    {
        try
        {
            parentInfo = new JsonInfo();
            GenerateAncestorData generate = new GenerateAncestorData();
            Connection conn = data.getConnect();
            DAOUser userAccess = new DAOUser(conn);
            DAOPerson personAccess = new DAOPerson(conn);
            DAOEvent eventAccess = new DAOEvent(conn);
            User user = userAccess.find(userName);
            Person person = null;
            boolean personExists = true;
            person = new Person(user.getPersonID(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getGender());
            personAccess.insert(person);
            Event birth = new Event();
            birth.setEventID(new AuthorizationToken().generateToken(5));
            birth.setPersonID(person.getPersonID());
            birth.setEventType("birth");
            birth.setYear(1997);
            Place birthPlace = parentInfo.getPlace();
            birth.setCity(birthPlace.getCity());
            birth.setCountry(birthPlace.getCountry());
            birth.setLongitude(birthPlace.getLongitude());
            birth.setLatitude(birthPlace.getLatitude());
            birth.setAssociatedUsername(userName);
            eventAccess.insert(birth);
            data.close(true);
            return person;
        }
        catch(DataAccessException e)
        {
            data.close(false);
            return null;
        }
    }

    private Event generateMarriage(int momYear, int dadYear, Place place)
    {
        Event marriage = new Event();
        int min = 0;
        int max = 0;
        if(momYear > dadYear)
        {
            min = momYear + 20;
            max = dadYear + 80;
        }
        else
        {
            max = momYear + 80;
            min = dadYear + 20;
        }
        int year = generateYear(max, min);
        String ID = new AuthorizationToken().generateToken(4);
        marriage.setYear(year);
        marriage.setCountry(place.getCountry());
        marriage.setCity(place.getCity());
        marriage.setLatitude(place.getLatitude());
        marriage.setLongitude(place.getLongitude());
        marriage.setEventType("marriage");
        marriage.setEventID(ID);
        return marriage;
    }

    private int generateYear(int max, int min)
    {
        assert max >= min;
        Random ran = new Random();
        return ran.nextInt((max - min) + 1) + min;
    }

    private Event generateBirth(String userName, String personID, int childBirthday, char gender, Place place)
    {
        Event birth = new Event();
        Random ran = new Random();
        int ageLimit = 70;
        if(gender == 'f')
        {
            ageLimit = 50;
        }
        int max = childBirthday - 13;
        int min = childBirthday - ageLimit;
        int birthYear = generateYear(max, min);

        birth.setYear(birthYear);
        birth.setPersonID(personID);
        birth.setEventID(new AuthorizationToken().generateToken(4));
        birth.setEventType("birth");
        birth.setAssociatedUsername(userName);
        birth.setCity(place.getCity());
        birth.setCountry(place.getCountry());
        birth.setLongitude(place.getLongitude());
        birth.setLatitude(place.getLatitude());
        return birth;
    }

    /**
     * Authenticates and acquires user person
     * @param userName
     * @return
     * @throws DataAccessException
     */
    private Person removePreviousData(String userName) throws DataAccessException
    {
        Person person = null;
        DataBase data = new DataBase();
        Connection conn = data.getConnect();
        try
        {
            assert userName != null;
            DAOUser accessUser = new DAOUser(conn);
            DAOToken accessToken = new DAOToken(conn);
            DAOPerson accessPerson = new DAOPerson(conn);
            DAOEvent accessEvent = new DAOEvent(conn);
            String personID = accessUser.find(userName).getPersonID();
            boolean hasPerson = true;
            try
            {
                person = accessPerson.find("personID", personID);
            }
            catch(DataAccessException e)
            {
                hasPerson = false;
            }
            if(hasPerson)
            {
                accessPerson.removeAncestorData(userName);
                accessEvent.removeAncestorData(userName);
            }
            data.close(true);
        }
        catch(DataAccessException e)
        {
            data.close(false);
            throw e;
        }
        return person;
    }

    private void removeRelatedData(Person person, DAOPerson personAccess, DAOEvent eventAccess) throws DataAccessException
    {
        assert personAccess != null;
        assert eventAccess != null;
        assert person != null;
        String momID = person.getMotherID();
        String dadID = person.getFatherID();
        if(momID != null)
        {
            Person mom = personAccess.find("personID", momID);
            removeRelatedData(mom, personAccess, eventAccess);
            personAccess.removeAncestorData(momID);
            eventAccess.removeAncestorData(momID);
        }
        if(dadID != null)
        {
            Person dad = personAccess.find("personID", dadID);
            removeRelatedData(dad, personAccess, eventAccess);
            personAccess.removeAncestorData(dadID);
            eventAccess.removeAncestorData(dadID);
        }
    }


    public int getAddNumPerson()
    {
        return addNumPerson;
    }

    public int getAddNumEvent()
    {
        return addNumEvent;
    }


}
