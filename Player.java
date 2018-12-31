import java.awt.Color;

public class Player {
    public Player(int mentalState) {
	// Mental states correspond to storylines:
	// 1. Loved: Parents die and leave you an inheritance
	// 2. Depressed: Long lost bank account rediscovered
	// 3. Hopeful: Work hard and receive a major bonus
	// 4. Nervous: Get buff and earn a well-paying movie contract
	
	// change invalid storyline values to a default
	if (mentalState <= 1 || mentalState >= 4) { 
	    mentalState = 1;
	} else {
	    this.storyline = mentalState;
	}
    }
    
    public final String NAME = "CONNER";
    
    private int storyline;
    
    // stats
    private int charm;
    private int intelligence;
    private int karma;
    private int strength;
    private int money;
    
    public void addCharm(int gain) {
	this.charm += gain;
    }
    
    public void addIntelligence(int gain) {
	this.intelligence += gain;
    }
    
    public void addKarma(int gain) {
	this.karma += gain;
    }
    
    public void addStrength(int gain) {
	this.strength += gain;
    }
    
    public void addMoney(int gain) {
	this.money += gain;
    }
    
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
}
