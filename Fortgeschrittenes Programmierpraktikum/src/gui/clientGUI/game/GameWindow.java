package gui.clientGUI.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import games.utils.GameboardData;
import games.utils.Move;
import gui.clientGUI.game.dialogs.ConfirmQuitDialog;
import gui.utils.StandardJFrame;
import network.clientside.Client;
import utils.Comm;

public class GameWindow {

	public StandardJFrame frame = new StandardJFrame(new Dimension(800, 640), JFrame.DO_NOTHING_ON_CLOSE);
	JLabel gameLabel = new JLabel();
	public JButton quitButton = new JButton("quit");
	public GameboardGUI gameboard;
	public String gamename;
	public boolean quit;
	
	public GameWindow(Client client, String gamename, String player1, String player2, boolean myTurn) {
		client.isIngame = true;
		client.gameWindow = this;
		client.openWindows.add(frame);
		this.gamename = gamename;
		
		
		frame.setTitle(gamename);
		
		gameLabel.setPreferredSize(new Dimension(400, 50));
		gameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameLabel.setText(player1 + " : " + player2);
		
		quitButton.setPreferredSize(new Dimension(150, 50));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(10, 10, 10, 10);
		
		frame.add(quitButton, gbc);
		
		gbc.gridx = 1;
		frame.add(gameLabel, gbc);
		
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		if(gamename.equals("Chomp")) {
			gameboard = new ChompGameboard(client, player2);
			frame.add(gameboard.getContainer(), gbc);
			ChompGameboard cgb = (ChompGameboard) gameboard;
			cgb.isMyTurn = myTurn;
		} else {
			gameboard = new ConnectFourGameboard(client, player2);
			frame.add(gameboard.getContainer(), gbc);
			ConnectFourGameboard cfgb = (ConnectFourGameboard) gameboard;
			cfgb.isMyTurn = myTurn;
		}
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quitButton.doClick();
			}
		});
		
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfirmQuitDialog cqd = new ConfirmQuitDialog(client);
				quit = cqd.quit;
				if(quit) {
					client.sendMessage(Comm.encode(client.invited, Comm.GAME_QUIT_COMM_CODE));
					client.invited = "";
					client.isIngame = false;
					client.openWindows.remove(frame);
					frame.setVisible(false);
					frame.dispose();
				}
			}
		});
		frame.setVisible(true);
	}
	
	public void receiveMove(String move) {
		String[] tokens = move.split(" ");
		if (gamename.equals("Connect Four")) {
			ConnectFourGameboard cfgb = (ConnectFourGameboard) gameboard;
			Point lastSet = new Point(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
			cfgb.receiveMove(lastSet);
		} else if (gamename.equals("Chomp")) {
			ChompGameboard cgb = (ChompGameboard) gameboard;
			ArrayList<ArrayList<Integer>> gameboard = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i < cgb.chomp.xDim; i++) {
				gameboard.add(new ArrayList<Integer>());
				for (int j = 0; j < cgb.chomp.yDim; j++) {
					gameboard.get(i).add(Integer.parseInt(tokens[i*cgb.chomp.yDim + j]));
				}
			}
			GameboardData gbd = new GameboardData(gameboard);
			Move mov = new Move(gbd);
			cgb.receiveMove(mov);
		}
	}
	
}
