package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_Stairs extends Entity {

    // Base
    Game gp;

    public OBJ_Stairs(Game gp) {
        super(gp);
        this.gp = gp;

        type = type_pickup;
        name = "Stairs";
        image = down = setup(27, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        collision = true;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void use(Entity ent) {
        gp.player.teleport(2, 40, 40);
    }
}
