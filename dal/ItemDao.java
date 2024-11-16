package pm3.dal;

import pm3.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ItemDao {
    protected ConnectionManager connectionManager;
    
    private static ItemDao instance = null;
	protected ItemDao() {
		connectionManager = new ConnectionManager();
	}
	public static ItemDao getInstance() {
		if(instance == null) {
			instance = new ItemDao();
		}
		return instance;
	}
	
//	public ItemDao(ConnectionManager connectionManager) {
//        this.connectionManager = connectionManager;
//    }
	
    /**
     * Create a new Item instance in the database.
     * This runs a SQL INSERT statement and returns the newly created Item
     * with auto-generated ItemID.
     */
    public Item create(Item item) throws SQLException {
    	String INSERT_ITEM = 
    	        "INSERT INTO Item(itemName, maxStackSize, vendorPrice, isSellable) " +
    	        "VALUES(?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(INSERT_ITEM, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, item.getItemName());
            insertStmt.setInt(2, item.getMaxStackSize());
            insertStmt.setDouble(3, item.getVendorPrice());
            
            // Since vendorPrice can be NULL
//            if (item.getVendorPrice() != null) {
//                insertStmt.setDouble(3, item.getVendorPrice());
//            } else {
//                insertStmt.setNull(3, java.sql.Types.DECIMAL);
//            }
            
            insertStmt.setBoolean(4, item.isSellable());
            insertStmt.executeUpdate();
            
            // Retrieve the auto-generated key and set it, before returning the item.
            resultKey = insertStmt.getGeneratedKeys();
            int itemId = -1;
            if(resultKey.next()) {
                itemId = resultKey.getInt(1);
            }
            item.setItemID(itemId);
            return item;
        } catch (SQLException e) {
            throw e;
        } finally {
            if(resultKey != null) resultKey.close();
            if(insertStmt != null) insertStmt.close();
            if(connection != null) connection.close();
        }
    }

    /**
     * Get the Item record by the given itemID.
     * Returns null if there is no matching Item.
     */
    public Item getItemByID(int itemID) throws SQLException {
    	String SELECT_ITEM_BY_ID = 
    	        "SELECT itemID, itemName, maxStackSize, vendorPrice, isSellable " +
    	        "FROM Item WHERE itemID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_ITEM_BY_ID);
            selectStmt.setInt(1, itemID);
            results = selectStmt.executeQuery();
            if(results.next()) {
            	int resultsItemID = results.getInt("itemID");
                String itemName = results.getString("itemName");
                int maxStackSize = results.getInt("maxStackSize");
                double vendorPrice = results.getDouble("vendorPrice");
                boolean isSellable = results.getBoolean("isSellable");
                
                return new Item(resultsItemID, itemName, maxStackSize, vendorPrice, isSellable);
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
     * Get a list of Items with names containing the given search string.
     * Returns an empty list if no matches are found.
     */
    public List<Item> getItemsByPartialName(String partialName) throws SQLException {
    	String SELECT_ITEMS_BY_NAME = 
    	        "SELECT itemID, itemName, maxStackSize, vendorPrice, isSellable " +
    	        "FROM Item WHERE itemName LIKE ?;";
        List<Item> items = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_ITEMS_BY_NAME);
            selectStmt.setString(1, "%" + partialName + "%");
            results = selectStmt.executeQuery();
            while(results.next()) {
                int itemID = results.getInt("itemID");
                String itemName = results.getString("itemName");
                int maxStackSize = results.getInt("maxStackSize");
                double vendorPrice = results.getDouble("vendorPrice");
                boolean isSellable = results.getBoolean("isSellable");
                
                Item item = new Item(itemID, itemName, maxStackSize, vendorPrice, isSellable);
                items.add(item);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if(results != null) results.close();
            if(selectStmt != null) selectStmt.close();
            if(connection != null) connection.close();
        }
        return items;
    }
    
    /**
     * Update the vendor price of the given Item.
     */
    public Item updateVendorPrice(Item item, double newVendorPrice) throws SQLException {
    	String UPDATE_VENDOR_PRICE = 
    	        "UPDATE Item SET vendorPrice=? WHERE itemID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(UPDATE_VENDOR_PRICE);
            updateStmt.setDouble(1, newVendorPrice);
            updateStmt.setInt(2, item.getItemID());
            updateStmt.executeUpdate();
            
            // Update the item parameter before returning to the caller.
            item.setVendorPrice(newVendorPrice);
            return item;
        } catch (SQLException e) {
            throw e;
        } finally {
            if(updateStmt != null) updateStmt.close();
            if(connection != null) connection.close();
        }
    }
    
    /**
     * Delete the given Item from the database.
     */
    public Item delete(Item item) throws SQLException {
    	String DELETE_ITEM = 
    	        "DELETE FROM Item WHERE itemID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(DELETE_ITEM);
            deleteStmt.setInt(1, item.getItemID());
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