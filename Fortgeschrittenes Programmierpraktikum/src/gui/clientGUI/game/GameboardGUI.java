package gui.clientGUI.game;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JPanel;

import network.clientside.Client;

public class GameboardGUI {

	Client client;
	Container container = new JPanel();
	JButton undoButton = new JButton("undo");
	JButton confirmButton = new JButton("confirm");
	
	public Container getContainer() {
		return container;
	}
	
}
