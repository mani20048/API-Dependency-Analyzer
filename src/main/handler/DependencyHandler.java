package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import util.GraphUtil;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DependencyHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        // very simple parsing (we improve later)
        String fromApi = body.split("\"fromApi\"\\s*:\\s*\"")[1].split("\"")[0];
        String toApi = body.split("\"toApi\"\\s*:\\s*\"")[1].split("\"")[0];

        try (Session session = GraphUtil.getSession()) {
            session.run(
                "MATCH (a:API {name:$from}), (b:API {name:$to}) " +
                "CREATE (a)-[:CALLS]->(b)",
                Values.parameters(
                    "from", fromApi,
                    "to", toApi
                )
            );
        }

        String response = "{ \"message\": \"Dependency created successfully\" }";
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
    }
}
