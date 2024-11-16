package pm3.dal;

import pm3.model.*;

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
	
//    protected ConnectionManager connectionManager;

//    public ConsumableItemDao(ConnectionManager connectionManager) {
//        this.connectionManager = connectionManager;
//    }

    /**
     * Create a new ConsumableItem instance in the database.
     * This runs two INSERT statements - one for the base Item and one for the ConsumableItem.
     */
    public ConsumableItem create(ConsumableItem consumableItem) throws SQLException {
//        // First create the base Item record
//        ItemDao itemDao = new ItemDao(connectionManager);
//        Item item = itemDao.create(new Item(
//            consumableItem.getItemName(),
//            consumableItem.getMaxStackSize(),
//            consumableItem.getVendorPrice(),
//            consumableItem.isSellable()
//        ));
//        
//        // Set the generated ItemID
//        consumableItem.setItemID(item.getItemID());
        
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
    	        "SELECT i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, " +
    	        "c.itemLevel, c.itemDescription " +
    	        "FROM ConsumableItem c INNER JOIN Item i ON c.itemID = i.itemID " +
    	        "WHERE c.itemID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_CONSUMABLE_BY_ID);
            selectStmt.setInt(1, itemId);
            results = selectStmt.executeQuery();
            if(results.next()) {
//                return new ConsumableItem(
//                    results.getInt("ItemID"),
//                    results.getString("ItemName"),
//                    results.getInt("MaxStackSize"),
//                    results.getDouble("VendorPrice"),
//                    results.getBoolean("IsSellable"),
//                    results.getInt("ItemLevel"),
//                    results.getString("ItemDescription")
//                );
            	int resultsItemID = results.getInt("itemID");
            	String itemName = results.getString("itemName");
                int maxStackSize = results.getInt("maxStackSize");
                double vendorPrice = results.getDouble("vendorPrice");
                boolean isSellable = results.getBoolean("isSellable");
                int itemLevel = results.getInt("itemLevel");
                String itemDescription = results.getString("itemDescription");
                
                ConsumableItem consumableItem = new ConsumableItem(resultsItemID,itemName,maxStackSize,vendorPrice,isSellable,
                		itemLevel,itemDescription);
                return consumableItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return null;
    }

    /**
     * Get all ConsumableItems of a specific item level.
     * This demonstrates the list-returning search method requirement.
     */
    public List<ConsumableItem> getConsumablesByLevel(int itemLevel) throws SQLException {
    	String SELECT_CONSUMABLE_BY_LEVEL = 
    	        "SELECT i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, " +
    	        "c.itemLevel, c.itemDescription " +
    	        "FROM ConsumableItem c INNER JOIN Item i ON c.itemID = i.itemID " +
    	        "WHERE c.itemLevel=?;";
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
//                consumableItems.add(new ConsumableItem(
//                    results.getInt("ItemID"),
//                    results.getString("ItemName"),
//                    results.getInt("MaxStackSize"),
//                    results.getDouble("VendorPrice"),
//                    results.getBoolean("IsSellable"),
//                    results.getInt("ItemLevel"),
//                    results.getString("ItemDescription")
//                ));
            	int itemID = results.getInt("itemID");
            	String itemName = results.getString("itemName");
                int maxStackSize = results.getInt("maxStackSize");
                double vendorPrice = results.getDouble("vendorPrice");
                boolean isSellable = results.getBoolean("isSellable");
                int resultsItemLevel = results.getInt("itemLevel");
                String itemDescription = results.getString("itemDescription");
                
                ConsumableItem consumableItem = new ConsumableItem(itemID,itemName,maxStackSize,vendorPrice,isSellable,
                		resultsItemLevel,itemDescription);
                consumableItems.add(consumableItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
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
//            deleteStmt.executeUpdate();
         // Execute delete statement
 			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for itemID=" + consumableItem.getItemID());
			}
			// If deletion in Consumable table was successful, delete from Item table
			super.delete(consumableItem);
			
//			// Return the deleted consumable object to confirm deletion
//			return weaponItem;
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