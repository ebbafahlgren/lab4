package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable {
	
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;
	private final int INROW = 5;
	
	private int[][] gridList;
	//private int xPos;
	//private int yPos;

	
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		
		gridList = new int[size][size];
		for(int x = 0; x < gridList.length; x++) {
			for(int y = 0; y < gridList.length; y++) {
				gridList[x][y] = EMPTY;
			}
		} 
		//Vad gör denna for-loop?
	}
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return gridList[x][y]; 
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return gridList.length;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player){
		if (getLocation(x, y) == EMPTY) {
			gridList[x][y] = player; 
			setChanged();
			notifyObservers();
			return true; 
		} else {
			return false;
		}
		
	}
	
	/**
	 * Clears the grid of pieces
	 * Using a for-loop to loop through every column and row
	 */
	public void clearGrid() {
		for (int x = 0; x < gridList.length; x++) {
            for (int y = 0; y < gridList.length; y++) {
                gridList[x][y] = EMPTY;
            }
        } //Samma loop här? Behövs den däruppe?
        setChanged();
        notifyObservers();
    }
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		
		if (vertical(player)) {
			return true;
		}
		if (horisontal(player)) {
			return true;
		}
		if (diagonals(player)) {
			return true;
		}
		return false;
	}
	/* Hjälpmetod
	 * Metoden vertical kollar ett x-värde i taget, uppifrån och ned
	 * mha for-loop, för varje x-värde. Är det tillräckligt många i rad
	 * för vinst så är count = INROW och vi returnerar true, annars 0
	 */
	private boolean vertical(int player) {
		int count = 0;
		for (int x = 0; x < gridList.length; x++) {
			for (int y = 0; y < gridList.length; y++) {
				if (getLocation(x, y) == player) {
					count++;
				} else {
					count = 0;
				} 
				//vill ju nollställa här om de ej är i rad
				//före den kollar om det är 5 st
			
				if (count == INROW) {
					return true;
				}
					
//				if (count == INROW) {
//					return true;
//				} else {
//					count = 0;
//				} //Tror ej denna räknar rätt? Den var fel! kollat i GUI..
//				  //nollställer efter varje
			}
		}
		return false;
	}
	/* Hjälpmetod
	 * Metoden horisontal kollar ett y-värde i taget, från vänster till höger, 
	 * mha for-loop, för varje y-värde. Är det tillräckligt många i rad
	 * för vinst, är count = INROW och vi returnerar true.
	 */
	private boolean horisontal(int player) {
		int count = 0;
		for (int y = 0; y < gridList.length; y++) {
			for (int x = 0; x < gridList.length; x++) {
				if (getLocation(x, y) == player) {
					count++;
				} else {
					count = 0;
				}
				
				if (count == INROW) {
					return true;
				} //samma här!
				
//				if (count == INROW) {
//					return true;
//				} else {
//					count = 0;
//				} //inte denna heller.. denna felaktig
			}
		}
		return false;
		
	} 
	
	/*Hjälpmetod som ska hitta diagonalerna både från båda hållen 
	 * Använder återigen oss av nästlade for-loopar för att få tillgång till
	 * rader och kolumner. 
	 */
	private boolean diagonals(int player) {
//		int count = 0;
		for (int x = 4; x < gridList.length; x++) { // itererar rader, botten till topp
			for (int y = 0; y < gridList.length - 4; y++) { // itererar kolumner, vänster till höger
				if (player == EMPTY) { 
		                continue;	// Vi skippar att kolla tomma rutor
				}
//				if (getLocation(x, y) == player) { 
//		            	count++;
//				}
				if (this.gridList[x][y] == player 
						&& this.gridList[x-1][y+1] == player
						&& this.gridList[x-2][y+2] == player 
						&& this.gridList[x-3][y+3] == player 
						&& this.gridList[x-4][y+4] == player) 
					return true;
				}
		}
		for (int x = 4; x < gridList.length; x++) {
			for (int y = 4; y < gridList.length; y++) {
				
				if (this.gridList[x][y] == player 
						&& this.gridList[x-1][y-1] == player
						&& this.gridList[x-2][y-2] == player 
						&& this.gridList[x-3][y-3] == player 
						&& this.gridList[x-4][y-4] == player)
					return true;
			}
		}
		    return false; // ingen vinnare hittades
	}
	
	
}
