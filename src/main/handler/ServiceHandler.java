package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;

import dao.ServiceDAO;
import model.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ServiceHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Allow only POST
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        // Read request body
        InputStream is = exchange.getRequestBody();
        String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        // Convert JSON -> Java object
        Gson gson = new Gson();
        Service service = gson.fromJson(requestBody, Service.class);

        // Save to MySQL
        ServiceDAO.save(service);

        // Response
        String response = "{ \"message\": \"Service saved successfully\" }";
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
    }
}
