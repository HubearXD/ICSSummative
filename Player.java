import java.awt.Color;

public class Player {
    public Player(int mentalState) {
	switch (mentalState) { 
	/*
	    Selecting 1, 2, 3, 4 chooses your mental state:
	    1: Loved
	    2: Depressed
	    3: Hopeful
	    4: Nervous
	*/
	
	
	    case 1:
		break;
	    case 2:
		break;
	    case 3:
		break;
	    case 4:
		break;
	    default:
		break;
	}
    }
    
    public final String NAME = "CONNER";
    
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
