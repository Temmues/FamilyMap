package Handlers;

import Results.Result;
import Service.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class EventHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;
        try
        {
            if(exchange.getRequestMethod().toLowerCase().equals("get"))
            {
                Headers header = exchange.getRequestHeaders();
                if(header.containsKey("Authorization"))
                {
                    String authToken = header.getFirst("Authorization");
                    URI url = exchange.getRequestURI();
                    String cmdUrl = url.toString();
                    Scanner parseUrl = new Scanner(cmdUrl);
                    parseUrl.useDelimiter("/");
                    if(parseUrl.hasNext() && parseUrl.next().equals("event"))
                    {
                        String json = null;
                        EventService service = null;
                        Gson g = new Gson();
                        Result result = null;
                        if(parseUrl.hasNext())
                        {
                            String eventID = parseUrl.next();
                            service = new EventService(eventID);
                            result = service.singleEvent(authToken);
                        }
                        else
                        {
                            service = new EventService(null);
                            result = service.multipleEvent(authToken);
                        }
                        json = g.toJson(result);

                        if(result.isSuccess())
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                        }
                        else
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                        }
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(json, respBody);
                        respBody.close();
                        success = true;
                    }
                }
                if(!success)
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
        }
        catch(Exception e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }

    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
