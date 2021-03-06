import java.awt.Color;

public class Player {
    public int date;      // 1. Ivy
    public int quest = 0; // 2. Kate
			  // 3. Miranda
			  // 4. Tiffany
    
    public int ending; // 1. Loved: Parents die and leave you an inheritance
		       // 2. Depressed: Long lost bank account rediscovered
		       // 3. Hopeful: Work hard and receive a major bonus
		       // 4. Nervous: Earn a well-paying movie contract
    
    // decisions
    public boolean hasSandwich;
    
    // stats
    private int charm = 0;
    private int intelligence = 0;
    private int karma = 0;
    private int strength = 0;
    private int money = 0;
    
    private int jobLevel = 0; // 0. Floor Sweeper
			      // 1. Cashier
			      // 2. Dough Flipper
			      // 3. Topping Topper
			      // 4. Major Manager
    private int shiftTotal = 0; // total amount of shifts worked
	    
    // stat modifiers
    // only karma can drop below 0
    public void addCharm(int gain) {
	if (this.charm + gain < 0) {
	    this.charm = 0;
	} else {
	    this.charm += gain;
	}
    }
    
    public void addIntelligence(int gain) {
	if (this.intelligence + gain < 0) {
	    this.intelligence = 0;
	} else {
	    this.intelligence += gain;
	}
    }
    
    public void addKarma(int gain) {
	// karma can be negative
	this.karma += gain;
    }
    
    public void addStrength(int gain) {
	if (this.strength + gain < 0) {
	    this.strength = 0;
	} else {
	    this.strength += gain;
	}
    }
    
    public void addMoney(int gain) {
	// player may accrue debt
	this.money += gain;
    }
    
    public void incrementShiftTotal(int gain) {
	this.shiftTotal++;
    }
    
    public void promote() {
	if (this.jobLevel < 5) {
	    this.jobLevel++;
	}
    }
    
    // getter methods
    public int getCharm() {
	return this.charm;
    }
    
    public int getIntelligence() {
	return this.intelligence;
    }
    
    public int getKarma() {
	return this.karma;
    }
    
    public int getStrength() {
	return this.strength;
    }
    
    public int getMoney() {
	return this.money;
    }
    
    public int getJobLevel() {
	return this.jobLevel;
    }
    
    public int getShiftTotal() {
	return this.shiftTotal;
    }
}
