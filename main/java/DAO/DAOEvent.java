package DAO;
import Model.Event;

import java.sql.Connection;

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
    public void insert(Event inputEvent)
    {

    }

    /**
     * Locate an event based on ID
     * @return Event object
     */
    public Event find(String eventID)
    {
        return null;
    }
}
