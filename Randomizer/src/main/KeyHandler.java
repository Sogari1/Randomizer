package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, attackPressed, shootPressed, enterPressed;
	public boolean god = false;
	public boolean onEffect = false;
	//debug
	boolean checkDrawTime = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(gp.gameState == gp.titleState) {
			titleState(code);
		}
		else if(gp.gameState == gp.playState) {		
			playState(code);
		}
		else if(gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		else if(gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
		else if(gp.gameState == gp.optionsState) {
			optionsState(code);
		}
		else if(gp.gameState == gp.winState) {
			winState(code);
		}
	}
	public void optionsState(int code) {
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
			if(gp.ui.subState == 1) {
				if(gp.ui.commandNum == 0) {
					gp.stopMusic();
					gp.restart();
					gp.ui.subState = 0;
					gp.gameState = gp.titleState;
				}
				if(gp.ui.commandNum == 1) {
					enterPressed = false;
					gp.ui.subState = 0;
					gp.ui.commandNum = 2;
				}
			}
		}
		int maxCommandNum = 0;
		switch(gp.ui.subState) {
		case 0: maxCommandNum = 2; break;
		case 1: maxCommandNum = 1; break;
		}
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
			//sonido
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
			//sonido
		}
		if (code == KeyEvent.VK_A) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 0 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
				}
				if(gp.ui.commandNum == 1 && gp.soundEffect.volumeScale > 0) {
					gp.soundEffect.volumeScale--;
				}
			}
		}
		if (code == KeyEvent.VK_D) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 0 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
				}
				if(gp.ui.commandNum == 1 && gp.soundEffect.volumeScale < 5) {
					gp.soundEffect.volumeScale++;
				}
			}		
		}				
	}
	public void titleState(int code) {
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
		}
		if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.ui.tutorial = true;
				gp.playMusic(gp.getRandomMusicId());
			}
			if(gp.ui.commandNum == 1) {
				System.exit(0);
			}
		}	
	}
	public void playState(int code) {
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_F) {
		    if (gp.ui.slotCol == 0) {
		        attackPressed = true;
		    }
		    if (gp.ui.slotCol == 1) {
		    	shootPressed = true;
		    }
		}
		if(code == KeyEvent.VK_SPACE) {
			if (gp.ui.slotCol == 2) {
				gp.player.useItem();	
				onEffect = true;
		    }		
		}
		if(code == KeyEvent.VK_1) {
			gp.ui.slotCol = 0;
		}
		if(code == KeyEvent.VK_2) {
			gp.ui.slotCol = 1;
		}
		if(code == KeyEvent.VK_3) {	
			gp.ui.slotCol = 2;
		}		
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
			gp.stopMusic();
		}
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionsState;
		}
		if (code == KeyEvent.VK_I) {
            if (gp.ui.tutorial == true) {
                gp.ui.tutorial = false;
            }
            else if (gp.ui.tutorial == false) {
                gp.ui.tutorial = true;
            }
        } 		
		//DEBUG
		if (code == KeyEvent.VK_F5) {
			if(checkDrawTime == false) {
				checkDrawTime = true;
			}
			else if(checkDrawTime == true) {
				checkDrawTime = false;
			}
		}
		//GOD
		if (code == KeyEvent.VK_F9) {
			if(god == false) {
				god = true;
			}
			else if(god == true) {
				god = false;
				if (gp.player.life < gp.player.maxLife) {
                    gp.player.life = gp.player.maxLife;
                }
			}
		}
	}
	public void pauseState(int code) {
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
			gp.music.play();
		}
	}
	public void gameOverState(int code) {
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
			//sonido
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			//sonido
		}
		if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
			if(gp.ui.commandNum == 0) {
				gp.retry();
				gp.gameState = gp.playState;
			}
			//sonido
			if(gp.ui.commandNum == 1) {
				gp.restart();
				gp.gameState = gp.titleState;
			}
			//sonido
		}
	}
	public void winState(int code) {
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
			//sonido
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			//sonido
		}
		if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.retry();
			}
			//sonido
			if(gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.restart();
			}
			//sonido
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}	
		if (code == KeyEvent.VK_F) {
			shootPressed = false;
		}
	}
}
