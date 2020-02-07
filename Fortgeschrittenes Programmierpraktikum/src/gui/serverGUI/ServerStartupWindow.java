package gui.serverGUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gui.utils.PopUp;
import gui.utils.StandardJDialog;

public class ServerStartupWindow {

	StandardJDialog dialog = new StandardJDialog(new Dimension(200, 180), new GridBagLayout());
	JLabel promptLabel = new JLabel("enter a port");
	JTextField portField = new JTextField();
	JButton startButton = new JButton("start");
	public int port = -1;
	
	public ServerStartupWindow() {
		promptLabel.setPreferredSize(new Dimension(150,20));
		promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
		portField.setMinimumSize(new Dimension(100, 20));
		portField.setPreferredSize(new Dimension(100, 20));
		portField.setToolTipText("port must be a number between 1024 and 65536");
		startButton.setPreferredSize(new Dimension(80, 20));
		
		portField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startButton.doClick();				
			}
		});
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					port = Integer.parseInt(portField.getText());
					if (port < 1025 || port > 65535) {
						new PopUp("port must be a number between 1024 and 65536");
					} else {
						dialog.setVisible(false);
						dialog.dispose();
					}
				} catch (NumberFormatException nfe){
					new PopUp("port must be a number between 1024 and 65536");
				}
			}
		});
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		dialog.add(promptLabel, gbc);
		
		gbc.gridy = 1;
		dialog.add(portField, gbc);
		
		gbc.gridy = 2;
		dialog.add(startButton, gbc);
		
		dialog.setVisible(true);
		
	}
	
}
