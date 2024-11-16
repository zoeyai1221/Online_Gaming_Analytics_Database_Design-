package Milestone2.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Milestone2.model.EquippedItem;
import Milestone2.model.Item;

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

    public EquippedItem create(EquippedItem equippedItem) throws SQLException {
        String insertEquippedItem = "INSERT INTO EquippedItem(equipmentSlotID, characterID, itemID) VALUES (?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertEquippedItem);
            insertStmt.setInt(1, equippedItem.getEquipmentSlotID());
            insertStmt.setInt(2, equippedItem.getCharacterID());
            insertStmt.setInt(3, equippedItem.getItemID());
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

    public Item getEquippedItem(int equipmentSlotID, int characterID) throws SQLException {
        String selectItem = "SELECT i.itemID, i.itemName, i.itemDescription " +
                            "FROM Item i " +
                            "JOIN EquippedItem e ON i.itemID = e.itemID " +
                            "WHERE e.equipmentSlotID = ? AND e.characterID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectItem);
            selectStmt.setInt(1, equipmentSlotID);
            selectStmt.setInt(2, characterID);
            results = selectStmt.executeQuery();

//            if (results.next()) {
//                return new Item(results.getInt("itemID"), results.getString("itemName"), results.getString("itemDescription"));
//            }
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
}
