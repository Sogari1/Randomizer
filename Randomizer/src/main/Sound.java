package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Sound {
	GamePanel gp;
	public Clip clip;
	URL soundURL[] = new URL[110];
	FloatControl fc;
	int volumeScale = 3;
	float volume;
		
	public Sound() {
		//MELEE
		soundURL[1] = getClass().getResource("/seMelee/Crowbar.wav");
		soundURL[2] = getClass().getResource("/seMelee/Crowbar Hit.wav");
		soundURL[3] = getClass().getResource("/seMelee/Andúril.wav");
		soundURL[4] = getClass().getResource("/seMelee/Andúril Hit.wav");
		soundURL[5] = getClass().getResource("/seMelee/Crisol.wav");
		soundURL[6] = getClass().getResource("/seMelee/Crisol Hit.wav");
		soundURL[7] = getClass().getResource("/seMelee/Cuchillo de mamá.wav");
		soundURL[8] = getClass().getResource("/seMelee/Cuchillo de mamá Hit.wav");
		soundURL[9] = getClass().getResource("/seMelee/Cuchillo mariposa.wav");
		soundURL[10] = getClass().getResource("/seMelee/Cuchillo mariposa Hit.wav");
		soundURL[11] = getClass().getResource("/seMelee/Karambit.wav");
		soundURL[12] = getClass().getResource("/seMelee/Karambit Hit.wav");
		soundURL[13] = getClass().getResource("/seMelee/Pico de diamante.wav");
		soundURL[14] = getClass().getResource("/seMelee/Pico de diamante Hit.wav");
		soundURL[15] = getClass().getResource("/seMelee/Sable Laser.wav");
		soundURL[16] = getClass().getResource("/seMelee/Sable Laser Hit.wav");
		soundURL[17] = getClass().getResource("/seMelee/Wrench.wav");
		soundURL[18] = getClass().getResource("/seMelee/Wrench Hit.wav");
		
		//OBJECTOS
		soundURL[20] = getClass().getResource("/seItem/adrenalina.wav");
		soundURL[21] = getClass().getResource("/seItem/Notch.wav");
		soundURL[22] = getClass().getResource("/seItem/anillo unico.wav");
		soundURL[23] = getClass().getResource("/seItem/cuerno.wav");
		soundURL[24] = getClass().getResource("/seItem/Holocron.wav");
		soundURL[25] = getClass().getResource("/seItem/MedicBag.wav");
		soundURL[26] = getClass().getResource("/seItem/nomnom.wav");
		soundURL[27] = getClass().getResource("/seItem/Ubercharg.wav");
		
		//WEAPONS
		soundURL[40] = getClass().getResource("/seWeapons/AWP.wav");
		soundURL[41] = getClass().getResource("/seWeapons/AWP Hit.wav");
		soundURL[42] = getClass().getResource("/seWeapons/BFG.wav");
		soundURL[43] = getClass().getResource("/seWeapons/BFGHit.wav");
		soundURL[44] = getClass().getResource("/seWeapons/Fireball.wav");
		soundURL[45] = getClass().getResource("/seWeapons/FireballHit.wav");
		soundURL[46] = getClass().getResource("/seWeapons/Arco.wav");
		soundURL[47] = getClass().getResource("/seWeapons/Arco Hit.wav");
		soundURL[48] = getClass().getResource("/seWeapons/Blaster.wav");
		soundURL[49] = getClass().getResource("/seWeapons/Blaster Hit.wav");
		soundURL[50] = getClass().getResource("/seWeapons/EstrellaNinja.wav");
		soundURL[51] = getClass().getResource("/seWeapons/EstrellaNinja Hit.wav");
		soundURL[52] = getClass().getResource("/seWeapons/GoldenGun.wav");
		soundURL[53] = getClass().getResource("/seWeapons/GoldenGun Hit.wav");
		soundURL[54] = getClass().getResource("/seWeapons/Nerf.wav");
		soundURL[55] = getClass().getResource("/seWeapons/Nerf Hit.wav");
		soundURL[56] = getClass().getResource("/seWeapons/Pew.wav");
		soundURL[57] = getClass().getResource("/seWeapons/Pew Hit.wav");
		soundURL[58] = getClass().getResource("/seWeapons/RescueRanger.wav");
		soundURL[59] = getClass().getResource("/seWeapons/RescueRanger Hit.wav");
		soundURL[60] = getClass().getResource("/seWeapons/Escopeta.wav");
		soundURL[61] = getClass().getResource("/seWeapons/Escopeta Hit.wav");
		soundURL[62] = getClass().getResource("/seWeapons/Sniper.wav");
		soundURL[63] = getClass().getResource("/seWeapons/Sniper Hit.wav");
		
		//MUSICA
		soundURL[70] = getClass().getResource("/music/ROBOTS!.wav"); // musica pelea	
		soundURL[71] = getClass().getResource("/music/Meathook.wav"); 
		soundURL[72] = getClass().getResource("/music/Darktide.wav"); // musica pelea	
		soundURL[73] = getClass().getResource("/music/Rip & Tear.wav"); 
		soundURL[74] = getClass().getResource("/music/BFG Division.wav"); // musica pelea	
		soundURL[75] = getClass().getResource("/music/The Only Thing They Fear Is You.wav"); 
		
		// OTHERS			
		soundURL[95] = getClass().getResource("/sound/ammo pickup.wav");
		soundURL[96] = getClass().getResource("/sound/medickit.wav");
		soundURL[97] = getClass().getResource("/sound/shield break.wav");
		soundURL[98] = getClass().getResource("/sound/Randomizer.wav");
		soundURL[99] = getClass().getResource("/sound/recivedamage.wav");
		soundURL[100] = getClass().getResource("/sound/Game Over.wav");
		soundURL[101] = getClass().getResource("/sound/winner.wav");
	}
	
	public void setFile(int i) {
        try {
        	AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
        	clip = AudioSystem.getClip();
        	clip.open(ais);
        	fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        	checkVolume();
        	clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        // La reproducción llegó al final, obtener una nueva música aleatoria
                        int randomMusicId = gp.getRandomMusicId();
                        setFile(randomMusicId);
                        play();
                        loop();
                    }
                }
            });
        } catch (Exception e) {
        }
    }
	public void play() {
		clip.start();
	}
	public void loop() {
	    clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
    	clip.stop();
    }
	public void checkVolume() {
		switch(volumeScale) {
		case 0: volume = -60f; break;
		case 1: volume = -18f; break;
		case 2: volume = -12f; break;
		case 3: volume = -6f; break;
		case 4: volume = 0f; break;
		case 5: volume = 6f; break;
		}
		fc.setValue(volume);
	}
}
