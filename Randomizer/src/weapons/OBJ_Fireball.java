package weapons;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile implements Weapon{
	GamePanel gp;
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "Fireball";
		speed = 20;
		maxLife = 35;
		life = maxLife;
		attack = 4;
		useCost = 1;
		alive = false;
		description = "[" + name + "]\nAMMO: " + useCost + "/10";	
		icon = setup("/shoots/fireball_up_1", gp.tileSize, gp.tileSize);
		getImage();
	}
	public void getImage() {
		up1 = setup("/shoots/fireball_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/shoots/fireball_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/shoots/fireball_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/shoots/fireball_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/shoots/fireball_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/shoots/fireball_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/shoots/fireball_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/shoots/fireball_right_2", gp.tileSize, gp.tileSize);
	}
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.ammo >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	public void subtractResource(Entity user) {
		user.ammo -= useCost;
	}
	@Override
	public int getShootSoundIndex() {
		return 44;  // Índice del sonido de disparo del AWP en tu arreglo de sonidos.
	}

	@Override
	public int getShootHitSoundIndex() {
		return 45;  // Índice del sonido de golpe del AWP en tu arreglo de sonidos.
	}
}
