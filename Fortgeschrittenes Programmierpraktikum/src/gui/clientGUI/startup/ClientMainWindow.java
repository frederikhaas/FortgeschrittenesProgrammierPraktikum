package gui.clientGUI.startup;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.clientGUI.game.GameChoiceWindow;
import gui.utils.Chat;
import gui.utils.DynamicViewer;
import gui.utils.StandardJFrame;
import network.clientside.Client;
import utils.Comm;

public class ClientMainWindow {

	public StandardJFrame frame = new StandardJFrame("Client", new Dimension(800, 640), JFrame.DO_NOTHING_ON_CLOSE);
	JLabel nameLabel = new JLabel();
	JButton logoutButton = new JButton("logout");
	public Chat chat;
	public DynamicViewer onlinePlayers;
	JButton gameButton = new JButton("Play a game");
	Client client;

	public ClientMainWindow(Client client) {
		this.client = client;
		client.mainWindow = this;

		nameLabel.setText("logged in as " + client.name);
		nameLabel.setPreferredSize(new Dimension(320, 20));
		logoutButton.setPreferredSize(new Dimension(160, 20));
		chat = new Chat(new Dimension(320, 400), client);
		onlinePlayers = new DynamicViewer(new Dimension(320, 400));
		gameButton.setPreferredSize(new Dimension(320, 20));

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(40, 40, 20, 40);
		frame.add(nameLabel, gbc);

		gbc.gridx = 1;
		gbc.insets = new Insets(40, 40, 20, 40);
		frame.add(logoutButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(20, 40, 20, 40);
		frame.add(chat.getChatContainer(), gbc);

		gbc.gridx = 1;
		frame.add(onlinePlayers.getContainer(), gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(20, 40, 40, 40);
		frame.add(gameButton, gbc);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				logoutButton.doClick();
			}
		});

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (client.isIngame) {
					client.gameWindow.quitButton.doClick();
				}
				if (client.invited != "") {
					client.sendMessage(Comm.encode(client.invited, Comm.INVITE_CANCELED_COMM_CODE));
				}
				client.sendMessage(Comm.encode("", Comm.LOGOUT_COMM_CODE));
				for(Window w : client.openWindows) {
					w.setVisible(false);
					w.dispose();
				}
			}
		});

		gameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!client.isIngame) {
					new GameChoiceWindow(client);
				}
			}
		});

		frame.setVisible(true);
	}

}
