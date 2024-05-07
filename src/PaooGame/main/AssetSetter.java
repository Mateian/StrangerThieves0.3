package PaooGame.main;

import PaooGame.Game;
import PaooGame.enemy.MST_Enemy;
import PaooGame.entity.NPC_Fen;
import PaooGame.objects.*;

public class AssetSetter {

    // Base Settings
    Game gp;

    public AssetSetter(Game gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Skargun(gp);
        gp.obj[0].worldX = 22 * gp.tileSize;
        gp.obj[0].worldY = 30 * gp.tileSize;
    }

    public void setNPC() {
        gp.NPC[0] = new NPC_Fen(gp);
        gp.NPC[0].worldX = gp.tileSize * 23;
        gp.NPC[0].worldY = gp.tileSize * 30 - 1;
    }

    public void setMonster() {
        int k = 0;
        for(int i = 0; i < 5; ++i) {
            gp.mst[k] = new MST_Enemy(gp);
            gp.mst[k].worldX = gp.tileSize * (23 + i);
            gp.mst[k].worldY = gp.tileSize * (15 + i);
            k++;
        }
    }
}
