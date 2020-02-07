package gui.clientGUI.game.invite;

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

import gui.clientGUI.game.GameWindow;
import gui.clientGUI.game.dialogs.TooManyInvitesDialog;
import gui.utils.StandardJFrame;
import network.clientside.Client;
import utils.Comm;

public class InviteReceivedWindow {

	public StandardJFrame frame;
	JLabel nameLabel = new JLabel();
	JLabel textLabel = new JLabel();
	JLabel gameLabel = new JLabel();
	JButton acceptButton = new JButton("accept");
	JButton declineButton = new JButton("decline");
	public String test = "test";
	public boolean canceled = false;
	
	public InviteReceivedWindow(String name, String gamename, Client client) {
		frame = new StandardJFrame(new Dimension(400, 200), JFrame.DO_NOTHING_ON_CLOSE);
		frame.setMaximumSize(new Dimension(1000, 200));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		client.openWindows.add(frame);
		client.invReceivedWin = this;
		
		nameLabel.setText(name);
		nameLabel.setPreferredSize(new Dimension(name.length()*8, 20));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setText("has invited You to a game of");
		textLabel.setPreferredSize(new Dimension(200, 20));
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameLabel.setText(gamename);
		gameLabel.setPreferredSize(new Dimension(200, 20));
		gameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		acceptButton.setPreferredSize(new Dimension(100, 20));
		declineButton.setPreferredSize(new Dimension(100, 20));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = 2;
		frame.add(nameLabel, gbc);
		
		gbc.gridy = 1;
		frame.add(textLabel, gbc);
		
		gbc.gridy = 2;
		frame.add(gameLabel, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy = 3;
		frame.add(acceptButton, gbc);
		
		gbc.gridx = 1;
		frame.add(declineButton, gbc);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (canceled) return;
				declineButton.doClick();
			}
		});
		
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (client.invited.equals("")) {
					client.invited = name;
					client.isIngame = true;
					client.sendMessage(Comm.encode(name + ";true:" + gamename, Comm.INVITE_ACCEPTED_COMM_CODE));
					client.gameWindow = new GameWindow(client, gamename, client.name, name, false);
				} else {
					TooManyInvitesDialog tmid = new TooManyInvitesDialog(client, client.invited);
					if (tmid.cancelOldInvite) {
						client.invited = name;
						client.isIngame = true;
						client.sendMessage(Comm.encode(name + ";true:" + gamename, Comm.INVITE_ACCEPTED_COMM_CODE));
						client.gameWindow = new GameWindow(client, gamename, client.name, name, false);
					} else {
						declineButton.doClick();
					}
				}
				client.openWindows.remove(frame);
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		declineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.sendMessage(Comm.encode(name + ";false:" + gamename, Comm.INVITE_ACCEPTED_COMM_CODE));
				client.openWindows.remove(frame);
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		
		frame.setVisible(true);
	}
	
}
