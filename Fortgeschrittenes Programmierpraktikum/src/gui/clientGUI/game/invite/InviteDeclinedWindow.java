package gui.clientGUI.game.invite;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.utils.StandardJFrame;
import network.clientside.Client;

public class InviteDeclinedWindow {

	StandardJFrame frame = new StandardJFrame(new Dimension(300, 200),  JFrame.DISPOSE_ON_CLOSE);
	JLabel textLabel = new JLabel("Your invite has been declined.");
	JButton okButton = new JButton("ok");
	
	public InviteDeclinedWindow(Client client) {
		
		client.openWindows.add(frame);
		
		textLabel.setPreferredSize(new Dimension(200, 20));
		
		okButton.setPreferredSize(new Dimension(100, 20));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		frame.add(textLabel, gbc);
		
		gbc.gridy = 1;
		frame.add(okButton, gbc);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.openWindows.remove(frame);
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		frame.setVisible(true);
	}
}
