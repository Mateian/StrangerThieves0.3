package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_Skargun extends Entity {
    public OBJ_Skargun(Game gp) {
        super(gp);
        name = "Skargun";
        image = setup(8, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
    }
}
