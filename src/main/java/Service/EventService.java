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
    public EventService(String eventID)
    {
        this.eventID = eventID;
    }
    /**
     * Returns the single Event object with the specified ID.
     * @return
     */
    public Result singleEvent(String authToken)
    {
        DataBase data = new DataBase();
        try
        {
            Connection conn = data.getConnect();
            DAOToken accessToken = new DAOToken(conn);
            DAOEvent accessEvent = new DAOEvent(conn);
            DAOPerson accessPerson = new DAOPerson(conn);
            AuthorizationToken token = accessToken.find("token", authToken);
            Event event = accessEvent.find(eventID);
            Boolean found = false;
            Person person = accessPerson.find("personID", token.getPersonID());
            if (confirmEvent(person, eventID, accessPerson, accessEvent)) // Check to make sure person belongs to user
            {
                data.close(false);
                EventResult result = new EventResult(event.getEventID(), event.getAssociatedUsername(), event.getPersonID(), event.getLatitude(),
                        event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear(), null, true);
                return result;
            }
            else
            {
                throw new Exception("EventID does match!");
            }
        }
        catch(Exception e)
        {
            try
            {
                data.close(false);
            } catch (DataAccessException error) {}
            Result failedResult = new Result("Internal Service Error",false);
            return failedResult;
        }
    }

    private boolean confirmEvent(Person person, String eventID, DAOPerson personAccess, DAOEvent eventAccess) throws Exception
    {
        assert eventID != null;
        assert person != null;
        String momID = person.getMotherID();
        String dadID = person.getFatherID();

        ArrayList<String> eventIDs = eventAccess.getEvents(person);
        if(eventIDs.contains(eventID))
        {
            return true;
        }
        else if (momID != null)
        {
            if(confirmEvent(personAccess.find("personID", momID), eventID, personAccess, eventAccess))
            {
                return true;
            }
        }
        else if (dadID != null)
        {
            if(confirmEvent(personAccess.find("personID", dadID), eventID, personAccess, eventAccess))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns ALL events for ALL family members of the current user. The current
     * user is determined from the provided auth token.
     * @return
     */
    public Result multipleEvent(String authToken)
    {
        //add current events based on userID
        DataBase data = new DataBase();
        ArrayList<Event> events = new ArrayList<Event>();
        try
        {
            Connection conn = data.getConnect();
            DAOPerson accessPerson = new DAOPerson(conn);
            DAOEvent accessEvent = new DAOEvent(conn);
            DAOToken accessToken = new DAOToken(conn);
            AuthorizationToken token = accessToken.find("token", authToken);
            Person person = accessPerson.find("personID", token.getPersonID());
            ArrayList<String> eventIDs = accessEvent.getEvents(person);
            for(String x: eventIDs)
            {
                Event newEvent = accessEvent.find(x);
                events.add(newEvent);
            }
            if(person.getSpouseID() != null)
            {
                Person spouse = accessPerson.find("personID", person.getSpouseID());
                ArrayList<String> spouseEvents = accessEvent.getEvents(spouse);
                for(String x: spouseEvents)
                {
                    Event newEvent = accessEvent.find(x);
                    events.add(newEvent);
                }
            }
            findRelatedEvents(person, accessPerson, accessEvent, events);



            Event[] eventList = new Event[events.size()];
            eventList = events.toArray(eventList);
            data.close(false);
            return new MultipleEventResult(eventList, null, true);
        }
        catch(DataAccessException e)
        {
            try
            {
                data.close(false);
            } catch(DataAccessException error){}
            Result result = new Result("Access Error!", false);
            return result;
            // make bad result
        }
        catch(Exception e)
        {
            try
            {
                data.close(false);
            } catch(DataAccessException error){}

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
        Person dad = null;
        Person mom = null;
        if(dadID != null)
        {
            // Person dad = null;
            dad = personAccess.find("personID", dadID);
            ArrayList<String> eventIDs = eventAccess.getEvents(dad);
            for(String x: eventIDs)
            {
                events.add(eventAccess.find(x));
            }

        }
        if(momID != null)
        {
            mom = personAccess.find("personID",  momID);
            ArrayList<String> eventIDs = eventAccess.getEvents(mom);
            for(String x: eventIDs)
            {
                events.add(eventAccess.find(x));
            }
        }
        if(dad != null)
        {
            findRelatedEvents(dad, personAccess, eventAccess, events);
        }
        if(mom != null)
        {
            findRelatedEvents(mom, personAccess, eventAccess, events);
        }
    }

}
