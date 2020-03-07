package Service;


import DAO.*;
import Model.AuthorizationToken;
import Model.Person;
import Model.User;
import Requests.RegisterRequest;
import Requests.Request;
import Results.LoginResult;
import Results.RegisterResult;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.util.ArrayList;

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
    public RegisterResult register(RegisterRequest r)
    {
        String personID = new AuthorizationToken().generateToken(5);
        User user = new User();
        user.setUserName(r.getUserName());
        user.setGender(r.getGender());
        user.setFirstName(r.getFirstName());
        user.setLastName(r.getLastName());
        user.setPassword(r.getPassword());
        user.setEmail(r.getEmail());
        user.setPersonID(personID);

        Person person = new Person(personID, r.getUserName(), r.getFirstName(), r.getLastName(), r.getGender());
        DAOPerson personAccess = null;
        DAOUser userAccess = null;
        try
        {
            DataBase data = new DataBase();
            Connection conn = data.getConnect();
            personAccess = new DAOPerson(conn);
            userAccess = new DAOUser(conn);

            personAccess.insert(person);
            userAccess.insert(user);
            data.close(true);
            generateData(person);
            LoginService loginService = new LoginService();
            Request loginRequest = new Request(user.getUserName(), user.getPassword());
            LoginResult result = null;
            result = (LoginResult) loginService.login(loginRequest);
            String token = result.getAuthToken();
            if(result.isSuccess())
            {
                return new RegisterResult(token, user.getUserName(), personID,true,"Good Job!");
            }
            else
            {
                return new RegisterResult(false,": Request property missing or has invalid value,");
                //bad message
            }
        }
        catch(DataAccessException e)
        {
            return new RegisterResult(false,": Request property missing or has invalid value,");
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        return null;
    }


    /**
     * Generates Ancestor Data
     */
    public void generateData(Person startPerson)
    {
        final int GEN_COUNT = 4;
        GenerateAncestorData genData = new GenerateAncestorData(GEN_COUNT);
        FillService fillService = new FillService();
        ArrayList<Person> ancestors = new ArrayList<Person>();
        ancestors.add(startPerson);
        for(int i = 0; i < GEN_COUNT; i++)
        {
            ArrayList<Person> newGeneration = new ArrayList<Person>();
            for(int j = 0; j < ancestors.size(); j++)
            {
                genData.generateParents(ancestors.get(j), newGeneration);
            }
            ancestors = newGeneration;
        }
    }
}
