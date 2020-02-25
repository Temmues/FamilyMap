package Service;

import Results.EventResult;
import Results.MultipleEventResult;

/**
 * Service to deal with Event request
 */
public class EventService
{
    /**
     * Returns the single Event object with the specified ID.
     * @return
     */
    public EventResult singleEvent(String authToken)
    {
        return null;
    }

    /**
     * Returns ALL events for ALL family members of the current user. The current
     * user is determined from the provided auth token.
     * @return
     */
    public MultipleEventResult multipleEvent(String authToken)
    {
        return null;
    }
}
