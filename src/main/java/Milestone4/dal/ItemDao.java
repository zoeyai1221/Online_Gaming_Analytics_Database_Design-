package Milestone4.dal;

import Milestone4.model.*;
import Milestone4.model.EquipmentSlot.SlotName;

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
	
    /**
     * Create a new Item instance in the database.
     * This runs a SQL INSERT statement and returns the newly created Item
     * with auto-generated ItemID.
     */
    public Item create(Item item) throws SQLException {
    	String INSERT_ITEM = 
    	        "INSERT INTO Item(itemName, maxStackSize, vendorPrice, isSellable, itemType) " + // Added itemType
    	        "VALUES(?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(INSERT_ITEM, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, item.getItemName());
            insertStmt.setInt(2, item.getMaxStackSize());
            // Check if vendorPrice is not null before setting it
            if (item.getVendorPrice() != null) {
                insertStmt.setDouble(3, item.getVendorPrice());
            } else {
                insertStmt.setNull(3, java.sql.Types.DECIMAL);
            }
            
            insertStmt.setBoolean(4, item.isSellable());
            insertStmt.setString(5, item.getItemType().name()); // Add itemType as string
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
        	e.printStackTrace();
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
    	        "SELECT itemID, itemName, maxStackSize, vendorPrice, isSellable, itemType " + // Added itemType
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
                // Handle vendorPrice properly
                Double vendorPrice = results.getDouble("vendorPrice");
                if (results.wasNull()) {
                    vendorPrice = null; // Explicitly set to null if database value is NULL
                }
                
                boolean isSellable = results.getBoolean("isSellable");
                Item.ItemType itemType = Item.ItemType.valueOf(results.getString("itemType")); // Convert string to enum
                
                return new Item(resultsItemID, itemName, maxStackSize, vendorPrice, isSellable, itemType);
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
     * Get a list of Items with names containing the given search string.
     * Returns an empty list if no matches are found.
     */
    public List<Item> getItemsByPartialName(String partialName) throws SQLException {
    	String SELECT_ITEMS_BY_NAME = 
    	        "SELECT itemID, itemName, maxStackSize, vendorPrice, isSellable, itemType " + // Added itemType
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
                // Handle vendorPrice properly
                Double vendorPrice = results.getDouble("vendorPrice");
                if (results.wasNull()) {
                    vendorPrice = null; // Explicitly set to null if database value is NULL
                }
                
                boolean isSellable = results.getBoolean("isSellable");
                Item.ItemType itemType = Item.ItemType.valueOf(results.getString("itemType")); // Convert string to enum
                
                Item item = new Item(itemID, itemName, maxStackSize, vendorPrice, isSellable, itemType);
                items.add(item);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
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
    public Item updateVendorPrice(Item item, Double newVendorPrice) throws SQLException {
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
        	e.printStackTrace();
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
    
    /**
     * Get item by item type.
     */
    public List<Item> getItemsByType(Item.ItemType itemType) throws SQLException {
        String SELECT_ITEMS_BY_TYPE = 
            "SELECT itemID, itemName, maxStackSize, vendorPrice, isSellable, itemType " +
            "FROM Item WHERE itemType=?;";
        List<Item> items = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_ITEMS_BY_TYPE);
            selectStmt.setString(1, itemType.name());
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
                
                Item item = new Item(itemID, itemName, maxStackSize, vendorPrice, isSellable, itemType);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(results != null) results.close();
            if(selectStmt != null) selectStmt.close();
            if(connection != null) connection.close();
        }
        return items;
    }

        /**
         * Retrieves equipped weapon for a character's main hand slot
         */
        public WeaponItem getEquippedWeaponByCharacter(int characterID) throws SQLException {
            String selectWeapon = "SELECT " +
                "i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, i.itemType, " + // Added itemType
                "w.itemLevel, w.requiredLevel, w.damage, w.autoAttack, w.attackDelay, " +
                "es.equipmentSlotID, es.slotName " +
                "FROM Item i " +
                "JOIN WeaponItem w ON i.itemID = w.itemID " +
                "JOIN EquippedItem ei ON i.itemID = ei.itemID " +
                "JOIN EquipmentSlot es ON ei.equipmentSlotID = es.equipmentSlotID " +
                "WHERE ei.characterID = ? AND es.slotName = 'mainHand'";
            
            Connection connection = null;
            PreparedStatement selectStmt = null;
            ResultSet results = null;
            
            try {
                connection = connectionManager.getConnection();
                selectStmt = connection.prepareStatement(selectWeapon);
                selectStmt.setInt(1, characterID);
                
                results = selectStmt.executeQuery();
                
                if (results.next()) {
                    int itemID = results.getInt("itemID");
                    String itemName = results.getString("itemName");
                    int maxStackSize = results.getInt("maxStackSize");
                    Double vendorPrice = results.getDouble("vendorPrice");
                    boolean isSellable = results.getBoolean("isSellable");
                    Item.ItemType itemType = Item.ItemType.valueOf(results.getString("itemType"));
                    
                    // Handle MainHandSlot
                    MainHandSlot mainHandSlot = new MainHandSlot(results.getInt("equipmentSlotID"));
                    
                    int itemLevel = results.getInt("itemLevel");
                    int requiredLevel = results.getInt("requiredLevel");
                    int damage = results.getInt("damage");
                    double autoAttack = results.getDouble("autoAttack");
                    double attackDelay = results.getDouble("attackDelay");
                    
                    // Create WeaponItem with full constructor
                    WeaponItem weaponItem = new WeaponItem(
                        itemID, itemName, maxStackSize, vendorPrice, isSellable,  
                        mainHandSlot, itemLevel, requiredLevel, damage, autoAttack, attackDelay,
                        itemType
                    );

                    return weaponItem;
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (connection != null) connection.close();
                if (selectStmt != null) selectStmt.close();
                if (results != null) results.close();
            }
        }
    
        /**
         * Retrieves equipped gear items for a character's optional slots
         */
        public List<GearItem> getEquippedGearByCharacter(int characterID) throws SQLException {
            String selectGear = "SELECT " +
                "i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, i.itemType, " +
                "g.itemLevel, g.requiredLevel, g.defense, g.magicDefense, " +
                "es.equipmentSlotID, es.slotName " +
                "FROM Item i " +
                "JOIN GearItem g ON i.itemID = g.itemID " +
                "JOIN EquippedItem ei ON i.itemID = ei.itemID " +
                "JOIN EquipmentSlot es ON ei.equipmentSlotID = es.equipmentSlotID " +
                "WHERE ei.characterID = ? AND es.slotName != 'mainHand'";
            
            Connection connection = null;
            PreparedStatement selectStmt = null;
            ResultSet results = null;
            
            List<GearItem> equippedGear = new ArrayList<>();
            
            try {
                connection = connectionManager.getConnection();
                selectStmt = connection.prepareStatement(selectGear);
                selectStmt.setInt(1, characterID);
                
                results = selectStmt.executeQuery();
                
                while (results.next()) {
                    int itemID = results.getInt("itemID");
                    String itemName = results.getString("itemName");
                    int maxStackSize = results.getInt("maxStackSize");
                    Double vendorPrice = results.getDouble("vendorPrice");
                    boolean isSellable = results.getBoolean("isSellable");
                    Item.ItemType itemType = Item.ItemType.valueOf(results.getString("itemType"));
                    
                    // Handle OptionalSlot (nullable)
                    OptionalSlot optionalSlot = null;
                    int equipmentSlotID = results.getInt("equipmentSlotID");
                	if (!results.wasNull()) {
                        optionalSlot = new OptionalSlot(equipmentSlotID);
                    }
                    int itemLevel = results.getInt("itemLevel");
                    int requiredLevel = results.getInt("requiredLevel");
                    int defense = results.getInt("defense");
                    int magicDefense = results.getInt("magicDefense");
                    
                    // Create GearItem with full constructor
                    GearItem gearItem = new GearItem(
                        itemID, itemName, maxStackSize, vendorPrice, isSellable, 
                        itemLevel, optionalSlot, requiredLevel, defense, magicDefense,
                        itemType
                    );
                    
                    equippedGear.add(gearItem);
                }
                
                return equippedGear;
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (connection != null) connection.close();
                if (selectStmt != null) selectStmt.close();
                if (results != null) results.close();
            }
        }
        
        /**
         * Retrieves flat bonus for an item
         */
        public List<FlatBonus> getFlatBonusesForItem(int itemID) throws SQLException {
            List<FlatBonus> flatBonuses = new ArrayList<>();

            String sql = "SELECT " +
            			 "i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, i.itemType, " +
            			 "fb.flatBonusValue, a.attributeID, a.attributeName, a.attributeValue " +
            			 "FROM Item i " +
            			 "JOIN FlatBonus fb ON i.itemID = fb.itemID " +
            			 "JOIN Attribute a ON fb.attributeID = a.attributeID " +
            			 "WHERE fb.itemID = ?";

            Connection connection = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                connection = connectionManager.getConnection();
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, itemID);

                rs = stmt.executeQuery();

                while (rs.next()) {
                	String itemName = rs.getString("itemName");
                	int maxStackSize = rs.getInt("maxStackSize");
                	Double vendorPrice = rs.getDouble("vendorPrice");
                	boolean isSellable = rs.getBoolean("isSellable");	
                	Item.ItemType itemType = Item.ItemType.valueOf(rs.getString("itemType"));
                	// Create Item with full constructor
                	Item item = new Item(itemID, itemName, maxStackSize, vendorPrice, isSellable, itemType);
                  
                	int flatBonusValue = rs.getInt("flatBonusValue");
                	int attributeID = rs.getInt("attributeID");
                	String attributeName = rs.getString("attributeName");
                	int attributeValue = rs.getInt("attributeValue");
                	// Create Attribute with full constructor
                	Attribute attribute = new Attribute(attributeID, attributeName, attributeValue);

                    FlatBonus flatBonus = new FlatBonus(item, attribute, flatBonusValue);
                    flatBonuses.add(flatBonus);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            }

            return flatBonuses;
        }
        
        
        /**
         * Retrieves slot for an item
         */
        public EquipmentSlot getSlotForItem(int itemID) throws SQLException {
            String sql = "SELECT es.equipmentSlotID, es.slotName, es.isMandatory " +
                         "FROM EquipmentSlot es " +
                         "JOIN EquippedItem ei ON es.equipmentSlotID = ei.equipmentSlotID " +
                         "WHERE ei.itemID = ?";
            
            EquipmentSlot slot = null;
            
            try (Connection connection = connectionManager.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, itemID);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    int equipmentSlotID = rs.getInt("equipmentSlotID");
                    String slotNameStr = rs.getString("slotName");
                    boolean isMandatory = rs.getBoolean("isMandatory");
                    
                    SlotName slotName = SlotName.valueOf(slotNameStr);
                    slot = new EquipmentSlot(equipmentSlotID, slotName, isMandatory);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }

            return slot;
        }  
}
