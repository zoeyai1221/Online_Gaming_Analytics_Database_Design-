package dal;
import model.Attribute;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttributeDao {
    private Connection connection;
    
    public AttributeDao(Connection connection) {
        this.connection = connection;
    }
    
    public Attribute getAttributeByID(int attributeID) throws SQLException {
        String sql = "SELECT * FROM Attribute WHERE attributeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, attributeID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Attribute(
                    rs.getInt("attributeID"),
                    rs.getString("attributeName"),
                    rs.getInt("attributeValue")
                );
            }
            return null;
        }
    }

    public Attribute getAttributeByName(String attributeName) throws SQLException {
        String sql = "SELECT * FROM Attribute WHERE attributeName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, attributeName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Attribute(
                    rs.getInt("attributeID"),
                    rs.getString("attributeName"),
                    rs.getInt("attributeValue")
                );
            }
            return null;
        }
    }

    public List<Attribute> getAttributesByValueRange(int minValue, int maxValue) throws SQLException {
        List<Attribute> attributes = new ArrayList<>();
        String sql = "SELECT * FROM Attribute WHERE attributeValue BETWEEN ? AND ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, minValue);
            stmt.setInt(2, maxValue);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                attributes.add(new Attribute(
                    rs.getInt("attributeID"),
                    rs.getString("attributeName"),
                    rs.getInt("attributeValue")
                ));
            }
        }
        return attributes;
    }
    
    public Attribute create(Attribute attribute) throws SQLException {
        if (getAttributeByName(attribute.getAttributeName()) != null) {
            throw new SQLException("Attribute name already exists: " + attribute.getAttributeName());
        }
        
        String sql = "INSERT INTO Attribute (attributeName, attributeValue) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, attribute.getAttributeName());
            stmt.setInt(2, attribute.getAttributeValue());
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                attribute.setAttributeID(rs.getInt(1));
            }
            return attribute;
        }
    }

    public boolean update(Attribute attribute) throws SQLException {
        Attribute existingAttribute = getAttributeByName(attribute.getAttributeName());
        if (existingAttribute != null && existingAttribute.getAttributeID() != attribute.getAttributeID()) {
            throw new SQLException("Attribute name already exists: " + attribute.getAttributeName());
        }
        
        String sql = "UPDATE Attribute SET attributeName = ?, attributeValue = ? WHERE attributeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, attribute.getAttributeName());
            stmt.setInt(2, attribute.getAttributeValue());
            stmt.setInt(3, attribute.getAttributeID());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int attributeID) throws SQLException {
        if (getAttributeByID(attributeID) == null) {
            return false;
        }
        
        String sql = "DELETE FROM Attribute WHERE attributeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, attributeID);
            return stmt.executeUpdate() > 0;
        }
    }
}