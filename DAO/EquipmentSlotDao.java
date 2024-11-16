package Milestone2.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Milestone2.model.EquipmentSlot;

public class EquipmentSlotDao {
    protected ConnectionManager connectionManager;
    private static EquipmentSlotDao instance = null;

    protected EquipmentSlotDao() {
        connectionManager = new ConnectionManager();
    }

    public static EquipmentSlotDao getInstance() {
        if (instance == null) {
            instance = new EquipmentSlotDao();
        }
        return instance;
    }

    public EquipmentSlot create(EquipmentSlot slot) throws SQLException {
        String insertEquipmentSlot = "INSERT INTO EquipmentSlot(slotName, isMandatory) VALUES (?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertEquipmentSlot);
            insertStmt.setString(1, slot.getSlotName());
            insertStmt.setBoolean(2, slot.isMandatory());
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