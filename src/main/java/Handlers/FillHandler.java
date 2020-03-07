package Handlers;


import DAO.DataAccessException;
import Requests.Request;
import Results.Result;
import Service.FillService;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class FillHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;
        try
        {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                Headers reqHeaders = exchange.getRequestHeaders();
                FillService fillService = new FillService();
                URI urlInfo = exchange.getRequestURI();
                String urlString = urlInfo.toString();
                Scanner getUsername = new Scanner(urlString);
                getUsername.useDelimiter("/");

                String username = null;
                int genNum = 4;

                if(getUsername.hasNext())
                {
                    if(!getUsername.next().equals("fill"))
                    {
                        throw new IOException("url was bad");
                    }
                    username = getUsername.next();
                    genNum = 4;
                    if(getUsername.hasNext())
                    {
                        genNum = Integer.parseInt(getUsername.next());
                    }
                }

                fillService.fill(username, genNum);
                String result = "Successfully added " + fillService.getAddNumPerson()
                + " persons and " + fillService.getAddNumEvent() + " events to the database.”";
                Result goodResult = new Result(result, true);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream out = exchange.getResponseBody();
                Gson g = new Gson();
                JSON convert = new JSON();
                String returnValue = g.toJson(goodResult);
                convert.writeString(returnValue, out);
                out.close();
                success = true;
            }
            if (!success)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException | DataAccessException e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            Result badResult = new Result(e.toString(), false);
            Gson g = new Gson();
            OutputStream out = exchange.getResponseBody();
            JSON convert = new JSON();
            String returnValue = g.toJson(badResult);
            convert.writeString(returnValue, out);
            exchange.getResponseBody().close();
            //e.printStackTrace();
        }
    }
}
