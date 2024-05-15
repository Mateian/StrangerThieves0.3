package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

import java.util.ArrayList;

public class OBJ_Paper extends Entity {

    // Base
    Game gp;

    public OBJ_Paper(Game gp, String paperText) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = "Paper";
        down = image = setup(23, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        collision = true;
        solidArea.x = 5;
        solidArea.y = 20;
        solidArea.width = gp.tileSize - 5;
        solidArea.height = 5;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
