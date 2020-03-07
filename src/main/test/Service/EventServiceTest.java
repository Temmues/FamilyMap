package Service;

import DAO.*;
import Model.AuthorizationToken;
import Model.Event;
import Model.User;
import Results.MultipleEventResult;
import Results.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest
{
    private Event event = null;
    private User user = null;
    private AuthorizationToken token = null;
    private DataBase data = null;

    @BeforeEach
    void setUp() throws DataAccessException
    {
        user = new User("CatMan97","Rambunctious","tanuki@yahoo.com","Kylie","White","135022",'m');
        data = new DataBase();
        token = new AuthorizationToken();
        token.setAuthToken(token.generateToken(9));
        token.setPersonID(user.getPersonID());
        event = new Event("12345","CatMan97","135022",34.7689, 137.3, "Japan","Toyohashi","Birth",1997);
        Connection conn = data.getConnect();

        DAOEvent accessEvent = new DAOEvent(conn);
        DAOToken accessToken = new DAOToken(conn);
        DAOUser accessUser = new DAOUser(conn);

        ClearService wipe = new ClearService();
        wipe.clear();
        //accessEvent.insert(event);
        accessToken.insert(token);
        accessUser.insert(user);
        data.close(true);
    }

    @Test
    void singleEvent() throws Exception
    {
        boolean failedQuery = false;
        EventService service = null;
        try
        {
            service = new EventService(event.getEventID());
            Result result = service.singleEvent("FakeAuthToken");
            if(!result.isSuccess())
            {
                failedQuery = true;
            }
        }
        catch(Exception e)
        {
            throw e;
        }
        assertTrue(failedQuery);


        //Test Case 2 query for authentic personID
        Result result = service.singleEvent(token.getAuthToken());
        assertTrue(result.isSuccess());
    }

    @Test
    void multipleEvent() throws Exception
    {
        EventService service = null;
        FillService occupy = new FillService();
        occupy.fill(event.getAssociatedUsername(), 2);
        boolean failedQuery = false;
        try
        {
            service = new EventService(token.getPersonID());
            Result result = service.multipleEvent("Fake token!");
            if(!result.isSuccess())
            {
                failedQuery = true;
            }
        }
        catch(Exception e)
        {
            throw e;
        }
        assertTrue(failedQuery);


        //Test Case 2 query for authentic personID
        Result result = service.multipleEvent(token.getAuthToken());
        assertTrue(result.isSuccess());

        //Test Case 3 make  sure output data has 19 elements
        MultipleEventResult outcome = (MultipleEventResult) result;
        assertTrue(outcome.getData().length == 19);
    }
}