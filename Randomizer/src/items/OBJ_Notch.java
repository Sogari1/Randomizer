package items;

import java.util.Timer;
import java.util.TimerTask;

import entity.Entity;
import main.GamePanel;

public class OBJ_Notch extends Entity implements Item{
	GamePanel gp;
	public OBJ_Notch(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_consumable;
		name = "Manzana de \nNotch";
		icon = setup("/items/manzana_notch", gp.tileSize, gp.tileSize);
		effectDescription = "Tiempo: medio\nVida Ad.: +10\nDaño: +6";
		description = "[" + name + "]";
	}
	public void use(Entity entity) {
		gp.playSoundEffect(21);
		entity.maxLife += 10;
		entity.life += 10;
		entity.strength += 6;
		if (gp.player.life < gp.player.maxLife) {
            gp.player.life = gp.player.maxLife;
        }
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
	                	entity.maxLife = 20;
	            		entity.life = 20;
	            		entity.strength = 0;
	            		gp.keyH.onEffect = false;
	            		if (gp.player.life > gp.player.maxLife) {
	                        gp.player.life = gp.player.maxLife;
	                    }
	                    timer.cancel();
	                }
	            }
	        }
	    }, 1000, 1000); // Ejecuta cada segundo
	    // sonido
	}
}
