package PaooGame.objects;

import PaooGame.entity.Entity;
import PaooGame.Game;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(Game gp) {
        super(gp);
        name = "Heart";
        image = setup(5, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        image2 = setup(6, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        image3 = setup(0, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down = image;
        collision = true;
        solidArea.x = 0;
        solidArea.y = gp.tileSize / 2 + 4;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize / 2 - 4;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}

