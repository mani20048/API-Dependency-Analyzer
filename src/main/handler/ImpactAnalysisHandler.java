package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import util.GraphUtil;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import org.neo4j.driver.Values;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ImpactAnalysisHandler implements HttpHandler {

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

        String apiName = body.split("\"apiName\"\\s*:\\s*\"")[1].split("\"")[0];

        List<String> impactedApis = new ArrayList<>();

        try (Session session = GraphUtil.getSession()) {
            Result result = session.run(
                "MATCH (start:API {name:$name})-[:CALLS*1..]->(impacted:API) " +
                "RETURN DISTINCT impacted.name AS name",
                Values.parameters("name", apiName)
            );

            while (result.hasNext()) {
                Record record = result.next();
                impactedApis.add(record.get("name").asString());
            }
        }

        String response = "{ \"impactedApis\": " + impactedApis.toString() + " }";
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
    }
}
