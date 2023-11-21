package items;

import java.util.Timer;
import java.util.TimerTask;

import entity.Entity;
import main.GamePanel;

public class OBJ_Holocron_Sith extends Entity implements Item{
	GamePanel gp;
	public OBJ_Holocron_Sith(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_consumable;
		name = "El Holocron \nSith";
		icon = setup("/items/holocron sith", gp.tileSize, gp.tileSize);
		effectDescription = "Tiempo: corto\nDaño: +10";
		description = "[" + name + "]";
	}
	public void use(Entity entity) {
	    gp.playSoundEffect(24);
		entity.strength += 10;
	    effectDuration = 10;
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
	    }, 1000, 1000); // Ejecuta cada segundo
	    // sonido
	}
}
