package server;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import handler.ServiceHandler;
import handler.ApiHandler;

public class MainServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/health", exchange -> {
            String response = "{ \"status\": \"OK\" }";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        });
        server.createContext("/services/add", new ServiceHandler());
        server.createContext("/apis/add", new ApiHandler());

        server.start();
        System.out.println("Server started on port 8080");
    }
}
