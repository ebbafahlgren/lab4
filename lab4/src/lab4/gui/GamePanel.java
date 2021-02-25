package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{

	private final int UNIT_SIZE = 20; //rutorna är 20*20.. 
	private GameGrid grid;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y){
		return new int[] {x/UNIT_SIZE, y/UNIT_SIZE};
		
	}
	//UNIT_SIZE för att konvertera till rutor.. 
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.gray);
		g.fillRect(0, 0, grid.getSize()*UNIT_SIZE, grid.getSize()*UNIT_SIZE);
	
		g.setColor(Color.black);
		for(int x = 0; x< grid.getSize(); x++) {
			for (int y = 0; y < grid.getSize(); y++) {
				g.drawRect(x*UNIT_SIZE, y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE); //först koordinater justerat i UNIT_SIZE, sen bredd, höjd
			}
		}
		
		//Rita spelarnas positioner med runda prickar i olika färg
		
		for(int x = 0; x<grid.getSize(); x++) {
			for(int y = 0; y< grid.getSize(); y++) {
				
				if(grid.getLocation(x, y) == GameGrid.ME) { //ME har färgen cyan
					g.setColor(Color.CYAN);
					g.fillOval(x*UNIT_SIZE, y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				}
				
				if(grid.getLocation(x, y)== GameGrid.OTHER) {
					g.setColor(Color.ORANGE);
					g.fillOval(x*UNIT_SIZE, y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				}
			}
		}
		this.repaint();
	}
	
}
