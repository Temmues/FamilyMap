package Service;

import DAO.DataAccessException;
import Handlers.LoginHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest
{

    @BeforeEach
    void setUp()
    {

    }

    @Test
    void fill()
    {
        FillService service = new FillService();
        try
        {
            service.fill("Catman",3);
        }
        catch(DataAccessException e)
        {
           System.out.println("Error " + e.toString());
        }
        //instanatiate Fillservice
        //pass in username and ensure that it gets validated correctly
    }
}