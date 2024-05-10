package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_Electron extends Entity {
    public OBJ_Electron(Game gp) {
        super(gp);

        name = "Electron";
        image = setup(13, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        attackValue = 10;
        projectile = new OBJ_Bullet(gp);
//        attackArea.width = 36;
//        attackArea.height = 36;
    }
}
