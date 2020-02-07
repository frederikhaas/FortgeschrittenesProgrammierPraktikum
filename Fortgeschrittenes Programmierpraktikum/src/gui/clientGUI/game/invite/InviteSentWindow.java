//package gui.clientGUI.game.invite;
//
//import java.awt.Dimension;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.SwingConstants;
//
//import gui.utils.StandardJFrame;
//import network.clientside.Client;
//
//public class InviteSentWindow {
//
//	StandardJFrame frame = new StandardJFrame(new Dimension(300, 300), JFrame.DISPOSE_ON_CLOSE);
//	JLabel textLabel1 = new JLabel("You have invited");
//	JLabel nameLabel = new JLabel();
//	JLabel textLabel2 = new JLabel("to a game of");
//	JLabel gameLabel = new JLabel();
//	JButton okButton = new JButton("ok");
//	JButton cancelButton = new JButton("cancel");
//	
//	public InviteSentWindow(String name, String gamename, Client client) {
//		
//		textLabel1.setPreferredSize(new Dimension(200, 20));
//		textLabel1.setHorizontalAlignment(SwingConstants.CENTER);
//		
//		nameLabel.setText(name);
//		nameLabel.setPreferredSize(new Dimension(200, 20));
//		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		
//		textLabel2.setPreferredSize(new Dimension(200, 20));
//		textLabel2.setHorizontalAlignment(SwingConstants.CENTER);
//		
//		gameLabel.setText(gamename);
//		gameLabel.setPreferredSize(new Dimension(200, 20));
//		gameLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		
//		okButton.setPreferredSize(new Dimension(100, 20));
//		cancelButton.setPreferredSize(new Dimension(100, 20));
//		
//		GridBagConstraints gbc = new GridBagConstraints();
//		
//		gbc.insets = new Insets(10, 10, 10, 10);
//		gbc.gridwidth = 2;
//		
//		frame.add(textLabel1, gbc);
//		
//		gbc.gridy = 1;
//		frame.add(nameLabel, gbc);
//		
//		gbc.gridy = 2;
//		frame.add(textLabel2, gbc);
//		
//		gbc.gridy = 3;
//		frame.add(gameLabel, gbc);
//		
//		gbc.gridwidth = 1;
//		gbc.gridy = 4;
//		frame.add(okButton, gbc);
//		
//		gbc.gridx = 1;
//		frame.add(cancelButton, gbc);
//		
//		okButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				frame.setVisible(false);
//				frame.dispose();
//			}
//		});
//		
//		cancelButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// TODO delete pending invite, send message to invited -> invite expired
//				frame.setVisible(false);
//				frame.dispose();
//			}
//		});
//		
//		frame.setVisible(true);
//		
//	}
//	
//}
