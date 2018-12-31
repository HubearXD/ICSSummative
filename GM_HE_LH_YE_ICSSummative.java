import java.awt.*;
import java.io.*;
import hsa.Console;
import java.awt.image.BufferedImage;
import javax.swing.*;
import hsa.Console;
import sun.audio.*;
import java.util.Random;

public class GM_HE_LH_YE_ICSSummative {
    private final static String WEEK[] = {"MONDAY", "TUESDAY", "WEDNESDAY",
					  "THURSDAY", "FRIDAY"};
    
    private static Console c;
    private static Player player;
    private static int day = 1;
    
    // awaits a numeric key press within a range and returns the number
    private static int awaitDigitRange(int max) {
	int in = 0;
	do {
	    in = Character.getNumericValue(c.getChar());
	} while(in < 1 || in > max);
	return in;
    }
    
    // awaits a character key press and returns the character
    private static char awaitTyping(char target) {
	if (target == 0) {
	    return 0;
	} else {
	    char in = 0;
	    do {
		in = c.getChar();
	    } while(in != target);
	    return in;
	}
    }
	
    // fades to black using progressively darker fullscreen rectangles
    private static void fadeBlack() {
	for (int i = 255; i >= 0; i--) {
	    Color bg = new Color(i, i, i);
	    c.setColor(bg);
	    c.fillRect(0, 0, c.getWidth(), c.getHeight());
	    wait(10);
	}
    }
    
    // fades to white using progressively lighter fullscreen rectangles
    private static void fadeWhite() {
	for (int i = 0; i <= 255; i++) {
	    Color bg = new Color(i, i, i);
	    c.setColor(bg);
	    c.fillRect(0, 0, c.getWidth(), c.getHeight());
	    wait(15);
	}
    }
    
    // simplifies audio resource imports
    private static AudioStream loadSound(String filePath) {
	try {
	    return new AudioStream(new FileInputStream(filePath));
	} catch (IOException ie) {
	    return null;
	}
    }
    
    // delivers text output one character at a time at default speed
    private static void typeByChar(String msg) {
	typeByChar(msg, 1, (char) 0);
    }
    
    // overloaded to allow typing speed adjustment
    private static void typeByChar(String msg, int speed) {
	typeByChar(msg, speed, (char) 0);
    }
    
    // overloaded to allow playing sound on text advance
    private static void typeByChar(String msg, String soundFilePath) {
	AudioPlayer.player.start(loadSound(soundFilePath));
	typeByChar(msg, 1, (char) 0);
    }
    
    // overloaded to allow typing speed adjustment
    // and playing sound on text advance
    private static void typeByChar(String msg, int speed,
	    String soundFilePath) {
	AudioPlayer.player.start(loadSound(soundFilePath));
	typeByChar(msg, speed, (char) 0);
    }
    
    // overloaded to allow typing speed adjustment and await keypress
    private static void typeByChar(String msg, int speed, char awaitTarget) {
	boolean debug = true; // setting true removes typing delay
	if (debug) {
	    c.print(msg);
	} else {
	    // changes invalid speed values to default
	    if (speed < 1 || speed > 3) {
		speed = 1; 
	    }
	    
	    for (int i = 0; i < msg.length(); i++) {
		char currentChar = msg.charAt(i);
		c.print(currentChar);
		wait((currentChar == '\n') ? 500 : (20 / speed));
	    }
	}
	awaitTyping(awaitTarget);
    }
	
    private static void typeByChar(String msg, int speed,
	    String soundFilePath, char awaitTarget) {
	AudioPlayer.player.start(loadSound(soundFilePath));
	typeByChar(msg, speed, awaitTarget);
    }
    
    // reads dialogue from TXT files
    private static void parseDialogue(String filePath) throws IOException {
	parseDialogue(filePath, 1, (char) 0);
    }
    
    // overloaded to await keypress before advancing to next line
    private static void parseDialogue(String filePath, char awaitTarget)
	throws IOException {
	parseDialogue(filePath, 1, awaitTarget);
    }
    
