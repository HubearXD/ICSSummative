import java.awt.*;
import java.io.*;
import hsa.Console;
import java.awt.image.BufferedImage;
import javax.swing.*;
import hsa.Console;
import sun.audio.*;
import java.util.Random;

public class GM_HE_LH_YE_ICSSummative {
    private static AudioStream as;    
    private static Console c;
    private static Player player;
    
    // awaits a numeric key press within a range and returns the number
    private static int awaitDigitRange(int max) {
	int in = 0;
	do {
	    in = Character.getNumericValue(c.getChar());
	} while(in < 1 || in > max);
	return in;
    }
    
    // overloaded to allow playing sound on selection
    private static int awaitDigitRange(int max, String soundFilePath) {
	int in = awaitDigitRange(max);
	AudioPlayer.player.start(loadSound(soundFilePath));
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
    
    // overloaded to accept multiple characters
    private static char awaitTyping(char[] targets) {
	char in = 0;
	boolean isTarget = false;
	
	do {
	    in = c.getChar();
	    for(int i = 0; i < targets.length; i++) {
		if(in == targets[i]) {
		    isTarget = true;
		    break;
		}
	    }
	} while(!isTarget);
	return in;
    }
    
    // fades to black using progressively darker fullscreen rectangles
    private static void fadeBlack() {
	for (int i = 255; i >= 0; i--) {
	    Color bg = new Color(i, i, i);
	    c.setColor(bg);
	    c.fillRect(0, 0, c.getWidth(), c.getHeight());
	    wait(15);
	}
    }
    
    // fades to white using progressively darker fullscreen rectangles
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
	typeByChar(msg, 1);
    }
    
    // overloaded to allow typing speed adjustment
    private static void typeByChar(String msg, int speed) {
	boolean debug = false; // setting true removes typing delay
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
    }
    
    // overloaded to allow playing sound on text advance
    private static void typeByChar(String msg, String soundFilePath) {
	AudioPlayer.player.start(loadSound(soundFilePath));
	typeByChar(msg, 1);
    }
    
    // overloaded to allow typing speed adjustment
    // and playing sound on text advance
    private static void typeByChar(String msg, int speed,
	    String soundFilePath) {
	AudioPlayer.player.start(loadSound(soundFilePath));
	typeByChar(msg, speed);
    }
    
    // reads dialogue from TXT files
    private static void parseDialogue(String filePath) throws IOException {
	parseDialogue(filePath, 1, (char) 0);
    }
    
    // overloaded to await keypress before advancing to next line
    private static void parseDialogue(String filePath, char awaitTarget) throws IOException {
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
		    if (line.startsWith(". ")) {
			c.println(line);
		    } else if (line.startsWith(">")) {
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
	typeByChar("How is your little buddy feeling today?\n", "snd/02_BEEP.wav");
	c.println("1. LOVED");
	c.println("2. DEPRESSED");
	c.println("3. HOPEFUL");
	c.println("4. NERVOUS");
	
	int mentalState = awaitDigitRange(4, "snd/02_LAUGH.wav");
	
	wait(1000);
	typeByChar("Very good.", "snd/02_BEEP.wav");
	wait(3000);
	c.clear();

	// creates Player instance for the protagonist character
	player = new Player(mentalState);
	
	
	// second part of introduction
	parseDialogue("dialogue/02_INTRO.txt");
	
	wait(3000);
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
	    fadeBlack();
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
	typeByChar("Take the sandwich?\n", "snd/02_BEEP.wav");
	c.println("1. Bring it to school");
	c.println("2. Leave it behind");
	c.println();
	int choice = awaitDigitRange(2, "snd/03_LAUGH.wav");
	
	if (choice == 1) {
	    player.addKarma(2);
	    player.hasSandwich = true;
	    c.print("You got an item: ");
	    typeByChar("CRUSTY SANDWICH\n");
	} else {
	    player.addKarma(-2);
	    player.hasSandwich = false;
	    typeByChar("I left the sandwich alone.\n", "snd/02_BEEP.wav");
	}
	awaitTyping('\n');
	
	typeByChar("Bye Mom, Bye Dad, I'm leaving!", 2, "snd/02_BEEP.wav");
	awaitTyping('\n');
	fadeBlack();
    }
}
