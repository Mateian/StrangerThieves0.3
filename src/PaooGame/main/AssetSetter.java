package PaooGame.main;

import PaooGame.Game;
import PaooGame.enemy.MST_Enemy;
import PaooGame.enemy.MST_MegaEnemy;
import PaooGame.entity.NPC_Fen;
import PaooGame.objects.*;

public class AssetSetter {

    // Base Settings
    Game gp;

    public AssetSetter(Game gp) {
        this.gp = gp;
    }

    public void setObject() {
        // Level 1 Objects
        int mapNum = 0;
        gp.obj[mapNum][0] = new OBJ_Chest(gp);
        gp.obj[mapNum][0].worldX = 22 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 30 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_Heart(gp);
        gp.obj[mapNum][1].worldX = 21 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 30 * gp.tileSize;

        // Level 2 Objects


        // Level 3 Objects
    }

    public void setNPC() {
        // Level 1 NPCs
        int mapNum = 0;
        gp.NPC[mapNum][0] = new NPC_Fen(gp);
        gp.NPC[mapNum][0].worldX = gp.tileSize * 23;
        gp.NPC[mapNum][0].worldY = gp.tileSize * 30 - 1;

        // Level 2 NPCs


        // Level 3 NPCs
    }

    public void setMonster() {
        // Level 1 Monsters
        int mapNum = 0;
        int k = 0;
        for(int i = 0; i < 5; ++i) {
            gp.mst[mapNum][k] = new MST_Enemy(gp);
            gp.mst[mapNum][k].worldX = gp.tileSize * (23 + i);
            gp.mst[mapNum][k].worldY = gp.tileSize * (15 + i);
            k++;
        }

        // Level 2 Monsters
        mapNum = 1;
        k = 0;

        // Zona 1
        for(int i = 0; i < 5; ++i) {
            gp.mst[mapNum][k] = new MST_Enemy(gp);
            gp.mst[mapNum][k].worldX = gp.tileSize * (19 + i);
            gp.mst[mapNum][k].worldY = gp.tileSize * (31 + i);
            k++;
            gp.mst[mapNum][k] = new MST_Enemy(gp);
            gp.mst[mapNum][k].worldX = gp.tileSize * (19 + i);
            gp.mst[mapNum][k].worldY = gp.tileSize * (32 + i);
            k++;
        }

        // Zona 2
        for(int i = 0; i < 2; ++i) {
            gp.mst[mapNum][k] = new MST_MegaEnemy(gp);
            gp.mst[mapNum][k].worldX = gp.tileSize * (17 + i);
            gp.mst[mapNum][k].worldY = gp.tileSize * 20;
            k++;
        }

        // Zona 3
        for(int i = 0; i < 2; ++i) {
            gp.mst[mapNum][k] = new MST_Enemy(gp);
            gp.mst[mapNum][k].worldX = gp.tileSize * (31 + i);
            gp.mst[mapNum][k].worldY = gp.tileSize * 20;
            k++;
        }

        // Zona 4
        for(int i = 0; i < 2; ++i) {
            gp.mst[mapNum][k] = new MST_Enemy(gp);
            gp.mst[mapNum][k].worldX = gp.tileSize * (14 + i);
            gp.mst[mapNum][k].worldY = gp.tileSize * 7;
            k++;
        }

        // Level 3 Monsters
        mapNum = 2;
        k = 0;
    }
}
