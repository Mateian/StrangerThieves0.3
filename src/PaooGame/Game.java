package PaooGame;

import PaooGame.ai.PathFinder;
import PaooGame.entity.EnemyManager;
import PaooGame.entity.Entity;
import PaooGame.entity.Player;
import PaooGame.main.*;
import PaooGame.objects.OBJ_Key;
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
    public EventHandler eventH = new EventHandler(this);
    public Thread gameThread;
    public CollisionChecker colChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Config config = new Config(this);
    public PathFinder pather = new PathFinder(this);
    public CutsceneManager cutsceneMng = new CutsceneManager(this);
    public DatabaseManager dbMng = new DatabaseManager("database.db", this);  // Builder Pattern
    public EnemyManager eMng = new EnemyManager(this);  // Factory Pattern
    public UI ui = new UI(this);
    public StateChanger sc = new StateChanger(this);  // State Pattern

    // Entities & Objects
    public Player player = Player.CreatePlayer(this, keyH);  // Singleton
    public Entity[][] obj = new Entity[maxMap][80];
    public Entity[][] NPC = new Entity[maxMap][20];
    public Entity[][] mst = new Entity[maxMap][20];
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
    public final int chestState = 7;
    public final int cutsceneState = 8;
    public final int paperState = 9;
    public final int gameFinished = 10;

    public int gameState = menuState;

    // Objectives
    public int spawnedDoors;
    public int openedDoors = 0;
    public int destroyedStone = 0;
    public boolean level2HeadInteraction = false;
    public boolean level3Eliminated = false;
    public int level1Score;
    public int level2Score;
    public int level3Score;
    public int levelScore;
    public int levelCounter;

    // Booleans
    public boolean paperDraw = false;

    // Debug
    int contor = 0;

    public Game() {

        // Setari fereastra + joc - initializare
        wnd = new GameWindow("Stranger Thieves", screenWidth, screenHeight, this);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        // Setup joc
        wnd = new GameWindow("Stranger Thieves", tileSize * maxScreenColumn, tileSize * maxScreenRow, this);
        wnd.BuildGameWindow();
//        config.loadConfig();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        levelSettings();
    }

    public void startGame() {

        // Initializare joc
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Programul va rula aceasta metoda si va repeta
    // instructiunile din aceasta pana cand se va inchide
    @Override
    public void run() {

        // Bucla joc
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            // Setare FPS
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if(delta >= 1) {
                if(sc.stateEqualTo(pauseState)) {
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

        // Update Game
        if(sc.stateEqualTo(playState)) {
            // Update player, npc, monstrii, obiecte, proiectile etc. - Entitati
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
                        mst[currentMap][i].checkDrop();
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
        // Pause State
        if(sc.stateEqualTo(pauseState)) {
            ui.drawPauseScreen();
        }

        // Spawnare cheie in harta 3 dupa eliminarea tuturor inamicilor
        if(currentMap == 2 && levelCounter >= levelScore) {
            for(int i = 0; i < obj[currentMap].length; ++i) {
                if(obj[currentMap][i] == null) {
                    obj[currentMap][i] = new OBJ_Key(this);
                    obj[currentMap][i].worldX = tileSize * 19;
                    obj[currentMap][i].worldY = tileSize * 26;
                    break;
                }
            }
            levelCounter = 0;
        }
        // Level 1 Completion
        if(currentMap == 0 && levelCounter >= levelScore) {
          sc.changeState(levelCompleteState);
//            currentMap = 1;
        }

        // Level 2 Completion
        if(currentMap == 1 && level2HeadInteraction) {
            sc.changeState(levelCompleteState);
            player.keyNumber = 0;
        }

        // Level 3 Completion
        if(currentMap == 2 && level3Eliminated) {
            sc.changeState(gameFinished);
        }

        // Debug

    }
    public void loadData() {

        // Player Settings
        String tableName = "PLAYER_SETTINGS";

        // Extragere date din tabel
        dbMng.selectTable(tableName);
        sc.changeState(playState);
    }
    public void saveData() {

        // Entities
        String monsters = "";
        for(int i = 0; i < maxMap; ++i) {
            for(int j = 0; j < mst[i].length; ++j) {
                if(mst[i][j] != null) {
                    monsters += mst[i][j].worldX + ", " + mst[i][j].worldY + ", " + mst[i][j].life + ", ";
                } else {
                    monsters += -1 + ", " + -1 + ", " + -1 + ", ";
                }
            }
        }
        if(!monsters.isEmpty()) {
            monsters = monsters.substring(0, monsters.length() - 2);
        }

        String npc = "";
        for(int i = 0; i < maxMap; ++i) {
            for(int j = 0; j < NPC[i].length; ++j) {
                if(NPC[i][j] != null) {
                    npc += NPC[i][j].worldX + ", " + NPC[i][j].worldY + ", ";
                } else {
                    npc += -1 + ", " + -1 + ", ";
                }
            }
        }
        if(!npc.isEmpty()) {
            npc = npc.substring(0, npc.length() - 2);
        }

        String objects = "";
        for(int i = 0; i < maxMap; ++i) {
            for(int j = 0; j < obj[i].length; ++j) {
                if(obj[i][j] != null) {
                    objects += obj[i][j].worldX + ", " + obj[i][j].worldY + ", ";
                } else {
                    objects += -1 + ", " + -1 + ", ";
                }
            }
        }
        if(!objects.isEmpty()) {
            objects = objects.substring(0, objects.length() - 2);
        }

        // Inventory
        String inventory = "";
        for(int i = 0; i < player.inventory.size(); ++i) {
            inventory += player.inventory.get(i).name + ", ";
        }
        if(!inventory.isEmpty()) {
            inventory = inventory.substring(0, inventory.length() - 2);
        }

        // Player Settings
        String tableName = "PLAYER_SETTINGS";

        // Creare tabel daca nu exista
        ArrayList<String> fields = new ArrayList<>();
        fields.add("PLAYERPOSX");
        fields.add("TEXT");
        fields.add("PLAYERPOSY");
        fields.add("TEXT");
        fields.add("CURRENTMAP");
        fields.add("TEXT");
        fields.add("DIRECTION");
        fields.add("TEXT");
        fields.add("LIFE");
        fields.add("TEXT");
        fields.add("SPAWNEDDOORS");
        fields.add("TEXT");
        fields.add("OPENEDDOORS");
        fields.add("TEXT");
        fields.add("DESTROYEDSTONE");
        fields.add("TEXT");
        fields.add("LEVEL2HEADINTERACTION");
        fields.add("TEXT");
        fields.add("LEVEL3ELIMINATED");
        fields.add("TEXT");
        fields.add("LEVEL1SCORE");
        fields.add("TEXT");
        fields.add("LEVEL2SCORE");
        fields.add("TEXT");
        fields.add("LEVEL3SCORE");
        fields.add("TEXT");
        fields.add("LEVELSCORE");
        fields.add("TEXT");
        fields.add("LEVELCOUNTER");
        fields.add("TEXT");
        fields.add("INVENTORY");
        fields.add("TEXT");
        fields.add("MONSTERS");
        fields.add("TEXT");
        fields.add("NPC");
        fields.add("TEXT");
        fields.add("OBJECTS");
        fields.add("TEXT");
        fields.add("MUSIC");
        fields.add("TEXT");
        fields.add("FX");
        fields.add("TEXT");
        fields.add("KEYS");
        fields.add("TEXT");
        dbMng.createTable(tableName, fields);

        // Adaugare date in tabel
        fields.clear();
        fields.add("PLAYERPOSX");
        fields.add("PLAYERPOSY");
        fields.add("CURRENTMAP");
        fields.add("DIRECTION");
        fields.add("LIFE");
        fields.add("SPAWNEDDOORS");
        fields.add("OPENEDDOORS");
        fields.add("DESTROYEDSTONE");
        fields.add("LEVEL2HEADINTERACTION");
        fields.add("LEVEL3ELIMINATED");
        fields.add("LEVEL1SCORE");
        fields.add("LEVEL2SCORE");
        fields.add("LEVEL3SCORE");
        fields.add("LEVELSCORE");
        fields.add("LEVELCOUNTER");
        fields.add("INVENTORY");
        fields.add("MONSTERS");
        fields.add("NPC");
        fields.add("OBJECTS");
        fields.add("MUSIC");
        fields.add("FX");
        fields.add("KEYS");
        ArrayList<String> values = new ArrayList<>();
        values.add(String.valueOf(player.worldX));
        values.add(String.valueOf(player.worldY));
        values.add(String.valueOf(currentMap));
        values.add(player.direction);
        values.add(String.valueOf(player.life));
        values.add(String.valueOf(spawnedDoors));
        values.add(String.valueOf(openedDoors));
        values.add(String.valueOf(destroyedStone));
        values.add(String.valueOf(level2HeadInteraction));
        values.add(String.valueOf(level3Eliminated));
        values.add(String.valueOf(level1Score));
        values.add(String.valueOf(level2Score));
        values.add(String.valueOf(level3Score));
        values.add(String.valueOf(levelScore));
        values.add(String.valueOf(levelCounter));
        values.add(inventory);
        values.add(monsters);
        values.add(npc);
        values.add(objects);
        values.add(String.valueOf(music.volumeScale));
        values.add(String.valueOf(fx.volumeScale));
        values.add(String.valueOf(player.keyNumber));
        dbMng.insertTable(tableName, fields, values);

        // Resetare joc dupa salvare in baza de date
        restart();
    }

    public void restart() {

        // Resetare nivel
        player.setDefaultPosition();
        player.restoreLife();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setObject();
        player.resetInventory();
        openedDoors = 0;
        levelCounter = 0;
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
        if(sc.stateEqualTo(menuState)) {
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

            // Cutscene
            cutsceneMng.draw(graph2);

            // UI
            ui.draw(graph2);
        }

        if(sc.stateEqualTo(levelCompleteState)) {
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

        // Play - muzica
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playFX(int i) {

        // Play - FX
        fx.setFile(i);
        fx.play();
    }

    void levelSettings() {

        // Setari nivel
        sc.changeState(menuState);
        levelCounter = 0;
        switch(currentMap) {
            case 0:
                levelScore = level1Score;
                break;
            case 1:
                levelScore = level2Score;
                break;
            case 2:
                levelScore = level3Score;
                break;
        }

        // Debug
//        contor = 0;
    }
}
