package items;

import java.util.Timer;
import java.util.TimerTask;

import entity.Entity;
import main.GamePanel;

public class OBJ_Adrenalina extends Entity implements Item{
	GamePanel gp;
    public OBJ_Adrenalina(GamePanel gp) {
    	super(gp);
    	this.gp = gp;
    	type = type_consumable;
        name = "Adrenalina";
		icon = setup("/items/adrenalina", gp.tileSize, gp.tileSize);
		description = "[" + name + "]";
		effectDescription = "Tiempo: corto\nVelocidad: +7";
    }

    public void use(Entity entity) {
    	gp.playSoundEffect(20);
    	entity.speed += 7;
	    effectDuration = 0;
	    Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	            if (gp.gameState == gp.playState) {
	                if (effectDuration > 0) {
	                    effectDuration--;
	                    System.out.println("Duracion:" + effectDuration);
	                } else {
	                	gp.keyH.onEffect = false;
	                	entity.speed = 6;
	                    timer.cancel();
	                }
	            }
	        }
	    }, 1000, 1000); // Ejecuta cada segundo	 
	}
}
