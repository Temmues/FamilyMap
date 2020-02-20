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
    private String personId;
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
     * @param personId
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     */
    public PersonResult(String message, boolean success, String associatedUsername, String personId, String firstName, String lastName, char gender, String fatherID, String motherID, String spouseID)
    {
        super(message, success);
        this.associatedUsername = associatedUsername;
        this.personId = personId;
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

    public String getPersonId()
    {
        return personId;
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
