package PaooGame.main;

import PaooGame.Game;
import PaooGame.enemy.MST_Enemy;
import PaooGame.entity.Entity;
import PaooGame.objects.OBJ_Card;
import PaooGame.objects.OBJ_Door;
import PaooGame.objects.OBJ_Heart;
import PaooGame.objects.OBJ_Key;

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
    BufferedImage weaponFrame;
    BufferedImage doorImage;
    BufferedImage cardImage;
    BufferedImage keyImage;

    // State
    int subState = 0;

    // Variables
    public int slotCol = 0;
    public int slotRow = 0;

    // Booleans
    public boolean messageOn = false;
    public boolean gameFinished = false;

    // String - Messages
    public String message = "";
    public String dialogText = "";
    public String paperText = "";

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
        console_40B = new Font("Consolas", Font.BOLD, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        // Images
        MST_Enemy enemy = new MST_Enemy(gp);
        enemyImage = enemy.down1;
        OBJ_Door door = new OBJ_Door(gp);
        doorImage = door.image;
        OBJ_Card card = new OBJ_Card(gp);
        cardImage = card.image;
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;

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
    }
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D graph2) {
        this.graph2 = graph2;

            // Menu
            if(gp.gameState == gp.menuState) {
                drawMenu();
            }
            if(gp.gameState == gp.optionsState) {
                drawOptionsScreen();
            }
            if(gp.gameState == gp.playState) {
                int levelScore;
                int levelCounter;
                switch(gp.currentMap) {
                    case 0:
                        levelScore = gp.level1Score;
                        break;
                    case 1:
                        levelScore = gp.level2Score;
                        break;
                    case 2:
                        levelScore = gp.level3Score;
                        break;
                    default:
                        levelScore = -1;
                        break;
                }
                levelCounter = gp.levelCounter;
                graph2.setColor(Color.white);
                if(gp.currentMap == 0 || gp.currentMap == 1 || (gp.currentMap == 2 && gp.levelScore != gp.levelCounter))
                {
                    graph2.drawImage(enemyImage, 40, 45, gp.tileSize / 2, gp.tileSize / 2, null);
                    graph2.setFont(new Font("Consolas", Font.PLAIN, 20));
                    graph2.drawString(levelCounter + "|" + levelScore, 74, 65);
                }

                if(gp.currentMap == 1) {
                    graph2.drawImage(doorImage, 40, 45 + gp.tileSize, gp.tileSize / 2, gp.tileSize / 2, null);
                    graph2.setFont(new Font("Consolas", Font.PLAIN, 20));
                    graph2.drawString(gp.spawnedDoors - gp.openedDoors + " remaining", 74, 65 + gp.tileSize);

                    graph2.drawImage(cardImage, 40, 45 + 2 * gp.tileSize, gp.tileSize / 2, gp.tileSize / 2, null);
                    graph2.setFont(new Font("Consolas", Font.PLAIN, 20));
                    graph2.drawString(String.valueOf(gp.player.keyNumber), 74, 65 + 2 * gp.tileSize);
                }
                if(gp.currentMap == 2) {
                    graph2.drawImage(keyImage, 40, 45 + gp.tileSize, gp.tileSize / 2, gp.tileSize / 2, null);
                    graph2.setFont(new Font("Consolas", Font.PLAIN, 20));
                    graph2.drawString(String.valueOf(gp.player.keyNumber), 74, 65 + gp.tileSize);
                }

                // Time
                playTime +=(double)1/60;
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
            if(gp.gameState == gp.inventoryState) {
                drawInventory();
            }
            if(gp.gameState == gp.gameFinished) {
                drawFinish();
            }
            if(gp.gameState == gp.paperState) {
                drawPaper(paperText);
            }
//            if(gp.gameState == gp.chestState) {
//                openChest();
//            }
    }
    public void drawPaper(String text) {
        int x = gp.tileSize * 5;
        int y = gp.tileSize;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 8;
        graph2.setColor(Color.white);
        graph2.fillRect(x, y, width, height);
        graph2.setColor(Color.black);
        x += gp.tileSize / 2;
        y += gp.tileSize / 2 + 10;
        graph2.setFont(new Font("Consolas", Font.ITALIC, 14));
        for(String line : text.split("\n")) {
            graph2.drawString(line, x, y);
            y += 15;
        }
    }
    public void drawFinish() {
        graph2.setColor(Color.darkGray);
        graph2.fillRect(xCenterText("Congratulations! You saved, Fen!"), gp.screenHeight / 2, graph2.getFontMetrics().getWidths()[0], graph2.getFontMetrics().getHeight());
        graph2.setFont(new Font("Consolas", Font.BOLD, 40));
        graph2.setColor(Color.white);
        graph2.drawString("Congratulations! You saved, Fen!", xCenterText("Congratulations! You saved, Fen!"), gp.screenHeight / 2);
    }
    public void setPaperText(String text) {
        paperText = text;
    }
    public void drawInventory() {

        // Window
        int frameX = gp.tileSize * 10;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 4;
        int frameHeight = gp.tileSize * 2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXstart = frameX + 20;
        final int slotYstart =frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // Items
        for(int i = 0; i < gp.player.inventory.size(); ++i) {

            // Equip
            if(gp.player.inventory.get(i) == gp.player.currentWeapon) {
                graph2.setColor(new Color(100, 0, 0));
                graph2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            graph2.drawImage(gp.player.inventory.get(i).image, slotX, slotY, null);
            slotX += slotSize;
            if(i == 2) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // Select
        int selectX = slotXstart + (slotSize * slotCol);
        int selectY = slotYstart + (slotSize * slotRow);
        int selectWidth = gp.tileSize;
        int selectHeight = gp.tileSize;

        // Draw Select
        graph2.setColor(Color.white);
        graph2.drawRoundRect(selectX, selectY, selectWidth, selectHeight, 10, 10);
    }
    public void drawOptionsScreen() {
        graph2.setColor(Color.white);
        graph2.setFont(graph2.getFont().deriveFont(30F));

        // Sub Window
        int frameX = gp.tileSize * 4;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 9;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_draw(frameX, frameY);
                break;
            case 1:
                controls_draw(frameX, frameY);
                break;
        }
        gp.keyH.enterPressed = false;
    }
    public void options_draw(int frameX, int frameY) {
        int textX;
        int textY;
        graph2.setFont(console_40B);

        // Title
        String text = "Options";
        textX = xCenterText(text);
        textY = frameY + gp.tileSize;
        graph2.drawString(text, textX, textY);
        graph2.setFont(graph2.getFont().deriveFont(30F));

        // Music
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        graph2.drawString("Music", textX, textY);
        if(commandNumber == 0) {
            graph2.setFont(graph2.getFont().deriveFont(25F));
            graph2.drawString("->", textX - 30, textY);
        }
        graph2.setFont(graph2.getFont().deriveFont(30F));

        // SE
        textY += gp.tileSize;
        graph2.drawString("FX", textX, textY);
        if(commandNumber == 1) {
            graph2.setFont(graph2.getFont().deriveFont(25F));
            graph2.drawString("->", textX - 30, textY);
        }
        graph2.setFont(graph2.getFont().deriveFont(30F));

        // Control
        textY += gp.tileSize;
        graph2.drawString("Control", textX, textY);
        if(commandNumber == 2) {
            graph2.setFont(graph2.getFont().deriveFont(25F));
            graph2.drawString("->", textX - 30, textY);
            if(gp.keyH.enterPressed) {
                subState = 1;
            }
        }
        graph2.setFont(graph2.getFont().deriveFont(30F));

        // Exit
        textY += gp.tileSize;
        graph2.drawString("Save & Exit", textX, textY);
        if(commandNumber == 3) {
            graph2.setFont(graph2.getFont().deriveFont(25F));
            graph2.drawString("->", textX - 30, textY);
            if(gp.keyH.enterPressed) {
                gp.gameState = gp.menuState;
                commandNumber = 0;
            }
        }
        graph2.setFont(graph2.getFont().deriveFont(30F));

        textY += gp.tileSize;
        graph2.setFont(graph2.getFont().deriveFont(15F));
        graph2.drawString("W, S, ENTER - select and confirm", textX, textY);
        graph2.setFont(graph2.getFont().deriveFont(30F));

        // Back
        textY += gp.tileSize;
        graph2.drawString("Back", textX, textY);
        if(commandNumber == 4) {
            graph2.setFont(graph2.getFont().deriveFont(25F));
            graph2.drawString("->", textX - 30, textY);
            if(gp.keyH.enterPressed) {
                gp.gameState = gp.playState;
                commandNumber = 0;
            }
        }
        graph2.setFont(graph2.getFont().deriveFont(30F));


        // Options Settings
        textX = frameX + gp.tileSize * 4;
        textY = frameY + gp.tileSize - 22;

        // Music Volume
        textY += gp.tileSize * 2;
        graph2.drawRect(textX, textY, 120 + gp.tileSize, 24);
        int volumeWidth = 42 * gp.music.volumeScale;
        graph2.fillRect(textX, textY, volumeWidth, 24);

        // FX Volume
        textY += gp.tileSize;
        graph2.drawRect(textX, textY, 120 + gp.tileSize, 24);
        volumeWidth = 42 * gp.fx.volumeScale;
        graph2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }
    public void controls_draw(int frameX, int frameY) {
        int textX;
        int textY;
        graph2.setFont(console_40B);

        // Title
        String text = "Control";
        textX = xCenterText(text);
        textY = frameY + gp.tileSize;
        graph2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        graph2.setFont(graph2.getFont().deriveFont(25F));
        graph2.drawString("Move", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Confirm", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Use", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Shoot", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Pause", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Drop weapon", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Inventory", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Restart Level", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Options", textX, textY); textY += gp.tileSize / 2;

        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2;
        graph2.drawString("W A S D", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Enter", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("E", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Space", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("P", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("G", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("I", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Up Arrow", textX, textY); textY += gp.tileSize / 2;
        graph2.drawString("Esc", textX, textY); textY += gp.tileSize / 2;

        // Back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 8;
        graph2.drawString("Back", textX, textY);
        if(commandNumber == 0) {
            graph2.drawString("->", textX - 30, textY);
            if(gp.keyH.enterPressed) {
                subState = 0;
            }
        } else {
            commandNumber = 0;
        }
    }
    public void drawCurrentWeapon() {
        if(gp.player.hasWeapon) {
            graph2.drawImage(gp.player.currentWeapon.image, gp.screenWidth - gp.tileSize * 3, gp.screenHeight - gp.tileSize * 3, 75, 75,null);
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
        graph2.setFont(new Font("Consolas", Font.PLAIN, 40));
        graph2.setColor(Color.white);
        graph2.drawString("Level Completed", xCenterText("Level Completed"), gp.screenHeight / 2);
    }
    public void drawLife() {

        int x = gp.tileSize / 2;
        int y = gp.screenHeight - gp.tileSize * 2;
        int i = 0;

        // Black Heart
        graph2.setColor(Color.black);
        int containerWidth = gp.tileSize * 5 + 10;
//        graph2.fillRect(x, y, gp.tileSize * gp.player.maxLife + 10, gp.tileSize);
        graph2.fillRect(x, y, containerWidth + 10, gp.tileSize);
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
        graph2.fillRect(x + 5, y + 5, (int)(((double)gp.player.life / gp.player.maxLife * containerWidth)), gp.tileSize - 10);

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
        // HP Text
        graph2.setColor(Color.white);
        graph2.setFont(console_40B);
        graph2.setFont(graph2.getFont().deriveFont(Font.PLAIN, 24));
        graph2.drawString(String.valueOf(gp.player.life), containerWidth / 2 + gp.tileSize / 2, y);
    }
    public void drawMenu() {
        // Background
        try {
            BufferedImage backGroundImage = ImageIO.read(getClass().getResourceAsStream("/icons/background-image.png"));
//            int width = backGroundImage.getWidth();
//            int height = backGroundImage.getHeight();
            graph2.drawImage(backGroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        graph2.setFont(new Font("Consolas", Font.PLAIN,50));

        // Game Name
        graph2.setFont(graph2.getFont().deriveFont(Font.BOLD, 80F));
        String name = "Stranger Thieves";
        int x = xCenterText(name);
        int y = 4 * gp.tileSize;
        graph2.setColor(Color.darkGray);
        graph2.fillRoundRect(x - 10, y - 70, 720, 100, 20, 20);
        graph2.setColor(Color.white);
        graph2.drawString(name, x, y);

        // Menu
        String option;
        graph2.setFont(graph2.getFont().deriveFont(Font.BOLD, 55));
        graph2.setColor(Color.darkGray);
        graph2.fillRect(xCenterText("New Game") - gp.tileSize / 2, y + 2 * gp.tileSize, 290, 180);
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


        option = "W, S, ENTER - select and confirm";
        graph2.setFont(graph2.getFont().deriveFont(Font.PLAIN, 30));
        graph2.setColor(Color.darkGray);
        graph2.fillRect(xCenterText("W, S, ENTER - select and confirm") - 10, y + 10 + gp.tileSize - (int)graph2.getFontMetrics().getStringBounds("W, S, ENTER - select and confirm", graph2).getHeight(), (int)graph2.getFontMetrics().getStringBounds("W, S, ENTER - select and confirm", graph2).getWidth() + 20, (int)graph2.getFontMetrics().getStringBounds("W, S, ENTER - select and confirm", graph2).getHeight());

        x = xCenterText(option);
        y += gp.tileSize;
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
        int MSTIndex = gp.colChecker.checkEntity(gp.player, gp.mst);
        if(NPCIndex != Entity.invalidIndex) {
            graph2.drawImage(gp.NPC[gp.currentMap][NPCIndex].down, x, y, null);
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
    public int getItemIndexOnSlot() {
        return slotCol + (slotRow * 3);
    }
    public void openChest(Entity entity) {
        // Window
        int frameX = gp.tileSize * 5;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // Items
        for(int i = 0; i < gp.player.inventory.size(); ++i) {

            // Equip
            if(gp.player.inventory.get(i) == gp.player.currentWeapon) {
                graph2.setColor(new Color(100, 0, 0));
                graph2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            graph2.drawImage(gp.player.inventory.get(i).image, slotX, slotY, null);
            slotX += slotSize;
            if(i == 2) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // Select
        int selectX = slotXstart + (slotSize * slotCol);
        int selectY = slotYstart + (slotSize * slotRow);
        int selectWidth = gp.tileSize;
        int selectHeight = gp.tileSize;

        // Draw Select
        graph2.setColor(Color.white);
        graph2.drawRoundRect(selectX, selectY, selectWidth, selectHeight, 10, 10);
    }
}
