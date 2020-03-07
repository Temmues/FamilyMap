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
    public Result fill(String userName, int genCount) throws DataAccessException
    {
        assert userName != null;
        assert genCount > 0;
        addNumEvent = 0;
        addNumPerson = 0;
        // Authenticate person
        parentInfo = new JsonInfo();
        Person startPerson = null;
        startPerson = removePreviousData(userName);

        this.genCount = genCount;
        this.partCount = partCount;

        //fill single
        ArrayList<Person> ancestors = new ArrayList<Person>();
        Person person = fillSingle(userName);
        if(person != null)
        {
            ancestors.add(person);
            for(int i = 0; i < genCount; i++)
            {
                ArrayList<Person> newGeneration = new ArrayList<Person>();
                for(int j = 0; j < ancestors.size(); j++)
                {
                    generateParents(ancestors.get(j), newGeneration);
                }
                ancestors = newGeneration;
            }
            //gud result
        }
        //bad result
        return null;
    }

    public Person fillSingle(String userName) throws DataAccessException
    {
        DataBase data = null;
        try
        {
            parentInfo = new JsonInfo();
            GenerateAncestorData generate = new GenerateAncestorData();
            data = new DataBase();
            Connection conn = data.getConnect();
            DAOUser userAccess = new DAOUser(conn);
            DAOPerson personAccess = new DAOPerson(conn);
            DAOEvent eventAccess = new DAOEvent(conn);
            User user = userAccess.find(userName);

            Person person = personAccess.find(user.getPersonID());
            if(person == null)
            {
                person = new Person(user.getPersonID(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getGender());
                personAccess.insert(person);
            }
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
            // check to see if a person exists

            //if exists
            String personID = accessUser.find(userName).getPersonID();
            person = accessPerson.find(personID);
            if (person != null)
            {
                removeRelatedData(person, accessPerson, accessEvent);
                accessPerson.removeAncestorData(personID);
                accessEvent.removeAncestorData(personID);
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
            Person mom = personAccess.find(momID);
            removeRelatedData(mom, personAccess, eventAccess);
            personAccess.removeAncestorData(momID);
            eventAccess.removeAncestorData(momID);
        }
        if(dadID != null)
        {
            Person dad = personAccess.find(dadID);
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

    /**
     * generates parents with random events for a specific person and adds to data base
     * @return Array with pointers to both parents
     * @param currentGuy
     */
    private void generateParents(Person currentGuy, ArrayList<Person> generation)
    {
        assert currentGuy != null;
        ArrayList<Person> couple = new ArrayList<Person>();

        final int SIZE_ID = 5;
        // generate mom and dad based on info from person
        genCount--;

        String momID = new AuthorizationToken().generateToken(SIZE_ID);
        String dadID = new AuthorizationToken().generateToken(SIZE_ID);

        currentGuy.setFatherID(dadID);
        currentGuy.setMotherID(momID);

        String momName = parentInfo.getNameW();
        String dadName = parentInfo.getNameM();

        Person mom = new Person(momID, momName.toLowerCase(), momName, parentInfo.getSurname(),'f');
        Person dad = new Person(dadID, dadName.toLowerCase(), dadName, currentGuy.getLastName(),'m');

        mom.setSpouseID(dadID);
        dad.setSpouseID(momID);

        generation.add(mom);
        generation.add(dad);

        // opening data base to insert people and acquire year
        DAOEvent accessEvent = null;
        DAOPerson accessPerson = null;
        int year = 0;
        try
        {
            DataBase data = new DataBase();
            Connection conn = data.getConnect();
            accessEvent = new DAOEvent(conn);
            accessPerson = new DAOPerson(conn);
            accessPerson.updateField(currentGuy.getPersonID(), momID, "motherID");
            accessPerson.updateField(currentGuy.getPersonID(), dadID, "fatherID");
            year = accessEvent.findYear(currentGuy.getPersonID());
            String momUsername = momName.toLowerCase();
            String dadUsername = dadName.toLowerCase();
            accessPerson.insert(mom);
            accessPerson.insert(dad);

            addNumPerson += 2;
            addNumEvent += 6;

            Event momBirth = generateBirth(momUsername, momID, year, 'f', parentInfo.getPlace());
            Event dadBirth = generateBirth(dadUsername, dadID, year,'m',parentInfo.getPlace());
            Place marriageSpot = parentInfo.getPlace();
            Event marriage = generateMarriage(momBirth.getYear(), dadBirth.getYear(), marriageSpot);
            marriage.setAssociatedUsername(momUsername);
            marriage.setPersonID(momID);
            accessEvent.insert(marriage); // insert mom marriage
            marriage.setPersonID(dadID);
            marriage.setAssociatedUsername(dadUsername);
            marriage.setEventID(new AuthorizationToken().generateToken(4));

            //Death event
            //Mom
            Event momDeath = generateDeath(momBirth.getYear(), year, marriage.getYear(), parentInfo.getPlace());
            momDeath.setAssociatedUsername(momUsername);
            momDeath.setEventID(new AuthorizationToken().generateToken(4));
            momDeath.setPersonID(momID);

            //Dad
            Event dadDeath = generateDeath(dadBirth.getYear(), year, marriage.getYear(), parentInfo.getPlace());
            dadDeath.setAssociatedUsername(dadUsername);
            dadDeath.setPersonID(dadID);
            dadDeath.setEventID(new AuthorizationToken().generateToken(4));

            accessEvent.insert(marriage);
            accessEvent.insert(dadDeath);
            accessEvent.insert(momDeath);
            accessEvent.insert(momBirth);
            accessEvent.insert(dadBirth);

            data.close(true);
        }
        catch(DataAccessException e)
        {
            System.out.println("Error " + e.toString());
        }
    }

    private Event generateDeath(int birthYear, int childYear, int marriageYear, Place place)
    {
        Event death = new Event();
        int min = 0;
        int max = 0;
        // is either my kids birth year or the marriage year
        if(childYear >= marriageYear)
        {
            min = childYear;
        }
        else
        {
            min = marriageYear;
        }
        max = birthYear + 100;
        int year = generateYear(max, min);
        death.setYear(year);
        death.setCountry(place.getCountry());
        death.setCity(place.getCity());
        death.setLongitude(place.getLongitude());
        death.setLatitude(place.getLatitude());
        death.setEventType("death");

        return death;
    }
}
