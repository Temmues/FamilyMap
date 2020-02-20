package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
     * @param commit
     */
    public Connection getConnect(boolean commit) throws DataAccessException
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

}
