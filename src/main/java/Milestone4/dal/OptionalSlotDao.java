package Milestone4.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Milestone4.model.EquipmentSlot;
import Milestone4.model.OptionalSlot;
import Milestone4.model.EquipmentSlot.SlotName;

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

    /**
     * Create a new OptionalSlot instance in the database.
     * This runs a SQL INSERT statement and returns the newly created OptionalSlot
     */
    public OptionalSlot create(OptionalSlot slot) throws SQLException {
    	create((EquipmentSlot) slot);
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
    
    /**
     * Get the OptionalSlot record by the given slotID.
     * Returns null if there is no matching slot.
     */
    public OptionalSlot getOptionalSlotByID (int equipmentSlotID) throws SQLException {
    	String SELECT_OPTIONALSLOT_BY_ID = 
    			"SELECT o.equipmentSlotID, e.slotName, e.isMandatory " +
					    "FROM OptionalSlot o " +
					    "JOIN EquipmentSlot e ON o.equipmentSlotID = e.equipmentSlotID " +
    	        "WHERE e.equipmentSlotID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_OPTIONALSLOT_BY_ID);
            selectStmt.setInt(1, equipmentSlotID);
            results = selectStmt.executeQuery();
            if(results.next()) {
                SlotName slotName = EquipmentSlot.SlotName.valueOf(results.getString("slotName"));
                boolean isMandatory = results.getBoolean("isMandatory");
                return new OptionalSlot(equipmentSlotID, slotName, isMandatory);
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
     * Get the OptionalSlot record by the given slot name.
     * Returns null if there is no matching slot.
     */
    public OptionalSlot getOptionalSlotByName (SlotName equipmentSlotName) throws SQLException {
    	String SELECT_OPTIONALSLOT_BY_NAME = 
    			 "SELECT o.equipmentSlotID, e.slotName, e.isMandatory " +
    					    "FROM OptionalSlot o " +
    					    "JOIN EquipmentSlot e ON o.equipmentSlotID = e.equipmentSlotID " +
    					    "WHERE e.slotName = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_OPTIONALSLOT_BY_NAME);
            selectStmt.setString(1, equipmentSlotName.name());
            results = selectStmt.executeQuery();
            if(results.next()) {
            	int equipmentslotID = results.getInt("equipmentSlotID");
                boolean isMandatory = results.getBoolean("isMandatory");
                return new OptionalSlot(equipmentslotID, equipmentSlotName, isMandatory);
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

