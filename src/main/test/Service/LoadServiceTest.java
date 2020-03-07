package Service;

import DAO.DataAccessException;
import Requests.LoadRequest;
import Results.Result;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest
{

    @Test
    void loadService() throws IOException
    {
        File file = new File("json/example.json");
        try
        {
            Scanner test = new Scanner(file);
            LoadService loadService = new LoadService();
            StringBuilder build = new StringBuilder();
            JsonInfo info = new JsonInfo();
            while(test.hasNext())
            {
                build.append(test.nextLine());
            }
            Gson g = new Gson();
            LoadRequest request = g.fromJson(build.toString(), LoadRequest.class);
            ClearService wipe = new ClearService();
            wipe.clear();
            Result result = loadService.load(request);

            //General load testCase #1
            assertTrue(result.isSuccess());

            wipe.clear();
            String failJson = "{ \"users\": [" +
                    "    {" +
                    "      \"userName\": \"sheila\",\n" +
                    "      \"email\": \"sheila@parker.com\",\n" +
                    "      \"firstName\": \"Sheila\",\n" +
                    "      \"lastName\": \"Parker\",\n" +
                    "      \"gender\": \"f\",\n" +
                    "      \"personID\": \"Sheila_Parker\"\n" +
                    "    }\n" +
                    "  ]" +
                        "}";
            LoadRequest failRequest = g.fromJson(failJson, LoadRequest.class);
            Result fail = loadService.load(failRequest);

            //Loading json with missing values test case #2
            assertFalse(fail.isSuccess());
        }
        catch(DataAccessException e)
        {
            System.out.println(e.toString());
        }



    }
}