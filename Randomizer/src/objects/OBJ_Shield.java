package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity{
	
	GamePanel gp;
	public OBJ_Shield(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickupOnly;
		name = "Defensa";	
		down1 = setup("/objects/shield_box", gp.tileSize, gp.tileSize);
		down2 = setup("/objects/shield_box", gp.tileSize, gp.tileSize);
		
		image = setup("/objects/shield_100", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/shield_80", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/shield_60", gp.tileSize, gp.tileSize);
		image4 = setup("/objects/shield_40", gp.tileSize, gp.tileSize);
		image5 = setup("/objects/shield_20", gp.tileSize, gp.tileSize);
		image6 = setup("/objects/shield_0", gp.tileSize, gp.tileSize);
    }
	public void use(Entity entity) {
		//sonido
		gp.playSoundEffect(95);
		entity.defense += 3;
		if(gp.player.defense > 5) {
			gp.player.defense = 5;
		}
	}
}
