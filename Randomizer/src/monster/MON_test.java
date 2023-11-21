package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_test extends Entity{

	GamePanel gp;
	
	public MON_test(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = 1;
		name = "Mollo";
		speed = 3;
		maxLife = 1;
		life = maxLife;
		attack = 100;
		
	    solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 42;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/monster/test_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/test_1", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/test_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/test_1", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/test_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/test_1", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/test_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/test_1", gp.tileSize, gp.tileSize);
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

