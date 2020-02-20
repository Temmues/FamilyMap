package DAO;
import Model.User;

import java.sql.Connection;

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
    public void insert(User inputUser)
    {

    }

    /**
     * locate a user in the database based on userID
     * @param userID
     * @return
     */
    public User find(String userID)
    {
        return null;
    }
}
