package Results;

/**
 * Result to be returned by PersonService "searchPerson" Method
 */
public class PersonResult extends Result
{
    /**
     * Person username
     */
    private String associatedUsername;
    /**
     * Person ID
     */
    private String personID;
    /**
     * Person firstname
     */
    private String firstName;
    /**
     * Person lastName
     */
    private String lastName;
    /**
     * Person gender
     */
    private char gender;
    /**
     * Person's fatherID
     */
    private String fatherID;
    /**
     * persons mother ID
     */
    private String motherID;
    /**
     * Persons Spouse ID
     */
    private String spouseID;

    /**
     * Parameterized constructor
     * @param message
     * @param success
     * @param associatedUsername
     * @param personID
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     */
    public PersonResult(String message, boolean success, String associatedUsername, String personID, String firstName, String lastName, char gender, String fatherID, String motherID, String spouseID)
    {
        super(message, success);
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getAssociatedUsername()
    {
        return associatedUsername;
    }

    public String getPersonID()
    {
        return personID;
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
}
