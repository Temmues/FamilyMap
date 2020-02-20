package Model;

import java.util.Objects;

/**
 * Class for holding person data
 */
public class Person
{
    /**
     * ID associated with Person
     */
    private String personID;
    /**
     * Person's username
     */
    private String username;
    /**
     * Person's firstname
     */
    private String firstname;
    /**
     * Person's lastname
     */
    private String lastname;
    /**
     * Person's gender
     */
    private char gender;
    /**
     * ID associated with father
     */
    private String fatherID;
    /**
     * ID associated with mother
     */
    private String motherID;
    /**
     * ID associated with spouse
     */
    private String spouseID;

    public Person()
    {

    }

    /**
     * Parameterized constructor
     * @param personID
     * @param username
     * @param firstname
     * @param lastname
     * @param gender
     */
    public Person(String personID, String username, String firstname, String lastname, char gender)
    {
        this.personID = personID;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.motherID = null;
        this.fatherID = null;
        this.spouseID = null;
    }

    public String getPersonID()
    {
        return personID;
    }

    public String getUsername()
    {
        return username;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public char getGender()
    {
        return gender;
    }

    public String getFatherID()
    {
        return fatherID;
    }

    public String getMotherID()
    {
        return motherID;
    }

    public String getSpouseID()
    {
        return spouseID;
    }

    public void setFatherID(String fatherID)
    {
        this.fatherID = fatherID;
    }

    public void setMotherID(String motherID)
    {
        this.motherID = motherID;
    }

    public void setSpouseID(String spouseID)
    {
        this.spouseID = spouseID;
    }

    public void setPersonID(String personID){this.personID = personID;}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getGender() == person.getGender() &&
                getPersonID().equals(person.getPersonID()) &&
                getUsername().equals(person.getUsername()) &&
                getFirstname().equals(person.getFirstname()) &&
                getLastname().equals(person.getLastname()) &&
                Objects.equals(getFatherID(), person.getFatherID()) &&
                Objects.equals(getMotherID(), person.getMotherID()) &&
                Objects.equals(getSpouseID(), person.getSpouseID());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getPersonID(), getUsername(), getFirstname(), getLastname(), getGender(), getFatherID(), getMotherID(), getSpouseID());
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "personID='" + personID + '\'' +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender=" + gender +
                ", fatherID='" + fatherID + '\'' +
                ", motherID='" + motherID + '\'' +
                ", spouseID='" + spouseID + '\'' +
                '}';
    }

    //toString and equals override
}
