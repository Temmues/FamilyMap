package DAO;

import java.sql.*;

/**
 * Opens closes and clears Database
 */
public class DataBase
{
    /**
     * Database connection
     */
    private Connection connect;

    /**
     * Opens the database
     * @return Connection
     * @throws DataAccessException
     */
    public Connection openConnect() throws DataAccessException
    {
        try
        {
            final String URL = "jdbc:sqlite:familymap.sqlite";
            connect = DriverManager.getConnection(URL);
            connect.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            System.out.println("Unable to connect to database");
        }
        return connect;
    }

    /**
     * gets connection
     * @return Connection
     * @throws DataAccessException
     */
    public Connection close(boolean commit) throws DataAccessException
    {
        try
        {
            if(commit)
            {
                connect.commit();
            }
            else
            {
                connect.rollback();
            }
            connect.close();
            connect = null;
        }
        catch(SQLException e)
        {
            System.out.println("Error closing connection");
        }
        return connect;
    }

    /**
     * Close connection
     *
     */
    public Connection getConnect() throws DataAccessException
    {
        if (connect == null)
        {
            return openConnect();
        }
        else
        {
            return connect;
        }

    }

    /**
     * Clear dataBase
     */
    public void wipe()
    {

    }

    public boolean hasData(String ID, String table) throws DataAccessException
    {
        try(Statement stmt = connect.createStatement())
        {
            String command = "SELECT " + ID + " FROM " + table;
            ResultSet res = stmt.executeQuery(command);
            if(!res.next())
            {
                return false;
            }
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e.toString());
        }
        return true;
    }

}
