package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_Card extends Entity {
    // Base
    Game gp;

    public OBJ_Card(Game gp) {
        super(gp);
        this.gp = gp;

        // De "decomentat" daca se doreste sa se foloseasca cheile ca un obiect in inventar
//        type = type_consumable;
//        pickUpable = true;
        type = type_pickup;
        pickUpable = true;
        name = "Card";
        image = down = setup(18, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
        collision = true;
        solidArea.x = 3;
        solidArea.y = 24;
        solidArea.width = 45;
        solidArea.height = 3;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void use(Entity entity) {
        gp.ui.showMessage("You got a key!");
        gp.player.keyNumber++;

        // De "decomentat" daca se doreste sa se foloseasca cheile ca un obiect in inventar
//
//        int objIndex = detectEntity(entity, gp.obj, "Door");
//
//        if(objIndex != Entity.invalidIndex) {
//            gp.player.keyNumber++;
//            gp.obj[gp.currentMap][objIndex] = null;
//        }
    }
}
