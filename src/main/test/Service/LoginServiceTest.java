package Service;

import DAO.DAOToken;
import DAO.DAOUser;
import DAO.DataAccessException;
import DAO.DataBase;
import Model.AuthorizationToken;
import Model.User;
import Requests.Request;
import Results.LoginResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

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

    @AfterEach
    void tearDown()
    {

    }
    @Test
    void login()
    {
        DAOUser accessUser = null;
        DAOToken accessToken = null;
        boolean threwError = false;
        LoginResult checkResult = new LoginResult();
        try
        {
            Connection conn = data.getConnect();
            accessUser = new DAOUser(conn);
            accessToken = new DAOToken(conn);
            accessUser.insert(testUser);
            data.close(true);// insert and commit user into database

            //Test Case #1 ensure our expression returns loginResult and not a standard result (error)
            assertTrue(testLogin.login(testRequest).getClass() == checkResult.getClass());

            conn = data.getConnect();
            accessUser.setConn(conn);
            accessToken.setConn(conn);
            AuthorizationToken generatedToken = accessToken.find("personID", testUser.getPersonID());

            //Test Case #2 ensure that generated Token associated with username has a value
            assertTrue(generatedToken.getAuthToken() != null);
        }
        catch(DataAccessException e)
        {
            System.out.println("Fail" + e.toString());
            threwError = true;
        }
        assertFalse(threwError);
    }

    @Test
    void loginFail()
    {
        DAOUser accessUser = null;
        DAOToken accessToken = null;
        boolean threwError = false;
        LoginResult checkResult = new LoginResult();
        try
        {
            Connection conn = data.getConnect();
            accessUser = new DAOUser(conn);
            accessToken = new DAOToken(conn);
            testRequest.setPassword("DifferentPassword");
            accessUser.insert(testUser);
            data.close(true);

            //Test Case #3 ensure that error report is returned
            assertNotSame(testLogin.login(testRequest).getClass(), checkResult.getClass());
        }
        catch(DataAccessException e)
        {
            System.out.println("Fail" + e.toString());
            threwError = true;
        }
        assertFalse(threwError);
    }

    @Test
    void inputConfirmation()
    {
        testUser = new User("username","password","horse@gmail.com","ferris","mcguffin","123456",'m');
        try
        {
            Connection conn = data.openConnect();
            DAOUser access = new DAOUser(conn);
            access.insert(testUser);
            data.close(true);
        }
        catch(DataAccessException e)
        {
            System.out.println(e.toString());
        }
    }
}