package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

import java.util.ArrayList;

public class OBJ_Paper extends Entity {

    // Base
    Game gp;
    String paperText;

    public OBJ_Paper(Game gp, String text) {
        super(gp);
        this.gp = gp;

        type = type_pickup;

        name = "Paper";
        down = image = setup(23, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        collision = true;
        solidArea.x = 5;
        solidArea.y = 20;
        solidArea.width = gp.tileSize - 5;
        solidArea.height = 5;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        this.paperText = text;
    }
    public void use(Entity entity) {
        gp.paperDraw = true;
        gp.gameState = gp.paperState;
        gp.ui.setPaperText(this.paperText);
    }
}
