package DAO;

import Model.Person;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * Class for transfer and retrieval of Persons
 */
public class DAOPerson
{
    /**
     * Database connection
     */
    private Connection conn;

    /**
     * Parameterized constructor
     * @param conn
     */
    public DAOPerson(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Transfer person to database
     * @param inputPerson
     */
    public void insert(Person inputPerson) throws DataAccessException
    {
        String command = "INSERT INTO person (personID, username, firstname, lastname, gender, fatherID, motherID, spouseID)" +
                " VALUES(?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(command))
        {
            StringBuilder genderMaker = new StringBuilder();
            genderMaker.append(inputPerson.getGender());
            stmt.setString(1,inputPerson.getPersonID());
            stmt.setString(2,inputPerson.getUsername());
            stmt.setString(3,inputPerson.getFirstname());
            stmt.setString(4,inputPerson.getLastname());
            stmt.setString(5,genderMaker.toString());
            if(inputPerson.getFatherID() == null)
            {
                stmt.setString(6,null);
            }
            else
            {
                stmt.setString(6,inputPerson.getFatherID());
            }
            if(inputPerson.getMotherID() == null)
            {
                stmt.setString(7,null);
            }
            else
            {
                stmt.setString(7,inputPerson.getMotherID());
            }
            if(inputPerson.getSpouseID() == null)
            {
                stmt.setString(8,null);
            }
            else
            {
                stmt.setString(8,inputPerson.getSpouseID());
            }
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e.toString());
        }
    }

    /**
     * Find and return person object based on ID
     * @param personID
     * @return
     */
    public Person find(String personID)
    {
        //we need to find the code to query for a certainID
        //SELECT username, firstname, lastname, gender, fatherID, motherID, spouseID
        //FROM person
        //WHEN personID = 'personID'
        Person resultPerson;
        ResultSet res = null;
        String command = "SELECT username, firstname, lastname, gender, fatherID, motherID, spouseID FROM person " +
                "WHERE personID = " + personID  + ";";
        try(Statement stmt = conn.createStatement())
        {
            res = stmt.executeQuery(command);
            if(res.next())
            {
                StringBuilder gender = new StringBuilder(res.getString("gender"));
                resultPerson = new Person(personID, res.getString("username"), res.getString("firstname"),
                        res.getString("lastname"), gender.charAt(0));
                resultPerson.setFatherID(res.getString("fatherID"));
                resultPerson.setMotherID(res.getString("motherID"));
                resultPerson.setSpouseID(res.getString("spouseID"));
                return resultPerson;
            }
        }
        catch(SQLException e)
        {
            System.out.printf("ERROR ", e.toString());
        }
        finally
        {
            if(res != null)
            {
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    System.out.printf("ERROR ",e.toString());
                }
            }
        }
        return null;
    }

    /**
     * Clears person data from database
     */
    public void clear() throws DataAccessException
    {
        try(Statement stmt = conn.createStatement())
        {
            String command = "DELETE FROM person";
            stmt.executeUpdate(command);
        }
        catch(SQLException e)
        {
            throw new DataAccessException("ERROR");
        }
    }
}
