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
    private Event[] eventList;
    /**
     * Result message
     */
    private String message;

    /**
     * Parameterized Constructor
     * @param eventList
     * @param message
     */
    public MultipleEventResult(Event[] eventList, String message)
    {
        this.eventList = eventList;
        this.message = message;
    }
}
