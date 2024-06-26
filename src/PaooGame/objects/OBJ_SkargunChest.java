package PaooGame.objects;

import PaooGame.entity.Entity;
import PaooGame.Game;

public class OBJ_SkargunChest extends Entity {
    public OBJ_SkargunChest(Game gp) {
        super(gp);
        name = "Skargun Chest";
        image = down = setup(7, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        collision = true;
        solidArea.x = 0;
        solidArea.y = 20;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
