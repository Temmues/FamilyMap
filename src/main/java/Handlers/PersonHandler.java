
package Handlers;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import Results.Result;
import Service.FillService;
import Service.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

/*
	The ListGamesHandler is the HTTP handler that processes
	incoming HTTP requests that contain the "/games/list" URL path.
	
	Notice that ListGamesHandler implements the HttpHandler interface,
	which is define by Java.  This interface contains only one method
	named "handle".  When the HttpServer object (declared in the Server class)
	receives a request containing the "/games/list" URL path, it calls 
	ListGamesHandler.handle() which actually processes the request.
*/
class PersonHandler implements HttpHandler
{


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("get"))
            {

                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization"))
                {

                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization");
                    FillService fillService = new FillService();
                    URI urlInfo = exchange.getRequestURI();
                    String urlString = urlInfo.toString();
                    Scanner getUsername = new Scanner(urlString);
                    getUsername.useDelimiter("/");
                    PersonService service = null;
                    String personID = null;
                    String json = null;
                    Result result = null;
                    if (getUsername.next().equals("person"))
                    {

                        if(getUsername.hasNext())
                        {
                            personID = getUsername.next();
                            service = new PersonService(personID);
                            result = service.searchPerson(authToken);
                            Gson g = new Gson();
                            json = g.toJson(result);
                        }
                        else
                        {
                            service = new PersonService(personID);
                            result = service.returnMembers(authToken);
                            Gson g = new Gson();
                            json = g.toJson(result);
                        }

                        if(result.isSuccess())
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(json, respBody);

                        respBody.close();
                        success = true;
                    }
                }
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}