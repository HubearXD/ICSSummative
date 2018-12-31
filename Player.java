import java.awt.Color;

public class Player {
    public Player(int mentalState) {
	// TODO: adjust stats
	switch (mentalState) {
	    case 1:
		break;
	    case 2:
		break;
	    case 3:
		break;
	    case 4:
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
