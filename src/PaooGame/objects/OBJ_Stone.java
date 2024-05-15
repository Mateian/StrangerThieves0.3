package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

import java.util.ArrayList;

public class OBJ_Stone extends Entity {

    // Base
    Game gp;

    public OBJ_Stone(Game gp, boolean breakable) {
        super(gp);
        this.gp = gp;

        if (breakable) {
            type = type_monster;
            maxLife = 10;
            life = maxLife;
        } else {
            type = type_obstacle;
        }
        name = "Stone";
        down = image = setup(26, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        collision = true;
        solidArea.x = 5;
        solidArea.y = 20;
        solidArea.width = gp.tileSize - 5;
        solidArea.height = 5;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
