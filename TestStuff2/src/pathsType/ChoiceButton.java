package pathsType;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class ChoiceButton extends JButton {
    private int choiceNumber;

    public ChoiceButton(int choiceNumber, String text) {
        super(text);
        this.choiceNumber = choiceNumber;
        this.setBackground(Color.BLACK);
        this.setForeground(Color.MAGENTA);
        this.setFont(new Font("Papyrus", Font.PLAIN, 20));
    }

    public int getChoiceNumber() {
        return choiceNumber;
    }
}