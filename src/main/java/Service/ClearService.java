package Service;

import DAO.*;
import Results.Result;

import java.sql.Connection;

/**
 * Service to deal with Clear request
 */
public class ClearService
{
    /**
     * Clear database
     * @return Result Object
     *
     */
    public Result clear() throws DataAccessException
    {
        DataBase data = new DataBase();
        try
        {
            Connection conn = data.openConnect();
            DAOUser user = new DAOUser(conn);
            DAOToken token = new DAOToken(conn);
            DAOPerson person = new DAOPerson(conn);
            DAOEvent event = new DAOEvent(conn);

            user.clear();
            token.clear();
            person.clear();
            event.clear();
            data.close(true);
            return new Result("Clear succeeded",true);
        }
        catch(DataAccessException e)
        {
            return new Result("Error: " + e.toString(), false);
        }
    }
}
