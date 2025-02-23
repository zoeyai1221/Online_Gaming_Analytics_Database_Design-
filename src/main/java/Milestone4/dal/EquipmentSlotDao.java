package Milestone4.dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Milestone4.model.EquipmentSlot;
import Milestone4.model.EquipmentSlot.SlotName;

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

    /**
     * Create a new EquipmentSlot instance in the database.
     * This runs a SQL INSERT statement and returns the newly created EquipmentSlot
     * with auto-generated EquipmentSlotID.
     */
    public EquipmentSlot create(EquipmentSlot slot) throws SQLException {
        String insertEquipmentSlot = "INSERT INTO EquipmentSlot(slotName, isMandatory) VALUES (?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertEquipmentSlot, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, slot.getSlotName().name());
            insertStmt.setBoolean(2, slot.isMandatory());
            insertStmt.executeUpdate();
            resultKey = insertStmt.getGeneratedKeys();
            int slotID = -1;
            if(resultKey.next()) {
            	slotID = resultKey.getInt(1);
            }
            slot.setEquipmentSlotID(slotID);
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
    
    /**
     * Get the EquipmentSlot record by the given slotID.
     * Returns null if there is no matching slot.
     */
    public EquipmentSlot getEquipmentSlotByID (int equipmentSlotID) throws SQLException {
    	String SELECT_EQUIPMENTSLOT_BY_ID = 
    	        "SELECT equipmentSlotID, slotName, isMandatory " +
    	        "FROM EquipmentSlot WHERE equipmentSlotID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_EQUIPMENTSLOT_BY_ID);
            selectStmt.setInt(1, equipmentSlotID);
            results = selectStmt.executeQuery();
            if(results.next()) {
                SlotName slotName = EquipmentSlot.SlotName.valueOf(results.getString("slotName"));
                boolean isMandatory = results.getBoolean("isMandatory");
                return new EquipmentSlot(equipmentSlotID, slotName, isMandatory);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if(results != null) results.close();
            if(selectStmt != null) selectStmt.close();
            if(connection != null) connection.close();
        }
        return null;
    }
    
    /**
     * Get the EquipmentSlot record by the given slot name.
     * Returns null if there is no matching slot.
     */
    public EquipmentSlot getEquipmentSlotByName (SlotName equipmentSlotName) throws SQLException {
    	String SELECT_EQUIPMENTSLOT_BY_NAME = 
    	        "SELECT equipmentSlotID, slotName, isMandatory " +
    	        "FROM EquipmentSlot WHERE slotName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_EQUIPMENTSLOT_BY_NAME);
            selectStmt.setString(1, equipmentSlotName.name());
            results = selectStmt.executeQuery();
            if(results.next()) {
            	int equipmentslotID = results.getInt("equipmentSlotID");
                boolean isMandatory = results.getBoolean("isMandatory");
                return new EquipmentSlot(equipmentslotID, equipmentSlotName, isMandatory);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if(results != null) results.close();
            if(selectStmt != null) selectStmt.close();
            if(connection != null) connection.close();
        }
        return null;
    }
}