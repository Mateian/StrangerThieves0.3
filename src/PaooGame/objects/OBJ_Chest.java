package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

import java.util.ArrayList;

public class OBJ_Chest extends Entity {

    // Base
    Game gp;
    ArrayList<Entity> loot;
    boolean opened = false;

    public OBJ_Chest(Game gp, ArrayList<Entity> loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;

        type = type_obstacle;
        name = "Chest";
        down = image = setup(19, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        collision = true;
        solidArea.x = 5;
        solidArea.y = 20;
        solidArea.width = gp.tileSize - 5;
        solidArea.height = 5;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void interact() {
//        gp.ui.openChest(this);
        for (Entity entity : loot) {
            for (int j = 0; j < gp.obj[gp.currentMap].length; ++j) {
                if (gp.obj[gp.currentMap][j] == null) {
                    gp.obj[gp.currentMap][j] = entity;
                    gp.obj[gp.currentMap][j].worldX = worldX;
                    gp.obj[gp.currentMap][j].worldY = worldY;
                    break;
                }
            }
        }
    }
}
