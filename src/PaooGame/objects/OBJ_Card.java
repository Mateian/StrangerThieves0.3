package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_Card extends Entity {
    // Base
    Game gp;

    public OBJ_Card(Game gp) {
        super(gp);
        this.gp = gp;

        type = type_pickup;
        name = "Card";
        image = down = setup(18, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        collision = true;
        solidArea.x = 0;
        solidArea.y = 24;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void use(Entity entity) {
        gp.ui.showMessage("You got a key!");
        gp.player.keyNumber++;
    }
}
