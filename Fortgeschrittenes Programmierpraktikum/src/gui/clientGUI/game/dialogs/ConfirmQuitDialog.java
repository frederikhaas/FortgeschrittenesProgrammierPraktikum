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

public class ConfirmQuitDialog {

	StandardJDialog dialog = new StandardJDialog(new Dimension(200, 100), new GridBagLayout());
	JLabel textLabel = new JLabel("Do You want to quit?");
	JButton yesButton = new JButton("yes");
	JButton noButton = new JButton("no");
	public boolean quit = false;
	
	public ConfirmQuitDialog(Client client) {
		client.openWindows.add(dialog);
		textLabel.setPreferredSize(new Dimension(120, 20));
		yesButton.setPreferredSize(new Dimension(40, 20));
		noButton.setPreferredSize(new Dimension(40, 20));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridwidth = 2;
		dialog.add(textLabel, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		dialog.add(yesButton, gbc);
		
		gbc.gridx = 1;
		dialog.add(noButton, gbc);
		
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				quit = true;
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
