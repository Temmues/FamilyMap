package DAO;

import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DAOEventTest
{
    private DataBase data = null;
    private Event test = null;

    @BeforeEach
    void startUp()
    {
        data = new DataBase();
        test = new Event("1234567","Catman","first13423",34.7689, 137.3, "Japan","Toyohashi","Birth",1997);
    }

    @AfterEach
    void tearDown() throws DataAccessException
    {
        Connection conn = data.getConnect();
        DAOEvent event = new DAOEvent(conn);
        event.clear();
        data.close(true);
    }

    @Test
    void insert()
    {
        boolean passed = true;
        Connection conn = null;
        DAOEvent access = null;
        try
        {
            conn = data.openConnect();
            access = new DAOEvent(conn);
            access.insert(test);
            test.setEventID("lalalala");
            test.setPersonID("different");
            test.setAssociatedUsername("Monkey");
            access.insert(test);
        }
        catch(DataAccessException e)
        {
            System.out.println("Insertion Error!" + e.toString());
            passed = false;
        }
        //Test Case #1 inserting same data with different IDs and username
        assertTrue(passed);

        try
        {
            access.insert(test);
        }
        catch(DataAccessException e)
        {
            passed = false;
        }
        //Test Case #2 inserting same data
        assertFalse(passed);


    }

    @Test
    void find()
    {
        boolean passed = true;
        Event compareEvent = null;
        Connection conn = null;
        DAOEvent access = null;
        try
        {
            conn = data.getConnect();
            access = new DAOEvent(conn);
            access.insert(test);
            compareEvent = access.find(test.getEventID());

            //Test Case #3 inserted object is findable
            assertEquals(compareEvent, test);
        }
        catch(DataAccessException e)
        {
            System.out.println("Find Error!");
            passed = false;
        }
        assertTrue(passed);

        boolean found = true;
        try
        {
            //Test Case #4 attempting to find a phony eventID;
            compareEvent = access.find("134203");
        }
        catch(DataAccessException e)
        {
            found = false;
        }
        assertFalse(found);
    }

    @Test
    void clear()
    {
        boolean passed = true;
        try
        {
            Connection conn = data.openConnect();
            DAOEvent access = new DAOEvent(conn);
            access.insert(test);
            access.clear();

            // Test Case #5 ensuring data added is completely cleared
            assertFalse(data.hasData("eventID","event"));
        }
        catch(DataAccessException e)
        {
            System.out.println("Clear Error!");
            passed = false;
        }
        assertTrue(passed);
    }
}