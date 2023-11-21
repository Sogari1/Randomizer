package weapons;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Blaster extends Projectile implements Weapon{
	GamePanel gp;
	public OBJ_Blaster(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "Blaster";
		speed = 27;
		maxLife = 30;
		life = maxLife;
		attack = 6;
		useCost = 2;
		alive = false;
		description = "[" + name + "]\nAMMO: " + useCost + "/10";	
		icon = setup("/weapons/blaster", gp.tileSize, gp.tileSize);
		getImage();
	}
	public void getImage() {
		up1 = setup("/shoots/blaster_shoot_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/shoots/blaster_shoot_up_1", gp.tileSize, gp.tileSize);
		down1 = setup("/shoots/blaster_shoot_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/shoots/blaster_shoot_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/shoots/blaster_shoot_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/shoots/blaster_shoot_left_1", gp.tileSize, gp.tileSize);
		right1 = setup("/shoots/blaster_shoot_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/shoots/blaster_shoot_right_1", gp.tileSize, gp.tileSize);
	}
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.ammo >= useCost) {
			haveResource = true;
		}
		else {
			haveResource = false;
		}
		return haveResource;
	}
	public void subtractResource(Entity user) {
		user.ammo -= useCost;
	}
	@Override
	public int getShootSoundIndex() {
		return 48;  // Índice del sonido de disparo del AWP en tu arreglo de sonidos.
	}

	@Override
	public int getShootHitSoundIndex() {
		return 49;  // Índice del sonido de golpe del AWP en tu arreglo de sonidos.
	}
}
