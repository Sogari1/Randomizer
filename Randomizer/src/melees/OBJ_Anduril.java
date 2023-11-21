package melees;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class OBJ_Anduril extends Entity implements Melee{
	GamePanel gp;
	public OBJ_Anduril(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_weapon;
		name = "Andúril";
		icon = setup("/melee/anduril", gp.tileSize, gp.tileSize);
		attackValue = 18;	
		description = "[" + name + "]";		
		attackArea.width = 50;
		attackArea.height = 50;	
		
	}
	@Override
	public void getPlayerAttackImage(Player player) {
		player.attackUp1 = setup("/player_attack/anduril_attack_up_1", gp.tileSize, gp.tileSize*2);
		player.attackUp2 = setup("/player_attack/anduril_attack_up_2", gp.tileSize, gp.tileSize*2);
		player.attackDown1 = setup("/player_attack/anduril_attack_down_1", gp.tileSize, gp.tileSize*2);
		player.attackDown2 = setup("/player_attack/anduril_attack_down_2", gp.tileSize, gp.tileSize*2);
		player.attackLeft1 = setup("/player_attack/anduril_attack_left_1", gp.tileSize*2, gp.tileSize);
		player.attackLeft2 = setup("/player_attack/anduril_attack_left_2", gp.tileSize*2, gp.tileSize);
		player.attackRight1 = setup("/player_attack/anduril_attack_right_1", gp.tileSize*2, gp.tileSize);
		player.attackRight2 = setup("/player_attack/anduril_attack_right_2", gp.tileSize*2, gp.tileSize);
	}
	@Override
	public int getHitSoundIndex() {
		return 3;  // Este sería el índice del sonido de golpe de la clase OBJ_Anduril en tu arreglo de sonidos.
	}
	@Override
	public int getHitMonsterSoundIndex() {
		return 4;  // Este sería el índice del sonido de golpe de la clase OBJ_Anduril en tu arreglo de sonidos.
	}
}
