package server;

import com.sun.net.httpserver.HttpServer;
import handler.ApiHandler;
import handler.ServiceHandler;
import handler.DependencyHandler;
import handler.ImpactAnalysisHandler;
import handler.ImpactExplainHandler;


import java.net.InetSocketAddress;

public class MainServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/services/add", new ServiceHandler());
        server.createContext("/apis/add", new ApiHandler());
        server.createContext("/dependencies/add", new DependencyHandler());
        server.createContext("/impact/analyze", new ImpactAnalysisHandler());
        server.createContext("/impact/explain", new ImpactExplainHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server started at http://localhost:8080");
    }
}
