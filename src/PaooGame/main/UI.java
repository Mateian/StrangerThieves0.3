package PaooGame.main;

import PaooGame.Game;
import PaooGame.enemy.MST_Enemy;
import PaooGame.entity.Entity;
import PaooGame.objects.OBJ_Heart;
import PaooGame.objects.OBJ_Skargun;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

public class UI {

    // Base Settings
    Graphics2D graph2;
    Game gp;

    // Fonts
    Font arial_40, arial_80B, console_40B;

    // Images
    BufferedImage full_heart, half_heart, black_heart;
    BufferedImage enemyImage;
    BufferedImage weapon;
    BufferedImage weaponFrame;


    // Booleans
    public boolean messageOn = false;
    public boolean gameFinished = false;

    // String - Messages
    public String message = "";
    public String dialogText = "";

    // Counters
    int messageCounter = 0;

    // Index
    public int commandNumber = 0;

    // Play time
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#000.00");

    // Tools tool = new Tools()

    public UI(Game gp) {
        this.gp = gp;
        arial_40 = new Font("Comic Sans MS", Font.PLAIN, 40);
        console_40B = new Font("Console", Font.BOLD, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        MST_Enemy enemy = new MST_Enemy(gp);
        enemyImage = enemy.down1;

        // HUD
        Entity heart = new OBJ_Heart(gp);
        full_heart = heart.image;
        half_heart = heart.image2;
        black_heart = heart.image3;

        // Current Weapon
        try {
            weaponFrame = ImageIO.read(getClass().getResourceAsStream("/gui/current-weapon-frame.png"));
        } catch(IOException e) {
            System.out.println("Error reading current-weapon-frame file.");
        }
        Entity currentWeapon = new OBJ_Skargun(gp);
        weapon = currentWeapon.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D graph2) {
        this.graph2 = graph2;
        if(gameFinished) {
            graph2.setFont(arial_40);
            graph2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure!";
            textLength = (int)graph2.getFontMetrics().getStringBounds(text, graph2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            graph2.drawString(text, x, y);

            text = "Your time is: " + dFormat.format(playTime);
            textLength = (int)graph2.getFontMetrics().getStringBounds(text, graph2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 4);
            graph2.drawString(text, x, y);

            graph2.setFont(arial_80B);
            graph2.setColor(Color.yellow);
            text = "Congratulations!";
            textLength = (int)graph2.getFontMetrics().getStringBounds(text, graph2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            graph2.drawString(text, x, y);

            gp.gameThread = null;
        } else {
            // Menu
            if(gp.gameState == gp.menuState) {
                drawMenu();
            }
            if(gp.gameState == gp.playState) {
                graph2.setFont(arial_40);
                graph2.setColor(Color.white);
                graph2.drawImage(enemyImage, 25, 25, gp.tileSize, gp.tileSize, null);
                graph2.drawString(gp.lvl1ObjectiveCounter + " | " + gp.lvl1Objective, 74, 65);

                // Time
                if(gp.gameState == gp.playState) {
                    playTime +=(double)1/60;
                }
                graph2.setFont(new Font("Consolas", Font.PLAIN, 20));
                graph2.drawString("Time: " + dFormat.format(playTime), gp.tileSize*12 + gp.tileSize - 10, 65);

                // Message
                if(messageOn) {

                    graph2.setFont(graph2.getFont().deriveFont(30F));
                    graph2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                    messageCounter++;

                    if(messageCounter > 120) {
                        messageCounter = 0;
                        messageOn = false;
                    }
                }

                // FPS draw
                drawFPS(gp.forShowFPS);

                // Player's Life
                drawLife();

                // Current Weapon
                currentWeaponFrame();
                drawCurrentWeapon();
            }
            if(gp.gameState == gp.pauseState) {
                drawPauseScreen();
            }
            // Dialog State
            if(gp.gameState == gp.dialogState) {
                drawDialogScreen();
            }
            if(gp.gameState == gp.deadState) {
                drawDeathScreen();
            }
        }
    }
    public void drawCurrentWeapon() {
        if(gp.player.hasWeapon) {
            graph2.drawImage(weapon, gp.screenWidth - gp.tileSize * 3, gp.screenHeight - gp.tileSize * 3, 75, 75,null);
        }
    }
    public void currentWeaponFrame() {
        graph2.drawImage(weaponFrame, gp.screenWidth - gp.tileSize * 3, gp.screenHeight - gp.tileSize * 3, 75, 75,null);

    }
    public void drawDeathScreen() {
        String text = "You are dead";
        int x = xCenterText(text);
        int y = gp.screenHeight / 2;
//        int width = ;
//        int height = ;
        graph2.setColor(Color.white);
        graph2.setFont(console_40B);
        graph2.drawString(text, x, y);
        text = "Press Enter to restart";
        y += 10;
        x += xCenterText(text);
        graph2.drawString(text, x, y);

    }
    public void drawWin() {
        graph2.setFont(arial_40);
        graph2.setColor(Color.white);
        graph2.drawString("Level 1 Completed", xCenterText("Level 1 Completed"), gp.screenHeight / 2);
    }
    public void drawLife() {

        int x = gp.tileSize / 2;
        int y = gp.screenHeight - gp.tileSize * 2;
        int i = 0;

        // Black Heart
        graph2.setColor(Color.black);
        graph2.fillRect(x, y, gp.tileSize * gp.player.maxLife + 10, gp.tileSize);
//
//        while(i < gp.player.maxLife / 2) {
//              graph2.drawImage(black_heart, x, y, null);
//            ++i;
//            x += gp.tileSize + 10;
//        }

        // Reset
        x = gp.tileSize / 2;
        y = gp.screenHeight - gp.tileSize * 2;
        i = 0;

        // Current Life
        graph2.setColor(Color.red);
        graph2.fillRect(x + 5, y + 5, gp.player.life * gp.tileSize, gp.tileSize - 10);

//        while(i < gp.player.life) {
////            graph2.drawImage(half_heart, x, y, null);
////            ++i;
////            if(i < gp.player.life) {
////                graph2.drawImage(full_heart, x, y, null);
////            }
//            ++i;
//            x += gp.tileSize + 10;
//        }

        // Reset
        x = gp.tileSize / 2  + gp.tileSize * gp.player.maxLife / 2;
        y = gp.screenHeight - gp.tileSize - 16;

        if(gp.player.life < 0) {
            gp.player.life = 0;
        }
        String hp = (int)((double)gp.player.life / (double)gp.player.maxLife * 100) + "%";
        // HP Text
        graph2.setColor(Color.white);
        graph2.setFont(console_40B);
        graph2.setFont(graph2.getFont().deriveFont(Font.PLAIN, 24));
        graph2.drawString(hp, x, y);
    }

    public void drawMenu() {
        // Background
        try {
            BufferedImage backGroundImage = ImageIO.read(getClass().getResourceAsStream("/icons/icon.png"));
//            int width = backGroundImage.getWidth();
//            int height = backGroundImage.getHeight();
            graph2.drawImage(backGroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Game Name
        graph2.setFont(graph2.getFont().deriveFont(Font.BOLD, 80F));
        String name = "Stranger Thieves";
        int x = xCenterText(name);
        int y = 4 * gp.tileSize;
        graph2.setColor(Color.white);
        graph2.drawString(name, x, y);

        // Menu
        String option;
        graph2.setFont(graph2.getFont().deriveFont(Font.BOLD, gp.tileSize));

        option = "New Game";
        x = xCenterText(option);
        y += 3 * gp.tileSize;
        if(commandNumber == 0) {
            graph2.setColor(new Color(255, 255 ,255));
            graph2.fillRect(x, y + 5, (int)graph2.getFontMetrics().getStringBounds(option, graph2).getWidth(), 2);
        }
        graph2.setColor(Color.white);
        graph2.drawString(option, x, y);


        option = "Load Game";
        x = xCenterText(option);
        y += 10 + gp.tileSize;
        if(commandNumber == 1) {
            graph2.setColor(new Color(255, 255 ,255));
            graph2.fillRect(x, y + 5, (int)graph2.getFontMetrics().getStringBounds(option, graph2).getWidth(), 2);
        }
        graph2.setColor(Color.white);
        graph2.drawString(option, x, y);

        option = "Quit";
        x = xCenterText(option);
        y += 10 + gp.tileSize;
        if(commandNumber == 2) {
            graph2.setColor(new Color(255, 255 ,255));
            graph2.fillRect(x, y + 5, (int)graph2.getFontMetrics().getStringBounds(option, graph2).getWidth(), 2);
        }
        graph2.setColor(Color.white);
        graph2.drawString(option, x, y);
    }

    public void drawDialogScreen() {
        // Window - fereastra dialogului
        int x = gp.tileSize * 2;
        int y = gp.screenHeight / 2 + gp.tileSize;
        int width = gp.screenWidth - 4 * gp.tileSize;
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        graph2.setFont(arial_40);
        graph2.setFont(new Font("Consolas", Font.PLAIN, 28));

        for(String line : dialogText.split("\n")) {
            graph2.drawString(line, x, y);
            y += 40;
        }
        x = gp.tileSize * 2 - gp.tileSize / 2 + 10;
        y = gp.screenHeight / 2 + gp.tileSize - gp.tileSize / 2 + 10;
        int NPCIndex = gp.colChecker.checkEntity(gp.player, gp.NPC);
        if(NPCIndex != Entity.invalidIndex) {
            graph2.drawImage(gp.NPC[NPCIndex].down, x, y, null);
        }


    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color clr = new Color(0, 0 ,0, 210);
        graph2.setColor(clr);
        graph2.fillRoundRect(x, y, width, height, 35, 35);

        clr = new Color(150, 150, 150);
        graph2.setColor(clr);
        graph2.setStroke(new BasicStroke(3));
        graph2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
        graph2.setColor(Color.white);
    }

    public void drawPauseScreen() {
        graph2.setColor(Color.white);
        graph2.setFont(graph2.getFont().deriveFont(Font.PLAIN, 80));
        String text = "PAUSED";
        int x = xCenterText(text);
        int y = gp.screenHeight / 2;


        graph2.drawString(text, x, y);
    }

    public void drawFPS(double FPS) {
        // Draw FPS
        graph2.setColor(Color.white);
        graph2.setFont(console_40B);
        graph2.setFont(new Font("Consolas", Font.PLAIN, 20));
        String text = "FPS: " + FPS;
        graph2.drawString(text, gp.screenWidth - 2 * gp.tileSize, gp.originalTileSize);
    }

    public int xCenterText(String text) {
        int length = (int)graph2.getFontMetrics().getStringBounds(text, graph2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
