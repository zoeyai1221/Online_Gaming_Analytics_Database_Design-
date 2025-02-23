package Milestone4.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Milestone4.model.Job;
import Milestone4.model.JobRecord;
import Milestone4.model.Character;

public class JobRecordDao {
    private ConnectionManager connectionManager;
    private static JobRecordDao instance = null;
    
    protected JobRecordDao() {
        connectionManager = new ConnectionManager();
    }
    
    public static JobRecordDao getInstance() {
        if (instance == null) {
            instance = new JobRecordDao();
        }
        return instance;
    }
    
    
    
    /*
     * Create and return a jobRecord instance
     * */
    public JobRecord create(JobRecord jobRecord) throws SQLException{
		String insertjobRecord =
				"INSERT INTO JobRecord(characterID,jobID,jobLevel,experiencePoint) " +
				"VALUES(?,?,?,?);";
    	Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertjobRecord);
			insertStmt.setInt(1, jobRecord.getCharacter().getCharacterID());
			insertStmt.setInt(2, jobRecord.getJob().getJobID());
			insertStmt.setInt(3, jobRecord.getJobLevel());
			insertStmt.setInt(4, jobRecord.getExperiencePoint());
			insertStmt.executeUpdate();
			return jobRecord;
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
     * Search and return a jobRecord instance with a specified character and job
     * */
    public JobRecord getJobRecordByCharacterIDAndJobID(Character character, Job job) throws SQLException{
        String selectJobRecord = "SELECT characterID, jobID, jobLevel, experiencePoint FROM JobRecord WHERE characterID=? AND jobID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
        	connection = connectionManager.getConnection();
        	selectStmt = connection.prepareStatement(selectJobRecord);
        	selectStmt.setInt(1, character.getCharacterID());
        	selectStmt.setInt(2, job.getJobID());
        	results = selectStmt.executeQuery();
        	if(results.next()) {
        		int jobLevel = results.getInt("jobLevel");
        		int experiencePoint = results.getInt("experiencePoint");
        		JobRecord jobRecord = new JobRecord(character, job, jobLevel, experiencePoint);	
        		return jobRecord;
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
    
    
    
    
    public List<Integer> getJobRecordByCharacterID (String characterID) throws SQLException{
    	String selectJobRecord = "SELECT characterID, jobID, jobLevel, experiencePoint FROM JobRecord WHERE characterID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        List<Integer>  returnValue = new ArrayList<Integer>();
        try {
        	Integer characterIDInt = Integer.valueOf(characterID);
        	connection = connectionManager.getConnection();
        	selectStmt = connection.prepareStatement(selectJobRecord);
        	selectStmt.setInt(1, characterIDInt);
        	results = selectStmt.executeQuery();
        	while (results.next()) {
        		int jobId = results.getInt("jobID");
        		returnValue.add(jobId);
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
     * Update and return a JobRecord instance with an updated job level
     * */
    public JobRecord updateJobLevel(JobRecord jobRecord, int jobLevel) throws SQLException{
    	String updateJobLevel = "UPDATE JobRecord SET jobLevel=? WHERE characterID=? AND jobID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateJobLevel);
			updateStmt.setInt(1, jobLevel);
			updateStmt.setInt(2, jobRecord.getCharacter().getCharacterID());
			updateStmt.setInt(3, jobRecord.getJob().getJobID());
			updateStmt.executeUpdate();
			jobRecord.setJobLevel(jobLevel);
			return jobRecord;
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
     * Delete an JobRecord instance with a specified characterID and jobID
     * */
    public JobRecord delete(JobRecord jobRecord) throws SQLException{
    	String deleteJobRecord = "DELETE FROM JobRecord WHERE characterID=? AND jobID=?;";
    	Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteJobRecord);
			deleteStmt.setInt(1, jobRecord.getCharacter().getCharacterID());
			deleteStmt.setInt(2, jobRecord.getJob().getJobID());
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
