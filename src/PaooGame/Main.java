package PaooGame;

import javax.swing.*;

import static java.lang.System.getProperty;

public class Main {
    public static void main(String[] args) {
        // Game Start
        Game gamePanel = new Game();
        gamePanel.setupGame();
        gamePanel.startGame();
        // End
    }
}