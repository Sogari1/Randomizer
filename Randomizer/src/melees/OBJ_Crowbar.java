package melees;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class OBJ_Crowbar extends Entity implements Melee{
	GamePanel gp;
	public OBJ_Crowbar(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_weapon;
		name = "Crowbar";
		icon = setup("/melee/crowbar", gp.tileSize, gp.tileSize);
		attackValue = 14;	 	
		description = "[" + name + "]";	
		//hitbox
		attackArea.width = 30;
		attackArea.height = 30;		
				
	}
	@Override
	public void getPlayerAttackImage(Player player) {
		player.attackUp1 = setup("/player_attack/crowbar_attack_up_1", gp.tileSize, gp.tileSize*2);
		player.attackUp2 = setup("/player_attack/crowbar_attack_up_2", gp.tileSize, gp.tileSize*2);
		player.attackDown1 = setup("/player_attack/crowbar_attack_down_1", gp.tileSize, gp.tileSize*2);
		player.attackDown2 = setup("/player_attack/crowbar_attack_down_2", gp.tileSize, gp.tileSize*2);
		player.attackLeft1 = setup("/player_attack/crowbar_attack_left_1", gp.tileSize*2, gp.tileSize);
		player.attackLeft2 = setup("/player_attack/crowbar_attack_left_2", gp.tileSize*2, gp.tileSize);
		player.attackRight1 = setup("/player_attack/crowbar_attack_right_1", gp.tileSize*2, gp.tileSize);
		player.attackRight2 = setup("/player_attack/crowbar_attack_right_2", gp.tileSize*2, gp.tileSize);
	}
	@Override
	public int getHitSoundIndex() {
		return 1;  // Este sería el índice del sonido de golpe de la clase OBJ_Anduril en tu arreglo de sonidos.
	}
	@Override
	public int getHitMonsterSoundIndex() {
		return 2;  // Este sería el índice del sonido de golpe de la clase OBJ_Anduril en tu arreglo de sonidos.
	}
}
