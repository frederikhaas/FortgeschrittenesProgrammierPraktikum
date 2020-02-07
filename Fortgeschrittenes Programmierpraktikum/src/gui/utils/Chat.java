package gui.utils;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import network.clientside.Client;
import utils.Comm;

public class Chat {

	private Dimension dim;
	private Container mainContainer;
	public DynamicViewer dlv;
	private JTextField inputField = new JTextField();
	private String message;

	public Chat(Dimension d, Client client) {
		dim = d;
		mainContainer = new JPanel();
		mainContainer.setMinimumSize(dim);
		dlv = new DynamicViewer(new Dimension(dim.width, dim.height - 30));
		inputField.setMinimumSize(new Dimension(dim.width, 20));
		inputField.setPreferredSize(new Dimension(dim.width, 20));
		mainContainer.add(dlv.getContainer());
		mainContainer.add(inputField);

		inputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				message = inputField.getText();
					if (!message.equals("")) {
						client.sendMessage(Comm.encode(client.name + " : " + message, Comm.MESSAGE_COMM_CODE));
						message = "";
						inputField.setText("");
					}
			}
		});
	}

	public void receiveMessage(String message) {
		dlv.addEntry(message);
	}

	public Container getChatContainer() {
		return mainContainer;
	}
}
