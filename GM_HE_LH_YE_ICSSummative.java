import java.awt.*;
import java.io.*;
import java.util.Random;
import hsa.Console;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class GM_HE_LH_YE_ICSSummative {
    private final static String BEEP = "snd/02_BEEP.wav"; // beep file path
    private final static boolean MORNING = true; // paramater value for
						 // mornings
    private final static boolean AFTERNOON = false; // paramater value for
						    // afternoons
    private final static String STAT = "snd/06_STATCHANGE.wav"; // stat sound
								// file path 
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
    
    // overloaded to allow playing a sound on valid key press
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
	
    // fades to black using progressively darker full-screen rectangles
    private static void fadeBlack() {
	for (int i = 255; i >= 0; i--) {
	    Color bg = new Color(i, i, i);
	    c.setColor(bg);
	    c.fillRect(0, 0, c.getWidth(), c.getHeight());
	    wait(10);
	}
    }
    
    // fades to white using progressively lighter full-screen rectangles
    private static void fadeWhite() {
	for (int i = 0; i <= 255; i++) {
	    Color bg = new Color(i, i, i);
	    c.setColor(bg);
	    c.fillRect(0, 0, c.getWidth(), c.getHeight());
	    wait(15);
	}
    }
    
    // handles gymnasium scenarios
    private static void gymActivities(int activity) {
	if (activity == 1) { // play basketball
	    int rand = rng(20);
	    if (rand <= 2) {
		player.addCharm(30);
		player.addIntelligence(10);
		player.addStrength(25);
		c.print("You swept the other team, scoring you points with ");
		c.println("the ladies in the bleachers.");
		AudioPlayer.player.start(loadSound(STAT));
		c.println("+30 Charm");
		c.println("+10 Intelligence");
		c.println("+25 Strength");
	    } else if (rand > 2 && rand <= 5) {
		player.addCharm(-10);
		player.addKarma(-10);
		player.addIntelligence(-20);
		c.print("You fouled a player. Would've been fine, but he ");
		c.println("was your own teammate.");
		AudioPlayer.player.start(loadSound(STAT));
		c.println("-10 Charm");
		c.println("-10 Karma");
		c.println("-20 Intelligence");
	    } else if (rand > 5 && rand <= 10) {
		player.addCharm(20);
		player.addStrength(20);
		c.print("You dunked the ball. (How? You're like four feet ");
		c.println("tall!)");
		AudioPlayer.player.start(loadSound(STAT));
		c.println("+20 Charm");
		c.println("+20 Strength");
	    } else {
		player.addCharm(10);
		player.addStrength(10);
		c.println("You played some ball with the boys.");
		AudioPlayer.player.start(loadSound(STAT));
		c.println("+10 Charm");
		c.println("+10 Strength");
	    }
	} else { // suicides
	    player.addStrength(1);
	    c.println("You tortured yourself by running 15 suicides.");
	    AudioPlayer.player.start(loadSound(STAT));
	    c.println("+1 Strength");
	}
    }
    
    private static void hallwayDecision(boolean isMorning) {
	boolean inClass = false;
	boolean ranSuicides = false; // flag only allows suicides to
				     // be ran once daily
	for (int hours = 0; !inClass && hours < 3; hours++) {
	    c.clear();
	    c.println("DAY " + day + ": " + WEEK[(day - 1) % 5]);
	    
	    if (isMorning) {
		c.println((9 + hours) + ":00 A.M.");
	    } else {
		c.println((1 + hours) + ":00 P.M.");
	    }
	    
	    // TODO-GUI: this choice to be replaced with
	    // interactive user movement
	    c.println("Where do you want to go?");
	    c.println("1. Classroom");
	    c.println("2. Library");
	    c.println("3. Weight room");
	    c.println("4. Gymnasium");
	    c.println("5. Stairwell");
	    c.println();
	    int hallwayChoice = awaitDigitRange(5, BEEP);
	    
	    switch (hallwayChoice) {
		case 1: // go to class
		    player.addKarma(10);
		    typeByChar("Changed my mind. I should go to class.\n", 
			    '\n');
		    typeByChar("Me: \"Sorry I'm late.\"\n", BEEP, '\n');
		    AudioPlayer.player.start(loadSound(STAT));
		    c.println("+10 Karma");
		    inClass = true;
		    break;
		case 2: // library
		    c.println("It's study time.");
		    c.println("1. Study alone");
		    c.println("2. Study with Miranda");
		    c.println("3. Study with friends");
		    c.println();
		    libraryActivities(awaitDigitRange(3, BEEP));
		    awaitTyping('\n');
		    break;
		case 3: // weight room
		    typeByChar("Is that Tiffany? Guess she's into beefy "
			    + "guys.\n", '\n');
		    c.println("1. Approach Tiffany");
		    c.println("2. Enter the weight room");
		    c.println();
		    
		    if (awaitDigitRange(2, BEEP) == 1) {
			typeByChar("Me: \"Hey Tiffany! Wanna work out "
				+ "together?\"\n", '\n');
			typeByChar("Tiffany: \"Ew, no, what's a loser like "
				+ "you doing here?\"\n", BEEP, '\n');
		    }
		    
		    c.print("You walk past Tiffany and enter the ");
		    c.println("weight room.");
		    c.println();
		    c.println("What do you do now?");
		    c.println("1. Work out");
		    c.println("2. Leave");
		    c.println();
		     
		    if (awaitDigitRange(2, BEEP) == 1) { // enter weight room
			weightRoomActivities();
			awaitTyping('\n');
		    }
		    break;
		case 4: // gymnasium
		    typeByChar("Basketball player: \"Move aside! You wanna "
			    + " get trampled?\"\n", '\n');
		    c.println();
		    c.println("What do you want to do here?");
		    c.println("1. Play basketball");
		    c.println("2. Do suicides");
		    c.println();
		    int gymChoice = awaitDigitRange(2, BEEP);
		    
		    if (ranSuicides && gymChoice == 2) {
			typeByChar("Enough suicides for today.", '\n');
			gymActivities(1);
		    } else {
			gymActivities(gymChoice);
		    }
		    awaitTyping('\n');
		    break;
		case 5: // stairwell
		    if (isMorning) {
			typeByChar("I bust in on a goods trade. Raian spots "
				+ "me.\n", '\n');
			typeByChar("Raian: Get outta here, runt.\n", BEEP,
				'\n');
		    } else {
			typeByChar("There's nothing to do here.", '\n');
		    }
		    break;
		}
	    }
    }
    
    // handles library scenarios
    private static void libraryActivities(int activity) {
	switch (activity) {
	    case 1: // study alone
		typeByChar("I pull out a textbook from my bag. It reads: "
			   + "\"Quantum Physics for the Mentally "
			   + "Uncertain\". Three pages in, my eyelids begin "
			   + "to fall. Then I'm out cold.\n", BEEP, '\n');
		int rand = rng(10);
		if (rand == 1) {
		    player.addCharm(-5);
		    player.addIntelligence(15);
		    c.print("You failed to keep your eyes open while ");
		    c.println("reading the 3000-page textbook. Shame.");
		    AudioPlayer.player.start(loadSound(STAT));
		    c.println("-5 Charm");
		    c.println("+15 Intelligence");
		} else if (rand > 1 && rand <= 3) {
		    player.addCharm(20);
		    player.addStrength(5);
		    player.addKarma(30);
		    c.print("A librarian dropped some books on the floor. ");
		    c.print("You pick them up and receive the title ");
		    c.println("Helper of the Month.");
		    AudioPlayer.player.start(loadSound(STAT));
		    c.println("+20 Charm");
		    c.println("+5 Strength");
		    c.println("+30 Karma");
		} else {
		    player.addCharm(30);
		    player.addIntelligence(30);
		    player.addKarma(20);
		    c.print("A girl asked you about quantum physics. You ");
		    c.print("helped her despite having no idea what you ");
		    c.println("doing and learned something together.");
		    AudioPlayer.player.start(loadSound(STAT));
		    c.println("+30 Charm");
		    c.println("+30 Intelligence");
		    c.println("+20 Karma");
		}
		break;
	    case 2: // study with Miranda
		typeByChar("She looks busy. Probably a bad time. I'll go "
		+ "talk to someone else.\n", BEEP, '\n');
		// fall through and talk with friends
	    case 3: // study with friends
		rand = rng(2);
		if (rand == 1) {
		    player.addCharm(-15);
		    player.addStrength(-10);
		    typeByChar("Me: \"Oi! Whatcha doin guys?\"\n",
			    BEEP, '\n');
		    typeByChar("Shang Gang memeber: Do I know you?\"\n",
			    BEEP, '\n');
		    c.print("Turns out those guys weren't your friends ");
		    c.print("after all. (Do you even have friends?) They ");
		    c.println("gang up and take you outside.");
		    AudioPlayer.player.start(loadSound(STAT));
		    c.println("-15 Charm");
		    c.println("-10 Strength");
		} else {
		    player.addCharm(+25);
		    player.addKarma(-30);
		    c.print("You and your buddies had a great time, but ");
		    c.print("got kicked out for having five people at the ");
		    c.println("table.");
		    AudioPlayer.player.start(loadSound(STAT));
		    c.println("+25 Charm");
		    c.println("-30 Karma");
		}
		break;
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
    
    // reads dialogue from TXT files
    private static void parseDialogue(String filePath) throws IOException {
	parseDialogue(filePath, (char) 0);
    }
    
    // overloaded to allow awaiting keypress before advancing to next line
    private static void parseDialogue(String filePath, char awaitTarget)
	    throws IOException {
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
				BEEP);
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
    
    // custom input handler
    private static String readIn(int length) {
	String input = "";
	char in = ' ';
	
	while (!(in == '\n' && input.length() > 0)) {
	    in = c.getChar();
	    if (((in >= 'A' && in <= 'Z') || (in >= '1' && in <= '9')
		    || in == ' ' || in == '-') && (input.length() < length)) {
		input += in;
		AudioPlayer.player.start(loadSound("snd/03_TYPING.wav"));
		c.print(in);
	    } else if (in >= 'a' && in <= 'z' && input.length() < length) {
		in = Character.toUpperCase(in); // majuscule conversion
		input += in;
		AudioPlayer.player.start(loadSound("snd/03_TYPING.wav"));
		c.print(in);
	    } else if (in == '\b' && input.length() > 0) { // clearing input
		input = input.substring(0, input.length() - 1);
		AudioPlayer.player.start(loadSound("snd/03_TYPING.wav"));
		c.setCursor(c.getRow(), c.getColumn() - 1);
		c.print(" ");
		c.setCursor(c.getRow(), c.getColumn() - 1);
	    }
	}
	c.println();
	return input;
    }
    
    // simple random number generator
    private static int rng(int max) {
	return (int) (Math.random() * max) + 1;
    }
    
    // free interaction with girls
    private static void schoolyardInteraction(boolean isMorning) {
	int schoolyardChoice;
	do {
	    c.clear();
	    c.println("DAY " + day + ": " + WEEK[(day - 1) % 5]);
	    if (isMorning) {
		c.println("8:30 A.M.");
	    } else {
		c.println("12:00 P.M.");
	    }
	    c.println();
	    
	    // TODO-GUI: this choice to be replaced with
	    // interactive user movement
	    c.println("What do you do?");
	    c.println("1. Talk to Ivy");
	    c.println("2. Talk to Kate");
	    c.println("3. Talk to Miranda");
	    c.println("4. Talk to Tiffany");
	    c.println("5. Go inside");
	    schoolyardChoice = awaitDigitRange(5, BEEP);
	    
	    // girls' dialogues
	    // TODO: all quest and taken responses
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
				+ "there a 'u' in \"relationship\"!\n", '\n');
			typeByChar("Looks like I need to get more charm, "
				+ "brains, and brawn before I can approach "
				+ "her.", BEEP, '\n');
		    }
		    break;
		case 2: // Kate
		    if (player.getCharm() >= 80
			&& player.getIntelligence() >= 30
			&& player.getKarma() >= 50
			&& player.getStrength() >= 50) {
			// positive response
		    } else {
			typeByChar("She's MY type: cute. I'm gonna try "
				+ "to talk to her.\n", '\n');
			typeByChar("Me: \"Hey, cutie, the name's Conner. "
			       + "What's yours?.\"\n", BEEP, '\n');
			c.println("You got cock-blocked by a tall jock.");
			typeByChar("Raian: \"HEY LITTLE BRAT! You're way "
				+ "too wimpy and weak to talk to Kate! Look "
				+ "at yourself, dwarf. Think ANY girl would "
				+ "want to be with you? Make sure you can "
				+ "look at a mirror without breaking it "
				+ "before you punish her eyes like that!"
				+ "\"\n", BEEP, '\n');
			typeByChar("No matter if it's by fraud or by "
				+ "force, I've gotta get that jock away from "
				+ "from her.", BEEP, '\n');
			}
		    break;
		case 3: // Miranda
		    if (player.getCharm() >= 80
			&& player.getIntelligence() >= 100
			&& player.getStrength() >= 30) {
			// positive response
		    } else {
			typeByChar("Me: \"Hey hottie, what are you "
				+ "looking at?\"\n", '\n');
			typeByChar("Miranda: \"Go away, I'm studying for "
				+ "AIME. Talk to me when you score perfect "
				+ "on AMC 12.\"\n", BEEP, '\n');
			typeByChar("Me: \"Okay then.\"", BEEP, '\n');
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
				+ "do you think you are anyway?\"\n", '\n');
			typeByChar("How am I gonna catch her attention? "
				+ "Do I need more looks? ", BEEP, '\n');
			}
		    break;
	    }
	} while (schoolyardChoice != 5);
    }
	
    // delivers text output one character at a time at default speed
    private static void typeByChar(String msg) {
	typeByChar(msg, (char) 0);
    }
    
    // overloaded to allow playing sound on text advance
    private static void typeByChar(String msg, String soundFilePath) {
	AudioPlayer.player.start(loadSound(soundFilePath));
	typeByChar(msg, (char) 0);
    }
    
    // overloaded to allow awaiting keypress
    private static void typeByChar(String msg, char awaitTarget) {
	boolean debug = false; // setting true removes typing delay
	if (debug) {
	    c.print(msg);
	} else {
	    for (int i = 0; i < msg.length(); i++) {
		char currentChar = msg.charAt(i);
		c.print(currentChar);
		wait((currentChar == '\n') ? 500 : 10);
	    }
	}
	awaitTyping(awaitTarget);
    }
    
    // overloaded to allow playing sound on text advance
    // and awaiting keypress
    private static void typeByChar(String msg, String soundFilePath,
	    char awaitTarget) {
	AudioPlayer.player.start(loadSound(soundFilePath));
	typeByChar(msg, awaitTarget);
    }
    
    // manual delay
    private static void wait(int delay) {
	try {
	    Thread.sleep(delay);
	} catch (InterruptedException ie) {
	    ie.printStackTrace();
	}
    }
    
    // handles weight room scenarios
    private static void weightRoomActivities() {
	int rand = rng(10);
	if (rand == 1) {
	    player.addCharm(30);
	    player.addStrength(40);
	    c.print("You went on a binge, crushing all the equipment in ");
	    c.println("the weight room. (What a god!)");
	    AudioPlayer.player.start(loadSound(STAT));
	    c.println("+30 Charm");
	    c.println("+40 Strength");
	} else if (rand >= 2 && rand <= 3){
	    player.addCharm(-10);
	    player.addKarma(-5);
	    player.addStrength(10);
	    c.print("You tried to bench more than you could lift. Some ");
	    c.print("nearby girls noticed and laughed at your failed ");
	    c.println("attempt.");
	    AudioPlayer.player.start(loadSound(STAT));
	    c.println("-10 Charm");
	    c.println("-5 Karma");
	    c.println("+10 Strength");
	} else if (rand >= 4 && rand <= 6) {
	    player.addCharm(20);
	    player.addKarma(20);
	    player.addStrength(30);
	    c.print("You completed 40 push-ups in a row. Working out has ");
	    c.println("steadily improved your capabilities.");
	    AudioPlayer.player.start(loadSound(STAT));
	    c.println("+20 Charm");
	    c.println("+20 Karma");
	    c.println("+30 Strength");
	} else {
	    player.addCharm(15);
	    player.addKarma(10);
	    player.addStrength(15);
	    c.print("The gym instructor was tired and needed help. You ");
	    c.println("put away your dumbbells to help him out.");
	    AudioPlayer.player.start(loadSound(STAT));
	    c.println("+15 Charm"); 
	    c.println("+10 Karma");
	    c.println("+15 Strength");
	}
    }
    
    public static void main(String[] args) throws IOException {
	c = new Console("LOVESTORY");
	
	// customize console
	c.setColor(Color.black);
	c.fillRect(0, 0, c.getWidth(), c.getHeight());
	c.setTextColor(Color.white);
	c.setTextBackgroundColor(Color.black);
	
	// opening dialogue
	AudioStream introMusic = loadSound("snd/01_INTROBG.wav");
	AudioPlayer.player.start(introMusic);
	
	parseDialogue("dialogue/01_INTRO.txt");

	c.println();
	c.println("(Type on your keyboard)");
	c.print("What is your name? ");
	String name = readIn(6);
	
	// these inputs are ignored and not saved to variables
	typeByChar("What do you look like?\n", BEEP);
	c.print("Your hair colour? ");
	readIn(20);
	c.print("Eye colour? ");
	readIn(20);
	c.print("Skin tone? ");
	readIn(20);
	c.print("Outfit? ");
	readIn(15);

	// mental state selection, which affects story
	c.println();
	c.println("How is your little buddy feeling today?");
	c.println("1. LOVED");
	c.println("2. DEPRESSED");
	c.println("3. HOPEFUL");
	c.println("4. NERVOUS");
	int mentalState = awaitDigitRange(4);
	
	typeByChar("Very good.", BEEP);
	wait(2000);
	c.clear();

	// creates Player instance for the protagonist character
	player = new Player(mentalState);
	
	// second part of introduction
	parseDialogue("dialogue/02_INTRO.txt");
	AudioPlayer.player.stop(introMusic);
	
	wait(2000);
	AudioStream laugh = loadSound("snd/04_LAUGH.wav");
	AudioPlayer.player.start(laugh);
	typeByChar("NO ONE GETS TO CHOOSE WHO THEY ARE IN THIS WORLD!\n",
		BEEP);
	
	if (!name.replaceAll(" ", "").equals("CONNER")) {
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
	AudioPlayer.player.stop(laugh);
	c.setColor(Color.black);
	c.fillRect(0, 0, c.getWidth(), c.getHeight());
	c.setTextBackgroundColor(Color.black);
	c.println("(Press ENTER to advance dialogue.)");
	
	// story begins
	AudioPlayer.player.start(loadSound("snd/05_ALARM.wav"));
	parseDialogue("dialogue/03_MOM.txt", '\n');
	
	// choice to take sandwich
	c.println();
	c.println("Take the sandwich?");
	c.println("1. Bring it to school");
	c.println("2. Leave it behind");
	c.println();
	int sandwichChoice = awaitDigitRange(2, BEEP);
	
	if (sandwichChoice == 1) {
	    player.addKarma(20);
	    player.hasSandwich = true;
	    c.println("You got a CRUSTY SANDWICH.");
	    AudioPlayer.player.start(loadSound(STAT));
	    c.println("+20 Karma");
	} else {
	    player.addKarma(-20);
	    player.hasSandwich = false;
	    c.println("You left the sandwich alone.");
	    AudioPlayer.player.start(loadSound(STAT));
	    c.println("-20 Karma");
	}
	awaitTyping('\n');
	
	c.println();
	typeByChar("Me: \"Bye Mom, bye Dad, I'm leaving!\"", BEEP, '\n');
	fadeBlack();
	
	// school day
	// game over on day 200 as everyone graduates
	for (; day <= 200; day++) {
	    // schoolyard
	    schoolyardInteraction(MORNING);
	    
	    // school morning
	    c.clear();
	    c.println("DAY " + day + ": " + WEEK[(day - 1) % 5]);
	    c.println("9:00 A.M.");
	    
	    boolean wentToClass = false;
	    
	    // hall monitor encounter on the first day
	    // random encounters afterwards
	    if (day == 1 || rng(10) == 1) {
		c.println();
		typeByChar("It's time for civics, but civics is a boring "
			+ "class. Should I skip it? I see a hall monitor "
			+ "coming. Gotta think fast!\n", '\n');
		c.println();
		c.println("What should you do?");
		c.println("1. Go to class");
		c.println("2. Avoid class");
		c.println();
		
		if (awaitDigitRange(2, BEEP) == 1) { // go to class
		    player.addKarma(10);
		    player.addIntelligence(10);
		    AudioPlayer.player.start(loadSound(STAT));
		    c.println("+10 Karma");
		    c.println("+10 Intelligence");
		    wentToClass = true;
		} else { // avoid class
		    player.addKarma(-30);
		    AudioPlayer.player.start(loadSound(STAT));
		    c.println("-30 Karma");
		    c.println();
		}
		awaitTyping('\n');
	    }
	    
	    
	    if (!wentToClass) {
		// morning activities
		hallwayDecision(MORNING);
	    }
	    
	    // lunch time
	    schoolyardInteraction(AFTERNOON);
	    
	    // afternoon activities
	    hallwayDecision(AFTERNOON);
	    
	    c.clear();
	}
    }
}
