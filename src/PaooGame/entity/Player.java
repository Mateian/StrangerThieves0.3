package PaooGame.entity;

import PaooGame.objects.OBJ_Bullet;
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

    // Position
    public final int screenX;
    public final int screenY;

    // Boolean
    public boolean moving = false;
    public static boolean hasWeapon = false;

    // Counters
    int counterGun = 0;
    public int hasKey = 0;

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
        attack = 1;

        setDefaultValues();
        getImage();
    }

    public static Player CreatePlayer(Game gp, KeyHandler keyH) {
        if(instances == 0) {
            instances++;
            return new Player(gp, keyH);
        }
        return null;
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 24;
        worldY = gp.tileSize * 30 - 1;
        speed = 4;
        direction = "up";
        maxLife = 10;
        life = maxLife;
        projectile = new OBJ_Bullet(gp);
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

        if(gp.keyH.shotPressed && !projectile.alive && shotCounter == 30 && hasWeapon) {
            projectile.set(worldX, worldY, direction, true, this);

            gp.projectileList.add(projectile);

            shotCounter = 0;
        }

        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotCounter < 30) {
            shotCounter++;
        }
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
            if(!gp.mst[i].invincible && !gp.mst[i].dead) {
                gp.playFX(5);

                int damage = attack - gp.mst[i].defense;
                if(damage < 0) {
                    damage = 0;
                }

                gp.mst[i].life -= damage;

                gp.mst[i].invincible = true;
                gp.mst[i].dmgReact();

                if(gp.mst[i].life <= 0) {
                    gp.mst[i].dead = true;
                    gp.playFX(6);
                    gp.lvl1ObjectiveCounter++;
                    if(gp.lvl1ObjectiveCounter == gp.lvl1Objective) {
                        gp.lvl1Completion = true;
                    }
                }
            }
        }
    }

    public void damageFromMonster(int i) {
        if(i != invalidIndex) {
            if(!invincible) {
                life--;
                if (life <= 0) {
                    gp.gameState = gp.deadState;
                }
                invincible = true;
            }
        }
    }
    public void setDefaultPosition() {
        worldX = gp.tileSize * 24;
        worldY = gp.tileSize * 30 - 1;
        direction = "down";
        hasWeapon = false;
    }
    public void restoreLife() {
        life = maxLife;
        invincible = false;
    }
    public void pickUpObject(int i) {
        if(i != invalidIndex) {
            String objectName = gp.obj[i].name;

            switch(objectName) {
                case "Key":
                    gp.playFX(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.playFX(3);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                    } else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Boots":
                    gp.playFX(2);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Speed up!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playFX(4);
                    break;
                case "Skargun":
                    counterGun++;
                        if(!hasWeapon) {
                            System.out.println("You picked Skargun!");

                            hasWeapon = true;
                        } else {
                            if(counterGun > 20) {
                                System.out.println("You already have a weapon!");
                                counterGun = 0;
                            }
                        }

                    break;
            }
        }
    }

    public void intersectNPC(int i) {
        if(gp.keyH.ePressed) {
            if(i != invalidIndex) {
                gp.gameState = gp.dialogState;
                gp.NPC[i].talk();
            } else {
                attacking = true;
            }
        }
        gp.keyH.ePressed = false;
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
