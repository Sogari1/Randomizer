package melees;

import entity.Player;

public interface Melee {
	 void getPlayerAttackImage(Player player);
	 int getHitSoundIndex();
	 int getHitMonsterSoundIndex();
}
