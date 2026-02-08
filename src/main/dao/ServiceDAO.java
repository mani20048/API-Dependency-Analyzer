package dao;

import model.Service;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ServiceDAO {

    public static void save(Service service) {

        String sql = "INSERT INTO services (name, description) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, service.getName());
            ps.setString(2, service.getDescription());
            ps.executeUpdate();

            System.out.println("Service saved to database");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
