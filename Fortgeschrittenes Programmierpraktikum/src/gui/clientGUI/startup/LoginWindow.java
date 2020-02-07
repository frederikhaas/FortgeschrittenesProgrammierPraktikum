package gui.clientGUI.startup;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gui.utils.PopUp;
import network.clientside.Client;
import utils.Comm;

public class LoginWindow {

	public JDialog dialog = new JDialog();
	JLabel portLabel = new JLabel();
	JLabel nameLabel = new JLabel("name");
	JLabel passwordLabel = new JLabel("password");
	JTextField nameTextfield = new JTextField();
	JPasswordField pwTextfield  = new JPasswordField();
	JButton loginButton = new JButton("login");
	JButton disconnectButton = new JButton("disconnect");
	
	Client client;
	
	public LoginWindow(Client c) {
		client = c;
		client.liw = this;
		client.openWindows.add(dialog);
		
		dialog.setSize(new Dimension(280, 200));
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
		dialog.setLayout(new GridBagLayout());
		dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		portLabel.setText("connected at port : " + client.port);
		portLabel.setPreferredSize(new Dimension(240, 20));
		portLabel.setHorizontalAlignment(SwingConstants.LEFT);
		nameLabel.setPreferredSize(new Dimension(100, 20));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		passwordLabel.setPreferredSize(new Dimension(100, 20));
		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		nameTextfield.setMinimumSize(new Dimension(120 ,20));
		nameTextfield.setToolTipText("name must be at least 3 long and may not contain any whitespace");
		pwTextfield.setMinimumSize(new Dimension(120, 20));
		pwTextfield.setToolTipText("please enter a password");
		
		loginButton.setPreferredSize(new Dimension(100, 20));
		disconnectButton.setPreferredSize(new Dimension(100, 20));
		
		//add Action- and WindowListener
		
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disconnectButton.doClick();
			}
		});
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameTextfield.getText();
				if(name.length() < 3 || name.split("\\s").length > 1) {
					nameTextfield.setText("");
					pwTextfield.setText("");
					new PopUp(nameTextfield.getToolTipText(), client);
					return;
				}
				String pw = String.valueOf(pwTextfield.getPassword());
				if (pw.equals("")) {
					new PopUp(pwTextfield.getToolTipText(), client);
					return;
				}
				client.name = name;
				
				client.sendMessage(Comm.encode(name, Comm.CLIENT_SENT_NAME_COMM_CODE));
				client.sendMessage(Comm.encode(pw, Comm.CLIENT_SENT_PW_COMM_CODE));
			}
		});
		
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.sendMessage(Comm.encode("", Comm.DISCONNECT_COMM_CODE));
				client.openWindows.remove(dialog);
				dialog.setVisible(false);
				dialog.dispose();
			}
		});
		
		nameTextfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginButton.doClick();
			}
		});
		
		pwTextfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginButton.doClick();
			}
		});
		
		
		//Place Elements in dialog
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(20, 20, 10, 20);
		dialog.add(portLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10, 20, 10, 10);
		dialog.add(nameLabel, gbc);
		
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 20, 10, 10);
		dialog.add(passwordLabel, gbc);
		
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 20, 20, 20);
		dialog.add(loginButton, gbc);

		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.insets = new Insets(10, 10, 10, 20);
		dialog.add(nameTextfield, gbc);
		
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 10, 10, 20);
		dialog.add(pwTextfield, gbc);
		
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 20, 20, 20);
		dialog.add(disconnectButton, gbc);
		
		dialog.setVisible(true);
		
	}
	
	public void clear() {
		nameTextfield.setText("");
		pwTextfield.setText("");
	}
}
