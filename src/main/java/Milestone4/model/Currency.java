package Milestone4.model;

public class Currency {
	private int currencyID;      // PK, auto_increment
	private String currencyName; // Unique, not null
	private int cap;             // not null
	private Integer weeklyCap;       // optional
	private boolean isDiscontinued;// not null
	
	
	// Constructor with all fields
	public Currency(int currencyID, String currencyName, int cap, Integer weeklyCap, boolean isDiscontinued) {
		this.currencyID = currencyID;
		this.currencyName = currencyName;
		this.cap = cap;
		this.weeklyCap = weeklyCap;
		this.isDiscontinued = isDiscontinued;
	}

	
	// Constructor with PK only
	public Currency(int currencyID) {
		this.currencyID = currencyID;
	}
	
	// Constructor with attributes other than PK
	public Currency(String currencyName, int cap, Integer weeklyCap, boolean isDiscontinued) {
		this.currencyName = currencyName;
		this.cap = cap;
		this.weeklyCap = weeklyCap;
		this.isDiscontinued = isDiscontinued;
	}


	// Constructor without weeklyCap(optional attribute)
	public Currency(int currencyID, String currencyName, int cap, boolean isDiscontinued) {
		this.currencyID = currencyID;
		this.currencyName = currencyName;
		this.cap = cap;
		this.isDiscontinued = isDiscontinued;
	}


	public int getCurrencyID() {
		return currencyID;
	}


	public void setCurrencyID(int currencyID) {
		this.currencyID = currencyID;
	}


	public String getCurrencyName() {
		return currencyName;
	}


	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}


	public int getCap() {
		return cap;
	}


	public void setCap(int cap) {
		this.cap = cap;
	}


	public Integer getWeeklyCap() {
		return weeklyCap;
	}


	public void setWeeklyCap(Integer weeklyCap) {
		this.weeklyCap = weeklyCap;
	}


	public boolean isDiscontinued() {
		return isDiscontinued;
	}


	public void setDiscontinued(boolean isDiscontinued) {
		this.isDiscontinued = isDiscontinued;
	}
}
