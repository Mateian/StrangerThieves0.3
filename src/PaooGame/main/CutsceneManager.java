package PaooGame.main;

import PaooGame.Game;

import java.awt.*;

public class CutsceneManager {
    Game gp;
    Graphics2D graph2;
    public int sceneNum;
    public int scenePhase;

    // Scene Number
    public final int NA = 0;
    public final int HEAD = 1;

    public CutsceneManager(Game gp) {
        this.gp = gp;
    }
    public void draw(Graphics2D graph2) {
        this.graph2 = graph2;
    }

}
