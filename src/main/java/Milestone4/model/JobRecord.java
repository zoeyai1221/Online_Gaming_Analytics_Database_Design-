package Milestone4.model;

public class JobRecord {
	private Character character;  // PK and FK to Character
	private Job job;              // PK and FK to Job
	private int jobLevel;
	private int experiencePoint;
	
	
	// Constructor with all fields
	public JobRecord(Character character, Job job, int jobLevel, int experiencePoint) {
		this.character = character;
		this.job = job;
		this.jobLevel = jobLevel;
		this.experiencePoint = experiencePoint;
	}
	
	
	// Constructor with compound PK only
	public JobRecord(Character character, Job job) {
		this.character = character;
		this.job = job;
	}

	
	// Constructor with attributes other than PK
	public JobRecord(int jobLevel, int experiencePoint) {
		this.jobLevel = jobLevel;
		this.experiencePoint = experiencePoint;
	}


	public Character getCharacter() {
		return character;
	}


	public void setCharacter(Character character) {
		this.character = character;
	}


	public Job getJob() {
		return job;
	}


	public void setJob(Job job) {
		this.job = job;
	}


	public int getJobLevel() {
		return jobLevel;
	}


	public void setJobLevel(int jobLevel) {
		this.jobLevel = jobLevel;
	}


	public int getExperiencePoint() {
		return experiencePoint;
	}


	public void setExperiencePoint(int experiencePoint) {
		this.experiencePoint = experiencePoint;
	}
	
}