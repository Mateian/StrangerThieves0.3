package PaooGame.entity;

import PaooGame.objects.*;
import PaooGame.Game;
import PaooGame.main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    private static int instances = 0;

    // Key Handler
    KeyHandler keyH;

    // Images
    public BufferedImage up, down, left, right;
    public BufferedImage weapUp, weapUp1, weapDown, weapDown1, weapLeft, weapLeft1, weapRight, weapRight1;
//    ArrayList<BufferedImage> defArray = new ArrayList<>();

    // Variables
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 3;
    Entity oldWeapon;

    // Position
    public final int screenX;
    public final int screenY;

    // Boolean
    public boolean canPickup = true;
    public  boolean hasWeapon = false;
    public boolean attackCancelled = false;
    public boolean weaponSwitch = false;

    // Counters
    int counterGun = 0;
    public int keyNumber = 0;

    private Player(Game gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenWidth / 2 - (gp.tileSize * 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getImage();
        setItems();
    }
    public static Player CreatePlayer(Game gp, KeyHandler keyH) {
        if(instances == 0) {
            instances++;
            return new Player(gp, keyH);
        }
        return null;
    }
    public void setDefaultValues() {
        setDefaultPosition();
        speed = 4;
        direction = "up";
        maxLife = 6;
        life = maxLife;
        strength = 1;
        attack = 1;
        difficulty = 4;
        inventory.add(new OBJ_KTPY(gp));
    }
    public void setItems() {
    }
    public void getImage() {
        // Moving Images
        up = setup(8, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        up1 = setup(9, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        up2 = setup(10, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down = setup(11, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down1 = setup(12, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        down2 = setup(13, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left = setup(14, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left1 = setup(15, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        left2 = setup(16, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right = setup(17, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right1 = setup(18, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        right2 = setup(19, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);

        // Attack Images
        attackUp1 = setup(0, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        attackUp2 = setup(1, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        attackDown1 = setup(2, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        attackDown2 = setup(3, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        attackLeft1 = setup(4, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        attackLeft2 = setup(5, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        attackRight1 = setup(6, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        attackRight2 = setup(7, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);

        // Armed Images
        weapUp = setup(27, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        weapUp1 = setup(26, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        weapDown = setup(25, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        weapDown1 = setup(24, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        weapLeft = setup(23, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        weapLeft1 = setup(22, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        weapRight = setup(21, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
        weapRight1 = setup(20, 0, "/player/skar_spritesheet", gp.originalTileSize, gp.originalTileSize);
    }
    public void update() {

        if(attacking) {
            attacking();
        }

        // Caracterul se va misca doar cand sunt active tastele de miscare
        // folosindu-ne de acest if
        else if(keyH.ePressed || keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if(keyH.upPressed) {
                direction = "up";

            } else if(keyH.downPressed) {
                direction = "down";

            } else if(keyH.rightPressed) {
                direction = "right";

            } else if(keyH.leftPressed) {
                direction = "left";

            }
            // Check tile collision
            collisionOn = false;
            gp.colChecker.checkTile(this);

            // Check object collision
            int objectIndex = gp.colChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            // Check NPC collision
            int NPCIdx = gp.colChecker.checkEntity(this, gp.NPC);
            intersectNPC(NPCIdx);
            int HEADIdx = gp.colChecker.checkEntity(this, gp.mst);
            intersectNPC(HEADIdx);

            // Check Monster collision
            int monsterIndex = gp.colChecker.checkEntity(this, gp.mst);
            damageFromMonster(monsterIndex);

            // Check Event
            gp.eventH.checkEvent();

            // Jucatorul nu poate "inainta" daca interactioneaza cu un obiect/tile
            // cu coliziune
            if(!collisionOn && !keyH.ePressed) {
                switch(direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            gp.keyH.ePressed = false;

            // Sprite changer
            spriteCounter++;
            if(spriteCounter > 10) {
                if(spriteNumber == 0) {
                    spriteNumber = 1;
                }
                if(spriteNumber == 1) {
                    spriteNumber = 2;
                } else if(spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        } else {
            spriteNumber = 0;
        }
        if(currentWeapon != null)
//            System.out.println(currentWeapon.name + "|" + gp.keyH.shotPressed + "|" + currentWeapon.projectile.alive + "|" + shotCounter + "|" + currentWeapon.projectile.delay + "|" + hasWeapon);
        if(currentWeapon != null && gp.keyH.shotPressed && !currentWeapon.projectile.alive && shotCounter == currentWeapon.projectile.delay && hasWeapon) {
            currentWeapon.projectile.set(worldX, worldY, direction, true, this);

            gp.projectileList.add(currentWeapon.projectile);

            shotCounter = 0;
        }

        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(currentWeapon != oldWeapon){
            oldWeapon = currentWeapon;
            weaponSwitch = true;
            shotCounter = 0;
        }
        if(currentWeapon != null) {
            if(shotCounter < currentWeapon.projectile.delay) {
                shotCounter++;
            }
        }

        if (life <= 0) {
            gp.gameState = gp.deadState;
        }
        if(!canPickup) {
            actionCounter++;
            if(actionCounter == 60) {
                canPickup = true;
                actionCounter = 0;
            }
        }
    }
    public int getAttack() {
        return attack = strength;
    }
    public void teleport(int map, int col, int row) {
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        gp.eventH.previousEventX = gp.player.worldX;
        gp.eventH.previousEventY = gp.player.worldY;
        gp.eventH.canTouchEvent = false;
    }
    public void attacking() {
        spriteCounter++;

        if(spriteCounter <= 12) {
            spriteNumber = 1;
        }
        if(spriteCounter > 12 && spriteCounter <= 24) {
            spriteNumber = 2;

            // Salveaza valoarea curenta
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // update
            switch(direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
            }
            // Area solida devine area de atac
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Verifica daca exista un inamic in zona
            int mstIndex = gp.colChecker.checkEntity(this, gp.mst);
            damageEnemy(mstIndex, attack);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 24 && spriteCounter <= 36) {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void damageEnemy(int i, int attack) {
        if(i != invalidIndex) {
            if(!gp.mst[gp.currentMap][i].invincible && !gp.mst[gp.currentMap][i].dead) {
                gp.playFX(5);
                int damage = attack - gp.mst[gp.currentMap][i].defense;
                if(damage < 0) {
                    damage = 0;
                }
                gp.mst[gp.currentMap][i].life -= damage;

                gp.mst[gp.currentMap][i].invincible = true;
                gp.mst[gp.currentMap][i].dmgReact();

                if(gp.mst[gp.currentMap][i].life <= 0) {
                    gp.mst[gp.currentMap][i].dead = true;
                    gp.playFX(6);
                    gp.levelCounter++;
                }
            }
        }
    }
    public void damageFromMonster(int i) {
        if(i != invalidIndex) {
            if(!invincible) {
                life -= gp.mst[gp.currentMap][i].attack;
                invincible = true;
            }
        }
    }
    public void setDefaultPosition() {
        switch(gp.currentMap) {
            case 0:
                worldX = gp.tileSize * 24;
                worldY = gp.tileSize * 30 - 1;
                break;
            case 1:
                worldX = gp.tileSize * 40;
                worldY = gp.tileSize * 42;
                break;
        }
        direction = "down";
        hasWeapon = false;
    }
    public void restoreLife() {
        life = maxLife;
        invincible = false;
    }
    public void resetInventory() {
        inventory.clear();
        keyNumber = 0;
    }
    public void pickUpObject(int i) {
        if(i != invalidIndex && canPickup) {
            String objectName = gp.obj[gp.currentMap][i].name;

            // Pickup Object
            if(gp.obj[gp.currentMap][i].type == type_pickup) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            } else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
                if(gp.obj[gp.currentMap][i].name.equals("Chest")) {
                    if(gp.keyH.ePressed) {
                        gp.obj[gp.currentMap][i].interact();
                        gp.obj[gp.currentMap][i] = null;
                    }
                } else
                if(gp.obj[gp.currentMap][i].name.equals("Door")) {
                    int oldKeyNumber = keyNumber;
                    gp.obj[gp.currentMap][i].interact();
                    if(gp.player.keyNumber != oldKeyNumber) {
                        gp.obj[gp.currentMap][i] = null;
                        gp.openedDoors++;
                    } else {
                        gp.ui.showMessage("You need a Key.");
                    }
                } else {
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            // Inventory Items
            else {
                String text;
                if(inventory.size() != maxInventorySize && gp.obj[gp.currentMap][i].pickUpable) {
                    inventory.add(gp.obj[gp.currentMap][i]);
                    text = "Picked up a " + objectName + "!";
                    gp.obj[gp.currentMap][i] = null;
                } else {
                    if (inventory.size() == maxInventorySize) {
                        text = "Inventory full!";
                    } else {
                        text = "";
                    }
                }
                gp.ui.showMessage(text);

                // Weapon Switch
                if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].isWeapon && inventory.size() != maxInventorySize) {
                    switch(gp.obj[gp.currentMap][i].name) {
                        case "Electron":
                            currentWeapon = new OBJ_Electron(gp);
                            break;
                        case "KTPY":
                            currentWeapon = new OBJ_KTPY(gp);
                            break;
                        case "Skargun":
                            currentWeapon = new OBJ_Skargun(gp);
                            break;
                        case "Snaipa":
                            currentWeapon = new OBJ_Snaipa(gp);
                            break;
                    }
                    counterGun++;
                    if(!hasWeapon) {
                        gp.ui.showMessage("You picked Skargun!");
                        hasWeapon = true;

                    } else {
                        if(counterGun > 20) {
                            gp.ui.showMessage("You already have a weapon!");
                            counterGun = 0;
                        }
                    }
                }

                switch(objectName) {
                    case "Skargun Chest":
                        counterGun++;
                        if(!hasWeapon) {
                            gp.ui.showMessage("You picked Skargun!");
                            currentWeapon = new OBJ_Skargun(gp);
                            inventory.add(currentWeapon);
                            hasWeapon = true;
                        } else {
                            if(counterGun > 20) {
                                gp.ui.showMessage("You already have a weapon!");
                                counterGun = 0;
                            }
                        }
                        break;
                }
            }

            }
    }
    public void intersectNPC(int i) {
        if(gp.keyH.ePressed) {
            if(i != invalidIndex) {
                gp.gameState = gp.dialogState;
                gp.NPC[gp.currentMap][i].talk();
            } else {
                attacking = true;
            }
        }
        gp.keyH.ePressed = false;

    }
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot();

        if(itemIndex < inventory.size()) {
            oldWeapon = currentWeapon;

            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_weapon) {
                currentWeapon = selectedItem;
                hasWeapon = true;
                attack = getAttack();
            }
            if(selectedItem.type == type_consumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    public void draw(Graphics2D graph2) {
        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(!attacking) {
                    if(hasWeapon) {
                        if(spriteNumber == 0) {
                            image = weapUp;
                        }
                        if(spriteNumber == 1) {
                            image = weapUp1;
                        }
                        if(spriteNumber == 2){
                            image = weapUp;
                        }
                    } else {
                        if (spriteNumber == 0) {
                            image = up;
                        }
                        if (spriteNumber == 1) {
                            image = up1;
                        }
                        if (spriteNumber == 2) {
                            image = up2;
                        }
                    }
                }
                if(attacking) {
                    if(spriteNumber == 0) {
                        spriteNumber = 1;
                    }
                    if(spriteNumber == 1) {
                        image = attackUp1;
                    }
                    if(spriteNumber == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if(!attacking) {
                    if(hasWeapon) {
                        if(spriteNumber == 0) {
                            image = weapDown;
                        }
                        if(spriteNumber == 1) {
                            image = weapDown1;
                        }
                        if(spriteNumber == 2){
                            image = weapDown;
                        }
                    } else {
                        if (spriteNumber == 0) {
                            image = down;
                        }
                        if (spriteNumber == 1) {
                            image = down1;
                        }
                        if (spriteNumber == 2) {
                            image = down2;
                        }
                    }
                }
                if(attacking) {
                    if(spriteNumber == 0) {
                        spriteNumber = 1;
                    }
                    if(spriteNumber == 1) {
                        image = attackDown1;
                    }
                    if(spriteNumber == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if(!attacking) {
                    if(hasWeapon) {
                        if(spriteNumber == 0) {
                            image = weapLeft;
                        }
                        if(spriteNumber == 1) {
                            image = weapLeft1;
                        }
                        if(spriteNumber == 2){
                            image = weapLeft;
                        }
                    } else {
                        if (spriteNumber == 0) {
                            image = left;
                        }
                        if (spriteNumber == 1) {
                            image = left1;
                        }
                        if (spriteNumber == 2) {
                            image = left2;
                        }
                    }
                }
                if(attacking) {
                    if(spriteNumber == 0) {
                        spriteNumber = 1;
                    }
                    if(spriteNumber == 1) {
                        image = attackLeft1;
                    }
                    if(spriteNumber == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if(!attacking) {
                    if(hasWeapon) {
                        if(spriteNumber == 0) {
                            image = weapRight;
                        }
                        if(spriteNumber == 1) {
                            image = weapRight1;
                        }
                        if(spriteNumber == 2){
                            image = weapRight;
                        }
                    } else {
                        if (spriteNumber == 0) {
                            image = right;
                        }
                        if (spriteNumber == 1) {
                            image = right1;
                        }
                        if (spriteNumber == 2) {
                            image = right2;
                        }
                    }
                }
                if(attacking) {
                    if(spriteNumber == 0) {
                        spriteNumber = 1;
                    }
                    if(spriteNumber == 1) {
                        image = attackRight1;
                    }
                    if(spriteNumber == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }
        if(invincible) {
            graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        graph2.drawImage(image, screenX, screenY, null);

        graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Debug
//        graph2.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
//        graph2.setColor(Color.white);
//        graph2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }
}
