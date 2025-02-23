package Milestone4.dal;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Milestone4.model.EquipmentSlot;
import Milestone4.model.EquippedItem;
import Milestone4.model.Item;
import Milestone4.model.Character;


public class EquippedItemDao {
    protected ConnectionManager connectionManager;
    private static EquippedItemDao instance = null;

    protected EquippedItemDao() {
        connectionManager = new ConnectionManager();
    }

    public static EquippedItemDao getInstance() {
        if (instance == null) {
            instance = new EquippedItemDao();
        }
        return instance;
    }


    /**
     * Create a new EquippedItem instance in the database.
     * This runs a SQL INSERT statement and returns the newly created EquippedItem.
     */
    public EquippedItem create(EquippedItem equippedItem) throws SQLException {
        String insertEquippedItem = "INSERT INTO EquippedItem(equipmentSlotID, characterID, itemID) VALUES (?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertEquippedItem);
            insertStmt.setInt(1, equippedItem.getEquipmentSlot().getEquipmentSlotID());
            insertStmt.setInt(2, equippedItem.getCharacter().getCharacterID());
            insertStmt.setInt(3, equippedItem.getItem().getItemID());
            insertStmt.executeUpdate();
            return equippedItem;
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
     * Retrieve the Item record given equipmentSlotID and characterID.
     * Returns null if there is no matching slot.
     */
    public Item getEquippedItemBySlotAndCharacter(EquipmentSlot equipmentSlot, Character character) throws SQLException {
        String selectItem = "SELECT i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, i.itemType " +
                            "FROM Item i " +
                            "JOIN EquippedItem e ON i.itemID = e.itemID " +
                            "WHERE e.equipmentSlotID = ? AND e.characterID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectItem);
            selectStmt.setInt(1, equipmentSlot.getEquipmentSlotID());
            selectStmt.setInt(2, character.getCharacterID());
            results = selectStmt.executeQuery();

            if (results.next()) {
            	int itemID = results.getInt("itemID");
            	String itemName = results.getString("itemName");
                int maxStackSize = results.getInt("maxStackSize");
                double vendorPrice = results.getDouble("vendorPrice");
                boolean isSellable = results.getBoolean("isSellable");
                Item.ItemType itemType = Item.ItemType.valueOf(results.getString("itemType"));
                return new Item(itemID, itemName, maxStackSize, vendorPrice, isSellable, itemType);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
    }
    
    /**
     * Delete EquippedItem for given equipmentSlot and character from the database.
     */
    public EquippedItem delete(EquipmentSlot equipmentSlot, Character character) throws SQLException {
    	String DELETE_EQUIPPEDITEM = 
    	        "DELETE FROM EquippedItem WHERE equipmentSlotID=? AND characterid=? ;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(DELETE_EQUIPPEDITEM);
            deleteStmt.setInt(1, equipmentSlot.getEquipmentSlotID());
            deleteStmt.setInt(2, character.getCharacterID());
            deleteStmt.executeUpdate();
            
            return null;
        } catch (SQLException e) {
        	e.printStackTrace();
            throw e;
        } finally {
            if(deleteStmt != null) deleteStmt.close();
            if(connection != null) connection.close();
        }
    }
}
