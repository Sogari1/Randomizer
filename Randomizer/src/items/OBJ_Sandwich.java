package items;

import java.util.Timer;
import java.util.TimerTask;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sandwich extends Entity implements Item{
	GamePanel gp;
	public OBJ_Sandwich(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_consumable;
		name = "Sandwich";
		icon = setup("/items/sanguche", gp.tileSize, gp.tileSize);
		effectDescription = "Tiempo: corto\nVelocidad: +1\nVida Ad.: +6";
		description = "[" + name + "]";
	}
	public void use(Entity entity) {
		gp.playSoundEffect(26);
	    entity.maxLife += 6;
	    entity.life += 6;
	    entity.speed += 1;
	    if (gp.player.life < gp.player.maxLife) {
	        gp.player.life = gp.player.maxLife;
	    }
	    effectDuration = 15;
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
	                    entity.speed = 6;
	                    gp.keyH.onEffect = false;
	                    if (gp.player.life < gp.player.maxLife) {
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
