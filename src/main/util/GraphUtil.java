package util;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;

public class GraphUtil {

    private static final String URI = "bolt://localhost:7687";
    private static final String USER = "neo4j";
    private static final String PASSWORD = "Mani@0001";
    private static final String DATABASE = "neo4j";

    private static final Driver driver = GraphDatabase.driver(
            URI,
            AuthTokens.basic(USER, PASSWORD)
    );

    public static Session getSession() {
        return driver.session(SessionConfig.forDatabase(DATABASE));
    }
}
