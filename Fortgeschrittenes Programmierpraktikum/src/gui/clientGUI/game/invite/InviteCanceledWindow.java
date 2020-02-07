package gui.clientGUI.game.invite;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.utils.StandardJFrame;
import network.clientside.Client;

public class InviteCanceledWindow {

	StandardJFrame frame = new StandardJFrame(new Dimension(300, 200), JFrame.DISPOSE_ON_CLOSE);
	JLabel textLabel1 = new JLabel("The invite from");
	JLabel nameLabel = new JLabel();
	JLabel textLabel2 = new JLabel("has been canceled.");
	JButton okButton = new JButton("ok");

	public InviteCanceledWindow(String inviteSender, Client client) {
		
		textLabel1.setPreferredSize(new Dimension(200, 20));
		
		nameLabel.setText(inviteSender);
		nameLabel.setPreferredSize(new Dimension(200, 20));
		
		textLabel2.setPreferredSize(new Dimension(200, 20));
		
		okButton.setPreferredSize(new Dimension(100, 20));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(10, 10, 10, 10);
		
		frame.add(textLabel1, gbc);
		
		gbc.gridy = 1;
		frame.add(nameLabel, gbc);
		
		gbc.gridy = 2;
		frame.add(textLabel2, gbc);
		
		gbc.gridy = 3;
		frame.add(okButton, gbc);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		frame.setVisible(true);
		
	}

}
