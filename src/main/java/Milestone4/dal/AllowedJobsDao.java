package Milestone4.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Milestone4.model.AllowedJobs;
import Milestone4.model.Item;
import Milestone4.model.Job;

public class AllowedJobsDao {
    private ConnectionManager connectionManager;
    private static AllowedJobsDao instance = null;
    
    protected AllowedJobsDao() {
        connectionManager = new ConnectionManager();
    }
    
    public static AllowedJobsDao getInstance() {
        if (instance == null) {
            instance = new AllowedJobsDao();
        }
        return instance;
    }
    
    
    /*
     * Create and return an allowedJobs instance
     * */
    public AllowedJobs create(AllowedJobs allowedJobs) throws SQLException{
		String insertAllowedJobs =
				"INSERT INTO AllowedJobs(itemID,jobID) " +
				"VALUES(?,?);";
    	Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAllowedJobs);
			insertStmt.setInt(1,allowedJobs.getItem().getItemID());
			insertStmt.setInt(2,allowedJobs.getJob().getJobID());
			insertStmt.executeUpdate();
			return allowedJobs;
			
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
     * Search and return an AllowedJobs instance with a specified item and job
     * */
    public AllowedJobs getAllowedJobsByItemIDAndJobID(Item item, Job job) throws SQLException{
        String selectAllowedJobs = "SELECT itemID, jobID FROM AllowedJobs WHERE itemID=? AND jobID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
        	connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAllowedJobs);
            selectStmt.setInt(1, item.getItemID());
            selectStmt.setInt(2, job.getJobID());
            results = selectStmt.executeQuery();
            // is this allowed????
            if (results.next()) {
                return new AllowedJobs(item, job);
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
     * Delete an Allowedjobs instance with a specified itemID and jobID
     * */
    public AllowedJobs delete(AllowedJobs allowedJobs) throws SQLException{
    	String deleteAllowedJobs = "DELETE FROM AllowedJobs WHERE itemID=? AND jobID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAllowedJobs);
			deleteStmt.setInt(1, allowedJobs.getItem().getItemID());
			deleteStmt.setInt(2, allowedJobs.getJob().getJobID());
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
