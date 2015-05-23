package quizzerside.gui;

import javax.swing.*;

public class Console extends JTextArea {

    public void printToConsole(String message) {
        this.append("\n" + message);
    }

}
