package main;

import java.awt.Point;
import java.util.HashSet;
import java.util.Random;

import entity.Entity;
import monster.MON_Murcielago;
import monster.MON_Robot;
import monster.MON_SlimeVerde;
import monster.MON_Zombie;
import monster.MON_test;
import tile.TileManager;
  
public class AssetSetter {

	GamePanel gp;
	TileManager tileManager;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	//AGREGAR OBJECTOS
	public void setObject() {}
	//AGREGAR ENEMIGO
	public void setMonsters(int numberOfMonsters, int[][] mapTileNum) {
	    Random random = new Random();
	    HashSet<Point> occupiedCoordinates = new HashSet<>();
	    int remainingMonsters = numberOfMonsters;
	    boolean hasTestMonster = false;  // Para rastrear si MON_test ya apareció

	    while (remainingMonsters > 0) {
	        int randomCordX, randomCordY;
	        Point coordinates;

	        do {
	            randomCordX = random.nextInt(50); // Rango de 0 a 49
	            randomCordY = random.nextInt(50); // Rango de 0 a 49
	            coordinates = new Point(randomCordX, randomCordY);
	        } while (occupiedCoordinates.contains(coordinates));

	        occupiedCoordinates.add(coordinates);

	        while (mapTileNum[randomCordX][randomCordY] == 1 || mapTileNum[randomCordX][randomCordY] == 2
	                || mapTileNum[randomCordX][randomCordY] == 3 || mapTileNum[randomCordX][randomCordY] == 4
	                	|| mapTileNum[randomCordX][randomCordY] == 5) {
	            randomCordX = random.nextInt(50); // Rango de 0 a 49
	            randomCordY = random.nextInt(50); // Rango de 0 a 49
	        }

	        Entity monster;

	        // Genera un número aleatorio en un rango grande (1-10000)
	        int randomChance = random.nextInt(500) + 1;

	        if (randomChance == 1 && !hasTestMonster) {
	            // Aparece MON_test con probabilidad 1 entre 10000
	            monster = new MON_test(gp);
	            remainingMonsters--;
	            hasTestMonster = true;  // Marcamos que MON_test ya apareció
	        } else {
	            // Elige aleatoriamente un tipo de monstruo
	            int randomMonsterType = random.nextInt(4); // Valores de 0 a 3

	            if (randomMonsterType == 0 && remainingMonsters > 0) {
	                monster = new MON_SlimeVerde(gp);
	                remainingMonsters--;
	            } 
	            else if (randomMonsterType == 1 && remainingMonsters > 0) {
	                monster = new MON_Zombie(gp);
	                remainingMonsters--;
	            } 
	            else if (randomMonsterType == 2 && remainingMonsters > 0) {
	                monster = new MON_Robot(gp);
	                remainingMonsters--;
	            } 
	            else {
	                monster = new MON_Murcielago(gp);
	                remainingMonsters--;
	            }
	        }
	        if (gp.roundCount % 5 == 0) {
                monster.increaseLife(10);
                monster.increaseAttack(2);
            }

	        gp.monster[numberOfMonsters - remainingMonsters] = monster;
	        gp.monster[numberOfMonsters - remainingMonsters].worldX = randomCordX * gp.tileSize;
	        gp.monster[numberOfMonsters - remainingMonsters].worldY = randomCordY * gp.tileSize;
	    }
	}
}
