package Milestone4.dal;
import Milestone4.model.*;
import java.sql.*;

public class AttributeDao {
    private ConnectionManager connectionManager;
    private static AttributeDao instance = null;
    
    private AttributeDao() {
    	connectionManager  = new ConnectionManager();
    }
   
    // Add singleton 
    public static AttributeDao getInstance() {
        if (instance == null) {
            instance = new AttributeDao();
        }
        return instance;
    }
    
    public Attribute create(Attribute attribute) throws SQLException {

        String insertAttribute = "INSERT INTO Attribute (attributeName, attributeValue) VALUES (?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultSet = null;

        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertAttribute, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, attribute.getAttributeName());
            insertStmt.setInt(2, attribute.getAttributeValue());
            insertStmt.executeUpdate();

            resultSet = insertStmt.getGeneratedKeys();
            if (resultSet.next()) {
                attribute.setAttributeID(resultSet.getInt(1));
            }
            return attribute;
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
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    /**
     * Update an existing Attribute instance in the database.
     */
    public Attribute update(Attribute attribute) throws SQLException {

        String updateAttribute = "UPDATE Attribute SET attributeName = ?, attributeValue = ? WHERE attributeID = ?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;

        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateAttribute);
            updateStmt.setString(1, attribute.getAttributeName());
            updateStmt.setInt(2, attribute.getAttributeValue());
            updateStmt.setInt(3, attribute.getAttributeID());
            return attribute;
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
     * Delete an Attribute instance from the database.
     */
    public Attribute delete(int attributeID) throws SQLException {

        String deleteAttribute = "DELETE FROM Attribute WHERE attributeID = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;

        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteAttribute);
            deleteStmt.setInt(1, attributeID);
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
     * Retrieve an Attribute instance by its ID.
     */
    public Attribute getAttributeByID(int attributeID) throws SQLException {
        String selectAttribute = "SELECT * FROM Attribute WHERE attributeID = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAttribute);
            selectStmt.setInt(1, attributeID);
            results = selectStmt.executeQuery();

            if (results.next()) {
                return new Attribute(
                    results.getInt("attributeID"),
                    results.getString("attributeName"),
                    results.getInt("attributeValue")
                );
            }
            return null;
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
    }

    /**
     * Retrieve an Attribute instance by its name.
     */
    public Attribute getAttributeByName(String attributeName) throws SQLException {
        String selectAttribute = "SELECT * FROM Attribute WHERE attributeName = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;

        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAttribute);
            selectStmt.setString(1, attributeName);
            results = selectStmt.executeQuery();

            if (results.next()) {
                return new Attribute(
                    results.getInt("attributeID"),
                    results.getString("attributeName"),
                    results.getInt("attributeValue")
                );
            }
            return null;
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
    }

}