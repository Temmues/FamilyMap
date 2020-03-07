package Handlers;

import Requests.RegisterRequest;
import Results.RegisterResult;
import Results.Result;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;
        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                RegisterService service = new RegisterService();
                JSON convert = new JSON();
                InputStream input = exchange.getRequestBody();
                String json = convert.readString(input);
                Gson g = new Gson();
                RegisterRequest request = g.fromJson(json, RegisterRequest.class);
                Result result = service.register(request);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream out = exchange.getResponseBody();
                System.out.println(result.toString());
                String returnValue = g.toJson(result);
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
        catch(IOException e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
        catch(Exception e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
