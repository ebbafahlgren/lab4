package lab4.gui;

import lab4.gui.ConnectionWindow;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

import javax.swing.*;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

    private GomokuClient client;
    private GomokuGameState gamestate;
    private JFrame frame;
    private GamePanel gameGridPanel;
    private JLabel messageLabel;
    private JButton connectButton, newGameButton, disconnectButton;

    /**
     * The constructor
     *
     * @param g   The game state that the GUI will visualize
     * @param c   The client that is responsible for the communication
     */
    public GomokuGUI(GomokuGameState g, GomokuClient c){
        this.client = c;
        this.gamestate = g;
        client.addObserver(this);
        gamestate.addObserver(this);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocation(175, 175);

        gameGridPanel = new GamePanel(gamestate.getGameGrid());



        Container pane = frame.getContentPane();
        SpringLayout thisLayout = new SpringLayout();
        pane.setLayout(thisLayout);
        // Denna bestämmer gridPanels startStorlek
        Dimension d = new Dimension(gameGridPanel.getWidth()+ 600, gameGridPanel.getWidth() + 600);
        pane.setPreferredSize(d);
        pane.setBackground(Color.WHITE);

        //Prints out the name of the game and the buttons
        messageLabel = new JLabel("Welcome to Gomoku!");

        connectButton = new JButton();
        connectButton.setText("Connect");

        disconnectButton = new JButton();
        disconnectButton.setText("Disconnect");

        newGameButton = new JButton();
        newGameButton.setText("New Game");

        pane.add(gameGridPanel);
        pane.add(messageLabel);
        pane.add(newGameButton);
        pane.add(connectButton);
        pane.add(disconnectButton);

        thisLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gameGridPanel, 0, SpringLayout.HORIZONTAL_CENTER, pane);
        thisLayout.putConstraint(SpringLayout.NORTH, gameGridPanel, 5, SpringLayout.NORTH, pane);

        thisLayout.putConstraint(SpringLayout.NORTH, connectButton, 20, SpringLayout.SOUTH, gameGridPanel);
        thisLayout.putConstraint(SpringLayout.EAST, connectButton, -20, SpringLayout.WEST, newGameButton);
        thisLayout.putConstraint(SpringLayout.NORTH, disconnectButton, 20, SpringLayout.SOUTH, gameGridPanel);
        thisLayout.putConstraint(SpringLayout.WEST, disconnectButton, 20, SpringLayout.EAST, newGameButton);
        thisLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, newGameButton, 0, SpringLayout.HORIZONTAL_CENTER, gameGridPanel);
        thisLayout.putConstraint(SpringLayout.NORTH, newGameButton, 20, SpringLayout.SOUTH, gameGridPanel);

        //Horizontal centrerar så att messageLabel hamnar rakt under griden
        thisLayout.putConstraint(SpringLayout.NORTH, messageLabel, 30, SpringLayout.SOUTH, newGameButton);
        thisLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, messageLabel, 0,SpringLayout.HORIZONTAL_CENTER,gameGridPanel);

        //Connectar pane med frame, gör även pack synlig
        frame.setContentPane(pane);
        frame.pack();
        frame.setVisible(true);

        // Anonym klass som fyller i rätt position när man klickar
        gameGridPanel.addMouseListener(new MouseAdapter() {
           // @Override
            public void mouseClicked(MouseEvent e) {

                //Kallar på metoden gridPosition och skickar in x, y koordinaten av gridPanel boarden som printas.
                int[] gridSquare = gameGridPanel.getGridPosition(e.getX(),e.getY());
                gamestate.move(gridSquare[0],gridSquare[1]);

            }
        });
        // anonyma klasser med lamdba expressions
        this.connectButton.addActionListener(e -> {
            ConnectionWindow connectionWindow = new ConnectionWindow(client);
        });

        this.newGameButton.addActionListener(e -> gamestate.newGame());

        this.disconnectButton.addActionListener(e -> gamestate.disconnect());
        
    }


    public void update(Observable arg0, Object arg1) {

        // Update the buttons if the connection status has changed
        if(arg0 == client){
            if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
                connectButton.setEnabled(true);
                newGameButton.setEnabled(false);
                disconnectButton.setEnabled(false);
            }else{
                connectButton.setEnabled(false);
                newGameButton.setEnabled(true);
                disconnectButton.setEnabled(true);
            }
        }

        // Update the status text if the gamestate has changed
        if(arg0 == gamestate){
            messageLabel.setText(gamestate.getMessageString());
        }

    }

}
