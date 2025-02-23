package Milestone4.dal;
import Milestone4.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDao {
    private ConnectionManager connectionManager;
    private static JobDao instance = null;
    
    protected JobDao() {
        connectionManager = new ConnectionManager();
    }
    
    public static JobDao getInstance() {
        if (instance == null) {
            instance = new JobDao();
        }
        return instance;
    }
    
    
    /*
     * Create and return a job instance with an auto_generated jobID
     * */
    public Job create(Job job) throws SQLException{
    	// uniqueness check
        if(!getJobByJobName(job.getJobName()).isEmpty()) {
        	throw new SQLException("JobName already exists: " + job.getJobName());
        }
    	
    	String insertJob =
    			"INSERT INTO Job(jobName) " +
    			"VALUES(?);";
    	Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertJob,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, job.getJobName());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int jobID = -1;
			if(resultKey.next()) {
				jobID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			job.setJobID(jobID);
			return job;
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
     * Search and return a job instance with a specified jobID
     * */
    public Job getJobByJobID(int jobID) throws SQLException{
		String selectJob =
				"SELECT jobID,jobName " +
				"FROM Job " +
				"WHERE jobID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectJob);
			selectStmt.setInt(1, jobID);
			results = selectStmt.executeQuery();
			// JobDao jobDao = JobDao.getInstance();
			if(results.next()) {
				int resultJobID = results.getInt("jobID");
				String jobName = results.getString("jobName");
				Job job = new Job(resultJobID, jobName);
				return job;
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
     * Update and return a job instance with an updated jobName
     * */
    public Job updateJobName(Job job, String jobName) throws SQLException  {
    	String updateJobName = "UPDATE Job SET jobName=? WHERE jobID=?;";
    	Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateJobName);
			updateStmt.setString(1, jobName);
			updateStmt.setInt(2, job.getJobID());
			updateStmt.executeUpdate();
			job.setJobName(jobName);
			return job;
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
     * Delete a job instance with a specified jobID
     * */
	public Job delete(Job job) throws SQLException{
		String deleteJob = "DELETE FROM Job WHERE jobID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteJob);
			deleteStmt.setInt(1, job.getJobID());
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
     * Return a list of job instances with a specified jobName
     * */
	public List<Job> getJobByJobName(String jobName) throws SQLException{
    	List<Job> jobList = new ArrayList<Job>();
		String selectJob =
				"SELECT jobID,jobName " +
				"FROM Job " +
				"WHERE jobName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectJob);
			selectStmt.setString(1, jobName);
				results = selectStmt.executeQuery();
			while(results.next()) {
				int jobID = results.getInt("jobID");
				String resultJobName = results.getString("jobName");
				Job job = new Job(jobID, resultJobName);
				jobList.add(job);
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
		return jobList;
	}
	

}
