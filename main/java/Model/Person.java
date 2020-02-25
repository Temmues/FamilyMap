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
    private String userName;
    /**
     * Person's firstname
     */
    private String firstName;
    /**
     * Person's lastname
     */
    private String lastName;
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
     * @param userName
     * @param firstName
     * @param lastName
     * @param gender
     */
    public Person(String personID, String userName, String firstName, String lastName, char gender)
    {
        this.personID = personID;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.motherID = null;
        this.fatherID = null;
        this.spouseID = null;
    }

    public String getPersonID()
    {
        return personID;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
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
                getUserName().equals(person.getUserName()) &&
                getFirstName().equals(person.getFirstName()) &&
                getLastName().equals(person.getLastName()) &&
                Objects.equals(getFatherID(), person.getFatherID()) &&
                Objects.equals(getMotherID(), person.getMotherID()) &&
                Objects.equals(getSpouseID(), person.getSpouseID());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getPersonID(), getUserName(), getFirstName(), getLastName(), getGender(), getFatherID(), getMotherID(), getSpouseID());
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "personID='" + personID + '\'' +
                ", username='" + userName + '\'' +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", gender=" + gender +
                ", fatherID='" + fatherID + '\'' +
                ", motherID='" + motherID + '\'' +
                ", spouseID='" + spouseID + '\'' +
                '}';
    }

    //toString and equals override
}
