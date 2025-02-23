package Milestone4.dal;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import Milestone4.model.CharacterCurrency;
import Milestone4.model.Currency;
import Milestone4.model.Character;

public class CharacterCurrencyDao {
    private ConnectionManager connectionManager;
    private static CharacterCurrencyDao instance = null;
    
    protected CharacterCurrencyDao() {
        connectionManager = new ConnectionManager();
    }
    
    public static CharacterCurrencyDao getInstance() {
        if (instance == null) {
            instance = new CharacterCurrencyDao();
        }
        return instance;
    }
    
    
    
    /*
     * Create and return a characterCurrency instance
     * */
    public CharacterCurrency create(CharacterCurrency characterCurrency) throws SQLException{
		String insertCharacterCurrency =
				"INSERT INTO CharacterCurrency(characterID,currencyID,totalAmount,weeklyEarned) " +
				"VALUES(?,?,?,?);";
    	Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCharacterCurrency);
			insertStmt.setInt(1, characterCurrency.getCharacter().getCharacterID());
			insertStmt.setInt(2, characterCurrency.getCurrency().getCurrencyID());
			insertStmt.setInt(3, characterCurrency.getTotalAmount());
			// handle the optional weeklyEarned attribute
			if (characterCurrency.getWeeklyEarned() != null) {
				insertStmt.setInt(4, characterCurrency.getWeeklyEarned());
			}else {
				insertStmt.setNull(4, Types.INTEGER);
			}
			insertStmt.executeUpdate();
			return characterCurrency;
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
		}
    }
    
    
    
    /*
     * Search and return a CharacterCurrency instance with a specified character and currency
     * */
    public CharacterCurrency getCharacterCurrencyByCharacterIDAndCurrencyID(Character character, Currency currency) throws SQLException{
        String selectCharacterCurrency = "SELECT characterID, currencyID, totalAmount, weeklyEarned FROM CharacterCurrency WHERE characterID=? AND currencyID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
        	connection = connectionManager.getConnection();
        	selectStmt = connection.prepareStatement(selectCharacterCurrency);
        	selectStmt.setInt(1, character.getCharacterID());
        	selectStmt.setInt(2, currency.getCurrencyID());
        	results = selectStmt.executeQuery();
        	// deal with the optional attribute weeklyEarned
        	if(results.next()) {
        		int totalAmount = results.getInt("totalAmount");
        		Integer weeklyEarned = null;
                int weeklyEarnedValue = results.getInt("weeklyEarned");
                if (!results.wasNull()) {
                    weeklyEarned = weeklyEarnedValue;
                }
                // switch constructors depending on whether weeklyEarned exists
        		if (weeklyEarned == null) {
                    return new CharacterCurrency(character, currency, totalAmount);
                } else {
                    return new CharacterCurrency(character, currency, totalAmount, weeklyEarned);
                }
        	}
        }catch (SQLException e) {
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
    
   
    
    
    /*
     * Search and return a list of currencyIDs with a string-characterID
     * */
    public List<Integer> getCharacterCurrencyIdByCharacterId(String characterID) throws SQLException{
        String selectCharacterCurrency = "SELECT characterID, currencyID, totalAmount, weeklyEarned FROM CharacterCurrency WHERE characterID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        CharacterCurrency tempCurrency = null;
        List<Integer>  returnValue = new ArrayList<Integer>();
        try {
        	Integer characterIDInt = Integer.valueOf(characterID);
        	connection = connectionManager.getConnection();
        	selectStmt = connection.prepareStatement(selectCharacterCurrency);
        	selectStmt.setInt(1, characterIDInt);
        	results = selectStmt.executeQuery();
        	// deal with the optional attribute weeklyEarned
        	while (results.next()) {
        		int currencyId = results.getInt("currencyID");
        		returnValue.add(currencyId);
        	}
        	return returnValue;
        }catch (SQLException e) {
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
    
    
    
    /*
     * Update and return a CharacterCurrency instance with an updated total amount
     * */
    public CharacterCurrency updateTotalAmount(CharacterCurrency characterCurrency, int totalAmount) throws SQLException{
    	String updateTotalAmount = "UPDATE CharacterCurrency SET totalAmount=? WHERE characterID=? AND currencyID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateTotalAmount);
			updateStmt.setInt(1,totalAmount);
			updateStmt.setInt(2,characterCurrency.getCharacter().getCharacterID());
			updateStmt.setInt(3,characterCurrency.getCurrency().getCurrencyID());
			updateStmt.executeUpdate();
			characterCurrency.setTotalAmount(totalAmount);
			return characterCurrency;	
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
    
    
    /*
     * Delete an CharacterCurrency instance with a specified characterID and currencyID
     * */
    public CharacterCurrency delete(CharacterCurrency characterCurrency) throws SQLException{
    	String deleteCharacterCurrency = "DELETE FROM CharacterCurrency WHERE characterID=? AND currencyID=?;";
    	Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCharacterCurrency);
			deleteStmt.setInt(1, characterCurrency.getCharacter().getCharacterID());
			deleteStmt.setInt(2, characterCurrency.getCurrency().getCurrencyID());
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
    
    

}
