package Model;

/**
 * Class for holding event data
 */
public class Event
{
    /**
     * ID sequence
     */
    private String eventID;
    /**
     * username associated with event
     */
    private String username;
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
    private int currentYear;

    /**
     * Parameterized constructor
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
    public Event(String eventID, String username, String personID, double latitude, double longitude, String country, String city, String eventType, int currentYear)
    {
        this.eventID = eventID;
        this.username = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.currentYear = currentYear;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getEventID()
    {
        return eventID;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPersonID()
    {
        return personID;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public String getCountry()
    {
        return country;
    }

    public String getCity()
    {
        return city;
    }

    public String getEventType()
    {
        return eventType;
    }

    public int getCurrentYear()
    {
        return currentYear;
    }

    public void setCity(String city)
    {
        this.city = city;
    }
}
