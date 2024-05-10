package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_KTPY extends Entity {

    public OBJ_KTPY(Game gp) {
        super(gp);

        name = "Electron";
        image = setup(13, 0, "/objects/object_spritesheet", gp.tileSize, gp.tileSize);
        attack = 1;
        attackArea.width = 36;
        attackArea.height = 36;
    }
}
