package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Sound;
import main.UtilityTool;

public class Entity {

	GamePanel gp;
	public void setDefaultValues() {}
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage icon;
	public BufferedImage shootAttackUp1, shootAttackUp2, shootAttackDown1, shootAttackDown2, shootAttackLeft1, shootAttackLeft2, shootAttackRight1, shootAttackRight2;
	public BufferedImage image, image2, image3, image4, image5, image6;
	public Rectangle solidArea = new Rectangle(6, 16, 30, 28);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;
	protected Sound hitSound;
	
	// STATE
	public String direction = "down";
	public int worldX, worldY;
	public int spriteNum = 1;
	public boolean collisionOn = false;
	public boolean invincible = false;
	boolean attacking = false;
	boolean shooting = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
	public boolean onPath = true;
	public boolean invisibleEffect = false;
	public int changeDirection1 = 0;
	public int changeDirection2 = 0;
	public int changeDirection3 = 0;
	public int changeDirection4 = 0;
	public float currentAlpha = 1.0f;
	
	// COUNTER
	public int spriteCounter = 0;
	public int actionLockCounter = 0;	
	public int invincibleCounter = 0;
	int dyingCounter = 0;
	int hpBarCounter = 0;
	public int effectDuration = 0;
	public int shotAvailableCounter = 0;
	
	// ENTITY ATRIBUTOS
	public String name;
	public int maxLife;
	public int life;
	public int speed;
	public int attack;
	public int maxAttack;
	public int defense;
	public int strength;
	public int maxAmmo;
	public int ammo;
	public Entity currentMelee;
	public Entity currentItem;
	public Entity currentWeapon;
	public Projectile projectile;
	
	//ITEM ATRIBUTOS
	public int attackValue;
	public String description = "";
	public String effectDescription = "";
	public int useCost;
	
	// TYPES
	public int type;
	public int type_id;
	public final int type_monster = 1;
	public final int type_consumable = 2;
	public final int type_melee = 3;
	public final int type_weapon = 4;
	public final int type_pickupOnly = 5;
		
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public void use(Entity entity) {}
	public void setAction() {}
	public void checkDrop() {}
	public void dropItem(Entity droppedItem) {
		for(int i = 0; i < gp.obj.length; i++) {
			if(gp.obj[i] == null) {
				gp.obj[i] = droppedItem;
				gp.obj[i].worldX = worldX;
				gp.obj[i].worldY = worldY;
				break;
			}
		}
	}
	public void checkCollision() {
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.monster); 
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if(this.type == type_monster && contactPlayer == true) {
			if(gp.player.invincible == false) {
				// gp.playSoundEffect(1); // monstruo golpe
				int damage = attack - gp.player.defense;
				if(damage < 0) {
					damage = 0;
				}
				
				gp.player.life -= damage;
				if(gp.player.defense > 0) {
					//sonido reduccion de defensa
					gp.player.defense -= 1;
					gp.playSoundEffect(97);
				}	
				else {
					gp.playSoundEffect(99);
				}
				gp.player.invincible = true;
			}
		}
	}
	public void update() {
		
		setAction();
		checkCollision();
		if(collisionOn == false) {
			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;				
			}
		}
		
		// cambio de sprite
		spriteCounter++;
		if(spriteCounter > 13) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum =1;
			}
			spriteCounter = 0;
		}
		// invensibilidad cuando se recibe un golpe
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 20) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(	worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			switch(direction) {
			case "up":
				if(spriteNum == 1) { image = up1; }
				if(spriteNum == 2) { image = up2; } 
				break;
			case "down":
				if(spriteNum == 1) { image = down1; }
				if(spriteNum == 2) { image = down2; }
				break;		
			case "left":
				if(spriteNum == 1) { image = left1; }
				if(spriteNum == 2) { image = left2; }	
				break;		 
			case "right":
				if(spriteNum == 1) { image = right1; }
				if(spriteNum == 2) { image = right2; }
				break;
			}
			
			// HP bar
			if(type == type_monster && hpBarOn == true) {
				double oneScale = (double)gp.tileSize/maxLife;
				double hpBarValue = oneScale*life;
				
				 if (hpBarValue < 0) {
				        hpBarValue = 0;
				    }
				
				g2.setColor(new Color(35, 35, 35));
				g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
				
				g2.setColor(new Color(255, 0, 30));
				g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
				
				hpBarCounter++;
				
				// oculta la barra de vida
				if(hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = true;
				}
			}
						
			if(invincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.5f);
			}				
			if(dying == true) {			
				dyingAnimation(g2);
			}
			if(invisibleEffect == true) {
				changeAlpha(g2, 0.3f);
			}
			g2.drawImage(image, screenX, screenY, null);
			changeAlpha(g2, 1f);
		}
	}
	public void dyingAnimation(Graphics2D g2) {
		
		dyingCounter++;
		int i = 5;
		
		if(dyingCounter <= i) { changeAlpha(g2, 0f); }
		if(dyingCounter > i && dyingCounter <= i*2) { changeAlpha(g2, 1f); }
		if(dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2, 0f); }
		if(dyingCounter > i*3 && dyingCounter <= i*4) { changeAlpha(g2, 1f);}
		if(dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(g2, 0f);}
		if(dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(g2, 1f);}
		if(dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(g2, 0f);}
		if(dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(g2, 1f);}
		if(dyingCounter > i*8) {
			alive = false;
		}	
	}
	public void changeAlpha(Graphics2D g2, float alphaValue) {
	        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	public BufferedImage setup(String imagePath, int width, int height) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public void searchPath(int goalCol, int goalRow) {
		
		int startCol = (worldX+solidArea.x)/gp.tileSize;
		int startRow = (worldY+solidArea.y)/gp.tileSize;
		
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		if(gp.pFinder.search() == true) {
			
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
			
			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x + solidArea.width;
			int enTopY = worldY + solidArea.y;
			int enBottomY = worldY + solidArea.y + solidArea.height;
			
			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "up";
			}
			else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "down";
			}
			else if(enTopY >= nextY && enBottomY < nextY +gp.tileSize) {
				if(enLeftX > nextX) {
					direction = "left";
				}
				if(enLeftX < nextX) {
					direction = "right";
				}
			}
			
			else if(enTopY > nextY && enLeftX > nextX) {
				direction = "up";
				checkCollision();
				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if(enTopY > nextY && enLeftX < nextX) {
				direction = "up";
				checkCollision();
				if(collisionOn == true) {
					direction = "right";
				}
			}
			else if(enTopY < nextY && enLeftX > nextX) {
				direction = "down";
				checkCollision();
				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if(enTopY < nextY && enLeftX < nextX) {
				direction = "down";
				checkCollision();
				if(collisionOn == true) {
					direction = "right";
				}
			}
		}
	}
	public void getPlayerAttackImage(Player player) {}
	public void getPlayerAttackShootImage(Player player) {}
	public void increaseLife(int amount) {
		maxLife += amount;
		life = maxLife;
	}
	public void increaseAttack(int amount) {
		maxAttack += amount;
		attack = maxAttack;
	}
}
