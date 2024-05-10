package PaooGame.main;

import PaooGame.Game;
import PaooGame.enemy.MST_Enemy;
import PaooGame.enemy.MST_MegaEnemy;
import PaooGame.entity.Entity;
import PaooGame.entity.NPC_Fen;
import PaooGame.objects.*;

import java.util.ArrayList;
import java.util.stream.Collector;

public class AssetSetter {

    // Base Settings
    Game gp;

    public AssetSetter(Game gp) {
        this.gp = gp;
    }
    public void setObject() {
        int mapNum;
        int k;
        ArrayList<Entity> chest = new ArrayList<>();
        // Level 1 Objects
        mapNum = 0;
        k = 0;
        gp.obj[mapNum][k] = new OBJ_SkargunChest(gp);
        gp.obj[mapNum][k].worldX = 22 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 30 * gp.tileSize;
        ++k;
        gp.obj[mapNum][k] = new OBJ_Heart(gp);
        gp.obj[mapNum][k].worldX = 21 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 30 * gp.tileSize;
        ++k;

        // Level 2 Objects
        k = 0;
        mapNum++;
        gp.spawnedDoors = 0;

        // Weapons

        // Doors
        gp.obj[mapNum][k] = new OBJ_Door(gp);
        gp.obj[mapNum][k].worldX = 37 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 19 * gp.tileSize;
        ++k;
        gp.spawnedDoors++;

        gp.obj[mapNum][k] = new OBJ_Door(gp);
        gp.obj[mapNum][k].worldX = 13 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 38 * gp.tileSize;
        ++k;
        gp.spawnedDoors++;

        gp.obj[mapNum][k] = new OBJ_Door(gp);
        gp.obj[mapNum][k].worldX = 10 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 32 * gp.tileSize;
        ++k;
        gp.spawnedDoors++;

        gp.obj[mapNum][k] = new OBJ_Door(gp);
        gp.obj[mapNum][k].worldX = 30 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 7 * gp.tileSize;
        ++k;
        gp.spawnedDoors++;

        gp.obj[mapNum][k] = new OBJ_Door(gp);
        gp.obj[mapNum][k].worldX = 10 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 14 * gp.tileSize;
        ++k;
        gp.spawnedDoors++;

        gp.obj[mapNum][k] = new OBJ_Door(gp);
        gp.obj[mapNum][k].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 9 * gp.tileSize;
        ++k;
        gp.spawnedDoors++;

        gp.obj[mapNum][k] = new OBJ_Door(gp);
        gp.obj[mapNum][k].worldX = 40 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 38 * gp.tileSize;
        ++k;
        gp.spawnedDoors++;

        gp.obj[mapNum][k] = new OBJ_Door(gp);
        gp.obj[mapNum][k].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 10 * gp.tileSize;
        ++k;
        gp.spawnedDoors++;

        // Cards

        // Chests
        chest.add(new OBJ_HealPotion(gp));
        chest.add(new OBJ_Electron(gp));
        chest.add(new OBJ_Card(gp));
        gp.obj[mapNum][k] = new OBJ_Chest(gp, chest);
        gp.obj[mapNum][k].worldX = 38 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 42 * gp.tileSize;
        ++k;
        chest.add(new OBJ_HealPotion(gp));
        chest.add(new OBJ_Electron(gp));
        chest.add(new OBJ_Card(gp));
        gp.obj[mapNum][k] = new OBJ_Chest(gp, chest);
        gp.obj[mapNum][k].worldX = 31 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 20 * gp.tileSize;
        ++k;
        chest.clear();
        chest.add(new OBJ_HealPotion(gp));
        chest.add(new OBJ_Electron(gp));
        chest.add(new OBJ_Card(gp));
        gp.obj[mapNum][k] = new OBJ_Chest(gp, chest);
        gp.obj[mapNum][k].worldX = 8 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 44 * gp.tileSize;
        ++k;
        chest.clear();
        chest.add(new OBJ_HealPotion(gp));
        chest.add(new OBJ_Electron(gp));
        chest.add(new OBJ_Card(gp));
        gp.obj[mapNum][k] = new OBJ_Chest(gp, chest);
        gp.obj[mapNum][k].worldX = 8 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 25 * gp.tileSize;
        ++k;
        chest.clear();
        chest.add(new OBJ_HealPotion(gp));
        chest.add(new OBJ_Electron(gp));
        chest.add(new OBJ_Card(gp));
        gp.obj[mapNum][k] = new OBJ_Chest(gp, chest);
        gp.obj[mapNum][k].worldX = 18 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 13 * gp.tileSize;
        ++k;
        chest.clear();
        chest.add(new OBJ_HealPotion(gp));
        chest.add(new OBJ_Electron(gp));
        chest.add(new OBJ_Card(gp));
        gp.obj[mapNum][k] = new OBJ_Chest(gp, chest);
        gp.obj[mapNum][k].worldX = 8 * gp.tileSize;
        gp.obj[mapNum][k].worldY = 15 * gp.tileSize;
        ++k;

        // Level 3 Objects
        k = 0;
        mapNum++;

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

        // Zona 1
        for(int i = 0; i < 5; ++i) {
            gp.mst[mapNum][k] = new MST_Enemy(gp);
            gp.mst[mapNum][k].worldX = gp.tileSize * (23 + i);
            gp.mst[mapNum][k].worldY = gp.tileSize * 15;
            k++;
        }

        // Zona 2
        for(int i = 0; i < 5; ++i) {
            gp.mst[mapNum][k] = new MST_Enemy(gp);
            gp.mst[mapNum][k].worldX = gp.tileSize * (15 + i);
            gp.mst[mapNum][k].worldY = gp.tileSize * 19;
            k++;
        }
        gp.level1Score = k;

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
        gp.level2Score = k;

        // Level 3 Monsters
        mapNum = 2;
        k = 0;
        gp.level3Score = k;
    }
}
