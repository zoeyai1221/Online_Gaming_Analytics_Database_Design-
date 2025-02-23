package Milestone4.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Milestone4.model.Currency;
import Milestone4.model.Job;

public class CurrencyDao {
    private ConnectionManager connectionManager;
    private static CurrencyDao instance = null;
    
    protected CurrencyDao() {
        connectionManager = new ConnectionManager();
    }
    
    public static CurrencyDao getInstance() {
        if (instance == null) {
            instance = new CurrencyDao();
        }
        return instance;
    }
    
    
    /*
     * Create and return a currency instance with an auto_generated currencyID
     * */
    public Currency create(Currency currency) throws SQLException {
    	// uniqueness check
    	
        if(!getCurrencyByCurrencyName(currency.getCurrencyName()).isEmpty()) {
        	throw new SQLException("CurrencyName already exists: " + currency.getCurrencyName());
        }
		String insertCurrency =
				"INSERT INTO Currency(currencyName,cap,weeklyCap,isDiscontinued) " +
				"VALUES(?,?,?,?);";
    	Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCurrency,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, currency.getCurrencyName());
			insertStmt.setInt(2, currency.getCap());
			
			// handle the optional weeklyCap attribute
			if (currency.getWeeklyCap() != null) {
				insertStmt.setInt(3, currency.getWeeklyCap());
			}else {
				insertStmt.setNull(3, Types.INTEGER);
			}
			insertStmt.setBoolean(4, currency.isDiscontinued());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int currencyID = -1;
			if(resultKey.next()) {
				currencyID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			currency.setCurrencyID(currencyID);
			return currency;
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
    }
    
    
    /*
     * Search and return a currency instance with a specified currencyID
     * */
    public Currency getCurrencyByCurrencyID(int currencyID) throws SQLException{
		String selectCurrency =
				"SELECT currencyID,currencyName,cap,weeklyCap,isDiscontinued " +
				"FROM Currency " +
				"WHERE currencyID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCurrency);
			selectStmt.setInt(1, currencyID);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int resultCurrencyID = results.getInt("currencyID");
				String currencyName = results.getString("currencyName");
				int cap = results.getInt("cap");
				
				// deal with the optional attribute weeklyCap
				Integer weeklyCap = null;
				int weeklyCapValue = results.getInt("weeklyCap");
				if (!results.wasNull()) {
	                weeklyCap = weeklyCapValue;
	            }
				boolean isDiscontinued = results.getBoolean("isDiscontinued");
				// switch constructors depending on whether weeklyCap exists
				if (weeklyCap == null) {
	                return new Currency(resultCurrencyID, currencyName, cap, isDiscontinued);
	            } else {
	                return new Currency(resultCurrencyID, currencyName, cap, weeklyCap, isDiscontinued);
	            }
			}
		}catch (SQLException e) {
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
    
    
    
    /*
     * Update and return a currency instance with an updated isDiscontinued
     * */
    public Currency updateIsDiscontinued (Currency currency, boolean isDiscontinued) throws SQLException {
    	String updateIsDiscontinued = "UPDATE Currency SET isDiscontinued=? WHERE currencyID=?;";
    	Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateIsDiscontinued);
			updateStmt.setBoolean(1, isDiscontinued);
			updateStmt.setInt(2, currency.getCurrencyID());
			updateStmt.executeUpdate();
			currency.setDiscontinued(isDiscontinued);
			return currency;
		}catch (SQLException e) {
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
    
    
    /*
     * Delete a currency instance with a specified currencyID
     * */
    public Currency delete(Currency currency) throws SQLException{
    	String deleteCurrency = "DELETE FROM Currency WHERE currencyID=?;";
    	Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCurrency);
			int test = currency.getCurrencyID();
			deleteStmt.setInt(1, currency.getCurrencyID());
			deleteStmt.executeUpdate();
			return null;
		}catch (SQLException e) {
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
    
    
    /*
     * Return a list of currency instances with a specified currencyName
     * */
    public List<Currency> getCurrencyByCurrencyName(String currencyName) throws SQLException{
    	List<Currency> currencyList = new ArrayList<Currency>();
		String selectCurrency =
				"SELECT currencyID,currencyName,cap,weeklyCap,isDiscontinued " +
				"FROM Currency " +
				"WHERE currencyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCurrency);
			selectStmt.setString(1, currencyName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int currencyID = results.getInt("currencyID");
				String resultCurrencyName = results.getString("currencyName");
				int cap = results.getInt("cap");
		        // Handle weeklyCap, which could be NULL in the database
	            int weeklyCap = results.getInt("weeklyCap");
	            boolean isDiscontinued = results.getBoolean("isDiscontinued");
	            // Check if weeklyCap is NULL
	            if (results.wasNull()) {
	                // If weeklyCap is NULL, use the constructor that doesn't take weeklyCap
	                Currency currency = new Currency(currencyID, resultCurrencyName, cap, isDiscontinued);
	                currencyList.add(currency);
	            } else {
	                // If weeklyCap has a value, use the constructor that takes weeklyCap
	                Currency currency = new Currency(currencyID, resultCurrencyName, cap, weeklyCap, isDiscontinued);
	                currencyList.add(currency);
	            }
			}
		}catch (SQLException e) {
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
		return currencyList;
    }
    

}
