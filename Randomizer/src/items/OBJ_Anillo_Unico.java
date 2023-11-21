package items;

import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import entity.Entity;
import main.GamePanel;

public class OBJ_Anillo_Unico extends Entity implements Item {
	GamePanel gp;
	Graphics2D g2;
    public OBJ_Anillo_Unico(GamePanel gp) {
    	super(gp);
    	this.gp = gp;
    	type = type_consumable;
        name = "El Anillo \nUnico";
        icon = setup("/items/anillo unico", gp.tileSize, gp.tileSize);
        effectDescription = "Tiempo: medio\nDaño: +20\nPierdes vida";
		description = "[" + name + "]";
    }

    public void use(Entity entity) {
    	gp.playSoundEffect(22);
    	gp.music.stop();
    	entity.strength = 20;
    	effectDuration = 20;
	    Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	            if (gp.gameState == gp.playState) {
	                if (effectDuration > 0) {
	                	effectDuration--;
	                    if(entity.life > 6) {
	                    	entity.life--;
	                    }                     
	                    System.out.println("Duracion: " + effectDuration + gp.keyH.onEffect);
	                } else {
	                	gp.keyH.onEffect = false;
	                	entity.strength = 0;  
	                	gp.music.play();
	                    timer.cancel();
	                }
	            }
	        }
	    }, 1000, 1000); // Ejecuta cada segundo
	}
}