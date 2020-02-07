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
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.clientGUI.game.dialogs.TooManyInvitesDialog;
import gui.utils.StandardJFrame;
import network.clientside.Client;
import utils.Comm;

public class PlayerChoiceWindow {

	StandardJFrame frame = new StandardJFrame("Choose wisely!", new Dimension(400, 400), JFrame.DO_NOTHING_ON_CLOSE);
	JLabel promptLabel = new JLabel();
	JList<String> availablePlayers;
	JTextField nameInputField = new JTextField();
	JButton inviteButton = new JButton("invite");

	public PlayerChoiceWindow(Client client, String gamename) {

		client.openWindows.add(frame);

		promptLabel.setText("Who would You like to play " + gamename + " with?");
		promptLabel.setPreferredSize(new Dimension(380, 20));

		availablePlayers = new JList<String>(client.availablePlayers);
		availablePlayers.setMinimumSize(new Dimension(350, 200));

		nameInputField.setMinimumSize(new Dimension(100, 20));

		inviteButton.setPreferredSize(new Dimension(80, 20));

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(10, 20, 10, 10);
		frame.add(promptLabel, gbc);

		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		frame.add(availablePlayers, gbc);

		gbc.gridy = 2;
		gbc.insets = new Insets(10, 20, 10, 20);
		frame.add(nameInputField, gbc);

		gbc.gridy = 3;
		gbc.insets = new Insets(10, 20, 10, 20);
		frame.add(inviteButton, gbc);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				client.starting = false;
				frame.setVisible(false);
				frame.dispose();
			}
		});

		nameInputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inviteButton.doClick();
			}
		});

		inviteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameInputField.getText();
				if (!(name.equals(""))) {
					if (!(client.invited.equals(""))) {
						TooManyInvitesDialog tmid = new TooManyInvitesDialog(client, client.invited);
						if (tmid.cancelOldInvite) {
							client.starting = false;
							client.sendMessage(Comm.encode(client.invited, Comm.INVITE_CANCELED_COMM_CODE));
							client.invited = name;
							client.sendMessage(Comm.encode(gamename + ";" + name, Comm.INVITE_COMM_CODE));
//							InviteSentWindow isw = new InviteSentWindow(name, gamename, client);
							client.openWindows.remove(frame);
							frame.setVisible(false);
							frame.dispose();
						}
					} else {
						client.starting = false;
						client.invited = name;
						client.sendMessage(Comm.encode(gamename + ";" + name, Comm.INVITE_COMM_CODE));
//						InviteSentWindow isw = new InviteSentWindow(name, gamename, client);
						client.openWindows.remove(frame);
						frame.setVisible(false);
						frame.dispose();
					}
				}
			}
		});

		availablePlayers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String player = availablePlayers.getSelectedValue();
				if (!(player.equals(null))) {
					nameInputField.setText(player);
				}
			}
		});

		frame.setVisible(true);

	}

}
