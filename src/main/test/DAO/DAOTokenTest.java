package DAO;

import Model.AuthorizationToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DAOTokenTest
{
    private DataBase data = null;
    private AuthorizationToken authToken = null;

    @BeforeEach
    void setUp()
    {
        authToken = new AuthorizationToken("CatMan");
        authToken.setAuthToken("D7893421");
        data = new DataBase();
    }

    @AfterEach
    void tearDown()
    {
        Connection conn = null;
        try
        {
            conn = data.getConnect();
            DAOToken close = new DAOToken(conn);
            close.clear();
            data.close(true);
        }
        catch(DataAccessException e)
        {
            System.out.println("Error closing file"+ e.toString());
        }
    }

    @Test
    void insert()
    {
        DAOToken access = null;
       try
       {
           Connection conn = data.getConnect();
           access = new DAOToken(conn);

           //Test Case #1 inserted object is present in program
           access.insert(authToken);
           AuthorizationToken testToken = access.find(authToken.getPersonID());
           assertTrue(testToken.equals(authToken));
       }
       catch(DataAccessException e)
       {
           System.out.println("We broke! " + e.toString());
       }

       //Test Case #2 cannot insert multiple Items of the same type
       boolean doubleUp = true;
       try
       {
           access.insert(authToken);
       }
       catch(DataAccessException e)
       {
           doubleUp = false;
       }
       assertFalse(doubleUp);
    }

    @Test
    void multipleInserts()
    {
        //Test Case #3 ensure multiple inserts with different data do not throw any errors
        boolean threwError = false;
        try
        {
            Connection conn = data.getConnect();
            DAOToken access = new DAOToken(conn);
            access.insert(authToken);
            authToken.setAuthToken("04/22/1997");
            authToken.setPersonID("504023123");
            access.insert(authToken);
            authToken.setAuthToken("135022abcdefg");
            authToken.setPersonID("7083-40234");
            access.insert(authToken);
        }
        catch(DataAccessException e)
        {
            threwError = true;
        }
        assertFalse(threwError);
    }

    @Test
    void find()
    {
        DAOToken access = null;
        boolean threwError = false;
        try
        {
            Connection conn = data.getConnect();
            access = new DAOToken(conn);
            access.insert(authToken);

            //Test Case #5 finding recently inserted token
            AuthorizationToken testToken = access.find(authToken.getPersonID());
            assertEquals(testToken, authToken);
        }
        catch(DataAccessException e)
        {
            System.out.println("We broke! " + e.toString());
            threwError = true;
        }
        assertFalse(threwError);

        try
        {
            //Test Case #6 attempting to find token that does not exist
            AuthorizationToken testToken = access.find("SantaClause");
        }
        catch(DataAccessException e)
        {
            threwError = true;
        }
        assertTrue(threwError);
    }

    @Test
    void clear()
    {
        try
        {
           Connection conn = data.getConnect();
           DAOToken access = new DAOToken(conn);
           access.insert(authToken);
           access.clear();
           assertFalse(data.hasData("token","authtoken"));
        }
        catch(DataAccessException e)
        {
            System.out.println("Clear Error: " + e.toString());
        }
    }

    @Test
    void authenticate()
    {

    }
}