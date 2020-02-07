package gui.clientGUI.startup;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gui.utils.PopUp;

public class ConnectWindow {
	
	JDialog dialog = new JDialog();
	JLabel promptLabel = new JLabel("please enter a port");
	JTextField portTextfield = new JTextField();
	JButton connectButton = new JButton("connect");
	
	public int port;
	
	public ConnectWindow() {
		dialog.setSize(new Dimension(280, 160));
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
		dialog.setLayout(new GridBagLayout());
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		promptLabel.setPreferredSize(new Dimension(200, 20));
		promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		portTextfield.setMinimumSize(new Dimension(120, 20));
		portTextfield.setToolTipText("port must be a number between 1024 and 65536");
		
		connectButton.setPreferredSize(new Dimension(80, 20));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(20, 40, 10, 40);
		dialog.add(promptLabel, gbc);
		
		gbc.insets = new Insets(10, 80, 10, 80);
		gbc.gridy = 1;
		dialog.add(portTextfield, gbc);
		
		gbc.insets = new Insets(10, 100, 40, 100);
		gbc.gridy = 2;
		dialog.add(connectButton, gbc);
		
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				int input = Integer.parseInt(portTextfield.getText());
				if (input < 1024 ||input > 65536) {
					new PopUp(portTextfield.getToolTipText());
					portTextfield.setText("");
				} else {
					port = input;
					dialog.setVisible(false);
					dialog.dispose();
					return;
				}
				} catch (NumberFormatException nfe) {
					new PopUp(portTextfield.getToolTipText());
					portTextfield.setText("");
				}	
			}
		});
		
		portTextfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connectButton.doClick();
			}
		});
		
		dialog.setVisible(true);
	}
	
}
