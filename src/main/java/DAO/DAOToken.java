package DAO;
import Model.AuthorizationToken;

import javax.naming.AuthenticationException;
import java.sql.Connection;

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
    public void insert(AuthorizationToken inputToken)
    {

    }

    /**
     * Retrieve Token based on userID
     * @param userID
     * @return
     */
    public AuthorizationToken find(String userID)
    {
        return null;
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
