package Service;

import DAO.*;
import Model.Person;
import Results.Result;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Service to deal with Fill request
 */
public class FillService
{
    private int genCount;
    private int partCount;
    private String startID = null;
    /**
     * Populates the server's database with generated data for the specified user name.
     * @return Result
     */
    public Result fill(String userName, int genCount) throws DataAccessException
    {
        assert userName != null;
        assert genCount > 0;

        // Authenticate person

        Person startPerson = AuthenticatePerson(userName);
        this.genCount = genCount;
        this.partCount = partCount;

        ArrayList<Person> ancestors = new ArrayList<Person>();
        ancestors.add(startPerson);
        for(int i = 0; i <= genCount; i++)
        {
            ArrayList<Person> newGeneration = new ArrayList<Person>();
            for(int j = 0; j < ancestors.size(); j++)
            {
                generateParents(ancestors.get(i), newGeneration);
            }
            ancestors = newGeneration;
        }



        // set generation count
        // set recursion off
        return null;
    }

    /**
     * Authenticates and acquires user person
     * @param userName
     * @return
     * @throws DataAccessException
     */
    private Person AuthenticatePerson(String userName) throws DataAccessException
    {
        assert userName != null;
        DataBase data = new DataBase();
        Connection conn = data.getConnect();
        DAOUser accessUser = new DAOUser(conn);
        DAOToken accessToken = new DAOToken(conn);
        DAOPerson accessPerson = new DAOPerson(conn);
        try
        {
            String personID = accessUser.find(userName).getPersonID();
            String token = accessToken.find(personID).getAuthToken();
            //FIXME: search and destroy
            //FIXME: generate person function
            Person newGuy = new Person(personID,"CatMan97","Jones","McMacklson",'m');
            //accessPerson.insert(newGuy);
            data.close(true);
            return newGuy;
        }
        catch(DataAccessException e)
        {
            System.out.println("ERROR " + e.toString());
            data.close(false);
            return null;
        }
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
        // generate mom and dad based on info from person
        genCount--;
        //generate firstname, lastname, username, and personID

        //generate Events birth, marriage, and death
        Person mom = new Person("uniqueID","batman","mcjones","henry",'f');
        Person dad = new Person("stuff","wonderWoman","dad","dad",'m');
        //generateFirstName(mom);
        //generateLastName(dad);

        generation.add(mom);
        generation.add(dad);
        //FIXME: generate events
        // put into database
        System.out.println("putting a mom into database");
        System.out.println("putting a dad into database");



    }
}
