package Milestone4.model;

public class Job {
	private int jobID;      // PK, auto_increment
	private String jobName; // Unique
	
	// Constructor with all fields
	public Job(int jobID, String jobName) {
		this.jobID = jobID;
		this.jobName = jobName;
	}

	// Constructor with PK only
	public Job(int jobID) {
		this.jobID = jobID;
	}

	
	// Constructor with attributes other than PK
	public Job(String jobName) {
		this.jobName = jobName;
	}

	public int getJobID() {
		return jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		if (jobName == null || jobName.trim().isEmpty()) {
            throw new IllegalArgumentException("jobName cannot be null or empty");
        }
		if (jobName.length() > 255) {
            throw new IllegalArgumentException("jobName cannot be longer than 255 characters");
        }
		this.jobName = jobName;
	}
}
