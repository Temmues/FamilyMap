package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = true;
        if (exchange.getRequestMethod().toLowerCase().equals("get"))
        {

            StringBuilder path = new StringBuilder();
            path.append(exchange.getRequestURI().getPath());
            if(path.toString().equals("/"))
            {
                path = new StringBuilder("C:/Users/tombo/FamilyMap/web/index.html");
            }
            else
            {
                StringBuilder base = new StringBuilder("C:/Users/tombo/FamilyMap/web");
                base.append(path.toString());
                path = base;
            }


            Path url = Paths.get(path.toString());

            OutputStream respBody = exchange.getResponseBody();
            Files.copy(url, respBody);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            respBody.close();
            success = true;
        }
        else
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }
    }
}
