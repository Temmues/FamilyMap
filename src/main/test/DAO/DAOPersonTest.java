package DAO;

import Model.Person;
import javax.xml.crypto.Data;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DAOPersonTest
{
    private DataBase data;
    private Person testPerson;
    @org.junit.jupiter.api.BeforeEach
    void setUp()
    {
        //instantiate Data base
        data = new DataBase();
        testPerson = new Person("173820","CatMan97","Jones","McMacklson",'m');
    }

    @org.junit.jupiter.api.AfterEach
    void cleanUp() throws Exception
    {
        Connection conn = data.getConnect();
        DAOPerson closer = new DAOPerson(conn);
        closer.clear();
        data.close(true);
    }

    @org.junit.jupiter.api.Test
    void insert()
    {
        boolean duplicateTest = false;
        try
        {
            Connection conn = data.openConnect();
            DAOPerson accessPerson = new DAOPerson(conn);
            accessPerson.insert(testPerson);

            //TEST CASE #1 Ensuring objects with same data but different IDs are okay
            boolean doubleAllowed = true;
            try
            {
                testPerson.setPersonID("DogMan17");
                accessPerson.insert(testPerson);
            }
            catch(DataAccessException e)
            {
                doubleAllowed = false;
            }
            assertTrue(doubleAllowed);

            //TEST CASE #2 Reinsertion should throw an exception
            try
            {
                accessPerson.insert(testPerson);
            }
            catch (DAO.DataAccessException e)
            {
                duplicateTest = true;
            }
            assertTrue(duplicateTest);
        }
        catch(DAO.DataAccessException e)
        {
            System.out.println("ERROR " + e.toString());
        }
    }

    @org.junit.jupiter.api.Test
    void find()
    {
        try
        {
            Connection conn = data.openConnect();
            DAOPerson access = new DAOPerson(conn);
            access.insert(testPerson);
            Person testGuy = access.find("personID","173820");

            //TEST CASE #3 Ensuring that inserted object can be found
            assertEquals(testGuy,testPerson);

            //Clear Database
            access.clear();

            //TEST CASE #4 attempting to find a person with an ID that does not exist
            testPerson.setPersonID("174820");
            access.insert(testPerson);
            testGuy = access.find("personID", "173820");
            assertNotEquals(testGuy,testPerson);
        }
        catch(DataAccessException e)
        {
            System.out.printf("ERROR %s", e.toString());
        }
    }

    @org.junit.jupiter.api.Test
    void clear()
    {
        try
        {
          Connection conn = data.getConnect();
          DAOPerson access = new DAOPerson(conn);
          access.insert(testPerson);
          access.clear();

          //TESTCASE #5 query the database after clear to determine whether table is empty
          assertFalse(data.hasData("personID","person"));
        }
        catch(DataAccessException e)
        {
            System.out.printf("ERROR %s", e.toString());
        }

    }

}