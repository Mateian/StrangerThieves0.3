package PaooGame.entity;

import PaooGame.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC_Fen extends Entity {

    public int spriteNumber = 1;

    public NPC_Fen(Game gp) {
        super(gp);

        direction = "down";
        speed = 1;
        solidArea.x = 0;
        solidArea.y = 20;
        solidArea.width = 48;
        solidArea.height = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

                setDialog();
        getImage();
    }

    public void getImage() {
        up = left = right = down = setup(2, 0, "/npc/fen_spritesheet", gp.originalTileSize, gp.originalTileSize);
        up1 = left1 = right1 = down1 = setup(3, 0, "/npc/fen_spritesheet", gp.originalTileSize, gp.originalTileSize);
        up2 = left2 = right2 = down2 = setup(2, 0, "/npc/fen_spritesheet", gp.originalTileSize, gp.originalTileSize);

//        Test pentru miscarea NPC-ului
//        up = setup("/player/skar_back");
//        up1 = setup("/player/skar_back_1");
//        up2 = setup("/player/skar_back_2");
//        down = setup("/player/skar_front");
//        down1 = setup("/player/skar_front_1");
//        down2 = setup("/player/skar_front_2");
//        left = setup("/player/skar_left");
//        left1 = setup("/player/skar_left_1");
//        left2 = setup("/player/skar_left_2");
//        right = setup("/player/skar_right");
//        right1 = setup("/player/skar_right_1");
//        right2 = setup("/player/skar_right_2");
    }

//        Test pentru miscare NPC
//        public void setAction() {
//        actionCounter++;
//
//        if(actionCounter == 120) {
//            Random random = new Random();
//            int i = random.nextInt(100) + 1;
//            System.out.println(i);
//            if(i <= 25) {
//                direction = "up";
//            } else if(i > 25 && i <= 50) {
//                direction = "left";
//            } else if(i > 50 && i <= 75) {
//                direction = "right";
//            } else if(i > 75 && i <= 100) {
//                direction = "down";
//            }
//            actionCounter = 0;
//        }
//    }

    public void update() {
//        Test pentru miscare NPC
//        setAction();

//        // Collision
//        collisionOn = false;
//        gp.cChecker.checkTile(this);
//        gp.cChecker.checkObject(this, false);
//        gp.cChecker.checkPlayer(this);
//        if(!collisionOn) {
//            switch(direction) {
//                case "up":
//                    worldy -= speed;
//                    break;
//                case "down":
//                    worldy += speed;
//                    break;
//                case "left":
//                    worldx -= speed;
//                    break;
//                case "right":
//                    worldx += speed;
//                    break;
//            }
//        }

        // Sprite changer
        spriteCounter++;
        if (spriteCounter > 30) {
            if (spriteNumber == 0) {
                spriteNumber = 1;
            }
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D graph2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX -gp.tileSize < gp.player.worldX + gp.player.screenX && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            switch(direction) {
                case "up":
                    if(spriteNumber == 0) {
                        image = up;
                    }
                    if(spriteNumber == 1) {
                        image = up1;
                    }
                    if(spriteNumber == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if(spriteNumber == 0) {
                        image = down;
                    }
                    if(spriteNumber == 1) {
                        image = down1;
                    }
                    if(spriteNumber == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if(spriteNumber == 0) {
                        image = left;
                    }
                    if(spriteNumber == 1) {
                        image = left1;
                    }
                    if(spriteNumber == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNumber == 0) {
                        image = right;
                    }
                    if(spriteNumber == 1) {
                        image = right1;
                    }
                    if(spriteNumber == 2) {
                        image = right2;
                    }
                    break;
            }
            graph2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public void setDialog() {
        dialogs[0] = "Skaar! ... Skar!";
        dialogs[1] = "Ei se apropie!";
        dialogs[2] = "Skar, te rog, salveaza-ma!";
    }

    public void talk() {
        super.talk();
    }
}
