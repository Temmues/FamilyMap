package Service;

import DAO.DAOToken;
import DAO.DAOUser;
import DAO.DataAccessException;
import DAO.DataBase;
import Model.AuthorizationToken;
import Model.User;
import Requests.Request;
import Results.LoginResult;
import Results.Result;

import javax.xml.crypto.Data;
import java.sql.Connection;
import DAO.DataAccessException;

/**
 * Service to deal with login Requests
 */
public class LoginService
{
    /**
     * Log the user In
     * @param r
     * @return
     */
    public Result login(Request r)
    {
        String userName = r.getUserName();
        String passWord = r.getPassword();
        DataBase db = new DataBase(); // attempt to open connection
        Connection conn = null;
        try
        {
            conn = db.getConnect();
            DAOUser checkPassword = new DAOUser(conn);
            User currentUser = checkPassword.find(userName);// this will throw an exception if not found
            String currentPassword = currentUser.getPassword();
            if(currentPassword.equals(passWord))
            {
                String personID = currentUser.getPersonID();
                DAOToken accessToken = new DAOToken(conn);
                AuthorizationToken userToken = new AuthorizationToken(personID);
                accessToken.insert(userToken);
                db.close(true);
                return new LoginResult(true,userToken.getAuthToken(), userName, currentUser.getPersonID());
            }
            else
            {
                db.close(false);
                return new Result("Password or username incorrect",false);
            }
        }
        catch(DataAccessException e)
        {
            //System.out.println("Failure: " + e.toString());
            try
            {
                db.close(false);
            }
            catch(DataAccessException error)
            {
                System.out.println("Closing problem " + error.toString());
            }
            return new Result("username was not found",false);
        }
    }
}
