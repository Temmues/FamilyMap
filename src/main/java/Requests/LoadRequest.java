package Requests;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.Arrays;

/**
 * Request to Load any number of User Person and Events
 */
public class LoadRequest
{
    /**
     * List of users
     */
    private User[] users;

    /**
     * List of people
     */
    private Person[] persons;

    /**
     * List of events
     */
    private Event[] events;

    /**
     * Parameterized Constructor
     * @param userList
     * @param peopleList
     * @param eventList
     */
    public LoadRequest(User[] userList, Person[] peopleList, Event[] eventList)
    {
        this.users = userList;
        this.persons = peopleList;
        this.events = eventList;
    }

    public LoadRequest()
    {

    }

    @Override
    public String toString()
    {
        return "LoadRequest{" +
                "userList=" + Arrays.toString(users) +
                ", peopleList=" + Arrays.toString(persons) +
                ", eventList=" + Arrays.toString(events) +
                '}';
    }

    public User[] getUsers()
    {
        return users;
    }

    public Person[] getPersons()
    {
        return persons;
    }

    public Event[] getEvents()
    {
        return events;
    }
}
