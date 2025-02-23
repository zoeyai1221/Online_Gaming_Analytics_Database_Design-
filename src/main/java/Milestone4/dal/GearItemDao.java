package Milestone4.dal;

import Milestone4.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GearItemDao extends ItemDao {
    private static GearItemDao instance = null;
    
    protected GearItemDao() {
		super();
	}
	public static GearItemDao getInstance() {
		if(instance == null) {
			instance = new GearItemDao();
		}
		return instance;
	}

    /**
     * Create a new GearItem instance in the database.
     * This runs two INSERT statements - one for the base Item and one for the GearItem.
     */
    public GearItem create(GearItem gearItem) throws SQLException {
    	create((Item) gearItem);
    	
    	String INSERT_GEAR = 
    	        "INSERT INTO GearItem(itemID, itemLevel, equipmentSlotID, requiredLevel, defense, magicDefense) " +
    	        "VALUES(?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
        	// Start transaction
            connection = connectionManager.getConnection();
            // Insert into GearItem table
            insertStmt = connection.prepareStatement(INSERT_GEAR);
            insertStmt.setInt(1, gearItem.getItemID());
            insertStmt.setInt(2, gearItem.getItemLevel());
            // Set optionalSlotID (nullable)
            if (gearItem.getOptionalSlot() != null) {
                insertStmt.setInt(3, gearItem.getOptionalSlot().getEquipmentSlotID());
            } else {
                insertStmt.setNull(3, java.sql.Types.INTEGER);
            }
            insertStmt.setInt(4, gearItem.getRequiredLevel());
            insertStmt.setInt(5, gearItem.getDefense());
            insertStmt.setInt(6, gearItem.getMagicDefense());
            insertStmt.executeUpdate();
            return gearItem;
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
     * Get a GearItem by its ID.
     * Returns null if there is no matching GearItem.
     */
    public GearItem getGearItemById(int itemID) throws SQLException {
    	String SELECT_GEAR_BY_ID = 
    	        "SELECT i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, i.itemType, " + // Added itemType
    	        "g.itemLevel, g.equipmentSlotID, g.requiredLevel, g.defense, g.magicDefense " +
    	        "FROM GearItem g " + 
    	        "INNER JOIN Item i ON g.itemID = i.itemID " +
    	        "WHERE g.itemID=? AND i.itemType = 'GEAR';"; // Added type check
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_GEAR_BY_ID);
            
            selectStmt.setInt(1, itemID);
            
            results = selectStmt.executeQuery();
            if(results.next()) {
            	int resultsItemID = results.getInt("itemID");
            	String itemName = results.getString("itemName");
            	int maxStackSize = results.getInt("maxStackSize");
            	// Handle vendorPrice properly
                Double vendorPrice = results.getDouble("vendorPrice");
                if (results.wasNull()) {
                    vendorPrice = null; // Explicitly set to null if database value is NULL
                }            	boolean isSellable = results.getBoolean("isSellable");
            	int itemLevel = results.getInt("itemLevel");
            	
            	// Handle OptionalSlot (nullable)
                OptionalSlot optionalSlot = null;
                int equipmentSlotID = results.getInt("equipmentSlotID");
                if (!results.wasNull()) {
                    optionalSlot = new OptionalSlot(equipmentSlotID);
                }
                
                Item.ItemType itemType = Item.ItemType.valueOf(results.getString("itemType")); // Get itemType
            	int requiredLevel = results.getInt("requiredLevel");
            	int defense = results.getInt("defense");
            	int magicDefense = results.getInt("magicDefense");
            	
            	GearItem gearItem = new GearItem(resultsItemID,itemName,maxStackSize,vendorPrice,isSellable,
            	        itemLevel,optionalSlot,requiredLevel,defense,magicDefense, itemType);  // Add itemType
				return gearItem;
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
     * Get all GearItems within a specific item level range.
     * This demonstrates the list-returning search method requirement.
     */
    public List<GearItem> getGearByLevelRange(int minLevel, int maxLevel) throws SQLException {
    	String SELECT_BY_LEVEL_RANGE = 
    	        "SELECT i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, i.itemType, " + // Added itemType
    	        "g.itemLevel, g.equipmentSlotID, g.requiredLevel, g.defense, g.magicDefense " +
    	        "FROM GearItem g " + 
    	        "INNER JOIN Item i ON g.itemID = i.itemID " +
    	        "WHERE g.itemLevel BETWEEN ? AND ? AND i.itemType = 'GEAR';"; // Added type check
        List<GearItem> gearItems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_BY_LEVEL_RANGE);

            // Set query parameters
            selectStmt.setInt(1, minLevel);
            selectStmt.setInt(2, maxLevel);
            
            results = selectStmt.executeQuery();
            while(results.next()) {
            	int itemID = results.getInt("itemID");
            	String itemName = results.getString("itemName");
            	int maxStackSize = results.getInt("maxStackSize");
            	// Handle vendorPrice properly
                Double vendorPrice = results.getDouble("vendorPrice");
                if (results.wasNull()) {
                    vendorPrice = null; // Explicitly set to null if database value is NULL
                }            	boolean isSellable = results.getBoolean("isSellable");
            	int itemLevel = results.getInt("itemLevel");
            	
            	// Handle OptionalSlot (nullable)
                OptionalSlot optionalSlot = null;
                int equipmentSlotID = results.getInt("equipmentSlotID");
            	if (!results.wasNull()) {
                    optionalSlot = new OptionalSlot(equipmentSlotID);
                }
            	
            	Item.ItemType itemType = Item.ItemType.valueOf(results.getString("itemType")); // Get itemType
            	int requiredLevel = results.getInt("requiredLevel");
            	int defense = results.getInt("defense");
            	int magicDefense = results.getInt("magicDefense");
            	
            	GearItem gearItem = new GearItem(itemID,itemName,maxStackSize,vendorPrice,isSellable,
            	        itemLevel,optionalSlot,requiredLevel,defense,magicDefense, itemType);  // Add itemType
            	gearItems.add(gearItem);
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
        return gearItems;
    }

    /**
     * Update both defense and magic defense values of a GearItem.
     * This demonstrates the update method requirement.
     */
    public GearItem updateDefenseValues(GearItem gearItem, int newDefense, int newMagicDefense) 
            throws SQLException {
    	String UPDATE_DEFENSE_VALUES = 
    	        "UPDATE GearItem SET defense=?, magicDefense=? WHERE itemID=?;";
    	        
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(UPDATE_DEFENSE_VALUES);
            updateStmt.setInt(1, newDefense);
            updateStmt.setInt(2, newMagicDefense);
            updateStmt.setInt(3, gearItem.getItemID());
            updateStmt.executeUpdate();
            
            // Update the model
            gearItem.setDefense(newDefense);
            gearItem.setMagicDefense(newMagicDefense);
            return gearItem;
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
     * Delete a GearItem from the database.
     * This demonstrates the delete method requirement.
     */
    public GearItem delete(GearItem gearItem) throws SQLException {
    	String DELETE_GEAR = 
    	        "DELETE FROM GearItem WHERE itemID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(DELETE_GEAR);
            deleteStmt.setInt(1, gearItem.getItemID());

         // Execute delete statement
 			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
//				throw new SQLException("No records available to delete for itemID=" + gearItem.getItemID());
				System.out.println("No records available to delete for itemID=" + gearItem.getItemID());
				return null;
			}
			// If deletion in Gear table was successful, delete from Item table
			super.delete(gearItem);
			
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