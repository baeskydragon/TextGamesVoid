package pathsType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StoryMaker {
	 private static Map<String, String> gameText;
	 private String currentSection;
	 private int sectionNumber;

	 // Constructor
	 public StoryMaker() {
	        this.currentSection = "";
	        this.sectionNumber = 0;
	    }
	 public StoryMaker(String filename) {
	        gameText = new HashMap<>();
	        loadGameText(filename);
	    }


	    private void loadGameText(String filename) {
	        Choice.getSection(filename, 0, section -> {
	            StringBuilder content = new StringBuilder();
	            String key = null;
	            for (String line : section) {
	                if (line.matches("[A-Z_]+")) {
	                    if (key != null) {
	                        gameText.put(key, content.toString().trim());
	                    }
	                    key = line;
	                    content = new StringBuilder();
	                } else {
	                    content.append(line).append("\n");
	                }
	            }
	            if (key != null) {
	                gameText.put(key, content.toString().trim());
	            }
	        });
	    }

	    public static String getText(String key) {
	        return gameText.get(key);
	    }

	    public static Choice[] getChoices(String key, Runnable[] actions) {
	        String[] choiceDescriptions = gameText.get(key).split("\n");
	        Choice[] choices = new Choice[choiceDescriptions.length];
	        for (int i = 0; i < choiceDescriptions.length; i++) {
	            choices[i] = new Choice(choiceDescriptions[i], actions[i]);
	        }
	        return choices;
	    }
	    
	public static void main(String[] args) {
        String filePath = "example.txt"; // Specify the file path
        String content = "# Section 0\n"
        		+ "You are at the entrance of a dark cave. \r\n"
        		+ "1. Enter the cave\n"
        		+ "2. Walk away\n"
        		+ "3. Look around\n"
        		+ "\n"
        		+ "# Section 1\n"
        		+ "You open your eyes. You are in complete darkness. You can't remember anything. "
        		+ "1. Walk forward. "
        		+ "You feel as if someone or something is watching you. As you look ahead, you see a flickering light."
        		+ "1. Walk forward."
        		+ "2. Turn around.\n"
        		+ "You see two glowing white eyes stare back at you. A clicking noise echoes.\n"
        		+ "1. Stand still.\n"
        		+ "Run into the light. You sprint to the light without looking back. As you run, you hear footsteps. chasing after you.\n "
        		+ "1. look back.\n "
        		+ "2. keep running.\n"
        		+ "\n"
        		+ "You feel something wrap around your legs and pull you away. You take 25 damage. Game over\n"
        		+ "\n"
        		+ "you made it to the light. You feel safe in it's present. You look closer. It's a torch."
        		+ "1. Pick it up."
        		+ "Torch was added to your inventory. You are now able to see the dark in the darkness."
        		+ "\n"
        		+ "# Section 2\n"
        		+ "You walk away and find a peaceful meadow. Do you:\n"
        		+ "1. Sit and rest\n"
        		+ "2. Continue walking\n"
        		+ "3. Explore the meadow\n"
        		+ "\n"
        		+ "# Section 3\n"
        		+ "You look around and meet a friendly traveler. Do you:\n"
        		+ "1. Talk to the traveler\n"
        		+ "2. Ignore the traveler\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("File written successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
     }

    // Example method to load a section
    public void loadSection(int sectionNumber) {
        this.sectionNumber = sectionNumber;
        // Logic to load the section (e.g., from a file or database)
        this.currentSection = "Loaded section " + sectionNumber;
    }

    // Example method to get the current section
    public String getCurrentSection() {
        return currentSection;
    }

    // Example method to advance to the next section
    public void nextSection() {
        loadSection(sectionNumber + 1);
    }
}

