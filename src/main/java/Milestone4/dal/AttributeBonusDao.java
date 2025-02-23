package Milestone4.dal;

import Milestone4.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object (DAO) class to interact with the AttributeBonus table in your database.
 * This is used to store and retrieve {@link AttributeBonus} instances.
 */

public class AttributeBonusDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static AttributeBonusDao instance = null;

    protected AttributeBonusDao() {
        connectionManager = new ConnectionManager();
    }

    public static AttributeBonusDao getInstance() {
        if (instance == null) {
            instance = new AttributeBonusDao();
        }
        return instance;
    }

    /**
     * Create a new AttributeBonus instance in the database.
     */
    public AttributeBonus create(AttributeBonus attributeBonus) throws SQLException {
        String insertAttributeBonus = "INSERT INTO AttributeBonus(itemID, attributeID) VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertAttributeBonus);
            insertStmt.setInt(1, attributeBonus.getItem().getItemID());
            insertStmt.setInt(2, attributeBonus.getAttribute().getAttributeID());
            insertStmt.executeUpdate();

            return attributeBonus;
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
     * Get the AttributeBonus record by fetching it from the database.
     */
    public AttributeBonus getAttributeBonusByItemAndAttribute(Item item, Attribute attribute) throws SQLException {
        String selectAttributeBonus = "SELECT itemID, attributeID FROM AttributeBonus WHERE itemID=? AND attributeID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAttributeBonus);
            selectStmt.setInt(1, item.getItemID());
            selectStmt.setInt(2, attribute.getAttributeID());
            results = selectStmt.executeQuery();

            if (results.next()) {
                return new AttributeBonus(item, attribute);
            }
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
        return null;
    }

    /**
     * Delete the AttributeBonus instance.
     */
    
    public AttributeBonus delete(AttributeBonus attributeBonus) throws SQLException {
        String deleteAttributeBonus = "DELETE FROM AttributeBonus WHERE itemID=? AND attributeID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteAttributeBonus);
            deleteStmt.setInt(1, attributeBonus.getItem().getItemID());
            deleteStmt.setInt(2, attributeBonus.getAttribute().getAttributeID());
            deleteStmt.executeUpdate();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }
    
    /**
     * Get all AttributeBonus records for a specific item.
     */
    public List<AttributeBonus> getAttributeBonusesByItemID(Item item) throws SQLException {
        List<AttributeBonus> attributeBonuses = new ArrayList<>();
        String selectAttributeBonuses = "SELECT itemID, attributeID FROM AttributeBonus WHERE itemID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAttributeBonuses);
            selectStmt.setInt(1, item.getItemID());
            results = selectStmt.executeQuery();

            AttributeDao attributeDao = AttributeDao.getInstance();

            while (results.next()) {
                int attributeID = results.getInt("attributeID");
                // need getAttributeByID in the attributeDao.java file
                Attribute attribute = attributeDao.getAttributeByID(attributeID);
                AttributeBonus attributeBonus = new AttributeBonus(item, attribute);
                attributeBonuses.add(attributeBonus);
            }
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
        return attributeBonuses;
    }
    
    
}