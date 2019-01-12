import java.awt.Color;
import java.io.*;
import hsa.Console;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class GameConsole extends Console {
    public GameConsole() {
	this("");
    }
    
    public GameConsole(String title) {
	super(title);
    }
    
    // awaits a numeric key press within a range and returns the number
    public int awaitDigitRange(int max) {
	int in = 0;
	do {
	    in = Character.getNumericValue(this.getChar());
	} while(in < 1 || in > max);
	return in;
    }
    
    // overloaded to allow playing a sound on valid key press
    public int awaitDigitRange(int max, String soundFilePath) {
	int in = this.awaitDigitRange(max);
	AudioPlayer.player.start(loadSound(soundFilePath));
	return in;
    }
    
    // awaits a character key press and returns the character
    public char awaitTyping(char target) {
	if (target == 0) {
	    return 0;
	} else {
	    char in = 0;
	    do {
		in = this.getChar();
	    } while(in != target);
	    return in;
	}
    }
    
    // fades to black using progressively darker full-screen rectangles
    public void fadeBlack() {
	for (int i = 255; i >= 0; i--) {
	    Color bg = new Color(i, i, i);
	    this.setColor(bg);
	    fillRect(0, 0, this.getWidth(), this.getHeight());
	    
	    try {
		Thread.sleep(15);
	    } catch (InterruptedException ie) {
		ie.printStackTrace();
	    }
	}
    }
    
    // fades to white using progressively lighter full-screen rectangles
    public void fadeWhite() {
	for (int i = 0; i <= 255; i++) {
	    Color bg = new Color(i, i, i);
	    this.setColor(bg);
	    fillRect(0, 0, this.getWidth(), this.getHeight());
	    
	    try {
		Thread.sleep(15);
	    } catch (InterruptedException ie) {
		ie.printStackTrace();
	    }
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
    
    // custom input handler
    public String readIn(int length) {
	String input = "";
	char in = ' ';

	while (!(in == '\n' && input.length() > 0)) {
	    in = this.getChar();
	    if (((in >= 'A' && in <= 'Z') || (in >= '1' && in <= '9')
		    || in == ' ' || in == '-') && (input.length() < length)) {
		input += in;
		AudioPlayer.player.start(loadSound("snd/03_TYPING.wav"));
		this.print(in);
	    } else if (in >= 'a' && in <= 'z' && input.length() < length) {
		in = Character.toUpperCase(in); // majuscule conversion
		input += in;
		AudioPlayer.player.start(loadSound("snd/03_TYPING.wav"));
		this.print(in);
	    } else if (in == '\b' && input.length() > 0) { // clearing input
		input = input.substring(0, input.length() - 1);
		AudioPlayer.player.start(loadSound("snd/03_TYPING.wav"));
		this.setCursor(this.getRow(), this.getColumn() - 1);
		this.print(" ");
		this.setCursor(this.getRow(), this.getColumn() - 1);
	    }
	}
	this.println();
	return input;
    }
    
    // delivers text output one character at a time at default speed
    public void typeChar(String msg) {
	this.typeChar(msg, (char) 0);
    }
    
    // overloaded to allow playing sound on text advance
    public void typeChar(String msg, String soundFilePath) {
	AudioPlayer.player.start(loadSound(soundFilePath));
	this.typeChar(msg, (char) 0);
    }
    
    // overloaded to allow awaiting keypress
    public void typeChar(String msg, char awaitTarget) {
	boolean debug = false; // setting true removes typing delay
	if (debug) {
	    this.print(msg);
	} else {
	    for (int i = 0; i < msg.length(); i++) {
		char currentChar = msg.charAt(i);
		this.print(currentChar);
		
		try {
		    Thread.sleep((currentChar == '\n') ? 500 : 10);
		} catch (InterruptedException ie) {
		    ie.printStackTrace();
		}
	    }
	}
	awaitTyping(awaitTarget);
    }
    
    // overloaded to allow playing sound on text advance
    // and awaiting keypress
    public void typeChar(String msg, String soundFilePath, char awaitTarget) {
	AudioPlayer.player.start(loadSound(soundFilePath));
	this.typeChar(msg, awaitTarget);
    }
}
