package pathsType;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class thePath {
    JFrame window;
    Container con;
    JPanel title;
    JPanel startButton;
    JPanel mainTextPanel;
    JLabel titleLabel;
    JPanel choiceButtonPanel;
    JButton start;
    JTextArea mainTextArea;
    Font titleFont = new Font("Papyrus", Font.BOLD, 50);
    Font startButtonFont = new Font("Papyrus", Font.ITALIC, 15);
    Font normalFont = new Font("Papyrus", Font.PLAIN, 20);
    TitleScreenHandler tsHandler = new TitleScreenHandler();
    StoryMaker storyMaker;
    Player player;
    Enemies currentEnemy;

    public thePath() {
        SwingUtilities.invokeLater(() -> {
            window = new JFrame();
            window.setSize(800, 600);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.getContentPane().setBackground(Color.BLACK);
            window.setLayout(null);
            window.setVisible(true);
            con = window.getContentPane();

            title = new JPanel();
            title.setBounds(100, 100, 600, 150);
            title.setBackground(Color.BLACK);

            titleLabel = new JLabel("THE DARK VOID");
            titleLabel.setForeground(Color.MAGENTA);
            titleLabel.setFont(titleFont);

            startButton = new JPanel();
            startButton.setBounds(260, 260, 210, 100);
            startButton.setBackground(Color.BLACK);

            start = new JButton("ENTER IF YOU DARE");
            start.setBackground(Color.BLACK);
            start.setForeground(Color.MAGENTA);
            start.setFont(startButtonFont);
            start.addActionListener(tsHandler);

            title.add(titleLabel);
            startButton.add(start);
            con.add(title);
            con.add(startButton);
            window.add(title);

            // Initialize the player
            player = new Player("Hero", 100, 20, "Sword", "Healing");

            // Initialize the story maker
            storyMaker = new StoryMaker("game_text.txt");
        });
    }

    public void createGameScreen() {
        SwingUtilities.invokeLater(() -> {
            title.setVisible(false);
            startButton.setVisible(false);

            mainTextPanel = new JPanel();
            mainTextPanel.setBounds(100, 100, 600, 250);
            mainTextPanel.setBackground(Color.BLACK);
            mainTextPanel.setLayout(new BorderLayout());
            con.add(mainTextPanel);

            mainTextArea = new JTextArea();
            mainTextArea.setBounds(100, 100, 600, 250);
            mainTextArea.setBackground(Color.BLACK);
            mainTextArea.setForeground(Color.WHITE);
            mainTextArea.setFont(normalFont);
            mainTextArea.setLineWrap(true);
            mainTextArea.setWrapStyleWord(true);
            mainTextArea.setEditable(false);
            mainTextPanel.add(mainTextArea, BorderLayout.CENTER);

            choiceButtonPanel = new JPanel();
            choiceButtonPanel.setBounds(250, 350, 300, 150);
            choiceButtonPanel.setBackground(Color.BLACK);
            choiceButtonPanel.setLayout(new GridLayout(0, 1)); // Dynamic rows
            con.add(choiceButtonPanel);

            introduction(); // Load the initial section
        });
    }

    private void introduction() {
        mainTextArea.setText(StoryMaker.getText("INTRODUCTION"));
        setChoices(StoryMaker.getChoices("CHOICES", new Runnable[]{
                this::warriorPath,
                this::magePath,
                this::roguePath
        }));
    }

    private void setChoices(Choice[] choices) {
        choiceButtonPanel.removeAll();
        choiceButtonPanel.setLayout(new GridLayout(choices.length, 1));
        for (Choice choice : choices) {
            JButton button = new JButton(choice.getDescription());
            button.addActionListener(e -> choice.execute());
            choiceButtonPanel.add(button);
        }
        choiceButtonPanel.revalidate();
        choiceButtonPanel.repaint();
    }

    private void warriorPath() {
        mainTextArea.setText(StoryMaker.getText("WARRIOR_PATH"));
        setChoices(StoryMaker.getChoices("WARRIOR_CHOICES", new Runnable[]{
                () -> mainTextArea.setText("You decide to investigate the dragon sighting..."),
                () -> mainTextArea.setText("You decide to protect the village from bandits..."),
                () -> mainTextArea.setText("You decide to seek out the legendary blacksmith...")
        }));
    }

    private void magePath() {
        mainTextArea.setText(StoryMaker.getText("MAGE_PATH"));
        setChoices(StoryMaker.getChoices("MAGE_CHOICES", new Runnable[]{
                () -> mainTextArea.setText("You decide to study the ancient runes in the hidden cave..."),
                () -> mainTextArea.setText("You decide to help the village plagued by dark magic..."),
                () -> mainTextArea.setText("You decide to seek the wisdom of the elder mage...")
        }));
    }

    private void roguePath() {
        mainTextArea.setText(StoryMaker.getText("ROGUE_PATH"));
        setChoices(StoryMaker.getChoices("ROGUE_CHOICES", new Runnable[]{
                () -> mainTextArea.setText("You decide to steal the artifact from the merchant..."),
                () -> mainTextArea.setText("You decide to uncover the spy within the city guard..."),
                () -> mainTextArea.setText("You decide to infiltrate the noble's mansion...")
        }));
    }

    public void encounterEnemy(String enemyName, int health, int attackPower, String weapon) {
        currentEnemy = new Enemies(enemyName, health, attackPower, weapon);
        mainTextArea.append("\nA wild " + currentEnemy.getName() + " appears!\n");
        displayFightChoices();
    }

    public void displayFightChoices() {
        choiceButtonPanel.removeAll(); // Clear previous choices

        JButton attackButton = new JButton("Attack");
        attackButton.addActionListener(e -> playerAttack());
        choiceButtonPanel.add(attackButton);

        JButton useDomainButton = new JButton("Use Domain Ability");
        useDomainButton.addActionListener(e -> player.useDomainAbility());
        choiceButtonPanel.add(useDomainButton);

        choiceButtonPanel.revalidate();
        choiceButtonPanel.repaint();
    }

    public void playerAttack() {
        player.attack(currentEnemy);
        if (currentEnemy.getHealth() <= 0) {
            mainTextArea.append("\n" + currentEnemy.getName() + " has been defeated!\n");
            introduction(); // Go back to the main section or next part of the story
        } else {
            enemyAttack();
        }
    }

    public void enemyAttack() {
        currentEnemy.attack(player);
        if (player.getHealth() <= 0) {
            mainTextArea.append("\nYou have been defeated!\n");
            // Handle player defeat (e.g., restart game, load last checkpoint)
        } else {
            displayFightChoices();
        }
    }

    public class TitleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            createGameScreen();
        }
    }

    public class ChoiceHandler implements ActionListener {
        private int choice;

        public ChoiceHandler(int choice) {
            this.choice = choice;
        }

        public void actionPerformed(ActionEvent e) {
            if (choice == 1) {
                // Example: Encounter an enemy
                encounterEnemy("Goblin", 50, 15, "Club");
            }}}}