package pm3.dal;

import pm3.model.*;

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
//	protected GearItemDao() {
//		connectionManager = new ConnectionManager();
//	}
	public static GearItemDao getInstance() {
		if(instance == null) {
			instance = new GearItemDao();
		}
		return instance;
	}
    

//    public GearItemDao(ConnectionManager connectionManager) {
//        this.connectionManager = connectionManager;
//    }

    /**
     * Create a new GearItem instance in the database.
     * This runs two INSERT statements - one for the base Item and one for the GearItem.
     */
    public GearItem create(GearItem gearItem) throws SQLException {
    	create((Item) gearItem);
    	
//    	GearItem gear = (GearItem) super.create(gearItem);
    	
    	String INSERT_GEAR = 
    	        "INSERT INTO GearItem(itemID, itemLevel, optionalSlot, requiredLevel, defense, magicDefense) " +
    	        "VALUES(?,?,?,?,?,?);";
//        // First create the base Item record
//        ItemDao itemDao = new ItemDao(connectionManager);
//        Item item = itemDao.create(new Item(
//            gearItem.getItemName(),
//            gearItem.getMaxStackSize(),
//            gearItem.getVendorPrice(),
//            gearItem.isSellable()
//        ));
//        
//        // Set the generated ItemID
//        gearItem.setItemID(item.getItemID());
        
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
        	// Start transaction
            connection = connectionManager.getConnection();
            // Insert into GearItem table
            insertStmt = connection.prepareStatement(INSERT_GEAR);
            insertStmt.setInt(1, gearItem.getItemID());
            insertStmt.setInt(2, gearItem.getItemLevel());
//            insertStmt.setString(3, gearItem.getOptionalSlot().toString());
//            insertStmt.setInt(3, gearItem.getOptionalSlot().getEquipmentSlotID());
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
    	        "SELECT i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, " +
    	        "g.itemLevel, g.optionalSlot, g.requiredLevel, g.defense, g.magicDefense " +
    	        "FROM GearItem g " + 
    	        "INNER JOIN Item i ON g.itemID = i.itemID " +
    	        "LEFT JOIN OptionalSlot os ON g.equipmentSlotID = os.equipmentSlotID " +
    	        "LEFT JOIN EquipmentSlot es ON os.equipmentSlotID = es.equipmentSlotID " +
    	        "WHERE g.itemID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_GEAR_BY_ID);
            selectStmt.setInt(1, itemID);
            results = selectStmt.executeQuery();
//            OptionalSlotDao optionalSlotDao = OptionalSlotDao.getInstance();  // use OptionalSlotDao to retrieve the referenced optionalSlot instance
            if(results.next()) {
//                return new GearItem(
//                    results.getInt("ItemID"),
//                    results.getString("ItemName"),
//                    results.getInt("MaxStackSize"),
//                    results.getDouble("VendorPrice"),
//                    results.getBoolean("IsSellable"),
//                    results.getInt("ItemLevel"),
//                    OptionalSlot.valueOf(results.getString("OptionalSlot")),
//                    results.getInt("RequiredLevel"),
//                    results.getInt("Defense"),
//                    results.getInt("MagicDefense")
//                );
            	int resultsItemID = results.getInt("itemID");
            	String itemName = results.getString("itemName");
            	int maxStackSize = results.getInt("maxStackSize");
            	double vendorPrice = results.getDouble("vendorPrice");
            	boolean isSellable = results.getBoolean("isSellable");
            	int itemLevel = results.getInt("itemLevel");
//            	OptionalSlot optionalslot = optionalSlotDao.getOptionalSlotById(equipmentSlotID);
            	// Handle OptionalSlot (nullable)
                OptionalSlot optionalSlot = null;
                int equipmentSlotID = results.getInt("equipmentSlotID");
                if (!results.wasNull()) {
                    String slotName = results.getString("slotName");
                    boolean isMandatory = results.getBoolean("isMandatory");
                    optionalSlot = new OptionalSlot(equipmentSlotID, slotName, isMandatory);
                }
            	int requiredLevel = results.getInt("requiredLevel");
            	int defense = results.getInt("defense");
            	int magicDefense = results.getInt("magicDefense");
            	
            	GearItem gearItem = new GearItem(resultsItemID,itemName,maxStackSize,vendorPrice,isSellable,
            	        itemLevel,optionalSlot,requiredLevel,defense,magicDefense);
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
    	        "SELECT i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, " +
    	        "g.itemLevel, g.optionalSlot, g.requiredLevel, g.defense, g.magicDefense " +
    	        "FROM GearItem g " + 
    	        "INNER JOIN Item i ON g.itemID = i.itemID " +
    	        "LEFT JOIN OptionalSlot os ON g.equipmentSlotID = os.equipmentSlotID " +
    	        "LEFT JOIN EquipmentSlot es ON os.equipmentSlotID = es.equipmentSlotID " +
    	        "WHERE g.itemLevel BETWEEN ? AND ?;";
        List<GearItem> gearItems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_BY_LEVEL_RANGE);
            selectStmt.setInt(1, minLevel);
            selectStmt.setInt(2, maxLevel);
            results = selectStmt.executeQuery();
//            OptionalSlotDao optionalSlotDao = OptionalSlotDao.getInstance();  // use OptionalSlotDao to retrieve the referenced optionalSlot instance
            while(results.next()) {
//                gearItems.add(new GearItem(
//                    results.getInt("ItemID"),
//                    results.getString("ItemName"),
//                    results.getInt("MaxStackSize"),
//                    results.getDouble("VendorPrice"),
//                    results.getBoolean("IsSellable"),
//                    results.getInt("ItemLevel"),
//                    OptionalSlot.valueOf(results.getString("OptionalSlot")),
//                    results.getInt("RequiredLevel"),
//                    results.getInt("Defense"),
//                    results.getInt("MagicDefense")
//                ));
            	int itemID = results.getInt("itemID");
            	String itemName = results.getString("itemName");
            	int maxStackSize = results.getInt("maxStackSize");
            	double vendorPrice = results.getDouble("vendorPrice");
            	boolean isSellable = results.getBoolean("isSellable");
            	int itemLevel = results.getInt("itemLevel");
//            	OptionalSlot optionalslot = optionalSlotDao.getOptionalSlotById(equipmentSlotID);
            	// Handle OptionalSlot (nullable)
                OptionalSlot optionalSlot = null;
                int equipmentSlotID = results.getInt("equipmentSlotID");
            	if (!results.wasNull()) {
                    String slotName = results.getString("slotName");
                    boolean isMandatory = results.getBoolean("isMandatory");
                    optionalSlot = new OptionalSlot(equipmentSlotID, slotName, isMandatory);
                }
            	int requiredLevel = results.getInt("requiredLevel");
            	int defense = results.getInt("defense");
            	int magicDefense = results.getInt("magicDefense");
            	
            	GearItem gearItem = new GearItem(itemID,itemName,maxStackSize,vendorPrice,isSellable,
            	        itemLevel,optionalSlot,requiredLevel,defense,magicDefense);
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
//            deleteStmt.executeUpdate();
         // Execute delete statement
 			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for itemID=" + gearItem.getItemID());
			}
			// If deletion in Gear table was successful, delete from Item table
			super.delete(gearItem);
			
//			// Return the deleted gear object to confirm deletion
//			return gearItem;
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