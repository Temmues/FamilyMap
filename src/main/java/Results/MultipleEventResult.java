package Results;

import Model.Event;
/**
 * Result to be returned by EventService "multipleEvent" Method
 */
public class MultipleEventResult
{
    /**
     * List of multiple events
     */
    private Event[] data;
    /**
     * Result message
     */
    private String message;

    /**
     * Parameterized Constructor
     * @param data
     * @param message
     */
    public MultipleEventResult(Event[] data, String message)
    {
        this.data = data;
        this.message = message;
    }
}
