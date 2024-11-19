package pathsType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.text.Utilities;

public class Choice {
	 private String description;
	 private Runnable action;

	    public Choice(String description, Runnable action) {
	        this.description = description;
	        this.action = action;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void execute() {
	        action.run();
	    }


	// Method to load all choices from a file
    public static void LoadChoices(String filePath, ChoicesCallback callback) {
        new Thread(() -> {
            List<String> choices = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    choices.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> callback.onChoicesLoaded(choices));
        }).start();
    }

    // Method to get a specific line from a file
    public static void getLine(String filePath, int lineNumber, LineCallback callback) {
        new Thread(() -> {
            String line = null;
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                int currentLine = 0;
                while ((line = br.readLine()) != null) {
                    if (currentLine == lineNumber) {
                        break;
                    }
                    currentLine++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String finalLine = line;
            SwingUtilities.invokeLater(() -> callback.onLineLoaded(finalLine));
        }).start();
    }

    // Method to open and read a file, printing its contents
    public static void openFile(String filePath) {
        new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public static void getSection(String filePath, int sectionNumber, SectionCallback callback) {
        new Thread(() -> {
            List<String> section = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean inSection = false;
                while ((line = br.readLine()) != null) {
                    if (line.trim().equals("# Section " + sectionNumber)) {
                        inSection = true;
                    } else if (line.trim().startsWith("# Section") && inSection) {
                        break;
                    } else if (inSection) {
                        section.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> callback.onSectionLoaded(section));
        }).start();
    }

    // Callback interfaces
    public interface ChoicesCallback {
        void onChoicesLoaded(List<String> choices);
    }
    public interface SectionCallback {
        void onSectionLoaded(List<String> section);
    }

    public interface LineCallback {
        void onLineLoaded(String line);
    }
}
