package PaooGame.objects;

import PaooGame.Game;
import PaooGame.entity.Entity;

public class OBJ_HealPotion extends Entity {
    Game gp;

    public OBJ_HealPotion(Game gp) {
        super(gp);
        this.gp = gp;

        value = 3;
        pickUpable = true;
        type = type_consumable;
        name = "Heal Potion";
        down = image = setup(16, 0, "/objects/object_spritesheet", gp.originalTileSize, gp.originalTileSize);
    }

    public void use(Entity entity) {
        gp.ui.showMessage("Healing Potion used!");
        if(gp.currentMap == 2) {
            entity.life += entity.maxLife / 3;
        } else {
            entity.life += entity.maxLife / 2;
        }
        if(gp.player.life > gp.player.maxLife) {
            gp.player.life = gp.player.maxLife;
        }
    }
}
