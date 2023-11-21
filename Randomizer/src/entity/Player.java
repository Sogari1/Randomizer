package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import items.Item;
import items.OBJ_Adrenalina;
import items.OBJ_Anillo_Unico;
import items.OBJ_Cuerno_Rohirrim;
import items.OBJ_Holocron_Sith;
import items.OBJ_Kevral;
import items.OBJ_Medic_Bag;
import items.OBJ_Notch;
import items.OBJ_Sandwich;
import items.OBJ_Ubercharge;
import main.GamePanel;
import main.KeyHandler;
import melees.Melee;
import melees.OBJ_Anduril;
import melees.OBJ_Crisol;
import melees.OBJ_Crowbar;
import melees.OBJ_Cuchillo_Mama;
import melees.OBJ_Karambit;
import melees.OBJ_Cuchillo_Mariposa;
import melees.OBJ_Pico_Diamante;
import melees.OBJ_Sable_Laser;
import melees.OBJ_Wrench;
import weapons.OBJ_AWP;
import weapons.OBJ_ArcoYFlecha;
import weapons.OBJ_BFG;
import weapons.OBJ_Blaster;
import weapons.OBJ_EstrellasNinja;
import weapons.OBJ_Fireball;
import weapons.OBJ_GoldenGun;
import weapons.OBJ_Nerf;
import weapons.OBJ_Pew;
import weapons.OBJ_RescueRanger;
import weapons.OBJ_Shotgun;
import weapons.OBJ_Sniper;
import weapons.Weapon;

public class Player extends Entity{
	
	KeyHandler keyH;	
	public final int screenX;
	public final int screenY;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int inventorySize = 3;
	public List<Melee> meleeList = new ArrayList<>();
	public List<Item> itemList = new ArrayList<>();
	public List<Weapon> weaponList = new ArrayList<>();
	
	public int hitSoundIndex;
	public int hitMonsterSoundIndex;
	public int shootSoundIndex;
	public int shootHitSoundIndex;
	
	private Timer randomizertTimer;
	public int randomizerTimer = 20;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		
		meleeList.add(new OBJ_Anduril(gp));
		meleeList.add(new OBJ_Crowbar(gp));
		meleeList.add(new OBJ_Crisol(gp));
		meleeList.add(new OBJ_Cuchillo_Mama(gp));
		meleeList.add(new OBJ_Karambit(gp));
		meleeList.add(new OBJ_Cuchillo_Mariposa(gp));
		meleeList.add(new OBJ_Pico_Diamante(gp));
		meleeList.add(new OBJ_Sable_Laser(gp));
		meleeList.add(new OBJ_Wrench(gp));
		
		itemList.add(new OBJ_Holocron_Sith(gp));
		itemList.add(new OBJ_Adrenalina(gp));
		itemList.add(new OBJ_Cuerno_Rohirrim(gp));
		itemList.add(new OBJ_Medic_Bag(gp));
		itemList.add(new OBJ_Notch(gp));
		itemList.add(new OBJ_Sandwich(gp));
		itemList.add(new OBJ_Ubercharge(gp));
		itemList.add(new OBJ_Anillo_Unico(gp));
		itemList.add(new OBJ_Kevral(gp));
		
		weaponList.add(new OBJ_Fireball(gp));
		weaponList.add(new OBJ_AWP(gp));
		weaponList.add(new OBJ_BFG(gp));
		weaponList.add(new OBJ_ArcoYFlecha(gp));
		weaponList.add(new OBJ_Blaster(gp));
		weaponList.add(new OBJ_EstrellasNinja(gp));
		weaponList.add(new OBJ_GoldenGun(gp));
		weaponList.add(new OBJ_Nerf(gp));
		weaponList.add(new OBJ_Pew(gp));
		weaponList.add(new OBJ_RescueRanger(gp));
		weaponList.add(new OBJ_Shotgun(gp));
		weaponList.add(new OBJ_Sniper(gp));
		
