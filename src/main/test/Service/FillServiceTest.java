package Service;

import DAO.*;
import Handlers.LoginHandler;
import Model.AuthorizationToken;
import Model.Event;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest
{

    private DataBase data = null;
    private Person newGuy = null;
    private User firstPersonUser = null;
    private Event firstPersonBirth = null;
    private AuthorizationToken authToken = null;
    @BeforeEach
    void setUp() throws DataAccessException
    {
        data = new DataBase();
        newGuy = new Person("135022","CatMan97","Jones","McMacklson",'m');
        firstPersonUser = new User("Catman","12345","tombo.dragonfly","Jones","McMacklson","135022",'m');
        firstPersonBirth = new Event("ID","CatMan97","135022",14.2,39.8,"Canada","Lethbridge","Birth",1997);
        authToken = new AuthorizationToken("135022");
        authToken.generateToken(9);
        ClearService wipe = new ClearService();
        wipe.clear();
    }

    @Test
    void fill() throws DataAccessException
    {
        boolean threwError = false;
        FillService fillService = new FillService();
        try
        {
            Connection conn = data.getConnect();
            DAOEvent accessEvent = new DAOEvent(conn);
            DAOPerson accessPerson = new DAOPerson(conn);
            DAOUser accessUser = new DAOUser(conn);
            DAOToken accessToken = new DAOToken(conn);

            accessEvent.insert(firstPersonBirth);
            accessPerson.insert(newGuy);
            accessUser.insert(firstPersonUser);
            accessToken.insert(authToken);
            data.close(true);

            conn = data.getConnect();
            // Initial fill test #1
            fillService.fill("Catman",2);

            assertTrue(queryNumPerson(conn) == 7);
            assertTrue(queryNumEvents(conn) == 19);
            data.close(true);

            conn = data.getConnect();
            // Person info deletion test #2
            fillService.fill("Catman",3);
            assertTrue(queryNumPerson(conn) == 15);
            assertTrue(queryNumEvents(conn) == 43);
            data.close(true);
        }
        catch(DataAccessException e)
        {
           System.out.println("Error " + e.toString());
        }
        catch(SQLException e)
        {
            threwError = true;
            data.close(false);
        }
        assertFalse(threwError);
    }

    private int queryNumPerson(Connection conn) throws SQLException
    {
        String cmd = "SELECT COUNT(ALL) FROM person";
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(cmd);
        int numPerson = res.getInt(1);
        return numPerson;
    }

    private int queryNumEvents(Connection conn) throws SQLException
    {
        String cmd = "SELECT COUNT(ALL) FROM event";
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(cmd);
        int numEvent = res.getInt(1);
        return numEvent;
    }
}