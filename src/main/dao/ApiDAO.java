package dao;

import model.Api;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ApiDAO {

    public static void save(Api api) {

        String sql = "INSERT INTO apis (name, endpoint, service_id) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, api.getName());
            ps.setString(2, api.getEndpoint());
            ps.setInt(3, api.getServiceId());

            ps.executeUpdate();
            System.out.println("API saved to database");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
