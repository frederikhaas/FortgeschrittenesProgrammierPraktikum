package gui.utils;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

import network.clientside.Client;

public class PopUp {
	
	public PopUp(String message) {
		int width = message.length() * 10;
		int height = 20 * message.split("\\n").length;
		
		StandardJDialog dialog = new StandardJDialog(new Dimension(width, 100 + height), null);
		JTextArea errorTextArea = new JTextArea(message);
		errorTextArea.setBackground(dialog.getBackground());
		errorTextArea.setEditable(false);
		JButton okButton = new JButton("ok");
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				dialog.dispose();
			}
		});
		
		dialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		
		errorTextArea.setPreferredSize(new Dimension(width, height));
		dialog.add(errorTextArea, gbc);
		
		okButton.setPreferredSize(new Dimension(50, 20));
		gbc.gridy = 1;		
		dialog.add(okButton, gbc);
		
		dialog.setVisible(true);
	}
	
	public PopUp(String message, Client client) {
		int width = message.length() * 8;
		int height = 20 * message.split("\\n").length;
		
		StandardJDialog dialog = new StandardJDialog(new Dimension(width, 100 + height), null);
		JTextArea errorTextArea = new JTextArea(message);
		errorTextArea.setBackground(dialog.getBackground());
		errorTextArea.setEditable(false);
		JButton okButton = new JButton("ok");
		
		client.openWindows.add(dialog);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.openWindows.remove(dialog);
				dialog.setVisible(false);
				dialog.dispose();
			}
		});
		
		dialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		
		errorTextArea.setPreferredSize(new Dimension(width, height));
		dialog.add(errorTextArea, gbc);
		
		okButton.setPreferredSize(new Dimension(50, 20));
		gbc.gridy = 1;		
		dialog.add(okButton, gbc);
		
		dialog.setVisible(true);
	}
}
