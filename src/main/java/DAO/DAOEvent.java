package DAO;
import Model.Event;
import Model.Person;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class for transfer and retrieval of events
 */
public class DAOEvent
{
    /**
     * Database connection
     */
    private Connection conn;

    /**
     * Parameterized Constructor
     * @param conn
     */
    public DAOEvent(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Transfer event into database
     * @param inputEvent
     */
    public void insert(Event inputEvent) throws DataAccessException
    {
        String command = "INSERT INTO event (eventID, username, personID, latitude, longitude, country, city, currentYear, eventType)" +
                " VALUES(?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(command))
        {
            stmt.setString(1, inputEvent.getEventID());
            stmt.setString(2, inputEvent.getAssociatedUsername());
            stmt.setString(3, inputEvent.getPersonID());
            stmt.setString(4, Double.toString(inputEvent.getLatitude()));
            stmt.setString(5, Double.toString(inputEvent.getLongitude()));
            stmt.setString(6, inputEvent.getCountry());
            stmt.setString(7, inputEvent.getCity());
            stmt.setString(8, Integer.toString(inputEvent.getYear()));
            stmt.setString(9, inputEvent.getEventType());
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new DataAccessException("Error: " + e.toString());
        }
    }

    /**
     * Locate an event based on ID
     * @return Event object
     */
    public Event find(String eventID) throws DataAccessException
    {
        Event currentEvent = new Event();
        try(Statement stmt = conn.createStatement())
        {
            String cmd = "SELECT eventID, username, personID, latitude, longitude, country, city, currentYear, eventType " +
                    "FROM event WHERE eventID = '" + eventID + "';";
            ResultSet res = stmt.executeQuery(cmd);
            currentEvent.setEventID(res.getString("eventID"));
            currentEvent.setAssociatedUsername(res.getString("username"));
            currentEvent.setPersonID(res.getString("personID"));
            currentEvent.setLatitude(Double.parseDouble(res.getString("latitude")));
            currentEvent.setLongitude(Double.parseDouble(res.getString("longitude")));
            currentEvent.setCountry(res.getString("country"));
            currentEvent.setCity(res.getString("city"));
            currentEvent.setYear(Integer.parseInt(res.getString("currentYear")));
            currentEvent.setEventType(res.getString("eventType"));
            return currentEvent;
        }
        catch(SQLException e)
        {
            throw new DataAccessException("Error: " + e.toString());
        }
    }

    public void clear() throws DataAccessException
    {
        try(Statement stmt = conn.createStatement())
        {
            String cmd = "DELETE FROM event";
            stmt.executeUpdate(cmd);
        }
        catch(SQLException e)
        {
            throw new DataAccessException("Error: " + e.toString());
        }
    }

    public int findYear(String personID) throws DataAccessException
    {
        String cmd = "SELECT currentYear " +
                "FROM event WHERE personID = '" + personID + "' AND eventType = 'birth';";
        ResultSet res = null;
        try(Statement stmt = conn.createStatement())
        {
            res = stmt.executeQuery(cmd);
            String year = res.getString("currentYear");
            return Integer.parseInt(year);
        }
        catch(SQLException e)
        {
            return 1997;
        }
    }

    public void removeAncestorData(String username) throws DataAccessException
    {
        String cmd = "DELETE FROM event WHERE username = '" + username + "'";
        try(Statement stmt = conn.createStatement())
        {
            stmt.executeUpdate(cmd);
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e.toString());
        }
    }
    public ArrayList<String> getEvents(Person person) throws Exception
    {
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
