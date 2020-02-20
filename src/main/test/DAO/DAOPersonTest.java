package DAO;

import Model.Person;

import javax.xml.crypto.Data;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DAOPersonTest
{
    private DataBase data;
    Person testPerson;
    @org.junit.jupiter.api.BeforeEach
    void setUp()
    {
        //instantiate a Data base object for the connection
        data = new DataBase();
        testPerson = new Person("173820","CatMan97","Jones","McMacklson",'m');
    }

    @org.junit.jupiter.api.AfterEach
    void cleanUp() throws Exception
    {
        Connection conn = data.openConnect();
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
            Person compareInstance = accessPerson.find(testPerson.getPersonID());

            //TEST CASE #1 Ensuring inserted object is in database
            assertEquals(compareInstance, testPerson);

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
            Person testGuy = access.find("173820");
            System.out.println(testGuy.toString());
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
          Connection conn = data.openConnect();
          DAOPerson access = new DAOPerson(conn);
          access.clear();
          data.close(true);
        }
        catch(DataAccessException e)
        {
            System.out.printf("ERROR %s", e.toString());
        }

    }
}