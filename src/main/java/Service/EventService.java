package Service;

import DAO.*;
import Model.AuthorizationToken;
import Model.Event;
import Model.Person;
import Results.EventResult;
import Results.MultipleEventResult;
import Results.PersonResult;
import Results.Result;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Service to deal with Event request
 */
public class EventService
{
    private String eventID = null;
    EventService(String eventID)
    {
        this.eventID = eventID;
    }
    /**
     * Returns the single Event object with the specified ID.
     * @return
     */
    public Result singleEvent(String authToken) throws DataAccessException
    {
        DataBase data = new DataBase();
        Connection conn = data.getConnect();
        try
        {

            DAOToken accessToken = new DAOToken(conn);
            DAOEvent accessEvent = new DAOEvent(conn);
            AuthorizationToken token = accessToken.find("token", authToken);
            Event event = accessEvent.find(eventID);
            if (token.getPersonID().equals(event.getPersonID())) // Check to make sure person belongs to user
            {
                EventResult result = new EventResult(event.getEventID(), event.getAssociatedUsername(), event.getPersonID(), event.getLatitude(),
                        event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear(), "", true);
                return result;
            }
            else
            {
                throw new Exception("EventID does match!");
            }
        }
        catch(Exception e)
        {
            Result failedResult = new Result("Internal Service Error",false);
            return failedResult;
        }
    }

    /**
     * Returns ALL events for ALL family members of the current user. The current
     * user is determined from the provided auth token.
     * @return
     */
    public Result multipleEvent(String authToken)
    {
        //add current events based on userID
        try
        {
            DataBase data = new DataBase();
            Connection conn = data.getConnect();
            DAOPerson accessPerson = new DAOPerson(conn);
            DAOEvent accessEvent = new DAOEvent(conn);
            DAOToken accessToken = new DAOToken(conn);

            AuthorizationToken token = accessToken.find("token", authToken);
            Person person = accessPerson.find(token.getPersonID());

            ArrayList<Event> events = new ArrayList<Event>();
            ArrayList<String> eventIDs = getEvents(person);
            for(String x: eventIDs)
            {
                events.add(accessEvent.find(x));
            }
            findRelatedEvents(person, accessPerson, accessEvent, events);

            Event[] eventList = new Event[events.size()];
            eventList = events.toArray(eventList);
            MultipleEventResult result = new MultipleEventResult(eventList, "", true);
            return result;
        }
        catch(DataAccessException e)
        {
            Result result = new Result("Access Error!", false);
            return result;
            // make bad result
        }
        catch(Exception e)
        {
            Result result = new Result("Related events finding Error!", false);
            return result;
        }

    }

    private void findRelatedEvents(Person person, DAOPerson personAccess, DAOEvent eventAccess, ArrayList<Event> events) throws Exception
    {
        assert personAccess != null;
        assert person != null;
        String momID = person.getMotherID();
        String dadID = person.getFatherID();
        if(momID != null)
        {
            Person mom = personAccess.find(momID);
            findRelatedEvents(mom, personAccess, eventAccess, events);
            //get event IDs related to person
            ArrayList<String> eventIDs = getEvents(mom);
            for(String x: eventIDs)
            {
                events.add(eventAccess.find(x));
            }
        }
        if(dadID != null)
        {
            Person dad = personAccess.find(dadID);
            findRelatedEvents(dad, personAccess, eventAccess, events);
            ArrayList<String> eventIDs = getEvents(dad);
            for(String x: eventIDs)
            {
                events.add(eventAccess.find(x));
            }
        }
    }

    private ArrayList<String> getEvents(Person person) throws Exception
    {
        DataBase data = new DataBase();
        Connection conn = data.getConnect();
        ArrayList<String> events = new ArrayList<String>();
        String cmd = "SELECT eventID " +
                "FROM event WHERE personID = '" + person.getPersonID() + "';";
        ResultSet res = null;
        try(Statement stmt = conn.createStatement())
        {
            res = stmt.executeQuery(cmd);
            while(res.next())
            {
                events.add(res.getString("eventID"));
            }
        }
        catch(SQLException e)
        {
            throw new Exception("query error");
        }
        return events;
    }
}
