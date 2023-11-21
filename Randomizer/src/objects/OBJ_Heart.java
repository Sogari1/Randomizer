package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{
	GamePanel gp;
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickupOnly;
		name = "Corazon";	
		down1 = setup("/objects/hearth_box", gp.tileSize, gp.tileSize);
		down2 = setup("/objects/hearth_box", gp.tileSize, gp.tileSize);
		
		image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);
    }
	public void use(Entity entity) {
		//sonido
		gp.playSoundEffect(96);
		entity.life += 6;
		if(gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
	}
	
}
