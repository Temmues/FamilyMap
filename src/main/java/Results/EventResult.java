package Results;

import Model.Event;

/**
 * EventResult to be returned by EventService "singleResult" Method
 */
public class EventResult extends Event
{
    /**
     * Parameterized Constructor
     * @param eventID
     * @param username
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param currentYear
     */
    public EventResult(String eventID, String username, String personID, double latitude, double longitude, String country, String city, String eventType, int currentYear)
    {
        super(eventID, username, personID, latitude, longitude, country, city, eventType, currentYear);
    }
}
