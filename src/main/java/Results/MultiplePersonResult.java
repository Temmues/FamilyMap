package Results;

import Model.Event;
import Model.Person;

import javax.xml.transform.Result;

/**
 * Result to be returned by EventService "multipleEvent" Method
 */
public class MultiplePersonResult extends Results.Result
{
    /**
     * List of multiple events
     */
    private Person[] data;
    /**
     * Result message
     */


    public Person[] getData()
    {
        return data;
    }
    /**
     * Parameterized Constructor
     * @param data
     * @param success
     */
    public MultiplePersonResult(Person[] data, boolean success)
    {
        super(null,success);
        this.data = data;
    }
}
