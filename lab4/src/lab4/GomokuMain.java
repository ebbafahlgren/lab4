package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

/**
 *
 * @author Anton Sandberg Ebba Fahlgren
 *
 * GomokuMain is a class that creates the game. It creates necessary objects to run the game.
 * It creates a client that keeps track of the different players connected
 * It creates a gamestate object (game) that controlls the different state of the game.
 * It creates a GUI object used to keep track of the view of the game. 
 */

public class GomokuMain {
    public static void main(String[] args) {

        GomokuClient client1;
        GomokuClient client2 = new GomokuClient(4401);

        if(args.length == 0) {
            client1 = new GomokuClient(4400);
        }else{
            client1 = new GomokuClient(Integer.parseInt(args[0]));
        }

        //Game object
        GomokuGameState game1 = new GomokuGameState(client1);
        GomokuGameState game2 = new GomokuGameState(client2);

        //Gui object.
        GomokuGUI gui = new GomokuGUI(game1,client1);
        GomokuGUI gui1 = new GomokuGUI(game2,client2);
    }

	//Argument av main - port number
	
	
	//Skapa 3 objekt;
	//GomokuClient - port number som arg
	//GomokuGameState - client som arg
	//GumokuGUI - game state & client som arg
	
	//Kod att kolla bara ett argument,
	//Annars sätts port nummer till default, minst 4000

	
}
