package gui.serverGUI;

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

import gui.utils.DynamicViewer;
import gui.utils.StandardJFrame;
import network.serverside.Server;

public class ServerControlWindow {

	StandardJFrame frame = new StandardJFrame(new Dimension(1000, 700), JFrame.DO_NOTHING_ON_CLOSE);
	JLabel portLabel = new JLabel();
	public DynamicViewer chat = new DynamicViewer(new Dimension(300, 500));
	public DynamicViewer onlinePlayers = new DynamicViewer(new Dimension(300, 500));
	public DynamicViewer games = new DynamicViewer(new Dimension(300, 500));
	JLabel chatLabel = new JLabel("Chat:");
	JLabel gamesLabel = new JLabel("Running games:");
	JLabel playerLabel = new JLabel("Online players:");
	JButton stopButton = new JButton("stop");
	Server server;
	
	public ServerControlWindow(Server Server) {
		server = Server;
		server.scw = this;
		portLabel.setPreferredSize(new Dimension(300, 20));
		portLabel.setHorizontalAlignment(SwingConstants.CENTER);
		portLabel.setText("server running at port : " + server.port);
		chatLabel.setPreferredSize(new Dimension(200, 20));
		playerLabel.setPreferredSize(new Dimension(200, 20));
		gamesLabel.setPreferredSize(new Dimension(200, 20));
		stopButton.setPreferredSize(new Dimension(200, 40));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridwidth = 3;
		frame.add(portLabel, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		frame.add(chatLabel, gbc);
		gbc.gridx = 1;
		frame.add(playerLabel, gbc);
		gbc.gridx = 2;
		frame.add(gamesLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		frame.add(chat.getContainer(), gbc);
		gbc.gridx = 1;
		frame.add(onlinePlayers.getContainer(), gbc);
		gbc.gridx = 2;
		frame.add(games.getContainer(), gbc);
		gbc.gridy = 3;
		gbc.gridx = 1;
		frame.add(stopButton, gbc);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stopButton.doClick();
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				server.forceLogoutAll();
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);//terminates server
			}
		});
		
		frame.setVisible(true);
	}
}
