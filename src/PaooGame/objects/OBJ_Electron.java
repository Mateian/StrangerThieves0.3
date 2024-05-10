package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_Electron extends Entity {
    public OBJ_Electron(Game gp) {
        super(gp);

        isWeapon = true;
        name = "Electron";
        down = image = setup(13, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        projectile = new OBJ_MegaBullet(gp);
//        attackArea.width = 36;
//        attackArea.height = 36;
    }
}
