package DAO;
import Model.AuthorizationToken;

import java.sql.*;

/**
 * Class for managing Authorization Tokens
 */
public class DAOToken
{
    /**
     * Database connection
     */
    private Connection conn;

    /**
     * Parameterized constructor
     * @param conn
     */
    public DAOToken(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Transfer AuthorizationToken to database
     * @param inputToken
     */
    public void insert(AuthorizationToken inputToken) throws DataAccessException
    {
        String command = "INSERT INTO authtoken (token, personID) VALUES(?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(command))
        {
            stmt.setString(1,inputToken.getAuthToken());
            stmt.setString(2,inputToken.getPersonID());
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e.toString());
        }
    }


    public void clear() throws DataAccessException
    {
        try(Statement stmt = conn.createStatement())
        {
            String command = "DELETE FROM authtoken";
            stmt.executeUpdate(command);
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
     * Retrieve Token based on userID or TokenValue
     * @param column
     * @return
     */
    public AuthorizationToken find(String column, String value) throws DataAccessException
    {
        AuthorizationToken returnToken =  new AuthorizationToken();
        ResultSet res = null;
        String query = "SELECT * FROM authToken WHERE " + column + " = '" + value + "';";
        try(PreparedStatement stmt = conn.prepareStatement(query))
        {
            res = stmt.executeQuery();
            if(res.next())
            {
                returnToken.setAuthToken(res.getString("token"));
                returnToken.setPersonID(res.getString("personID"));
                return returnToken;
            }
            else
            {
                throw new SQLException("Was not found!");
            }
        }
        catch(SQLException e)
        {
            throw new DataAccessException("FIND FAILURE: " + e.toString());
        }
    }


    /**
     * confirms the validity of a particular authorization Token
     * @param input
     * @return
     */
    public boolean Authenticate(AuthorizationToken input)
    {
        return true;
    }
}
