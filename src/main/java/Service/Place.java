package Service;

public class Place
{
    private String city = null;
    private String country = null;
    private double latitude;
    private double longitude;

    public Place(String city, String country, double latitude, double longitude)
    {
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Place(){}

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getCity()
    {
        return city;
    }

    public String getCountry()
    {
        return country;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    @Override
    public String toString()
    {
        return "Place{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
