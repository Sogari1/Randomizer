package items;

import java.util.Timer;
import java.util.TimerTask;

import entity.Entity;
import main.GamePanel;

public class OBJ_Ubercharge extends Entity implements Item{
	GamePanel gp;
	public OBJ_Ubercharge(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_consumable;
		name = "Übercharge";
		icon = setup("/items/ubercharge", gp.tileSize, gp.tileSize);
		effectDescription = "Tiempo: largo\nVida Ad.: +30\nDaño: +10\nMunicion: +10\nVelocidad: -2";
		description = "[" + name + "]";
	}
	public void use(Entity entity) {
		gp.playSoundEffect(27);
		entity.maxLife += 30;
		entity.life += 30;
		entity.strength += 10;
		entity.ammo += 10;
		entity.maxAmmo += 10;
		entity.speed -= 2;
		if (gp.player.life < gp.player.maxLife) {
            gp.player.life = gp.player.maxLife;
        }
		if (gp.player.ammo < gp.player.maxAmmo) {
            gp.player.ammo = gp.player.maxAmmo;
        }
		effectDuration = 23;
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
	            		entity.speed = 6;
	            		entity.ammo = 10;
	            		entity.maxAmmo = 10;
	            		gp.keyH.onEffect = false;
	            		if (gp.player.life > gp.player.maxLife) {
	                        gp.player.life = gp.player.maxLife;
	                    }
	            		if (gp.player.ammo > gp.player.maxAmmo) {
	                        gp.player.ammo = gp.player.maxAmmo;
	                    }
	                    timer.cancel();
	                }
	            }
	        }
	    }, 1000, 1000); // Ejecuta cada segundo
	    // sonido
	}
}