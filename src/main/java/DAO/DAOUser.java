package DAO;
import Model.User;

import java.sql.*;

/**
 * Transfer userObject to database
 */
public class DAOUser
{
    /**
     * Database connection
     */
    private Connection conn;

    /**
     * parameterized constructor
     * @param conn
     */
    public DAOUser(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * transferring a User to the database
     * @param inputUser
     */
    public void insert(User inputUser) throws DataAccessException
    {
        String command = "INSERT INTO user (username, password, email, firstname, lastname, gender, personID)" +
                " VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(command))
        {
            StringBuilder genderMaker = new StringBuilder();
            genderMaker.append(inputUser.getGender());
            stmt.setString(1,inputUser.getUserName());
            stmt.setString(2,inputUser.getPassword());
            stmt.setString(3,inputUser.getEmail());
            stmt.setString(4,inputUser.getFirstName());
            stmt.setString(5,inputUser.getLastName());
            stmt.setString(6,genderMaker.toString());
            stmt.setString(7,inputUser.getPersonID());
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new DataAccessException("Insertion failure!: " + e.toString());
        }
    }

    public void setConn(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * locate a user in the database based on userID
     * @param username
     * @return The found user or and error is thrown
     */
    public User find(String username) throws DataAccessException
    {
        ResultSet res;
        User foundUser = new User();
        String command = "SELECT password, email, firstname, lastname, gender, personID FROM user " +
                "WHERE username = '" + username + "';";
        try(Statement stmt = conn.createStatement())
        {
            res = stmt.executeQuery(command);
            if(res.next())
            {
                StringBuilder makeGender = new StringBuilder(res.getString("gender"));
                foundUser.setUserName(username);
                foundUser.setPassword(res.getString("password"));
                foundUser.setEmail(res.getString("email"));
                foundUser.setFirstName(res.getString("firstname"));
                foundUser.setLastName(res.getString("lastname"));
                foundUser.setGender(makeGender.charAt(0));
                foundUser.setPersonID(res.getString("personID"));
                return foundUser;
            }
            else
            {
                throw new SQLException(username + "Was not found");
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("Finding failed: " + e.toString());
        }
    }

    public void clear() throws DataAccessException
    {
        try(Statement stmt = conn.createStatement())
        {
            String command = "DELETE FROM user";
            stmt.executeUpdate(command);
        }
        catch(SQLException e)
        {
            throw new DataAccessException("Unable to clear table");
        }
    }
}
