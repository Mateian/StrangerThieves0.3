package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_Snaipa extends Entity {

    public OBJ_Snaipa(Game gp) {
        super(gp);

        pickUpable = true;
        type = type_weapon;
        isWeapon = true;
        name = "Snaipa";
        image = setup(13, 0, "/objects/object_spritesheet", gp.tileSize, gp.tileSize);
        projectile = new OBJ_MegaBullet(gp);
    }
}