    // overloaded to adjust typing speed and await keypress before
    // advancing to next line
    private static void parseDialogue(String filePath, int charSpeed,
	    char awaitTarget) throws IOException {
	BufferedReader br = new BufferedReader(new FileReader(filePath));
	try {
	    String line = br.readLine();
	    
	    while (line != null) {
		// output style specified using line prefixes
		if (line.equals("^")) {
		    c.println();
		    wait(500);
		} else if (line.equals("\\")) {
		    c.clear();
		} else {
		    if (line.startsWith(">")) {
			typeByChar(line.substring(2, line.length()) + "\n",
				charSpeed, "snd/02_BEEP.wav");
		    } else {
			c.println(line);
		    }
		    awaitTyping(awaitTarget);
		}
		line = br.readLine();
	    }
	} finally {
	    br.close();
	}
    }
    
    // pauses the threads
    private static void wait(int delay) {
	try {
	    Thread.sleep(delay);
	} catch (InterruptedException ie) {
	    // do nothing
	}
    }
    
    public static void main(String[] args) throws IOException {
	c = new Console("LOVE STORY");
	
	c.setColor(Color.black);
	c.fillRect(0, 0, c.getWidth(), c.getHeight());
	c.setTextColor(Color.white);
	c.setTextBackgroundColor(Color.black);
	
	// opening dialogue
	AudioStream introMusic = loadSound("snd/01_INTROBG.wav");
	AudioPlayer.player.start(introMusic);
	
	parseDialogue("dialogue/01_INTRO.txt");
	
	// these characteristics are ultimately ignored
	c.print("What is your name? ");
	String name = c.readString();
	typeByChar("What do you look like?\n", "snd/02_BEEP.wav");
	c.print("Your hair colour? ");
	c.readString();
	c.print("Eye colour? ");
	c.readString();
	c.print("Skin tone? ");
	c.readString();
	c.print("Outfit? ");
	c.readString();

	// mental state selection, which affects story
	c.println();
	typeByChar("How is your little buddy feeling today?\n",
	    "snd/02_BEEP.wav");
	c.println("1. LOVED");
	c.println("2. DEPRESSED");
	c.println("3. HOPEFUL");
	c.println("4. NERVOUS");
	int mentalState = awaitDigitRange(4);
	
	typeByChar("Very good.", "snd/02_BEEP.wav");
	wait(3000);
	c.clear();

	// creates Player instance for the protagonist character
	player = new Player(mentalState);
	
	// second part of introduction
	parseDialogue("dialogue/02_INTRO.txt");
	
	wait(2000);
	AudioStream laugh = loadSound("snd/03_LAUGH.wav");
	AudioPlayer.player.start(laugh);
	
	if (!name.equalsIgnoreCase("Conner")) {
	    typeByChar("Y O U R   N A M E  I S . . .");
	    for (int i = 0; i < 256; i++) {
		Color bg = new Color(i, i, i);
		c.setColor(bg);
		c.setTextBackgroundColor(bg);
		c.setCursor(7, 1);
		wait(15);
		c.fillRect(0, 0, c.getWidth(), c.getHeight());
		c.print("Y O U R   N A M E  I S . . .");
	    }
	    wait(2000);
	} else {
	    fadeWhite();
	    wait(2000);
	}
	
	c.clear();
	AudioPlayer.player.stop(introMusic);
	AudioPlayer.player.stop(laugh);
	c.setColor(Color.black);
	c.fillRect(0, 0, c.getWidth(), c.getHeight());
	c.setTextBackgroundColor(Color.black);
	c.println("(Press ENTER to advance dialogue.)");
	
	// story begins
	AudioPlayer.player.start(loadSound("snd/04_ALARM.wav"));
	parseDialogue("dialogue/03_DAY1.txt", 2, '\n');
	
	// choice to take sandwich
	c.println();
	typeByChar("Take the sandwich?\n", 2, "snd/02_BEEP.wav");
	c.println("1. Bring it to school");
	c.println("2. Leave it behind");
	c.println();
	int sandwichChoice = awaitDigitRange(2);
	
	if (sandwichChoice == 1) {
	    player.addKarma(20);
	    player.hasSandwich = true;
	    c.print("You got an item: ");
	    typeByChar("CRUSTY SANDWICH.\n", 2, "snd/02_BEEP.wav");
	    c.println("+20 Karma");
	} else {
	    player.addKarma(-20);
	    player.hasSandwich = false;
	    typeByChar("You left the sandwich alone.\n", 2,
		"snd/02_BEEP.wav");
	    c.println("-20 Karma");
	}
	awaitTyping('\n');
	
	c.println();
	typeByChar("Me: \"Bye Mom, bye Dad, I'm leaving!\"", 2,
	    "snd/02_BEEP.wav", '\n');
	fadeBlack();
	
	// schoolyard
	c.clear();
	for (int day = 1;; day++) {
	    
	    typeByChar("What do you do?\n", 2, "snd/02_BEEP.wav");
	    int schoolyardChoice;
	    do {
		c.println("DAY " + day + ": " + WEEK[(day - 1) % 5]);
		c.println("8:30 a.m.");
		c.println();
		c.println("1. Talk to Ivy");
		c.println("2. Talk to Kate");
		c.println("3. Talk to Miranda");
		c.println("4. Talk to Tiffany");
		c.println("5. Go inside");
		schoolyardChoice = awaitDigitRange(5);
		
		// dialogues with girls
		c.println();
		switch (schoolyardChoice) {
		    case 1: // Ivy
			if (player.getCharm() >= 80
			    && player.getIntelligence() >= 60
			    && player.getKarma() <= -150
			    && player.getStrength() >= 30) {
			    // positive response
			} else {
			    typeByChar("Ivy: There's no 'I' in team, nor is "
			    + "there a 'u' in \"relationship\"!", 2,
			    "snd/02_BEEP.wav", '\n');
			    typeByChar("Looks like I need to get more charm, "
			    + "brains, and brawn before I can approach her.",
			    2, "snd/02_BEEP.wav", '\n');
			}
			break;
		    case 2: // Kate
			if (player.getCharm() >= 80
			    && player.getIntelligence() >= 30
			    && player.getKarma() >= 50
			    && player.getStrength() >= 100) {
			    // positive response
			} else {
			    typeByChar("She's MY type: cute. I'm gonna try "
				+ "to talk to her?\"\n", 2,
				"snd/02_BEEP.wav", '\n');
			    typeByChar("Me: \"Hey, cutie, the name's Conner. "
				+ "What's yours?.\"\n", 2, "snd/02_BEEP.wav",
				'\n');
			    typeByChar("Raian: \"HEY LITTLE BRAT! You're way "
				+ "too wimpy and weak to talk to Kate! Look "
				+ "at yourself, dwarf. Think ANY girl would "
				+ "want to be with you? Make sure you can "
				+ "look at a mirror without breaking it "
				+ "before you punish her eyes like this!"
				+ "\"\n", 2, "snd/02_BEEP.wav", '\n');
			}
			break;
		    case 3: // Miranda
			if (player.getCharm() >= 80
			    && player.getIntelligence() >= 100
			    && player.getStrength() >= 30) {
			    // positive response
			} else {
			    typeByChar("Me: \"Hey, what are you "
				+ "looking at?\"\n", 2, "snd/02_BEEP.wav",
				'\n');
			    typeByChar("Miranda: \"Go away, I'm studying for "
				+ "AIME. Talk to me when you score perfect "
				+ "on AMC 12.\"\n", 2, "snd/02_BEEP.wav",
				'\n');
			    typeByChar("Me: \"Okay then.\"\n", 2,
				"snd/02_BEEP.wav", '\n');
			}
			break;
		    case 4: // Tiffany
			if (player.getCharm() >= 40
			    && player.getIntelligence() >= 40
			    && player.getKarma() <= -20
			    && player.getStrength() >= 60) {
			    // positive response
			} else {
			    typeByChar("Tiffany: \"Sorry not interested. Who "
				+ "do you think you are anyway?\"\n", 2,
				"snd/02_BEEP.wav", '\n');
			    typeByChar("Looks like I need to catch her "
				+ "attention and get on her good side "
				+ "somehow. Do I need more looks?\n", 2,
				"snd/02_BEEP.wav", '\n');
			}
			break;
		}
		c.clear();
	    } while (schoolyardChoice != 5);
	    
	    // school morning
	    c.clear();
	}
    }
}
