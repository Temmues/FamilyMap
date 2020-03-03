package DAO;

import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DAOUserTest
{

    private DataBase data;
    private User testUser;

    @BeforeEach
    void setUp()
    {
        data = new DataBase();
        testUser = new User("CatMan97","Rambunctious","tanuki@yahoo.com","Kylie","White","1423",'f');
    }

    @AfterEach
    void cleanUp() throws Exception
    {
        Connection conn = data.getConnect();
        DAOUser access = new DAOUser(conn);
        access.clear();
        data.close(true);
    }

    @Test
    void insert() throws DataAccessException
    {
        Connection conn = data.getConnect();
        DAOUser access = new DAOUser(conn);
        access.insert(testUser);

        //Test Case #1 Insert two usernames with the same information, but different usernames and person ID's
        boolean duplicateFail = false;
        try
        {
            testUser.setPersonID("differs");
            testUser.setUserName("DogMan97");
            access.insert(testUser);
        }
        catch(DataAccessException e)
        {
            duplicateFail = true;
        }
        assertFalse(duplicateFail);

        //Test Case #2 Test reinsertion of the same object
        boolean insertFail = false;
        try
        {
            access.insert(testUser);
        }
        catch(DataAccessException e)
        {
            insertFail = true;
        }
        assertTrue(insertFail);

    }

    @Test
    void find() throws DataAccessException
    {
        Connection conn = data.getConnect();
        DAOUser access = new DAOUser(conn);

        //TEST #3 asserting an inserted object is findable
        access.insert(testUser);
        User cmprUser = access.find(testUser.getUserName());
        assertEquals(cmprUser, testUser);

        //TEST #4 finding a username that doesn't exist
        access.clear();
        boolean notFound = false;
        try
        {
            access.find(testUser.getUserName());
        }
        catch(DataAccessException e)
        {
            notFound = true;
        }
        assertTrue(notFound);
    }

    @Test
    void clear() throws DataAccessException
    {
        Connection conn = data.getConnect();
        DAOUser access = new DAOUser(conn);

        //TEST CASE #5 make sure clear after insert removes table data
        access.insert(testUser);
        access.clear();
        assertFalse(data.hasData("username","user"));

    }
}