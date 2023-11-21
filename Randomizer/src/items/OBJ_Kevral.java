package items;

import java.util.Timer;
import java.util.TimerTask;

import entity.Entity;
import main.GamePanel;

public class OBJ_Kevral extends Entity implements Item {
	GamePanel gp;
    public OBJ_Kevral(GamePanel gp) {
    	super(gp);
    	this.gp = gp;
    	type = type_consumable;
        name = "Kevral y \nCasco";
		icon = setup("/objects/shield_box", gp.tileSize, gp.tileSize);
		effectDescription = "Defensa: +5";
		description = "[" + name + "]";
    }

    public void use(Entity entity) {
    	gp.playSoundEffect(95);
    	entity.defense += 5;
    	if(entity.defense > 5) {
    		entity.defense = 5;
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
	}	
}
