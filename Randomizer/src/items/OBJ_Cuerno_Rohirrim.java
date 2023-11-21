package items;

import java.util.Timer;
import java.util.TimerTask;

import entity.Entity;
import main.GamePanel;

public class OBJ_Cuerno_Rohirrim extends Entity implements Item {
	GamePanel gp;
    public OBJ_Cuerno_Rohirrim(GamePanel gp) {
    	super(gp);
    	this.gp = gp;
    	type = type_consumable;
        name = "Cuerno de \nlos Rohirrim";
        icon = setup("/items/cuerno", gp.tileSize, gp.tileSize);		
        effectDescription = "Tiempo: medio\nVelocidad: +2";
		description = "[" + name + "]";
    }

    public void use(Entity entity) {
    	gp.playSoundEffect(23);
    	entity.speed += 2;
    	effectDuration = 20;
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
	                	entity.speed = 6;
	                    timer.cancel();
	                }
	            }
	        }
	    }, 1000, 1000); // Ejecuta cada segundo
	}
}