		randomizertTimer = new Timer();
		randomizertTimer.scheduleAtFixedRate(new TimerTask() {
	        @Override
	        public void run() {
	            if (gp.gameState == gp.playState) { // Verifica que el gameState sea igual a playState
	                if (randomizerTimer > 0) {
	                	randomizerTimer--;
	                    System.out.println("Cambio en: " + randomizerTimer);
	                } 
	                else {
	                	randomizeMeleeItemWeapon();  
	                	gp.playSoundEffect(98);		
	                	randomizerTimer = 20;
	                }
	            }
	        }
	    }, 1000, 1000);
       
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		// HITBOX PLAYER
		solidArea = new Rectangle(6, 16, 30, 30);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		setDefaultValues();
		getPlayerImage();
		randomizeMeleeItemWeapon();
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 6;
		direction = "down";
		
		// status player
		maxLife = 20;
		life = maxLife;
		maxAmmo = 10;
		ammo = maxAmmo;
		strength = 0;
		defense = 0;
		
	}
	public void randomizeMeleeItemWeapon() {
	
	    Random randomMelee = new Random();
	    int randomMeleeIndex = randomMelee.nextInt(meleeList.size());
	    currentMelee = (Entity) meleeList.get(randomMeleeIndex); 
	    
	    Random randomWeapon = new Random();
        int randomWeaponIndex = randomWeapon.nextInt(weaponList.size());
        currentWeapon = (Entity) weaponList.get(randomWeaponIndex);
        
        Random randomItem = new Random();
	    int randomItemIndex = randomItem.nextInt(itemList.size());
	    currentItem = (Entity) itemList.get(randomItemIndex);

	    // añadir el randomizar de currentWeapon aqui
	    setItems();
		getPlayerAttackImage();
		attackArea = currentMelee.attackArea;
		attack = currentMelee.attackValue;
		projectile = (Projectile) currentWeapon;	
		
		hitSoundIndex = ((Melee) currentMelee).getHitSoundIndex();
		hitMonsterSoundIndex = ((Melee) currentMelee).getHitMonsterSoundIndex();
		shootSoundIndex = ((Weapon) currentWeapon).getShootSoundIndex();
		shootHitSoundIndex = ((Weapon) currentWeapon).getShootHitSoundIndex();
		
	    System.out.println("Se ha cambiado el arma e ítem.");
	    System.out.println("Arma seleccionada: " + currentMelee);
	    System.out.println("Arma seleccionada: " + currentWeapon);
	    System.out.println("Ítem seleccionado: " + currentItem);
	}

	public void restoreLife() {
		life = maxLife;
		invincible = true;
	}
	public void setItems() {
		inventory.clear();
		inventory.add(currentMelee);
		inventory.add(currentWeapon);
		inventory.add(currentItem);
	}
	public void getPlayerImage() {
		up1 = setup("/player_move//player_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player_move//player_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player_move/player_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player_move/player_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player_move/player_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player_move/player_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player_move/player_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player_move/player_right_2", gp.tileSize, gp.tileSize);
	}	
	public void getPlayerAttackImage() {
		currentMelee.getPlayerAttackImage(this);
    }
	public void update() {
		
		if(attacking == true) {
			attacking();
		}
		//movimiento
		else if(keyH.upPressed == true || keyH.downPressed == true || 
				keyH.leftPressed == true || keyH.rightPressed == true || keyH.attackPressed == true) {
			
			if(keyH.upPressed == true) { direction = "up"; }
			else if(keyH.downPressed == true) { direction = "down"; }
			else if(keyH.leftPressed == true) { direction = "left"; }
			else if(keyH.rightPressed == true) { direction = "right"; }
			if(keyH.attackPressed == true) { gp.playSoundEffect(hitSoundIndex); attacking = true;  }
			
			//COLLISION CON TILE
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// COLLISION CON OBJECTS
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			//MONSTER collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);		
			
			//IF COLLISION ES FALSO, PLAYER PUEDE MOVERSE
			if(collisionOn == false && keyH.attackPressed == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;				
				}
			}
			gp.keyH.attackPressed = false;
			
			// cambio de sprite
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum =1;
				}
				spriteCounter = 0;
			}
		}
		if(gp.keyH.shootPressed == true && projectile.alive == false 
				&& shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
			
			projectile.set(worldX, worldY, direction, true, this);		
			projectile.subtractResource(this);

			gp.projectileList.add(projectile);
			// SONIDO DE DISPARO
			gp.playSoundEffect(shootSoundIndex);
			shotAvailableCounter = 0;
		}
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		if(shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
		if(keyH.god == false) {
			if(life <= 0) {
				gp.gameState = gp.gameOverState;
				randomizerTimer = 20;
				gp.stopMusic();
				gp.playSoundEffect(100);
			}	
		}				
	}
	public void attacking() {	
		spriteCounter++;
		if(spriteCounter <=5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25){
			spriteNum = 2;
			// guarda el worldx, wolrdx y solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//ajusta player's worldX/Y para el attackArea
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;	
			}
			// attackArea se convierte en solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			// checkea la colision del monstruo con worldX/Y y solidArea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack);
					
			monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageShootMonster(monsterIndex, attack);
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
		
	}
	
	public void pickUpObject(int i) {
	    if (i != 999) {  
	    	if(gp.obj[i].type == type_pickupOnly) {
	    		gp.obj[i].use(this);
	    		gp.obj[i] = null;
	    	}
	    }
	}
	public void contactMonster(int i) {
		if (i != 999) {
			
			if(invincible == false && gp.monster[i].dying == false) {
				//gp.playSoundEffect(1); // sonido golpe
				int damage = gp.monster[i].attack - defense;
				if(damage < 0) {
					damage = 0;
				}
				life -= damage;
				if(defense > 0) {
					//sonido reduccion de defensa
					defense -= 1;
					gp.playSoundEffect(97);
				}	
				else {
					// SONIDO DE GOLPEO AL PLAYER
					gp.playSoundEffect(99);	
				}
				invincible = true;
			}						
	    }
	}
	public void damageMonster(int i, int attack) {
	    if(i != 999) {
	        if(gp.monster[i].invincible == false) {
	           //sonido 
	            int damage = attack + strength;
	            if(damage < 0) {
	                damage = 0;
	            }		     
	            System.out.println("Daño melee: " + damage);
	            gp.playSoundEffect(hitMonsterSoundIndex);
	            gp.monster[i].life -= damage;
	            gp.monster[i].invincible = true;
	               
	            if(gp.monster[i].life <= 0) {
	                gp.monster[i].dying = true;
	                gp.aliveMonsters--;
	            } 	            
	        }
	    }	    
	}
	public void damageShootMonster(int i, int attack) {
	    if(i != 999) {
	        if(gp.monster[i].invincible == false) {
	            int damage = attack + strength;
	            if(damage < 0) {
	                damage = 0;
	            }		           
	            System.out.println("Daño disparo: " + damage);
	            gp.playSoundEffect(shootHitSoundIndex);
	            gp.monster[i].life -= damage;
	            gp.monster[i].invincible = true;
	               
	            if(gp.monster[i].life <= 0) {
	                gp.monster[i].dying = true;
	                gp.aliveMonsters--;
	            }           
	        }
	    }	    
	}
	public void useItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_consumable && gp.keyH.onEffect == false) {
				System.out.println(gp.keyH.onEffect);
				selectedItem.use(this);
				inventory.remove(itemIndex);			
			}
			if(gp.keyH.onEffect == true) {
				System.out.println("En efecto");
			}
		}
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		//dibujar segun la direccion
		switch(direction) {
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) { image = up1; }
				if(spriteNum == 2) { image = up2; }
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) { image = attackUp1; }
				if(spriteNum == 2) { image = attackUp2; }
			}		
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) { image = down1; }
				if(spriteNum == 2) { image = down2; }
			}
			if(attacking == true) {
				if(spriteNum == 1) { image = attackDown1; }
				if(spriteNum == 2) { image = attackDown2; }
			}
			break;		
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) { image = left1; }
				if(spriteNum == 2) { image = left2; }
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) { image = attackLeft1; }
				if(spriteNum == 2) { image = attackLeft2; }
			}			
			break;		 
		case "right":
			if(attacking == false) {
				if(spriteNum == 1) { image = right1; }
				if(spriteNum == 2) { image = right2; }
			}
			if(attacking == true) {
				if(spriteNum == 1) { image = attackRight1; }
				if(spriteNum == 2) { image = attackRight2; }
			}		
			break;
		}		
		
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// reset alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}
