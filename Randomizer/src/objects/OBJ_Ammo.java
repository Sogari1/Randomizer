package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Ammo extends Entity{
	
	GamePanel gp;
	public OBJ_Ammo(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickupOnly;
		name = "Munición";	
		down1 = setup("/objects/ammo_box", gp.tileSize, gp.tileSize);
		down2 = setup("/objects/ammo_box", gp.tileSize, gp.tileSize);
		
		image = setup("/objects/ammo", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/ammo_blank", gp.tileSize, gp.tileSize);
    }
	public void use(Entity entity) {
		//sonido
		gp.playSoundEffect(95);
		entity.ammo += 8;
		if(gp.player.ammo > gp.player.maxAmmo) {
			gp.player.ammo = gp.player.maxAmmo;
		}
	}
}
