package dao;
import util.GraphUtil;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import model.Api;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ApiDAO {
    public static void save(Api api) {

    System.out.println(">>> ApiDAO.save() CALLED for: " + api.getName());

    String sql = "INSERT INTO apis (name, endpoint, service_id) VALUES (?, ?, ?)";

    try (Connection conn = DBUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, api.getName());
        ps.setString(2, api.getEndpoint());
        ps.setInt(3, api.getServiceId());
        ps.executeUpdate();

        System.out.println(">>> Saved to MySQL: " + api.getName());

    } catch (java.sql.SQLIntegrityConstraintViolationException e) {
        // API already exists — this is OK
        System.out.println(">>> API already exists in MySQL, syncing Neo4j");

    } catch (Exception e) {
        e.printStackTrace();
        return; // real failure
    }

    // ✅ ALWAYS ensure Neo4j node exists
    try (org.neo4j.driver.Session session = util.GraphUtil.getSession()) {

        session.run(
            "MERGE (:API {name:$name, endpoint:$endpoint, serviceId:$serviceId})",
            org.neo4j.driver.Values.parameters(
                "name", api.getName(),
                "endpoint", api.getEndpoint(),
                "serviceId", api.getServiceId()
            )
        );

        System.out.println(">>> API node ensured in Neo4j: " + api.getName());

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
