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
    
    // waits for a character key press and returns the character
    private static char awaitTyping(char target) {
	char in = 0;
	do {
	    in = c.getChar();
	}
	while(in != target);
	return in;
    }
    
    // overloaded to accept multiple characters
    private static char awaitTyping(char[] targets) {
	char in = 0;
	boolean isTarget = false;
	
	do {
	    in = c.getChar();
	    for(int i = 0 ; i < targets.length ; i++) {
		if(in == targets [i]) {
		    isTarget = true;
		    break;
		}
	    }
	} while(!isTarget);
	return in;
    }
    
    // simplifies audio resource imports
    private static AudioStream loadSound(String filepath) {
	try {
	    return new AudioStream(new FileInputStream(filepath));
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
	    if (speed < 1 || speed > 5) {
		speed = 1; 
	    }
	    
	    for (int i = 0 ; i < msg.length() ; i++) {
		char currentChar = msg.charAt(i);
		c.print(currentChar);
		wait(currentChar == '\n' ? 500 : 20 / speed);
	    }
	}
    }
    
    // reads dialogue from TXT files
    private static void parseDialogue(String filepath) throws IOException {
	BufferedReader br = new BufferedReader(new FileReader(filepath));
	try {
	    String line = br.readLine();
	    
	    while (line != null) {
		// output style specified using line prefixes
		if (line.equals("^")) {
		    wait(500);
		    c.println();
		} else {
		    AudioPlayer.player.start(loadSound("snd/02_TEXTBEEP.wav"));
		    if(line.startsWith(". ")) {
			c.println(line);
		    } else if (line.startsWith(">")) {
			typeByChar(line.substring(2, line.length()) + "\n");
		    } else {
			c.println(line);
		    }
		}
		line = br.readLine();
	    }            
	} finally {
	    br.close();
	}
    }
    
    // overloaded to await keypress before advancing to next line
    private static void parseDialogue(String filepath, char awaitTarget) throws IOException {
	BufferedReader br = new BufferedReader(new FileReader(filepath));
	try {
	    String line = br.readLine();
	    
	    while (line != null) {
		// output style specified using line prefixes
		if (line.equals("^")) {
		    wait(500);
		    c.println();
		} else {
		    AudioPlayer.player.start(loadSound("snd/02_TEXTBEEP.wav"));
		    if (line.startsWith(". ")) {
			c.println(line);
		    } else if (line.startsWith(">")) {
			typeByChar(line.substring(2, line.length()) + "\n");
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
    
    // overloaded to adjust typing speed and await keypress before
    // advancing to next line
    private static void parseDialogue(String filepath, int charSpeed,
	    char awaitTarget) throws IOException {
	BufferedReader br = new BufferedReader(new FileReader(filepath));
	try {
	    String line = br.readLine();
	    
	    while (line != null) {
		// output style specified using line prefixes
		if (line.equals("^")) {
		    wait(500);
		    c.println();
		} else {
		    AudioPlayer.player.start(loadSound("snd/02_TEXTBEEP.wav"));
		    if (line.startsWith(". ")) {
			c.println(line);
		    } else if (line.startsWith(">")) {
			typeByChar(line.substring(2, line.length()) + "\n",
				charSpeed);
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
    
    private static void wait(int delay) {
	try {
	    Thread.sleep(delay);
	} catch (InterruptedException ie) {
	    // do nothing
	}
    }
    
    public static void main(String[] args) throws IOException {
	c = new Console("LOVE STORY");
	
	// opening dialogue
	AudioStream introMusic = loadSound("snd/01_INTROBG.wav");
	AudioPlayer.player.start(introMusic);

	c.setColor(Color.black);
	c.fillRect(0, 0, c.getWidth(), c.getHeight());
	c.setTextColor(Color.white);
	c.setTextBackgroundColor(Color.black);
	
	parseDialogue("dialogue/01_INTRO.txt");
	
	// these characteristics are ultimately ignored
	c.print("What is your name? ");
	String name = c.readString();
	AudioPlayer.player.start(loadSound("snd/02_TEXTBEEP.wav"));
	typeByChar("What do you look like?\n");
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
	AudioPlayer.player.start(loadSound("snd/02_TEXTBEEP.wav"));
	typeByChar("How is your little buddy feeling today?\n");
	c.println("1. LOVED");
	c.println("2. DEPRESSED");
	c.println("3. HOPEFUL");
	c.println("4. NERVOUS");
	
	char sel = ' ';
	do {
	    sel = c.getChar();
	} while(sel < '1' || sel > '4');
	
	wait(1000);
	AudioPlayer.player.start(loadSound("snd/02_TEXTBEEP.wav"));
	typeByChar("Very good.");
	wait(3000);
	c.clear();

	// creates Player instance for the protagonist character
	player = new Player(Character.getNumericValue(sel));
	
	
	// second part of introduction
	parseDialogue("dialogue/02_INTRO.txt");
	
	wait(3000);
	AudioStream laugh = loadSound("snd/03_LAUGH.wav");
	AudioPlayer.player.start(laugh);
	
	if (!name.equalsIgnoreCase("Conner")) {
	    typeByChar("Y O U R   N A M E  I S . . .");
	    for (int i = 0 ; i < 256 ; i++) {
		wait(15);
		Color bg = new Color(i, i, i);
		c.setColor(bg);
		c.fillRect(0, 0, c.getWidth(), c.getHeight());
		c.setTextBackgroundColor(bg);
		c.setCursor(7, 1);
		c.print("Y O U R   N A M E  I S . . .");
	    }
	    wait(2000);
	} else {
	    for (int i = 0; i < 256; i++) {
		Color bg = new Color(i, i, i);
		c.setColor(bg);
		wait(15);
		c.fillRect(0, 0, c.getWidth(), c.getHeight());
	    }
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
	parseDialogue("dialogue/03_DAY1.txt", 2, '\n');
    }
}
