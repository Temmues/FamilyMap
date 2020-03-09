package Handlers;

import Requests.Request;
import Results.LoginResult;
import Results.Result;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.print.DocFlavor;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class LoginHandler implements HttpHandler
{
    LoginResult check = new LoginResult();
    Result normCheck = new Result();

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;
        try
        {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                JSON convert = new JSON();
                InputStream reqBody = exchange.getRequestBody();
                String output = convert.readString(reqBody);
                Gson g = new Gson();
                Request request = (Request) g.fromJson(output, Request.class);

                LoginService access = new LoginService();
                Result result = access.login(request);

                if(result.isSuccess())
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                OutputStream out = exchange.getResponseBody();
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
    }
}
