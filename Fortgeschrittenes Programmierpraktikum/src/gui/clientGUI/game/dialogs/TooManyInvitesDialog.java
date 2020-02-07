package gui.clientGUI.game.dialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import gui.utils.StandardJDialog;
import network.clientside.Client;

public class TooManyInvitesDialog {

	StandardJDialog dialog;
	JLabel textLabel1 = new JLabel();
	JLabel textLabel2 = new JLabel("Do You want to cancel that invite?");
	JButton yesButton = new JButton("yes");
	JButton noButton = new JButton("no");
	public boolean cancelOldInvite = false;
	
	public TooManyInvitesDialog(Client client, String oldInvite) {
		dialog = new StandardJDialog(new Dimension(300, 200), new GridBagLayout());
		client.openWindows.add(dialog);
		
		textLabel1.setPreferredSize(new Dimension(40 + oldInvite.length()*8, 20));
		textLabel1.setText("You have already invited " + oldInvite);
		
		yesButton.setPreferredSize(new Dimension(80, 20));
		noButton.setPreferredSize(new Dimension(80, 20));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridwidth = 2;
		dialog.add(textLabel1, gbc);
		
		gbc.gridy = 1;
		dialog.add(textLabel2, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy = 2;
		dialog.add(yesButton, gbc);
		
		gbc.gridx = 1;
		dialog.add(noButton, gbc);
		
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancelOldInvite = true;
				client.openWindows.remove(dialog);
				dialog.setVisible(false);
				dialog.dispose();
			}
		});
		
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.openWindows.remove(dialog);
				dialog.setVisible(false);
				dialog.dispose();
			}
		});
		
		
		dialog.setVisible(true);
	}
	
}
