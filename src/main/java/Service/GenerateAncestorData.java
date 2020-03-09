package Service;

import DAO.*;
import Model.AuthorizationToken;
import Model.Event;
import Model.Person;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;

public class GenerateAncestorData
{
    private int genCount;
    private JsonInfo parentInfo;
    private int addNumPerson;
    private int addNumEvent;

    GenerateAncestorData(int genCount)
    {
        this.genCount = genCount;
        parentInfo = new JsonInfo();
        addNumPerson = 1;
        addNumEvent = 0;
    }

    GenerateAncestorData()
    {
        parentInfo = new JsonInfo();
        addNumPerson = 0;
        addNumEvent = 0;
        genCount = 0;
    }

    public void generateParents(Person currentGuy, ArrayList<Person> generation, DataBase data)
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

    public Event generateBirth(String userName, String personID, int childBirthday, char gender, Place place)
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


    public int getAddNumPerson()
    {
        return addNumPerson;
    }

    public int getAddNumEvent()
    {
        return addNumEvent;
    }
}
