package weapons;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_BFG extends Projectile implements Weapon{
	GamePanel gp;
	public OBJ_BFG(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "BFG-9000";
		speed = 3;
		maxLife = 600;
		life = maxLife;
		attack = 100;
		useCost = 10;
		alive = false;
		description = "[" + name + "]\nAMMO: " + useCost + "/10";	
		icon = setup("/weapons/bfg9000", gp.tileSize, gp.tileSize);
		getImage();
	}
	public void getImage() {
		up1 = setup("/shoots/bfg_shoot_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/shoots/bfg_shoot_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/shoots/bfg_shoot_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/shoots/bfg_shoot_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/shoots/bfg_shoot_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/shoots/bfg_shoot_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/shoots/bfg_shoot_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/shoots/bfg_shoot_down_2", gp.tileSize, gp.tileSize);
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
		return 42;  // Índice del sonido de disparo del AWP en tu arreglo de sonidos.
	}

	@Override
	public int getShootHitSoundIndex() {
		return 43;  // Índice del sonido de golpe del AWP en tu arreglo de sonidos.
	}
}
