package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int originalTileSize = 16; // 16x16
	final int scale = 3; // Multiplica el tamaño del personaje
	
	public final int tileSize = originalTileSize * scale; //64x64
	public final int maxScreenCol = 22; // 16 columnas
	public final int maxScreenRow = 14; // 12 filas
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixeles
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixeles
	
	// SETTINGS MUNDO
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	//FPS
	int FPS = 60;
	
	// SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	public Sound soundEffect = new Sound();
	public Sound music = new Sound();
	public AssetSetter aSetter = new AssetSetter(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public UI ui = new UI(this);
	public PathFinder pFinder = new PathFinder(this);
	Thread gameThread;
	private Random random = new Random();
	// PLAYER Y OBJECT
	public Player player = new Player(this,keyH);
	public Entity obj[] = new Entity[50];
	public Entity monster[] = new Entity[100];
	ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> projectileList = new ArrayList<>();
	// OTHER
	public int numberOfMonsters; // Cambia este valor según tus necesidades
	public int aliveMonsters;
	public long respawnStartTime = 0;
	public int roundCount = 1;
	
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int gameOverState = 3;
	public final int optionsState = 4;
	public final int winState = 5;
	
	public GamePanel(){	
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	public void setupGame() {
		aSetter.setObject();
		numberOfMonsters = 10;
		aliveMonsters = numberOfMonsters;
		aSetter.setMonsters(10, tileM.mapTileNum);
		gameState = titleState;		
	}
	public void retry() {
		ui.gameHourTimer = 0;
		ui.gameMinutesTimer = 0;
		ui.gameSecondsTimer = 0;
		
	    roundCount = 1;
	    numberOfMonsters = 10;
		aliveMonsters = numberOfMonsters;
		
	    player.setDefaultValues();
	    player.restoreLife();
	    player.randomizeMeleeItemWeapon();
	    
	    for (int i = 0; i < obj.length; i++) {
	        obj[i] = null;
	    }
	    for (int i = 0; i < monster.length; i++) {
	    	if (monster[i] != null) {
	            monster[i].setDefaultValues();
	        }
	    	monster[i] = null;
	    }
		aSetter.setMonsters(10, tileM.mapTileNum); 
		playMusic(getRandomMusicId());
	}

	public void restart() {
		ui.gameHourTimer = 0;
		ui.gameMinutesTimer = 0;
		ui.gameSecondsTimer = 0;
		
	    roundCount = 1;
	    numberOfMonsters = 10;
		aliveMonsters = numberOfMonsters;
		
	    player.setDefaultValues();
	    player.restoreLife();
	    player.randomizeMeleeItemWeapon();
	    
	    player.setDefaultValues();
	    player.restoreLife();
	    player.randomizeMeleeItemWeapon();
	    
	    for (int i = 0; i < obj.length; i++) {
	        obj[i] = null;
	    }
	    for (int i = 0; i < monster.length; i++) {
	    	if (monster[i] != null) {
	            monster[i].setDefaultValues();
	        }
	    	monster[i] = null;
	    }
		aSetter.setMonsters(10, tileM.mapTileNum);
	    playMusic(getRandomMusicId()); // Reproduce la música aleatoria
	    stopMusic();
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long tiempoActual;
		long timer = 0;
		
		while(gameThread != null) {
			
			tiempoActual = System.nanoTime();
			
			delta += (tiempoActual - lastTime) / drawInterval;
			timer += (tiempoActual - lastTime);
			lastTime = tiempoActual;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;

			}
			
			if (timer >= 1000000000) {
				timer = 0;
			}			
		}
		
	}
	public void update() {
	
		if(gameState == playState) {
			//player
			player.update();
			// object
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					obj[i].update();
				}
			}
			// monster
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					if(monster[i].alive == true && monster[i].dying == false) {
						monster[i].update();
					}
					if(monster[i].alive == false) {
						monster[i].checkDrop();
						monster[i] = null;
					}
				}
			}
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					if(projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}
					if(projectileList.get(i).alive == false) {
						projectileList.remove(i);
					}
				}
			}
		    if (aliveMonsters == 0) {
			    if (respawnStartTime == 0) {
			        // Inicia el contador
			        respawnStartTime = System.currentTimeMillis();
			    } else {
			        // Verifica si han transcurrido 5 segundos (5000 milisegundos)
			        long currentTime = System.currentTimeMillis();
			        if (currentTime - respawnStartTime >= 6000) {			        
			            // Han transcurrido 5 segundos, ejecuta la función respawnMonsters
			        	respawnMonsters();
			            respawnStartTime = 0;
			        }
			    }
			}
		}
		if(gameState == pauseState) {}		
	}
	public void respawnMonsters() {
		roundCount++;
		numberOfMonsters += 2;
		for (int i = 0; i < obj.length; i++) {
	        obj[i] = null;
	    }
		if(numberOfMonsters >= 80) {
			numberOfMonsters = 80;
		}
		aliveMonsters = numberOfMonsters;
	    if (roundCount < 41) {				    
	        aSetter.setMonsters(numberOfMonsters, tileM.mapTileNum);
	    }

	    if (roundCount == 41) {
	        gameState = winState;
	        stopMusic();
	        playSoundEffect(101);
	    }
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		//debug
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		
		if(gameState == titleState) {
			ui.draw(g2);
		}
		else {
			// TILE
			tileM.draw(g2);
					
			entityList.add(player);
			// OBJECT
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			// MONSTER
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			// PROJECTIL
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			Collections.sort(entityList, new Comparator<Entity>() {
			    @Override
			    public int compare(Entity e1, Entity e2) {
			        if (e1.worldX != e2.worldX) {
			            return Integer.compare(e1.worldX, e2.worldX);
			        } else {
			            return Integer.compare(e1.worldY, e2.worldY);
			        }
			    }
			});

			
			// DIBUJAR ENTIDADES
			for(int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			entityList.clear();
			
			//UI
			ui.draw(g2);
		}
		
		// debug
			if (keyH.checkDrawTime == true) {
				long drawEnd = System.nanoTime();
				long passed = drawEnd - drawStart;
				double passedInSeconds = (double) passed / 1_000_000_000.0;
	
				 // Formatear el número con 6 decimales
				DecimalFormat df = new DecimalFormat("#.######");
				String formattedPassed = df.format(passedInSeconds);
				int x = 10;
				int y = 520;
				int lineHeight = 20;
				
				Font font = new Font("Arial", Font.PLAIN, 20); // Crear la fuente Arial con tamaño 24
			    g2.setFont(font);
				g2.setColor(Color.white);
				g2.drawString("Tiempo de dibujado: " + formattedPassed + " segundos",  x, y); y += lineHeight;
				g2.drawString("GOD: " + keyH.god,  x, y);
			}			
		g2.dispose();
		
	}
	public void playMusic(int i) {		
	    music.setFile(i);
		music.play();
		long duration = music.clip.getMicrosecondLength() / 1000;
	    // Crear un hilo para esperar la duración y luego cambiar la música
	    new Thread(() -> {
	        try {
	            Thread.sleep(duration);
	            playMusic(getRandomMusicId()); // Reproduce la música aleatoria
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }).start();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSoundEffect(int i) {	
		soundEffect.setFile(i);
		soundEffect.play();
	}
	public int getRandomMusicId() {
	    return 70 + random.nextInt(6);
	}
}
