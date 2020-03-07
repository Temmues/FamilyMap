package Service;

import DAO.*;
import Model.AuthorizationToken;
import Model.Person;
import Model.User;
import Results.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest
{

    private Person newGuy = null;
    private AuthorizationToken token = null;
    private DataBase data = null;
    private User user = null;
    @BeforeEach
    void setUp() throws DataAccessException
    {
        newGuy = new Person("135022","CatMan97","Jones","McMacklson",'m');
        token = new AuthorizationToken();
        token.setAuthToken(token.generateToken(9));
        token.setPersonID(newGuy.getPersonID());
        user = new User("CatMan97","Rambunctious","tanuki@yahoo.com","Kylie","White","135022",'m');
        data = new DataBase();

        Connection conn = data.getConnect();
        DAOPerson accessPerson = new DAOPerson(conn);
        DAOToken accessToken = new DAOToken(conn);
        DAOUser accessUser = new DAOUser(conn);

        ClearService wipe = new ClearService();
        wipe.clear();
        accessPerson.insert(newGuy);
        accessToken.insert(token);
        accessUser.insert(user);
        data.close(true);
    }


    @Test
    void searchPerson() throws Exception
    {
        //Test Case #1 search for invalid personID
        boolean failedQuery = false;
        PersonService service = null;
        try
        {
            service = new PersonService(newGuy.getPersonID());
            Result result = service.searchPerson("FakeAuthToken");
            if(!result.isSuccess())
            {
                failedQuery = true;
            }
        }
        catch(Exception e)
        {
            throw e;
        }
        assertTrue(failedQuery);


        //Test Case 2 query for authentic personID
        Result result = service.searchPerson(token.getAuthToken());
        assertTrue(result.isSuccess());
    }

    @Test
    void returnMembers() throws Exception
    {
        FillService occupy = new FillService();
        occupy.fill(newGuy.getAssociatedUsername(), 2);

        //Test Case #1 search for invalid personID
        boolean failedQuery = false;
        PersonService service = null;
        try
        {
            service = new PersonService(newGuy.getPersonID());
            Result result = service.returnMembers("FakeAuthToken");
            if(!result.isSuccess())
            {
                failedQuery = true;
            }
        }
        catch(Exception e)
        {
            throw e;
        }
        assertTrue(failedQuery);


        //Test Case 2 query for authentic personID
        Result result = service.returnMembers(token.getAuthToken());
        assertTrue(result.isSuccess());
    }
}