package melees;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class OBJ_Sable_Laser extends Entity implements Melee{
	GamePanel gp;
	public OBJ_Sable_Laser(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_weapon;
		name = "Sable Laser";
		icon = setup("/melee/sable", gp.tileSize, gp.tileSize);
		attackValue = 12;		
		description = "[" + name + "]";	
		//hitbox
		attackArea.width = 40;
		attackArea.height = 40;		
				
	}
	@Override
	public void getPlayerAttackImage(Player player) {
		player.attackUp1 = setup("/player_attack/sable laser_attack_up_1", gp.tileSize, gp.tileSize*2);
		player.attackUp2 = setup("/player_attack/sable laser_attack_up_2", gp.tileSize, gp.tileSize*2);
		player.attackDown1 = setup("/player_attack/sable laser_attack_down_1", gp.tileSize, gp.tileSize*2);
		player.attackDown2 = setup("/player_attack/sable laser_attack_down_2", gp.tileSize, gp.tileSize*2);
		player.attackLeft1 = setup("/player_attack/sable laser_attack_left_1", gp.tileSize*2, gp.tileSize);
		player.attackLeft2 = setup("/player_attack/sable laser_attack_left_2", gp.tileSize*2, gp.tileSize);
		player.attackRight1 = setup("/player_attack/sable laser_attack_right_1", gp.tileSize*2, gp.tileSize);
		player.attackRight2 = setup("/player_attack/sable laser_attack_right_2", gp.tileSize*2, gp.tileSize);
	}
	@Override
	public int getHitSoundIndex() {
		return 15;  // Este sería el índice del sonido de golpe de la clase OBJ_Anduril en tu arreglo de sonidos.
	}
	@Override
	public int getHitMonsterSoundIndex() {
		return 16;  // Este sería el índice del sonido de golpe de la clase OBJ_Anduril en tu arreglo de sonidos.
	}
}
