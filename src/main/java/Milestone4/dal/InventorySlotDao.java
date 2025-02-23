package Milestone4.dal;

import Milestone4.model.*;
import Milestone4.model.Character;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the InventorySlot table.
 * This is used to store and retrieve {@link InventorySlot} instances.
 */
public class InventorySlotDao {
    protected ConnectionManager connectionManager;

    private static InventorySlotDao instance = null;

    protected InventorySlotDao() {
        connectionManager = new ConnectionManager();
    }

    public static InventorySlotDao getInstance() {
        if (instance == null) {
            instance = new InventorySlotDao();
        }
        return instance;
    }

    /**
     * Create a new InventorySlot in the database.
     */
    public InventorySlot create(InventorySlot inventorySlot) throws SQLException {
        String insertInventorySlot = 
            "INSERT INTO InventorySlot (characterID, position, itemID, quantity) VALUES (?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertInventorySlot, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, inventorySlot.getCharacter().getCharacterID());
            insertStmt.setInt(2, inventorySlot.getPosition());
            if (inventorySlot.getItem() != null) {
                insertStmt.setInt(3, inventorySlot.getItem().getItemID());
            } else {
                insertStmt.setNull(3, Types.INTEGER);
            }
            insertStmt.setInt(4, inventorySlot.getQuantity());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int inventorySlotID = -1;
            if (resultKey.next()) {
                inventorySlotID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            inventorySlot.setInventorySlotID(inventorySlotID);
            return inventorySlot;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
            if (resultKey != null) resultKey.close();
        }
    }

    /**
     * Retrieve an InventorySlot by its ID.
     */
    public InventorySlot getInventorySlotByID(int inventorySlotID) throws SQLException {
        String selectInventorySlot = 
            "SELECT inventorySlotID, characterID, position, itemID, quantity FROM InventorySlot WHERE inventorySlotID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectInventorySlot);
            selectStmt.setInt(1, inventorySlotID);
            results = selectStmt.executeQuery();
            CharacterDao characterDao = CharacterDao.getInstance();
            ItemDao itemDao = ItemDao.getInstance();

            if (results.next()) {
                int characterID = results.getInt("characterID");
                int position = results.getInt("position");
                int itemID = results.getInt("itemID");
                int quantity = results.getInt("quantity");

                Character character = characterDao.getCharacterByID(characterID);
                Item item = itemID != 0 ? itemDao.getItemByID(itemID) : null;

                return new InventorySlot(inventorySlotID, character, position, item, quantity);
            }
        }catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return null;
    }

    /**
     * Update the quantity of an InventorySlot.
     */
    public InventorySlot updateQuantity(InventorySlot inventorySlot, int newQuantity) throws SQLException {
        String updateQuantity = "UPDATE InventorySlot SET quantity = ? WHERE inventorySlotID = ?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;

        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateQuantity);
            updateStmt.setInt(1, newQuantity);
            updateStmt.setInt(2, inventorySlot.getInventorySlotID());
            updateStmt.executeUpdate();

            inventorySlot.setQuantity(newQuantity);
            return inventorySlot;
        }catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
            if (connection != null) connection.close();
            if (updateStmt != null) updateStmt.close();
        }
    }

    /**
     * Delete an InventorySlot.
     */
    public InventorySlot delete(InventorySlot inventorySlot) throws SQLException {
        String deleteInventorySlot = "DELETE FROM InventorySlot WHERE inventorySlotID = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteInventorySlot);
            deleteStmt.setInt(1, inventorySlot.getInventorySlotID());
            deleteStmt.executeUpdate();
            return null;
        }catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
            if (connection != null) connection.close();
            if (deleteStmt != null) deleteStmt.close();
        }
    }

    /**
     * Retrieve all InventorySlots for a specific character.
     */
    public List<InventorySlot> getInventorySlotsForCharacter(Character character) throws SQLException {
        List<InventorySlot> inventorySlots = new ArrayList<>();
        String selectInventorySlots = 
            "SELECT inventorySlotID, position, itemID, quantity FROM InventorySlot WHERE characterID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectInventorySlots);
            selectStmt.setInt(1, character.getCharacterID());
            results = selectStmt.executeQuery();

            ItemDao itemDao = ItemDao.getInstance();

            while (results.next()) {
                int inventorySlotID = results.getInt("inventorySlotID");
                int position = results.getInt("position");
                int itemID = results.getInt("itemID");
                int quantity = results.getInt("quantity");

                Item item = itemID != 0 ? itemDao.getItemByID(itemID) : null;
                InventorySlot inventorySlot = new InventorySlot(inventorySlotID, character, position, item, quantity);
                inventorySlots.add(inventorySlot);
            }
        } catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return inventorySlots;
    }

}
