package gui.clientGUI.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Timer;

import games.utils.Chomp;
import games.utils.GameboardData;
import games.utils.Move;
import gui.utils.PopUp;
import network.clientside.Client;
import utils.Comm;

public class ChompGameboard extends GameboardGUI {

	Chomp chomp = new Chomp(6, 4, 0);
	String player2;
	public boolean isMyTurn = true;
	// GameboardData gameboardData = chomp.gameboardData;

	int undoTime = 5;
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

	JButton[][] gameboardButtons;

	public ChompGameboard(Client client, String player2) {
		this.player2 = player2;
		this.client = client;

		container.setPreferredSize(new Dimension(700, 500));
		container.setLayout(new GridBagLayout());

		gameboardButtons = new JButton[chomp.gameboardData.xDim][chomp.gameboardData.yDim];
		GridBagConstraints gbc = new GridBagConstraints();

		initializeAndAddGameboardbuttons(new Dimension(100, 100), gbc);

		undoButton.setPreferredSize(new Dimension(100, 50));
		undoButton.setEnabled(false);
		confirmButton.setEnabled(false);
		confirmButton.setPreferredSize(new Dimension(100, 50));

		gbc.gridy = 4;
		gbc.gridx = 0;
		container.add(confirmButton, gbc);

		gbc.gridx = 5;
		container.add(undoButton, gbc);

		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		});

		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm();
			}
		});

	}

	public void undo() {
		undoTimer.stop();
		undoButton.setEnabled(false);
		confirmButton.setEnabled(false);
		isMyTurn = true;
		undoButton.setText("undo");
		resetTimer();
		chomp.popMove();
		setGameboard(chomp.gameboardData);
	}

	public void confirm() {
		undoTimer.stop();
		undoButton.setEnabled(false);
		confirmButton.setEnabled(false);
		undoButton.setText("undo");
		resetTimer();
		isMyTurn = false;
		if (player2 == "pc") {
			if (chomp.checkWin(chomp.lastMove)) {
				new PopUp("You have won!", client);
				return;
			}
			chomp.pcMove();
			setGameboard(chomp.gameboardData);
			if (chomp.checkWin(chomp.lastMove)) {
				new PopUp("The computer has won!", client);
				return;
			}
			isMyTurn = true;
		} else {
			sendMove();
		}
		if (chomp.checkWin(chomp.lastMove)) {
			new PopUp("You have won!", client);
			return;
		}
	}

	public void setGameboard(GameboardData gameboardData) {
		for (int i = 0; i < gameboardData.xDim; i++) {
			for (int j = 0; j < gameboardData.yDim; j++) {
				if (i == 0 && j == 0)
					continue;
				if (gameboardData.gameboard.get(i).get(j) == 0) {
					gameboardButtons[i][j].setEnabled(true);
					gameboardButtons[i][j].setBackground(new Color(61, 16, 5));
				} else if (gameboardData.gameboard.get(i).get(j) == 1) {
					gameboardButtons[i][j].setEnabled(false);
					gameboardButtons[i][j].setBackground(new Color(125, 85, 60));
				}
			}
		}
	}

	private void initializeAndAddGameboardbuttons(Dimension prefButtonSize, GridBagConstraints gbc) {
		gbc.insets = new Insets(5, 5, 5, 5);
		for (int i = 0; i < chomp.gameboardData.xDim; i++) {
			for (int j = 0; j < chomp.gameboardData.yDim; j++) {
				JButton currentButton = new JButton();
				currentButton.setPreferredSize(prefButtonSize);
				currentButton.setBackground(new Color(61, 16, 5));
				gbc.gridx = i;
				gbc.gridy = j;
				container.add(currentButton, gbc);
				gameboardButtons[i][j] = currentButton;
				if (i == 0 && j == 0) {
					currentButton.setText("X");
					currentButton.setBackground(Color.DARK_GRAY);
					currentButton.setEnabled(false);
				} else {
					final int a = i;
					final int b = j;
					currentButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (isMyTurn) {
								isMyTurn = false;
								for (int x = a; x < chomp.gameboardData.xDim; x++) {
									for (int y = b; y < chomp.gameboardData.yDim; y++) {
										gameboardButtons[x][y].setEnabled(false);
										gameboardButtons[x][y].setBackground(new Color(125, 85, 60));
										chomp.gameboardData.gameboard.get(x).set(y, 1);
									}
								}
								GameboardData gb = new GameboardData(chomp.gameboardData.gameboard);

								Move move = new Move(gb);
								chomp.pushMove(move);
								chomp.lastMove = move;
								undoButton.setEnabled(true);
								confirmButton.setEnabled(true);
								undoButton.setText("undo - 5");
								undoTimer.restart();

							}
						}
					});
				}
			}
		}
	}

	public void resetTimer() {
		undoTime = 5;
	}

	public void receiveMove(Move move) {
		chomp.pushMove(move);
		chomp.gameboardData.gameboard = move.gameboardData.gameboard;
		setGameboard(move.gameboardData);
		if (chomp.checkWin(move)) {
			new PopUp("You have lost!", client);
			return;
		} else {
			isMyTurn = true;
		}
		// TODO set client turn-state
	}

	public void sendMove() {
		String gbdString = chomp.gameboardData.transformGameboard();
		client.sendMessage(Comm.encode(gbdString, Comm.PLAYER_MOVE_COMM_CODE));
	}

}
