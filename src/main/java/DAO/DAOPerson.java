package DAO;

import Model.Person;

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
            stmt.setString(2,inputPerson.getAssociatedUsername());
            stmt.setString(3,inputPerson.getFirstName());
            stmt.setString(4,inputPerson.getLastName());
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

    public void setConn(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Find and return person object based on ID
     * @param column
     * @param value
     * @return
     */
    public Person find(String column, String value) throws DataAccessException
    {
        Person resultPerson;
        ResultSet res = null;
        String command = "SELECT personID, username, firstname, lastname, gender, fatherID, motherID, spouseID FROM person " +
                "WHERE " + column + " = '" + value  + "';";
        try(Statement stmt = conn.createStatement())
        {
            res = stmt.executeQuery(command);
            if(res.next())
            {
                StringBuilder gender = new StringBuilder(res.getString("gender"));
                resultPerson = new Person(res.getString("personID"), res.getString("username"), res.getString("firstname"),
                        res.getString("lastname"), gender.charAt(0));
                resultPerson.setFatherID(res.getString("fatherID"));
                resultPerson.setMotherID(res.getString("motherID"));
                resultPerson.setSpouseID(res.getString("spouseID"));
                return resultPerson;
            }
            else
            {
                throw new DataAccessException("Unable to find: person");
            }
        }
        catch(SQLException e)
        {
            throw new DataAccessException("Unable to find: " + e.toString());
        }
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

    public void updateField(String personID, String value, String field) throws DataAccessException
    {
        try(Statement stmt = conn.createStatement())
        {
            String comm = "UPDATE person " +
                    "SET " + field + " = '" + value +
                    "' WHERE personID = '" + personID + "';";
            stmt.executeUpdate(comm);
        }
        catch(SQLException e)
        {
            throw new DataAccessException("Error");
        }
    }

    public void removeAncestorData(String username) throws DataAccessException
    {
        String cmd = "DELETE FROM person WHERE username = '" + username + "'";
        try(Statement stmt = conn.createStatement())
        {
            stmt.executeUpdate(cmd);
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e.toString());
        }
    }
}
