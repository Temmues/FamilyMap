package Service;


import DAO.DAOPerson;
import Model.Person;

import javax.xml.transform.Result;

/**
 * Service to deal with Register Requests
 */
public class RegisterService
{

    /**
     * Creates new account generates 4 generations of ancestor data and logs the user in
     * @param r RegisterRequest
     * @return The RegisterResult
     */
    public Result register(RegisterRequest r)
    {
        return null;
    }

    /**
     * Creates a new person
     * @param inputPerson
     */
    public void newAccount(DAOPerson inputPerson)
    {
    }

    /**
     * Generates Ancestor Data
     */
    public void generateAncestorData()
    {
    }
}
