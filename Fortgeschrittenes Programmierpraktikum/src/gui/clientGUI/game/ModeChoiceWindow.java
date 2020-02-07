package gui.clientGUI.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import gui.clientGUI.game.dialogs.TooManyInvitesDialog;
import gui.utils.StandardJFrame;
import network.clientside.Client;
import utils.Comm;

public class ModeChoiceWindow {

	StandardJFrame frame;
	JLabel gameLabel;
	JLabel textLabel1 = new JLabel("another");
	JButton playerButton = new JButton("player?");
	JLabel textLabel2 = new JLabel("or the");
	JButton pcButton = new JButton("computer?");

	public ModeChoiceWindow(Client client, String gamename) {
		frame = new StandardJFrame(gamename, new Dimension(360, 160), JFrame.DO_NOTHING_ON_CLOSE);
		client.openWindows.add(frame);

		gameLabel = new JLabel("Do You want to play " + gamename + " with");
		gameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameLabel.setPreferredSize(new Dimension(320, 20));
		textLabel1.setPreferredSize(new Dimension(100, 20));
		textLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel2.setPreferredSize(new Dimension(100, 20));
		textLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		playerButton.setPreferredSize(new Dimension(100, 20));
		pcButton.setPreferredSize(new Dimension(100, 20));

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridwidth = 2;
		frame.add(gameLabel, gbc);

		gbc.gridwidth = 1;
		gbc.gridy = 1;
		frame.add(textLabel1, gbc);

		gbc.gridx = 1;
		frame.add(playerButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		frame.add(textLabel2, gbc);

		gbc.gridx = 1;
		frame.add(pcButton, gbc);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				client.openWindows.remove(frame);
				frame.setVisible(false);
				frame.dispose();
			}
		});

		playerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new PlayerChoiceWindow(client, gamename);
				client.openWindows.remove(frame);
				frame.setVisible(false);
				frame.dispose();
			}
		});

		pcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (client.invited.equals("")) {
					client.sendMessage(Comm.encode(client.name, Comm.UNAVAILABLE_PLAYER_COMM_CODE));
					client.sendMessage(Comm.encode(gamename + ":" + client.name + "-" + "pc", Comm.NEW_GAME_COMM_CODE));
					client.isIngame = true;
					client.invited = "pc";
					new GameWindow(client, gamename, client.name, "pc", true);
					client.openWindows.remove(frame);
					frame.setVisible(false);
					frame.dispose();
				} else {
					TooManyInvitesDialog tmid = new TooManyInvitesDialog(client, client.invited);
					if(tmid.cancelOldInvite) {
						client.sendMessage(Comm.encode(client.invited, Comm.INVITE_CANCELED_COMM_CODE));
						client.invited = "pc";
						client.openWindows.remove(frame);
						frame.setVisible(false);
						frame.dispose();
						new GameWindow(client, gamename, client.name, "pc", true);
					}
				}
			}
		});
		frame.setVisible(true);
	}

}
