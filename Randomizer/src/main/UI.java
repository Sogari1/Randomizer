package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import entity.Entity;
import objects.OBJ_Ammo;
import objects.OBJ_Shield;
import objects.OBJ_Heart;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font Pixel, Arial;
	BufferedImage heart_full, heart_half, heart_blank, ammo_full, ammo_blank, shield_100, shield_80, shield_60, shield_40, shield_20, shield_0;
	public int commandNum = 0;
	public int slotCol = 0;
	public int slotRow = 0;
	public int gameHourTimer = 0;
	public int gameMinutesTimer = 0;
	public int gameSecondsTimer = 0;
	public boolean tutorial = false;
	int subState = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/dogica.ttf");
			Pixel = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/dogicapixelbold.ttf");
			Arial = Font.createFont(Font.TRUETYPE_FONT, is);
		}catch(FontFormatException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}	
		
		// HUD OBJECTO
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;		
		
		Entity ammo = new OBJ_Ammo(gp);
		ammo_full = ammo.image;
		ammo_blank = ammo.image2;
		
		Entity shield = new OBJ_Shield(gp);
		shield_100 = shield.image;
		shield_80 = shield.image2;
		shield_60 = shield.image3;
		shield_40 = shield.image4;
		shield_20 = shield.image5;
		shield_0 = shield.image6;	
	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(Pixel);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
		g2.setColor(Color.white);
		
		//TITULO
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}	
		// JUEGO
		if(gp.gameState == gp.playState) {
		    gameHourTimer++;
		    gameMinutesTimer++;
			gameSecondsTimer++;
			
			drawPlayerStats();
			drawInventory();
			drawTutorial();
			drawItemDescription();
			drawRoundsAndMonster();
		}
		// PAUSA
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		if(gp.gameState == gp.winState) {
			drawWinScreen();
		}
	}
	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(17F));
		int frameX = gp.tileSize*7;
		int frameY = gp.tileSize*2;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize*10;	
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: options_top(frameX,frameY); break;
		case 1: options_endGameConfirmation(frameX,frameY); break;
		}
		
		
	}
	public void options_endGameConfirmation(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		String text = "¿Desea salir del \njuego y volver \nal menu principal?";
		
		for(String line: text.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		//SI 
		text = "SI";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
		}
		//NO
		text = "NO";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
	}
	public void options_top(int frameX, int frameY) {
		int textX;
		int textY;
		
		String text = "Opciones";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		//MUSICA
		textX = frameX + gp.tileSize;
		textY += gp.tileSize*2;
		g2.drawString("Musica", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
		}
		//SE
		textY += gp.tileSize*2;
		g2.drawString("Efectos", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		//SALIR
		textY += gp.tileSize*2;
		g2.drawString("Salir", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				gp.keyH.enterPressed = false;
				subState = 1;
				commandNum = 1;
			}
		}
		
		//MUSICA VOLUMEN
		textX = frameX + gp.tileSize*5;
		textY = frameY + gp.tileSize*2+24;
		g2.drawRect(textX, textY, 120, 24);
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		//SE VOLUMEN
		textY += gp.tileSize*2;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.soundEffect.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
	}
	public void drawWinScreen() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
		String text = "YOU WIN";
		int x = getXforCenteredText(text);
		int y = gp.tileSize*4;

		long currentTime = System.currentTimeMillis(); // Obtener el tiempo actual
	    int colorShift = 0;

	    for (int i = 0; i < text.length(); i++) {
	        char letter = text.charAt(i);
	        String letterStr = String.valueOf(letter);

	        Color rainbowColor = getRainbowColor(currentTime, colorShift);

	        g2.setColor(rainbowColor);
	        g2.drawString(letterStr, x, y);
	        x += g2.getFontMetrics().stringWidth(letterStr);
	        colorShift += 10; // Ajusta este valor para cambiar la velocidad del arco iris
	    }	
	  //TIEMPO	        
	    long hours = gameHourTimer / 216000;
	    long minutes = gameMinutesTimer / 3600;
        long seconds = gameSecondsTimer / 60;      
        g2.setFont(g2.getFont().deriveFont(30f));
	    String finalTimeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	    x = getXforCenteredText(finalTimeString);
	    y += gp.tileSize*3;
        g2.setColor(Color.gray);
		g2.drawString(finalTimeString, x+3, y+3);
		// color principal
		g2.setColor(Color.white);
		g2.drawString(finalTimeString, x, y);	
		
		//RETRY
		g2.setFont(g2.getFont().deriveFont(40f));
		text = "Reintentar";
		x = getXforCenteredText(text);
		y += 80;
		g2.setColor(Color.gray);
		g2.drawString(text, x+3, y+3);
		// color principal
		g2.setColor(Color.white);
		g2.drawString(text, x, y);	
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		//QUIT
		g2.setFont(g2.getFont().deriveFont(40f));
		text = "Salir";
		x = getXforCenteredText(text);
		y += 55;
		g2.setColor(Color.gray);
		g2.drawString(text, x+3, y+3);
		// color principal
		g2.setColor(Color.white);
		g2.drawString(text, x, y);	
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}			

	}
	public void drawRoundsAndMonster() {
	    int x = gp.tileSize;
	    int y = gp.tileSize*12;	
	    // Establece la fuente y el color para el contador de rondas
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
	 // Dibuja el contador de rondas
        g2.setColor(Color.gray);
        g2.drawString("Enemigos restantes: " + gp.aliveMonsters, x + 3, y + 3);
        g2.setColor(Color.white);
        g2.drawString("Enemigos restantes: " + gp.aliveMonsters, x, y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
	    y += gp.tileSize;	
	    // Dibuja el contador de rondas
        g2.setColor(Color.gray);
        g2.drawString("Ronda: " + gp.roundCount, x + 3, y + 3);
        g2.setColor(Color.white);
        g2.drawString("Ronda: " + gp.roundCount, x, y);
	}
	public void drawItemDescription() {
		
	    int x = gp.tileSize * 17 + 30;
	    int y = gp.tileSize * 9 + 30;

	    int itemIndex = getItemIndexOnSlot();

	    if (itemIndex < gp.player.inventory.size() && gp.keyH.onEffect == false) {
	        String[] lines = gp.player.inventory.get(itemIndex).effectDescription.split("\\n");
	      //ITEM EFECTOS
	        for (int i = 0; i < lines.length; i++) {
	            String text = lines[i];
	            
	            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
	            g2.setColor(Color.gray);
	            g2.drawString(text, x + 3, y + 3 - i * 18); // Uso de valor negativo
	            g2.setColor(Color.white);
	            g2.drawString(text, x, y - i * 18); // Uso de valor negativo
	        }
	    }   
	    if(gp.keyH.onEffect == true) {
	    	String text = "ITEM EN USO";
	    	g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
            g2.setColor(Color.gray);
            g2.drawString(text, x + 3, y + 3); // Uso de valor negativo
            g2.setColor(Color.white);
            g2.drawString(text, x, y); // Uso de valor negativo
		}
	}
	public void drawTutorial() {
		
        if (tutorial == true) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15F));
            String text;
            int x;
            int y;
            
            text = "Teclas:";
            x = gp.tileSize;
    		y = gp.tileSize*4;
            g2.setColor(Color.gray);
            g2.drawString(text, x + 3, y + 3);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            text = "Opciones: ESC";
            x = gp.tileSize;
    		y += gp.tileSize;
    		g2.setColor(Color.gray);
    		g2.drawString(text, x + 3, y + 3);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            text = "Movimiento: W, A, S, D";
            x = gp.tileSize;
    		y += gp.tileSize;
    		g2.setColor(Color.gray);
    		g2.drawString(text, x + 3, y + 3);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            text = "Golpear/Disparar: F";
            x = gp.tileSize;
    		y += gp.tileSize;
    		g2.setColor(Color.gray);
    		g2.drawString(text, x + 3, y + 3);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            text = "Inventario: 1, 2, 3";
            x = gp.tileSize;
    		y += gp.tileSize;
    		g2.setColor(Color.gray);
    		g2.drawString(text, x + 3, y + 3);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            text = "Usar Item: ESPACIO";
            x = gp.tileSize;
    		y += gp.tileSize;
    		g2.setColor(Color.gray);
    		g2.drawString(text, x + 3, y + 3);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            
            text = "OCULTAR: I";
            x = gp.tileSize;
    		y += gp.tileSize;
    		g2.setColor(Color.gray);
    		g2.drawString(text, x + 3, y + 3);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
        }

    } 
	public void drawGameOverScreen() {
		
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
		String text = "GAME OVER";
		int x = getXforCenteredText(text);
		int y = gp.tileSize*4;

		long currentTime = System.currentTimeMillis(); // Obtener el tiempo actual
	    int colorShift = 0;

	    for (int i = 0; i < text.length(); i++) {
	        char letter = text.charAt(i);
	        String letterStr = String.valueOf(letter);

	        Color rainbowColor = getRainbowColor(currentTime, colorShift);

	        g2.setColor(rainbowColor);
	        g2.drawString(letterStr, x, y);
	        x += g2.getFontMetrics().stringWidth(letterStr);
	        colorShift += 10; // Ajusta este valor para cambiar la velocidad del arco iris
	    }	
		//TIEMPO	        
	    long hours = gameHourTimer / 216000;
	    long minutes = gameMinutesTimer / 3600;
        long seconds = gameSecondsTimer / 60;      
        g2.setFont(g2.getFont().deriveFont(30f));
	    String finalTimeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	    x = getXforCenteredText(finalTimeString);
	    y += gp.tileSize*3;
        g2.setColor(Color.gray);
		g2.drawString(finalTimeString, x+3, y+3);
		// color principal
		g2.setColor(Color.white);
		g2.drawString(finalTimeString, x, y);	
        
		//RETRY
		g2.setFont(g2.getFont().deriveFont(40f));
		text = "Reintentar";
		x = getXforCenteredText(text);
		y += 80;
		g2.setColor(Color.gray);
		g2.drawString(text, x+3, y+3);
		// color principal
		g2.setColor(Color.white);
		g2.drawString(text, x, y);	
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		//QUIT
		g2.setFont(g2.getFont().deriveFont(40f));
		text = "Salir";
		x = getXforCenteredText(text);
		y += 55;
		g2.setColor(Color.gray);
		g2.drawString(text, x+3, y+3);
		// color principal
		g2.setColor(Color.white);
		g2.drawString(text, x, y);	
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}			
	}
	public void drawInventory() {
		
		int frameX = gp.tileSize*18 + 5;
		int frameY = gp.tileSize*10;
		int frameWidth = gp.tileSize*4 - 8;
		int frameHeight = gp.tileSize*2 - 8;	
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize;
		
		//DRAW PLAYER ITEMS
		for(int i = 0; i < gp.player.inventory.size(); i++) {
	
			g2.drawImage(gp.player.inventory.get(i).icon, slotX, slotY, null);			
			slotX += slotSize;
		}
		
		//CURSOR
		int cursorX = slotXstart + (gp.tileSize * slotCol);
		int cursorY = slotYstart + (gp.tileSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		// DRAW CURSOR
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		// DESCRIPTION FRAME
		int dFrameX = gp.tileSize*16 + 2;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth + 100;
		int dFrameHeight = gp.tileSize * 2;
		
		//DRAW DESCRIPTION TEXT
		int textX = dFrameX + 12;
		int textY = dFrameY + gp.tileSize/2 + 10;
		g2.setFont(g2.getFont().deriveFont(16f));
		
		int itemIndex = getItemIndexOnSlot();
		
		if(itemIndex < gp.player.inventory.size()) {
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
			for(String line: gp.player.inventory.get(itemIndex).description.split("\n")){
				g2.drawString(line, textX, textY);
				textY += 32;
			}		
		}		
	}
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol;
		return itemIndex;
	}
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	public void drawPlayerStats() {
	    int x = gp.tileSize /2;
	    int y = gp.tileSize /2;
	    int i = 0;
	    int heartsPerRow = 10; // Número de corazones por fila
	    int remainingLife = gp.player.life; // Vida restante a dibujar

	    // DIBUJAR CORAZONES
	    while (i < gp.player.maxLife / 2) {
	        if (i % heartsPerRow == 0 && i > 0) {
	            x = gp.tileSize / 2;
	            y += gp.tileSize;
	        }

	        if (remainingLife >= 2) {
	            g2.drawImage(heart_full, x, y, null);
	            remainingLife -= 2;
	        } else if (remainingLife == 1) {
	            g2.drawImage(heart_half, x, y, null);
	            remainingLife -= 1;
	        } else {
	            g2.drawImage(heart_blank, x, y, null);
	        }

	        i++;
	        x += gp.tileSize;
	    }	       
	    // DIBUJAR MAX MUNICION
	    x = gp.tileSize * 11;
	    y = gp.tileSize / 2;
	    i = 0;
	    int ammoPerRow = 5; // Número de municiones por fila

	    while (i < gp.player.maxAmmo) {
	        if (i % ammoPerRow == 0 && i > 0) {
	            x = gp.tileSize * 11;
	            y += gp.tileSize;
	        }

	        g2.drawImage(ammo_blank, x, y, null);
	        i++;
	        x += 35;
	    }

	    // DIBUJAR MUNICION
	    x = gp.tileSize * 11;
	    y = gp.tileSize / 2;
	    i = 0;
	    while (i < gp.player.ammo) {
	    	if (i % ammoPerRow == 0 && i > 0) {
	            x = gp.tileSize * 11;
	            y += gp.tileSize;
	        }
	        g2.drawImage(ammo_full, x, y, null);
	        i++;
	        x += 35;
	    }
	    // DIBUJAR DEFENSA
	    x = gp.tileSize * 15;
	    y = gp.tileSize / 2;
	    i = 0;
	    int defenseLevel = gp.player.defense;

	    switch (defenseLevel) {
	        case 5: g2.drawImage(shield_100, x, y, null); break;
	        case 4: g2.drawImage(shield_80, x, y, null); break;
	        case 3: g2.drawImage(shield_60, x, y, null); break;
	        case 2: g2.drawImage(shield_40, x, y, null); break;
	        case 1: g2.drawImage(shield_20, x, y, null); break;
	        default: g2.drawImage(shield_0, x, y, null); break;
	    }	    
	    //DIBUJAR TIEMPO DE JUEGO   
	    
	    int timerX = gp.tileSize * 17;
	    int timerY = gp.tileSize;
	    long hours = gameHourTimer / 216000;
	    long minutes = gameMinutesTimer / 3600;
        long seconds = gameSecondsTimer / 60;
        if (minutes == 60) {
        	gameMinutesTimer = 0;
        }
        if (seconds == 60) {
        	gameSecondsTimer = 0;
        }
        String timerString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));
	    g2.setColor(Color.gray);
	    g2.drawString(timerString, timerX+3, timerY+3);
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));
	    g2.setColor(Color.white);
	    g2.drawString(timerString, timerX, timerY);
	    
	    //DIBUJAR TIEMPO DE RANDOMIZADOR    
	    String RandomizerString = String.format("RANDOMIZER EN:");
	    
	    int timerRandomizerX = getXforCenteredText(RandomizerString)+ 20;
	    int timerRandomizerY = gp.tileSize * 13;
	    
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
	    g2.setColor(Color.gray);
	    g2.drawString(RandomizerString, timerRandomizerX + 3, timerRandomizerY + 3);
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
	    g2.setColor(Color.white);
	    g2.drawString(RandomizerString, timerRandomizerX, timerRandomizerY);
	    
	    String timerRandomizerString = String.format("%02ds", gp.player.randomizerTimer);
	    
	    timerRandomizerX =  getXforCenteredText(timerRandomizerString);
	    timerRandomizerY += 30;
	    
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
	    g2.setColor(Color.gray);
	    g2.drawString(timerRandomizerString, timerRandomizerX + 3, timerRandomizerY + 3);
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
	    g2.setColor(Color.white);
	    g2.drawString(timerRandomizerString, timerRandomizerX, timerRandomizerY);
	    
	}
	public void drawTitleScreen() {
		//TITULO
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
		String text = "RAND0MIZER";
		int x = getXforCenteredText(text);
		int y = gp.tileSize*5;

		long currentTime = System.currentTimeMillis(); // Obtener el tiempo actual
	    int colorShift = 0;

	    for (int i = 0; i < text.length(); i++) {
	        char letter = text.charAt(i);
	        String letterStr = String.valueOf(letter);

	        Color rainbowColor = getRainbowColor(currentTime, colorShift);

	        g2.setColor(rainbowColor);
	        g2.drawString(letterStr, x, y);
	        x += g2.getFontMetrics().stringWidth(letterStr);
	        colorShift += 10; // Ajusta este valor para cambiar la velocidad del arco iris
	    }
	
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
		text = "Iniciar";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		// sombra
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		// color principal
		g2.setColor(Color.white);
		g2.drawString(text, x, y);	
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
		text = "Salir";
		x = getXforCenteredText(text);
		y += gp.tileSize*2;
		// sombra
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		// color principal
		g2.setColor(Color.white);
		g2.drawString(text, x, y);	
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}		
	}
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
		String text = "PAUSADO";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;		

		g2.setColor(Color.gray);
		g2.drawString(text, x+6, y-94);
		// color principal
		g2.setColor(Color.white);
		g2.drawString(text, x, y-100);	
	}	
	public int getXforCenteredText(String text) {		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;		
	}
	private Color getRainbowColor(long currentTime, int shift) {
	    float hue = (float) (currentTime % 3000) / 3000.0f; // Cambia los colores cada minuto
	    hue = (hue + (shift / 360.0f)) % 1.0f; // Cambia el color gradualmente a lo largo del texto
	    return Color.getHSBColor(hue, 1.0f, 1.0f);
	}
}
