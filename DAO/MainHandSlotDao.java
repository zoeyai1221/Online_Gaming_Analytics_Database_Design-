package Milestone2.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Milestone2.model.MainHandSlot;

public class MainHandSlotDao extends EquipmentSlotDao {
    private static MainHandSlotDao instance = null;

    protected MainHandSlotDao() {
        super();
    }

    public static MainHandSlotDao getInstance() {
        if (instance == null) {
            instance = new MainHandSlotDao();
        }
        return instance;
    }

    public MainHandSlot create(MainHandSlot slot) throws SQLException {
        String insertMainHandSlot = "INSERT INTO MainHandSlot(equipmentSlotID) VALUES (?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertMainHandSlot);
            insertStmt.setInt(1, slot.getEquipmentSlotID());
            insertStmt.executeUpdate();
            return slot;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
        }
    }
}
