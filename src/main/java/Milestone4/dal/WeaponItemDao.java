package Milestone4.dal;

import Milestone4.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeaponItemDao extends ItemDao {
	private static WeaponItemDao instance = null;
	protected WeaponItemDao() {
		super();
	}
	public static WeaponItemDao getInstance() {
		if(instance == null) {
			instance = new WeaponItemDao();
		}
		return instance;
	}

    
    /**
     * Create a new WeaponItem instance in the database.
     * This runs SQL INSERT statements for both Item and WeaponItem tables.
     */
    public WeaponItem create(WeaponItem weaponItem) throws SQLException {
    	create((Item) weaponItem);
        String INSERT_WEAPON = 
                "INSERT INTO WeaponItem(itemID, equipmentSlotID, itemLevel, requiredLevel, " +
                "damage, autoAttack, attackDelay) VALUES(?,?,?,?,?,?,?);";
        
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(INSERT_WEAPON);
            insertStmt.setInt(1, weaponItem.getItemID());
            insertStmt.setInt(2, weaponItem.getMainHandSlot().getEquipmentSlotID());
            insertStmt.setInt(3, weaponItem.getItemLevel());
            insertStmt.setInt(4, weaponItem.getRequiredLevel());
            insertStmt.setInt(5, weaponItem.getDamage());
            insertStmt.setDouble(6, weaponItem.getAutoAttack());
            insertStmt.setDouble(7, weaponItem.getAttackDelay());
            insertStmt.executeUpdate();
            return weaponItem;
        } catch (SQLException e) {
        	e.printStackTrace();
        	throw e;
        } finally {
            if(insertStmt != null) insertStmt.close();
            if(connection != null) connection.close();
        }
    }
    
    /**
     * Get the WeaponItem record by the given itemID.
     * Returns null if there is no matching WeaponItem.
     */
    public WeaponItem getWeaponByID(int itemID) throws SQLException {
        String SELECT_WEAPON_BY_ID = 
            "SELECT i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, i.itemType, " +
            "w.equipmentSlotID, w.itemLevel, w.requiredLevel, w.damage, w.autoAttack, w.attackDelay " +
            "FROM WeaponItem w " +
            "INNER JOIN Item i ON w.itemID = i.itemID " +
            "WHERE i.itemID = ? AND i.itemType = 'WEAPON';";

        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_WEAPON_BY_ID);
            selectStmt.setInt(1, itemID);
            results = selectStmt.executeQuery();
            if(results.next()) {
                int resultsItemID = results.getInt("itemID");
                String itemName = results.getString("itemName");
                int maxStackSize = results.getInt("maxStackSize");
                
                Double vendorPrice = results.getDouble("vendorPrice");
                if (results.wasNull()) {
                    vendorPrice = null;
                }
                
                boolean isSellable = results.getBoolean("isSellable");
                
                MainHandSlot mainHandSlot = null;
                int equipmentSlotID = results.getInt("equipmentSlotID");
                if (!results.wasNull()) {
                    mainHandSlot = new MainHandSlot(equipmentSlotID);
                }
                
                int itemLevel = results.getInt("itemLevel");
                int requiredLevel = results.getInt("requiredLevel");
                Item.ItemType itemType = Item.ItemType.valueOf(results.getString("itemType"));
                int damage = results.getInt("damage");
                Double autoAttack = results.getDouble("autoAttack");
                Double attackDelay = results.getDouble("attackDelay");

                WeaponItem weaponItem = new WeaponItem(resultsItemID, itemName, maxStackSize, vendorPrice,
                        isSellable, mainHandSlot, itemLevel, requiredLevel, damage, autoAttack, 
                        attackDelay, itemType);
                
                return weaponItem;
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
     * Get a list of WeaponItems that have a required level less than or equal to the specified level.
     * Returns an empty list if no matches are found.
     */
    public List<WeaponItem> getWeaponsByMaxLevel(int maxLevel) throws SQLException {
    	String SELECT_WEAPONS_BY_LEVEL = 
    	        "SELECT i.itemID, i.itemName, i.maxStackSize, i.vendorPrice, i.isSellable, i.itemType, " +  // Added itemType
    	        "w.equipmentSlotID, w.itemLevel, w.requiredLevel, w.damage, w.autoAttack, w.attackDelay " +
    	        "FROM WeaponItem w " +
    	        "INNER JOIN Item i ON w.itemID = i.itemID " +
    	        "WHERE w.requiredLevel <= ? AND i.itemType = 'WEAPON';";  // Added type check
        List<WeaponItem> weaponItems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(SELECT_WEAPONS_BY_LEVEL);
            selectStmt.setInt(1, maxLevel);
            results = selectStmt.executeQuery();

            while(results.next()) {
                int itemId = results.getInt("itemID");
                String itemName = results.getString("itemName");
                int maxStackSize = results.getInt("maxStackSize");
             // Handle vendorPrice properly
                Double vendorPrice = results.getDouble("vendorPrice");
                if (results.wasNull()) {
                    vendorPrice = null; // Explicitly set to null if database value is NULL
                }                boolean isSellable = results.getBoolean("isSellable");
                // Handle MainHandSlotSlot (nullable)
                MainHandSlot mainHandSlot = null;
                int equipmentSlotID = results.getInt("equipmentSlotID");
                Item.ItemType itemType = Item.ItemType.valueOf(results.getString("itemType")); // Get itemType
                if (!results.wasNull()) {
                    mainHandSlot = new MainHandSlot(equipmentSlotID);
                }
                int itemLevel = results.getInt("itemLevel");
                int requiredLevel = results.getInt("requiredLevel");
                int damage = results.getInt("damage");
                Double autoAttack = results.getDouble("autoAttack");
                Double attackDelay = results.getDouble("attackDelay");
                
                WeaponItem weaponItem = new WeaponItem(itemId, itemName, maxStackSize, vendorPrice,
                    isSellable, mainHandSlot, itemLevel, requiredLevel, damage, autoAttack, attackDelay, itemType); // Add itemType
                weaponItems.add(weaponItem);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            throw e;
        } finally {
            if(results != null) results.close();
            if(selectStmt != null) selectStmt.close();
            if(connection != null) connection.close();
        }
        return weaponItems;
    }
    
    /**
     * Update the damage value of the given WeaponItem.
     */
    public WeaponItem updateDamage(WeaponItem weaponItem, int newDamage) throws SQLException {
    	String UPDATE_DAMAGE = 
    	        "UPDATE WeaponItem SET damage=? WHERE itemID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(UPDATE_DAMAGE);
            updateStmt.setInt(1, newDamage);
            updateStmt.setInt(2, weaponItem.getItemID());
            updateStmt.executeUpdate();
            
            // Update the weapon parameter before returning to the caller.
            weaponItem.setDamage(newDamage);
            return weaponItem;
        } catch (SQLException e) {
        	e.printStackTrace();
            throw e;
        } finally {
            if(updateStmt != null) updateStmt.close();
            if(connection != null) connection.close();
        }
    }
    
    
    /**
     * Delete a WeaponItem from the database.
     * This demonstrates the delete method requirement.
     */
    public WeaponItem delete(WeaponItem weaponItem) throws SQLException {
    	String DELETE_WEAPON = 
    	        "DELETE FROM GearItem WHERE itemID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(DELETE_WEAPON);
            deleteStmt.setInt(1, weaponItem.getItemID());
         // Execute delete statement
 			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				System.out.println("No records available to delete for itemID=" + weaponItem.getItemID());
				return null;
			}
			// If deletion in Weapon table was successful, delete from Item table
			super.delete(weaponItem);
			
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
