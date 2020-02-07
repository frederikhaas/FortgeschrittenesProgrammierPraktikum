package exe;

import gui.clientGUI.startup.ConnectWindow;
import gui.clientGUI.startup.LoginWindow;
import network.clientside.Client;
import network.clientside.ReaderThread;

public class ClientExe {
	
	public static void main(String[] args) {
		ConnectWindow connectWindow = new ConnectWindow();	//connects the sockets
		Client client = new Client(connectWindow.port);
		if (client.connected) {
			ReaderThread rthr = new ReaderThread(client);
			rthr.start();
			new LoginWindow(client);
		}
	}
	
}
