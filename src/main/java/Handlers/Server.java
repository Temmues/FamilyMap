package Handlers;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server
{
    //maximum number of incoming connections
    private static final int MAX_WAITNG_CONNECTION = 12;

    //Server instance
    private HttpServer server;

    //Initializes and runs the server
    private void run(String portNumber)
    {
        System.out.println("Server Start Up!");

        try
        {
            //instantiate the server
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)),MAX_WAITNG_CONNECTION);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return;
        }

        // Set default executor
        server.setExecutor(null);
        System.out.println("Creating contexts");
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill/", new FillHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/", new FileHandler());
        System.out.println("Starting server");
        //Start collecting incoming client requests
        server.start();
        System.out.println("server has been started");
    }


    public static void main(String[] args)
    {
        String portNum = args[0];
        new Server().run(portNum);
    }
}
