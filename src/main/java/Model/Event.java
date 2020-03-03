package Model;

import java.util.Objects;

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
    private int year;



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
     * @param year
     */
    public Event(String eventID, String username, String personID, double latitude, double longitude, String country, String city, String eventType, int year)
    {
        this.eventID = eventID;
        this.username = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Default constructor
     */
    public Event()
    {
    }

    public void setEventID(String eventID)
    {
        this.eventID = eventID;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public void setYear(int year)
    {
        this.year = year;
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

    public void setCity(String city)
    {
        this.city = city;
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

    public int getYear()
    {
        return year;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return Double.compare(event.getLatitude(), getLatitude()) == 0 &&
                Double.compare(event.getLongitude(), getLongitude()) == 0 &&
                getYear() == event.getYear() &&
                getEventID().equals(event.getEventID()) &&
                getUsername().equals(event.getUsername()) &&
                getPersonID().equals(event.getPersonID()) &&
                getCountry().equals(event.getCountry()) &&
                getCity().equals(event.getCity()) &&
                getEventType().equals(event.getEventType());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getEventID(), getUsername(), getPersonID(), getLatitude(), getLongitude(), getCountry(), getCity(), getEventType(), getYear());
    }

    @Override
    public String toString()
    {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", username='" + username + '\'' +
                ", personID='" + personID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", eventType='" + eventType + '\'' +
                ", year=" + year +
                '}';
    }
}
