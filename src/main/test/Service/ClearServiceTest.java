package Service;

import DAO.*;
import Model.AuthorizationToken;
import Model.Event;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest
{
    private DataBase data = null;
    Event testEvent = null;
    Person testPerson = null;
    AuthorizationToken testToken = null;
    User testUser = null;

    @Test
    void clear()
    {
        data = new DataBase();
        testEvent = new Event("1234567","Catman","first13423",34.7689, 137.3, "Japan","Toyohashi","Birth",1997);
        testPerson = new Person("173820","CatMan97","Jones","McMacklson",'m');
        testToken = new AuthorizationToken("CatMan");
        testToken.setAuthToken("D7893421");
        testUser = new User("CatMan97","Rambunctious","tanuki@yahoo.com","Kylie","White","1423",'f');

        try
        {
            Connection conn = data.getConnect();
            ClearService wipe = new ClearService();
            DAOEvent accessEvent = new DAOEvent(conn);
            DAOUser accessUser = new DAOUser(conn);
            DAOToken accessToken = new DAOToken(conn);
            DAOPerson accessPerson = new DAOPerson(conn);

            accessEvent.insert(testEvent);
            accessUser.insert(testUser);
            accessToken.insert(testToken);
            accessPerson.insert(testPerson);
            data.close(true);

            wipe.clear();
            data.getConnect();
            final int TABLE_NUM = 4;
            String tableNames[] = {"event","user","authToken","person"};

            //Test Case #1 ensure after deletion nothing is left
            for (int i = 0; i < TABLE_NUM; i++)
            {
                assertFalse(data.hasData("personID", tableNames[i]));
            }
            data.close(false);
        }
        catch(DataAccessException e)
        {
            System.out.println("Error " + e.toString());
        }
    }


}