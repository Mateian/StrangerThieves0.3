package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

import java.util.ArrayList;

public class OBJ_Chair extends Entity {

    // Base
    Game gp;

    public OBJ_Chair(Game gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = "Chair";
        down = image = setup(25, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        collision = true;
        solidArea.x = 5;
        solidArea.y = 20;
        solidArea.width = gp.tileSize - 5;
        solidArea.height = 5;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
