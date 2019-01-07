import java.awt.Color;

public class Player {
    public Player(int mentalState) {
	this.ending = mentalState;
    }
    
    public final String NAME = "CONNER";
    
    public int date; // 1. Ivy
		     // 2. Kate
		     // 3. Miranda
		     // 4. Tiffany
    private int ending; // 1. Loved: Parents die and leave you an inheritance
			// 2. Depressed: Long lost bank account rediscovered
			// 3. Hopeful: Work hard and receive a major bonus
			// 4. Nervous: Earn a well-paying movie contract
    
    // stats
    private int charm = 0;
    private int intelligence = 0;
    private int karma = 0;
    private int strength = 0;
    private int money = 0;
    
    private int jobLevel = 1; // job promotion counter
    private int shiftTotal = 0; // total amount of shifts worked
    
    // decisions
    public boolean hasSandwich;
    
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
    
    public void addShiftTotal(int gain) {
	if (this.shiftTotal + gain < 0) {
	    this.strength = 0;
	} else {
	    this.shiftTotal += gain;
	}
    }
    
    public void promote() {
	if (this.jobLevel < 5) {
	    this.jobLevel += 1;
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
