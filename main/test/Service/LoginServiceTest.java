package Service;

import DAO.DAOUser;
import DAO.DataAccessException;
import DAO.DataBase;
import Model.User;
import Requests.Request;
import Results.LoginResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class LoginServiceTest
{
    private LoginService testLogin = null;
    private Request testRequest = null;
    User testUser = null;
    DataBase data = null;
    LoginResult successResult = null;
    @BeforeEach
    void setUp()
    {
        data = new DataBase();
        testRequest = new Request("cat","ILikeCats");
        testUser = new User("cat","ILikeCats","horse@gmail.com","ferris","mcguffin","123456",'m');
        testLogin = new LoginService();
    }

    @Test
    void login()
    {
        // we could query for the id that comes back and use the key to figure out some nonsense
        try
        {
            Connection conn = data.getConnect();
            DAOUser access = new DAOUser(conn);
            access.insert(testUser);
            data.close(true);
        }
        catch(DataAccessException e)
        {
            System.out.println("Fail" + e.toString());
        }


    }
}