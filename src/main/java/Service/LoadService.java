package Service;

import DAO.*;
import Model.Event;
import Model.Person;
import Model.User;
import Requests.LoadRequest;
import Results.Result;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Service to deal with Load requests
 */
public class LoadService
{
    /**
     * Clears all data from the database (just like the /clear API), and then loads the
     * posted user, person, and event data into the database.
     * @param r LoadRequest
     * @return Result
     */
    public Result load(LoadRequest r)
    {
        assert r != null;

        Person[] persons = r.getPersons();
        User[] users = r.getUsers();
        Event[] events = r.getEvents();

        DataBase data = new DataBase();
        DAOPerson personAccess = null;
        DAOUser userAccess = null;
        DAOEvent eventAccess = null;

        try
        {
            Connection conn = data.getConnect();
            personAccess = new DAOPerson(conn);
            userAccess = new DAOUser(conn);
            eventAccess = new DAOEvent(conn);

            //insert persons
            for(Person x: persons)
            {
                personAccess.insert(x);
            }

            //insert users
            for(User x: users)
            {
                userAccess.insert(x);
            }

            //insert events
            for(Event x: events)
            {
                eventAccess.insert(x);
            }

            data.close(true);
            String message = "Successfully added " + users.length + " users, " + persons.length +
            " persons, and " + events.length + " events to the database.";
            return new Result(message, true);
        }
        catch (DataAccessException e)
        {
            return new Result(e.toString(),false);
        }
        catch(NullPointerException e)
        {
            return new Result(e.toString(), false);
        }
    }
}
