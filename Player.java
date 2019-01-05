import java.awt.Color;

public class Player {
    public Player(int mentalState) {
	// Mental states correspond to storylines:
	// 1. Loved: Parents die and leave you an inheritance
	// 2. Depressed: Long lost bank account rediscovered
	// 3. Hopeful: Work hard and receive a major bonus
	// 4. Nervous: Get buff and earn a well-paying movie contract
	
	this.ending = mentalState;
    }
    
    public final String NAME = "CONNER";
    
    private int date; // 1. Ivy
		      // 2. Kate
		      // 3. Miranda
		      // 4. Tiffany
    private int ending;
    
    // stats
    private int charm = 0;
    private int intelligence = 0;
    private int karma = 0;
    private int strength = 0;
    private int money = 0;
    
    // job promotion counter
    private int jobLevel = 0;
    
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
	this.money += gain;
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
}
