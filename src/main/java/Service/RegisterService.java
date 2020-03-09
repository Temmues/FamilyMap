package Service;


import DAO.*;
import Model.AuthorizationToken;
import Model.Person;
import Model.User;
import Requests.RegisterRequest;
import Requests.Request;
import Results.LoginResult;
import Results.RegisterResult;
import Results.Result;

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
    public RegisterResult register(RegisterRequest r)  throws Exception
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
        DAOEvent eventAccess = null;
        DataBase data = null;
        try
        {
            data = new DataBase();
            Connection conn = data.getConnect();
            personAccess = new DAOPerson(conn);
            userAccess = new DAOUser(conn);
            eventAccess = new DAOEvent(conn);
            //query for username
            boolean usernameTaken = true;
            try
            {
                personAccess.find("username",person.getAssociatedUsername());
            }
            catch(DataAccessException e)
            {
                usernameTaken = false;
            }
            if(usernameTaken)
            {
                data.close(false);
                return new RegisterResult(false,"ERROR (username already registered)");
            }
            personAccess.insert(person);
            userAccess.insert(user);
            GenerateAncestorData genData = new GenerateAncestorData();
            JsonInfo info = new JsonInfo();
            eventAccess.insert(genData.generateBirth(person.getAssociatedUsername(), person.getPersonID(), 2025,'m', info.getPlace()));
            generateData(person, data);
            data.close(true);
            LoginService loginService = new LoginService();
            Request loginRequest = new Request(user.getUserName(), user.getPassword());
            Result result = loginService.login(loginRequest);
            if(result.isSuccess())
            {
                LoginResult loginResult = (LoginResult) result;
                String token = loginResult.getAuthToken();
                return new RegisterResult(token, user.getUserName(), personID,true,null);
            }
            else
            {
                return new RegisterResult(false, result.getMessage());
            }
        }
        catch(DataAccessException e)
        {
            try
            {
                data.close(false);
            }
            catch(DataAccessException error) {}

            return new RegisterResult(false,": Request property missing or has invalid value,");
        }
    }


    /**
     * Generates Ancestor Data
     */
    public void generateData(Person startPerson, DataBase data)
    {
        final int GEN_COUNT = 4;
        GenerateAncestorData genData = new GenerateAncestorData(GEN_COUNT);
        FillService fillService = new FillService();
        ArrayList<Person> ancestors = new ArrayList<Person>();
        JsonInfo placeMaker = new JsonInfo();

        ancestors.add(startPerson);
        for(int i = 0; i < GEN_COUNT; i++)
        {
            ArrayList<Person> newGeneration = new ArrayList<Person>();
            for(int j = 0; j < ancestors.size(); j++)
            {
                genData.generateParents(ancestors.get(j), newGeneration, data);
            }
            ancestors = newGeneration;
        }
    }
}
