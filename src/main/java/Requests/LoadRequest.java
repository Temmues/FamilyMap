package Requests;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * Request to Load any number of User Person and Events
 */
public class LoadRequest
{
    /**
     * List of users
     */
    private User[] userList;

    /**
     * List of people
     */
    private Person[] peopleList;

    /**
     * List of events
     */
    private Event[] eventList;

    /**
     * Parameterized Constructor
     * @param userList
     * @param peopleList
     * @param eventList
     */
    public LoadRequest(User[] userList, Person[] peopleList, Event[] eventList)
    {
        this.userList = userList;
        this.peopleList = peopleList;
        this.eventList = eventList;
    }
}
