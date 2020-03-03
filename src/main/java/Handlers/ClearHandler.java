package Handlers;

import DAO.DataAccessException;
import Results.Result;
import Service.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;
        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                JSON convert = new JSON();
                ClearService wipe = new ClearService();
                Result wipeResult = wipe.clear();
                Gson g = new Gson();
                String json = g.toJson(wipeResult);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream out = exchange.getResponseBody();
                convert.writeString(json, out);
                out.close();
                success = true;
            }
            if (!success)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(DataAccessException e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }

    }
}
