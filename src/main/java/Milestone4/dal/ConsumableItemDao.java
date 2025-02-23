package Milestone4.dal;

import Milestone4.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ConsumableItemDao extends ItemDao {
	
	private static ConsumableItemDao instance = null;
	protected ConsumableItemDao() {
		super();
	}
	public static ConsumableItemDao getInstance() {
		if(instance == null) {
			instance = new ConsumableItemDao();
		}
		return instance;
	}

    /**
     * Create a new ConsumableItem instance in the database.
     * This runs two INSERT statements - one for the base Item and one for the ConsumableItem.
     */
    public ConsumableItem create(ConsumableItem consumableItem) throws SQLException {
    	create((Item) consumableItem);
    	
        String INSERT_CONSUMABLE = 
    	        "INSERT INTO ConsumableItem(itemID, itemLevel, itemDescription) VALUES(?,?,?);";
        
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(INSERT_CONSUMABLE);
            insertStmt.setInt(1, consumableItem.getItemID());
            insertStmt.setInt(2, consumableItem.getItemLevel());
            insertStmt.setString(3, consumableItem.getItemDescription());
            insertStmt.executeUpdate();
            return consumableItem;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(insertStmt != null) {
                insertStmt.close();
            }
        }
    }

    /**
     * Get a ConsumableItem by its ID.
     * Returns null if there is no matching ConsumableItem.
     */
    public ConsumableItem getConsumableItemById(int itemId) throws SQLException {
    	String SELECT_CONSUMABLE_BY_ID = 
    	        "SELECT i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, i.itemType, " + // Added itemType
    	        "c.itemLevel, c.itemDescription " +
    	        "FROM ConsumableItem c INNER JOIN Item i ON c.itemID = i.itemID " +
    	        "WHERE c.itemID=? AND i.itemType='CONSUMABLE';"; // Added type check
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_CONSUMABLE_BY_ID);
            selectStmt.setInt(1, itemId);
            results = selectStmt.executeQuery();
            if(results.next()) {
            	int resultsItemID = results.getInt("itemID");
            	String itemName = results.getString("itemName");
                int maxStackSize = results.getInt("maxStackSize");
             // Handle vendorPrice properly
                Double vendorPrice = results.getDouble("vendorPrice");
                if (results.wasNull()) {
                    vendorPrice = null; // Explicitly set to null if database value is NULL
                } 
                boolean isSellable = results.getBoolean("isSellable");
                Item.ItemType itemType = Item.ItemType.valueOf(results.getString("itemType")); // Get itemType
                int itemLevel = results.getInt("itemLevel");
                String itemDescription = results.getString("itemDescription");
                
                ConsumableItem consumableItem = new ConsumableItem(resultsItemID, itemName, maxStackSize, vendorPrice, 
                        isSellable, itemType, itemLevel, itemDescription);
                return consumableItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
        	 if(results != null) results.close();       
        	 if(selectStmt != null) selectStmt.close(); 
        	 if(connection != null) connection.close();
        }
        return null;
    }

    /**
     * Get all ConsumableItems of a specific item level.
     * This demonstrates the list-returning search method requirement.
     */
    public List<ConsumableItem> getConsumablesByLevel(int itemLevel) throws SQLException {
        String SELECT_CONSUMABLE_BY_LEVEL = 
            "SELECT i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, i.itemType, " + // Added itemType
            "c.itemLevel, c.itemDescription " +
            "FROM ConsumableItem c INNER JOIN Item i ON c.itemID = i.itemID " +
            "WHERE c.itemLevel=? AND i.itemType='CONSUMABLE';"; // Added type check
        
        List<ConsumableItem> consumableItems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_CONSUMABLE_BY_LEVEL);
            selectStmt.setInt(1, itemLevel);
            results = selectStmt.executeQuery();
            while(results.next()) {
                int itemID = results.getInt("itemID");
                String itemName = results.getString("itemName");
                int maxStackSize = results.getInt("maxStackSize");
                Double vendorPrice = results.getDouble("vendorPrice");
                if (results.wasNull()) {
                    vendorPrice = null;
                }
                boolean isSellable = results.getBoolean("isSellable");
                Item.ItemType itemType = Item.ItemType.valueOf(results.getString("itemType")); // Get itemType
                int resultsItemLevel = results.getInt("itemLevel");
                String itemDescription = results.getString("itemDescription");
                
                ConsumableItem consumableItem = new ConsumableItem(itemID, itemName, maxStackSize, 
                    vendorPrice, isSellable, itemType, resultsItemLevel, itemDescription);
                consumableItems.add(consumableItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(results != null) results.close();
            if(selectStmt != null) selectStmt.close();
            if(connection != null) connection.close();
        }
        return consumableItems;
    }
    
    /**
     * Update the description of a ConsumableItem.
     * This demonstrates the update method requirement.
     */
    public ConsumableItem updateDescription(ConsumableItem consumableItem, String newDescription) 
            throws SQLException {
    	String UPDATE_DESCRIPTION = 
    	        "UPDATE ConsumableItem SET itemDescription=? WHERE itemID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(UPDATE_DESCRIPTION);
            updateStmt.setString(1, newDescription);
            updateStmt.setInt(2, consumableItem.getItemID());
            updateStmt.executeUpdate();
            
            // Update the model
            consumableItem.setItemDescription(newDescription);
            return consumableItem;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(updateStmt != null) {
                updateStmt.close();
            }
        }
    }

    /**
     * Delete a ConsumableItem from the database.
     * This demonstrates the delete method requirement.
     */
    public ConsumableItem delete(ConsumableItem consumableItem) throws SQLException {
    	String DELETE_CONSUMABLE = 
    	        "DELETE FROM ConsumableItem WHERE itemID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(DELETE_CONSUMABLE);
            deleteStmt.setInt(1, consumableItem.getItemID());

         // Execute delete statement
 			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
//				throw new SQLException("No records available to delete for itemID=" + consumableItem.getItemID());
				System.out.println("No records available to delete for itemID=" + consumableItem.getItemID());
				return null;
			}
			// If deletion in Consumable table was successful, delete from Item table
			super.delete(consumableItem);
			
			return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }
}