package Milestone4.model;

public class AllowedJobs {
	private Item item;    // PK and FK to Item
	private Job job;      // PK and FK to Job
	
	// Constructor with all fields
	public AllowedJobs(Item item, Job job) {
		this.item = item;
		this.job = job;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
}
