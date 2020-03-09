package Service;

import DAO.DataAccessException;
import Requests.RegisterRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest
{

    @Test
    void register() throws Exception
    {
        ClearService clearService = new ClearService();
        clearService.clear();
        String json = "{\n" +
                "\t\"userName\":\"username\",\n" +
                "\t\"password\":\"password\",\n" +
                "\t\"email\":\"email\",\n" +
                "\t\"firstName\":\"firstname\",\n" +
                "\t\"lastName\":\"lastname\",\n" +
                "\t \"gender\":\"m\"\n" +
                "}";
        RegisterService registerService = new RegisterService();
        Gson g = new Gson();
        RegisterRequest r = g.fromJson(json, RegisterRequest.class);
        registerService.register(r);
    }

    @Test
    void generateAncestorData()
    {

    }
}