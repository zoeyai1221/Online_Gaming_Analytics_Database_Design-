package Milestone4.dal;

import Milestone4.model.*;

import java.sql.*;

/**
 * Data access object (DAO) class to interact with the PercentageBonus table in the database.
 * This is used to store and retrieve {@link PercentageBonus} instances.
 */
public class PercentageBonusDao extends AttributeBonusDao {
    protected ConnectionManager connectionManager;

    // Singleton pattern: Only one instance of PercentageBonusDao is created.
    private static PercentageBonusDao instance = null;

    protected PercentageBonusDao() {
        connectionManager = new ConnectionManager();
    }

    public static PercentageBonusDao getInstance() {
        if (instance == null) {
            instance = new PercentageBonusDao();
        }
        return instance;
    }

    /**
     * Create a new PercentageBonus in the database.
     */
    public PercentageBonus create(PercentageBonus percentageBonus) throws SQLException {
        // Insert into the AttributeBonus superclass table first.
        super.create((AttributeBonus) percentageBonus);

        String insertPercentageBonus = "INSERT INTO PercentageBonus (itemID, attributeID, percentageBonusValue, bonusCap) VALUES (?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertPercentageBonus);
            insertStmt.setInt(1, percentageBonus.getItem().getItemID()); // Composite key part 1: itemID
            insertStmt.setInt(2, percentageBonus.getAttribute().getAttributeID()); // Composite key part 2: attributeID
            insertStmt.setDouble(3, percentageBonus.getPercentageBonusValue()); // PercentageBonus-specific value
            insertStmt.setInt(4, percentageBonus.getBonusCap()); // PercentageBonus cap value
            insertStmt.executeUpdate();
            return percentageBonus;
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
     * Retrieve a PercentageBonus by its related item and attribute.
     */
    public PercentageBonus getPercentageBonusByItemAndAttribute(Item item, Attribute attribute) throws SQLException {
        String selectPercentageBonus = "SELECT percentageBonusValue, bonusCap FROM PercentageBonus WHERE itemID = ? AND attributeID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectPercentageBonus);
            selectStmt.setInt(1, item.getItemID()); // Composite key part 1: itemID
            selectStmt.setInt(2, attribute.getAttributeID()); // Composite key part 2: attributeID
            results = selectStmt.executeQuery();

            if (results.next()) {
                double percentageBonusValue = results.getDouble("percentageBonusValue");
                int bonusCap = results.getInt("bonusCap");
                return new PercentageBonus(item, attribute, percentageBonusValue, bonusCap); // Construct PercentageBonus
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
     * Update the percentageBonusValue and bonusCap of a PercentageBonus in the database.
     */
    public PercentageBonus updatePercentageBonus(PercentageBonus percentageBonus, double newPercentageBonusValue, int newBonusCap) throws SQLException {
        String updatePercentageBonus = "UPDATE PercentageBonus SET percentageBonusValue = ?, bonusCap = ? WHERE itemID = ? AND attributeID = ?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;

        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updatePercentageBonus);
            updateStmt.setDouble(1, newPercentageBonusValue);
            updateStmt.setInt(2, newBonusCap);
            updateStmt.setInt(3, percentageBonus.getItem().getItemID()); // Composite key part 1: itemID
            updateStmt.setInt(4, percentageBonus.getAttribute().getAttributeID()); // Composite key part 2: attributeID
            updateStmt.executeUpdate();

            // Update the PercentageBonus object before returning it.
            percentageBonus.setPercentageBonusValue(newPercentageBonusValue);
            percentageBonus.setBonusCap(newBonusCap);
            return percentageBonus;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (updateStmt != null) {
                updateStmt.close();
            }
        }
    }

    /**
     * Delete a PercentageBonus from the database.
     */
    public PercentageBonus delete(PercentageBonus percentageBonus) throws SQLException {
        String deletePercentageBonus = "DELETE FROM PercentageBonus WHERE itemID = ? AND attributeID = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deletePercentageBonus);
            deleteStmt.setInt(1, percentageBonus.getItem().getItemID());
            deleteStmt.setInt(2, percentageBonus.getAttribute().getAttributeID());
            deleteStmt.executeUpdate();

            super.delete(percentageBonus); // Optionally delete from the superclass table if necessary.

            return null; // Return null to indicate the object has been deleted.
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
}
