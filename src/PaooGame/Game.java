package PaooGame;

import PaooGame.entity.Entity;
import PaooGame.entity.Player;
import PaooGame.main.*;
import PaooGame.tiles.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Game extends JPanel implements Runnable {

    // Graphic size
    public final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    // Screen Settings - standard
    GameWindow wnd;
    public final int maxScreenColumn = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenColumn;
    public final int screenHeight = tileSize * maxScreenRow;

    // World Settings
    public final int maxWorldColumn = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 5;
    public int currentMap = 0;

    // FPS
    int FPS = 60;
    public double forShowFPS = 0;

    // System
    public TileManager tileMng = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Sound music = new Sound();
    public Sound fx = new Sound();
    public UI ui = new UI(this);
    public EventHandler eventH = new EventHandler(this);
    public Thread gameThread;
    public CollisionChecker colChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Config config = new Config(this);

    // Entities & Objects
    public Player player = Player.CreatePlayer(this, keyH);
    public Entity[][] obj = new Entity[maxMap][10];
    public Entity[][] NPC = new Entity[maxMap][10];
    public Entity[][] mst = new Entity[maxMap][30];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();

    // Game State
    public final int deadState = -1;
    public final int pauseState = 0;
    public final int playState = 1;
    public final int dialogState = 2;
    public final int menuState = 3;
    public final int levelCompleteState = 4;
    public final int optionsState = 5;
    public final int inventoryState = 6;

    public int gameState = menuState;

    // Objectives
    public boolean lvl1Completion = false;
    public int levelObjectiveCounter = 0;
    public int lvlObjective = 5;

    // Debug
    int contor = 0;

    public Game() {
        wnd = new GameWindow("Stranger Thieves", screenWidth, screenHeight, this);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        wnd = new GameWindow("Stranger Thieves", tileSize * maxScreenColumn, tileSize * maxScreenRow, this);
        wnd.BuildGameWindow();
        config.loadConfig();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = menuState;

        // Debug
//        contor = 0;
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Programul va rula aceasta metoda si va repeta
    // instructiunile din aceasta pana cand se va inchide
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if(delta >= 1) {
                if(gameState == pauseState) {
                    ui.drawPauseScreen();
                }
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000) {
                forShowFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if(gameState == playState) {
            player.update();
            for(int i = 0; i < NPC[1].length; ++i) {
                if(NPC[currentMap][i] != null) {
                    NPC[currentMap][i].update();
                }
            }
            for(int i = 0; i < mst[1].length; ++i) {
                if(mst[currentMap][i] != null) {
                    if(mst[currentMap][i].alive && !mst[currentMap][i].dead) {
                        mst[currentMap][i].update();
                    }
                    if(!mst[currentMap][i].alive) {
                        mst[currentMap][i] = null;
                    }
                }
            }
            for(int i = 0; i < projectileList.size(); ++i) {
                if(projectileList.get(i) != null) {
                    if(projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }
        }
        if(gameState == pauseState) {
            ui.drawPauseScreen();
        }
        if(levelObjectiveCounter == lvlObjective) {
          gameState = levelCompleteState;
          levelObjectiveCounter = 0;
          lvlObjective = -1;
//            currentMap = 1;
        }
        // Debug

        if(contor == 0 && currentMap == 1) {
            eventH.teleport(1, 40, 42);
            contor++;
        }
    }
    public void restart() {
        player.setDefaultPosition();
        player.restoreLife();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setObject();
    }
    public void paintComponent(Graphics graph) {

        super.paintComponent(graph);

        Graphics2D graph2 = (Graphics2D)graph;

        // Debug
        long drawStart = 0;
        if(keyH.toggleDebugInfo) {
            drawStart = System.nanoTime();
        }

        // Menu
        if(gameState == menuState) {
            ui.draw(graph2);
        } else {

        //  Tile
            tileMng.draw(graph2);

        //  Add all entities in the List
            entityList.add(player);

            for(int i = 0; i < NPC[1].length; ++i) {
                if(NPC[currentMap][i] != null) {
                    entityList.add(NPC[currentMap][i]);
                }
            }
            for(int i = 0; i < obj[1].length; ++i) {
                if(obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for(int i = 0; i < mst[1].length; ++i) {
                if(mst[currentMap][i] != null) {
                    entityList.add(mst[currentMap][i]);
                }
            }
            for(int i = 0; i < projectileList.size(); ++i) {
                if(projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }

            // Sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // Draw Entities
            for(int i = 0; i < entityList.size(); ++i) {
                entityList.get(i).draw(graph2);
            }

            // Empty Entity List
            entityList.clear();

            // UI
            ui.draw(graph2);
        }

        if(gameState == levelCompleteState) {
            ui.drawWin();
        }

        // Debug
        if(keyH.toggleDebugInfo) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            graph2.setFont(new Font("Consolas", Font.PLAIN, 20));
            graph2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int height = 20;
            graph2.drawString("X: " + player.worldX, x, y);
            y += height;
            graph2.drawString("Y: " + player.worldY, x, y);
            y += height;
            graph2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
            y += height;
            graph2.drawString("Column: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
            y += height;
            graph2.drawString("Draw Time: " + passed, x, y);
        }
        graph2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playFX(int i) {
        fx.setFile(i);
        fx.play();
    }
}
