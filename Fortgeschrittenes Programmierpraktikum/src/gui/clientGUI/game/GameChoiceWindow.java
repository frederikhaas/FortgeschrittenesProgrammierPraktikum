package gui.clientGUI.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import network.clientside.Client;

public class GameChoiceWindow {
	
	public JFrame frame = new JFrame("Choose a game");
	JLabel promptLabel = new JLabel("Which game would You like to play?");
	JButton connectfourButton = new JButton("Connect Four");
	JButton chompButton = new JButton("Chomp!");
	
	public GameChoiceWindow(Client client) {
		client.openWindows.add(frame);
		
		frame.setSize(new Dimension(440, 120));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		
		promptLabel.setPreferredSize(new Dimension(400, 20));
		connectfourButton.setPreferredSize(new Dimension(140 ,20));
		chompButton.setPreferredSize(new Dimension(140, 20));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10, 20, 10, 20);
		frame.add(promptLabel, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		frame.add(connectfourButton, gbc);
		
		gbc.gridx = 1;
		frame.add(chompButton, gbc);
		
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				client.starting = false;
				client.openWindows.remove(frame);
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		connectfourButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.openGameStartup.add(new ModeChoiceWindow(client, "Connect Four").frame);	
				client.openWindows.remove(frame);
				client.openGameStartup.remove(frame);
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		chompButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.openGameStartup.add(new ModeChoiceWindow(client, "Chomp").frame);
				client.openWindows.remove(frame);
				client.openGameStartup.remove(frame);
				frame.setVisible(false);
				frame.dispose();
			}
		});
	}
}
