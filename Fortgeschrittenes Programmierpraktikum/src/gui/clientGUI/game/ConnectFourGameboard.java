package gui.clientGUI.game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.Timer;

import games.utils.ConnectFour;
import games.utils.GameboardData;
import games.utils.Move;
import gui.utils.PopUp;
import network.clientside.Client;
import utils.Comm;

public class ConnectFourGameboard extends GameboardGUI {
	
	Color playerColor = Color.RED;
	ArrayList<ConnectFourColumn> columnList = new ArrayList<ConnectFourColumn>();
	String player2;
	public boolean isMyTurn = true;
	
	ConnectFour connectFour = new ConnectFour(0);
	GameboardData gameboardData = connectFour.gameboardData;
	int undoTime = 5;
	ArrayList<JButton> columnButtons = new ArrayList<JButton>();
	
	Timer undoTimer = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			undoTime--;
			undoButton.setText("undo - " + undoTime);
			if (undoTime == 0) {
				confirm();
			}
		}
	});

	public ConnectFourGameboard(Client client, String player2) {
		this.player2 = player2;
		this.client = client;

		container.setPreferredSize(new Dimension(700, 500));
		container.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		createAndAddGameboard(gbc);
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		confirmButton.setPreferredSize(new Dimension(100, 50));
		confirmButton.setEnabled(false);
		container.add(confirmButton, gbc);
		gbc.gridx = 5;
		undoButton.setPreferredSize(new Dimension(100, 50));
		undoButton.setEnabled(false);
		container.add(undoButton, gbc);
		
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		});
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isMyTurn = false;;
				confirm();
			}
		});
	}

	public void createAndAddGameboard(GridBagConstraints gbc) {
		gbc.insets = new Insets(0, 0, 0, 0);
		
		for (int i = 0; i < gameboardData.xDim; i++) {
			
			final int x = i;
			gbc.gridx = i;
			ConnectFourColumn cfci = new ConnectFourColumn(gameboardData.yDim, 50);
			columnList.add(cfci);
			columnButtons.add(cfci.columnButton);
			cfci.columnButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (isMyTurn) {
					connectFour.playerMove(x);
					setGameboard(gameboardData);
					isMyTurn = false;
					confirmButton.setEnabled(true);
					undoButton.setEnabled(true);
					undoButton.setText("undo - 5");
					undoTimer.restart();
					}
				}
			});
			cfci.columnButton.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					if (isMyTurn && cfci.columnButton.isEnabled()) {
						int currentTop = 0;
						for (int i = 0; i < connectFour.gameboardData.yDim; i++) {
							if (i == connectFour.gameboardData.yDim - 1 || connectFour.gameboardData.gameboard.get(x).get(i+1) != 0) {
								currentTop = i;
								break;
							}
						}
						columnList.get(x).slotList.get(currentTop).color = new Color(255, 128, 128);
						columnList.get(x).slotList.get(currentTop).repaint();
					}
				}
				public void mouseExited(MouseEvent e) {
					if (isMyTurn && cfci.columnButton.isEnabled()) {
						int currentTop = 0;
						for (int i = 0; i < connectFour.gameboardData.yDim; i++) {
							if (i == connectFour.gameboardData.yDim - 1 || connectFour.gameboardData.gameboard.get(x).get(i+1) != 0) {
								currentTop = i;
								break;
							}
						}
						columnList.get(x).slotList.get(currentTop).color = new Color(180, 180, 180);
						columnList.get(x).slotList.get(currentTop).repaint();
					}
				}
				
			});
			container.add(cfci.container, gbc);
		}
	}

	public void undo() {
		undoTimer.stop();
		undoButton.setEnabled(false);
		confirmButton.setEnabled(false);
		undoButton.setText("undo");
		resetTimer();
		connectFour.popMove();
		setGameboard(connectFour.gameboardData);
		isMyTurn = true;
	}
	
	public void confirm() {
		undoTimer.stop();
		undoButton.setEnabled(false);
		confirmButton.setEnabled(false);
		undoButton.setText("undo");
		resetTimer();
		if(player2.equals("pc")) {
			if (connectFour.checkWin(connectFour.lastSet)) {
				for(JButton btn : columnButtons) {
					btn.setEnabled(false);
				}
				new PopUp("You have won!", client);
				return;
			}
			connectFour.pcMove();
			setGameboard(connectFour.gameboardData);
			isMyTurn = true;
			if(connectFour.checkWin(connectFour.lastSet)) {
				for(JButton btn : columnButtons) {
					btn.setEnabled(false);
				}
				new PopUp("The computer has won!", client);
			}
		} else {
			sendMove();
		}
		if (connectFour.checkWin(connectFour.lastSet)) {
			for(JButton btn : columnButtons) {
				btn.setEnabled(false);
			}
			new PopUp("You have won!", client);
			return;
		}
	}

	public void setGameboard(GameboardData gameboardData) {
		for (int i = 0; i < gameboardData.xDim; i++) {
			for (int j = 0; j < gameboardData.yDim; j++) {
				if (gameboardData.gameboard.get(i).get(j) == 2) {
					columnList.get(i).slotList.get(j).color = Color.BLUE;
					columnList.get(i).slotList.get(j).repaint();
					if (j == 0) {
						columnButtons.get(i).setEnabled(false);
					}
				} else if (gameboardData.gameboard.get(i).get(j) == 0) {
					columnList.get(i).slotList.get(j).color = new Color(180, 180, 180);
					columnList.get(i).slotList.get(j).repaint();
					if (j == 0) {
						columnButtons.get(i).setEnabled(true);
					}
				} else if (gameboardData.gameboard.get(i).get(j) == 1) {
					columnList.get(i).slotList.get(j).color = Color.RED;
					columnList.get(i).slotList.get(j).repaint();
					if (j == 0) {
						columnButtons.get(i).setEnabled(false);
					}
				}
			}
		}
	}

	public void resetTimer() {
		undoTime = 5;
	}

	public Container getContainer() {
		return container;
	}
	
	public void receiveMove(Point p) {
		connectFour.gameboardData.gameboard.get(p.x).set(p.y, 2);
		GameboardData gbd = new GameboardData(connectFour.gameboardData.gameboard);
		Move move = new Move(gbd);
		connectFour.pushMove(move);
		setGameboard(gbd);
		for(JButton btn : columnButtons) {
			btn.setForeground(Color.RED);
		}
		if (connectFour.checkWin(p)) {
			new PopUp("You have lost!", client);
			return;
		}
		isMyTurn = true;
	}
	
	public void sendMove() {
		String message = String.valueOf(connectFour.lastSet.x) + " " +  String.valueOf(connectFour.lastSet.y);
		client.sendMessage(Comm.encode(message, Comm.PLAYER_MOVE_COMM_CODE));
	}
	
	public void pcMove() {
		connectFour.pcMove();
		setGameboard(connectFour.gameboardData);
	}
}
