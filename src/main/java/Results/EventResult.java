package Results;

import Model.Event;

/**
 * EventResult to be returned by EventService "singleResult" Method
 */
public class EventResult extends Result
{
    /**
     * username associated with event
     */
    private String associatedUsername;
    /**
     * ID associated with event
     */
    private String eventID;
    /**
     * PersonID associate with event
     */
    private String personID;
    /**
     * latitude of event
     */
    private double latitude;
    /**
     * longitude of event
     */
    private double longitude;
    /**
     * country where event took place
     */
    private String country;
    /**
     * city where event took place
     */
    private String city;
    /**
     * kind of event
     */
    private String eventType;
    /**
     * Year when event takes place
     */
    private int year;

    public EventResult(String eventID, String associatedUsername, String personID, double latitude, double longitude, String country, String city, String eventType, int year, String msg, boolean success)
    {
        super(msg, success);
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public EventResult(String message, boolean success, String eventID, String associatedUsername, String personID, double latitude, double longitude, String country, String city, String eventType, int year)
    {
        super(message, success);
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }
}
