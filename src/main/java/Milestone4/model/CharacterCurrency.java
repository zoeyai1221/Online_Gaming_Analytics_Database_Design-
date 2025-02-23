package Milestone4.model;

public class CharacterCurrency {
	private Character character; // PK and FK to Character
	private Currency currency;   // PK and FK to Currency
	private int totalAmount;     // not null
	private Integer weeklyEarned;    // optional
	
	
	// Constructor with all fields
	public CharacterCurrency(Character character, Currency currency, int totalAmount, Integer weeklyEarned) {
		this.character = character;
		this.currency = currency;
		this.totalAmount = totalAmount;
		this.weeklyEarned = weeklyEarned;
	}
	
	
	// Constructor with compound PK only
	public CharacterCurrency(Character character, Currency currency) {
		this.character = character;
		this.currency = currency;
	}
	

	// Constructor with attributes other than compound PK
	public CharacterCurrency(int totalAmount, Integer weeklyEarned) {
		this.totalAmount = totalAmount;
		this.weeklyEarned = weeklyEarned;
	}

	// Constructor without weeklyEarned(optional attribute)
	public CharacterCurrency(Character character, Currency currency, int totalAmount) {
		this.character = character;
		this.currency = currency;
		this.totalAmount = totalAmount;
	}

	public Character getCharacter() {
		return character;
	}


	public void setCharacter(Character character) {
		this.character = character;
	}


	public Currency getCurrency() {
		return currency;
	}


	public void setCurrency(Currency currency) {
		this.currency = currency;
	}


	public int getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}


	public Integer getWeeklyEarned() {
		return weeklyEarned;
	}


	public void setWeeklyEarned(Integer weeklyEarned) {
		this.weeklyEarned = weeklyEarned;
	}

}
