package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

import java.util.ArrayList;

public class OBJ_BrokenChest extends Entity {

    // Base
    Game gp;

    public OBJ_BrokenChest(Game gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = "Broken Chest";
        down = image = setup(21, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        collision = true;
        solidArea.x = 5;
        solidArea.y = 20;
        solidArea.width = gp.tileSize - 5;
        solidArea.height = 5;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
