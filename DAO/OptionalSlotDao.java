package Milestone2.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Milestone2.model.OptionalSlot;

public class OptionalSlotDao extends EquipmentSlotDao {
    private static OptionalSlotDao instance = null;

    protected OptionalSlotDao() {
        super();
    }

    public static OptionalSlotDao getInstance() {
        if (instance == null) {
            instance = new OptionalSlotDao();
        }
        return instance;
    }

    public OptionalSlot create(OptionalSlot slot) throws SQLException {
        String insertOptionalSlot = "INSERT INTO OptionalSlot(equipmentSlotID) VALUES (?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertOptionalSlot);
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
