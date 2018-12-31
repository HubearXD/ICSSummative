import java.awt.*;
import java.io.*;
import hsa.Console;


public class GM_HE_LH_YE_ICSSummative {
    private static Console c;
    
    private static Player player;
    
    // waits for a character key press and returns the character
    private static char awaitTyping(char target) {
	char in = 0;
	do {
	    in = c.getChar();
	} while (in != target);
	return in;
    }
    
    // overloaded to accept multiple characters
    private static char awaitTyping(char[] targets) {
	char in = 0;
	boolean isTarget = false;
	
	do {
	    in = c.getChar();
	    for (int i = 0; i < targets.length; i++) {
		if (in == targets[i]) {
		    isTarget = true;
		    break;
		}
	    }
	} while (!isTarget);
	return in;
    }
    
    private static void typeByChar(String msg) {
	boolean debug = false; // setting true removes typing delay 
	if (debug) {
	    c.print(msg);
	} else {
	    for (int i = 0; i < msg.length(); i++) {
		char currentChar = msg.charAt(i);
		c.print(currentChar);
		
		wait(currentChar == '\n' ? 500 : 30);
	    }
	}
    }
    
    private static void parseDialogue(String filepath) throws IOException {
	BufferedReader br = new BufferedReader(new FileReader(filepath));
	try {
	    String line = br.readLine();
	    
	    while (line != null) {
		// output style specified using line prefixes
		if (line.equals("^")) {
		    wait(500);
		    c.println();
		} else if (line.startsWith(". ")) {
		    c.println(line);
		} else if (line.startsWith(">")) {
		    typeByChar(line.substring(2, line.length()) + "\n");
		} else {
		    c.println(line);
		}
		line = br.readLine();
	    }
	} finally {
	    br.close();
	}
    }
    
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
	parseDialogue("dialogue/01_INTRO.txt");
	
	// these characteristics are ultimately ignored
	c.print("What is your name? ");
	String name = c.readString();
	typeByChar("What do you look like?\n");
	c.print("Your hair colour? ");
	c.readString();
	c.print("Eye colour? ");
	c.readString();
	c.print("Skin tone? ");
	c.readString();
	c.print("Outfit? ");
	c.readString();
	
	// mental state, which affects starting stats
	c.println();
	typeByChar("How is your little buddy feeling today?\n");
	c.println("1. LOVED");
	c.println("2. DEPRESSED");
	c.println("3. HOPEFUL");
	c.println("4. NERVOUS");
	
	char sel = ' ';
	do {
	    sel = c.getChar();
	} while (sel < '1' || sel > '4');
	
	typeByChar("Very good.");
	wait(1500);
	c.clear();
	
	// creates Player instance for the protagonist character
	player = new Player(Character.getNumericValue(sel));
	
	// second part of introduction
	parseDialogue("02_INTRO.txt");
	
	c.clear();
	c.println("(Press ENTER to advance dialogue.)");
	awaitTyping('\n');
	c.clear();
	
	// story begins
	parseDialogue("03_DAY1.txt");
    }
}
