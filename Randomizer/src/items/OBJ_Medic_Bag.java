package items;

import java.util.Timer;
import java.util.TimerTask;

import entity.Entity;
import main.GamePanel;

public class OBJ_Medic_Bag extends Entity implements Item{
	GamePanel gp;
	public OBJ_Medic_Bag(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_consumable;
		name = "Medic Bag";
		icon = setup("/objects/hearth_box", gp.tileSize, gp.tileSize);
		effectDescription = "Vida: +100";
		description = "[" + name + "]";
	}
	public void use(Entity entity) {
		gp.playSoundEffect(25);
		if(gp.player.life < gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		effectDuration = 0;
	    Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	            if (gp.gameState == gp.playState) {
	                if (effectDuration > 0) {
	                    effectDuration--;
	                    System.out.println("Duracion:" + effectDuration + gp.keyH.onEffect);
	                } else {            
	                	gp.keyH.onEffect = false;
	                	entity.strength = 0;
	                    timer.cancel();
	                }
	            }
	        }
	    }, 1000, 1000);
		// sonid	
	}
}
