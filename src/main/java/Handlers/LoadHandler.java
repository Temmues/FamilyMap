package Handlers;

import DAO.DataAccessException;
import Requests.LoadRequest;
import Results.Result;
import Service.ClearService;
import Service.JsonInfo;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;
        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                //Get json body and create request
                JSON convert = new JSON();
                LoadService loadService = new LoadService();
                ClearService clearService = new ClearService();
                InputStream input = exchange.getRequestBody();
                String inputJson = convert.readString(input);
                System.out.println(inputJson);
                Gson g = new Gson();
                LoadRequest request = g.fromJson(inputJson, LoadRequest.class);

                clearService.clear();
                Result result = loadService.load(request);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream output = exchange.getResponseBody();
                String returnValue = g.toJson(result);
                convert.writeString(returnValue, output);
                output.close();
                success = true;

            }
            if(!success)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.close();
            }
        }
        catch(DataAccessException e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
        catch(Exception e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
