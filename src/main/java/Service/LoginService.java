package Service;

import DAO.*;
import Model.AuthorizationToken;
import Model.User;
import Requests.Request;
import Results.LoginResult;
import Results.Result;


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
            DAOToken accessToken = new DAOToken(conn);

            User currentUser = checkPassword.find(userName);// this will throw an exception if not found
            checkPassword.find(userName);
            String currentPassword = currentUser.getPassword();
            if(currentPassword.equals(passWord))
            {
                String personID = currentUser.getPersonID();
                AuthorizationToken userToken = new AuthorizationToken(personID);
                accessToken.insert(userToken);
                db.close(true);
                return new LoginResult(true,userToken.getAuthToken(), userName, currentUser.getPersonID());
            }
            else
            {
                db.close(false);
                return new Result("Error (password or username incorrect)",false);
            }
        }
        catch(DataAccessException e)
        {
            try
            {
                db.close(false);
            }
            catch(DataAccessException error)
            {
                System.out.println("Closing problem " + error.toString());
            }
            return new Result("Error (login failed)",false);
        }
    }
}
