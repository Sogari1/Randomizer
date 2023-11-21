package weapons;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Nerf extends Projectile implements Weapon{
	GamePanel gp;
	public OBJ_Nerf(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "Pistola Nerf";
		speed = 20;
		maxLife = 20;
		life = maxLife;
		attack = 1;
		useCost = 0;
		alive = false;
		description = "[" + name + "]\nAMMO: " + useCost + "/10";	
		icon = setup("/weapons/nerf", gp.tileSize, gp.tileSize);
		getImage();
	}
	public void getImage() {
		up1 = setup("/shoots/neft_shoot_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/shoots/neft_shoot_up_1", gp.tileSize, gp.tileSize);
		down1 = setup("/shoots/neft_shoot_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/shoots/neft_shoot_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/shoots/neft_shoot_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/shoots/neft_shoot_left_1", gp.tileSize, gp.tileSize);
		right1 = setup("/shoots/neft_shoot_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/shoots/neft_shoot_right_1", gp.tileSize, gp.tileSize);
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
		return 54;  // Índice del sonido de disparo del AWP en tu arreglo de sonidos.
	}

	@Override
	public int getShootHitSoundIndex() {
		return 55;  // Índice del sonido de golpe del AWP en tu arreglo de sonidos.
	}
}