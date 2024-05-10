package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_Door extends Entity {

    // Base
    Game gp;

    public OBJ_Door(Game gp) {
        super(gp);
        this.gp = gp;

        type = type_noStore;
        name = "Door";
        image = down = setup(17, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        collision = true;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void use(Entity entity) {

    }
}
