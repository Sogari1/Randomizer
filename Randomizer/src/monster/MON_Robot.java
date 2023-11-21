package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import objects.OBJ_Ammo;
import objects.OBJ_Heart;
import objects.OBJ_Shield;

public class MON_Robot extends Entity {
	GamePanel gp;
	public MON_Robot(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_monster;
		name = "Robot";
		speed = 2;
		maxLife = 10;
		life = maxLife;  // Incrementa la vida
		maxAttack = 2;
	    attack = maxAttack;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getImage();

	}
	public void setDefaultMonsterValues() {
		maxLife = 10;
		life = maxLife;
		maxAttack = 2;
	    attack = maxAttack;
    }
	public void getImage() {
		up1 = setup("/monster/robot_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/robot_down_1", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/robot_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/robot_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/robot_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/robot_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/robot_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/robot_down_1", gp.tileSize, gp.tileSize);
	}
	public void checkDrop() {
		int i = new Random().nextInt(100)+1;
		
		if(i >= 70 && i < 80) {
			dropItem(new OBJ_Heart(gp));
		}
		if(i >= 80 && i < 90) {
			dropItem(new OBJ_Ammo(gp));
		}
		if(i >= 90 && i < 100) {
			dropItem(new OBJ_Shield(gp));
		}
	}
	public void setAction() {	
		
		if(onPath == true) {
			
			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
			
			searchPath(goalCol, goalRow);
		}
		else {
			actionLockCounter++;
			
			if(actionLockCounter == 120) {
				Random random = new Random();
				int i = random.nextInt(100)+1;
				
				if(i <= 25) {
					direction = "up";
				}
				if(i > 25 && i <= 50) {
					direction = "down";
				}
				if(i > 50 && i <= 75) {
					direction = "left";
				}
				if(i > 75 && i <= 100) {
					direction = "right";
				}
				actionLockCounter = 0;
			}	
		}
	}	
}