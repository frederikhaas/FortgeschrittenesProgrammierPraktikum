package exe;

import gui.serverGUI.ServerControlWindow;
import gui.serverGUI.ServerStartupWindow;
import network.serverside.Server;

public class ServerExe {

	public static void main(String[] args) {
		ServerStartupWindow ssuw = new ServerStartupWindow();
		if (ssuw.port > 0) {
			Server server = new Server(ssuw.port);
			new ServerControlWindow(server);
			server.runServer();
		}
	}
}
