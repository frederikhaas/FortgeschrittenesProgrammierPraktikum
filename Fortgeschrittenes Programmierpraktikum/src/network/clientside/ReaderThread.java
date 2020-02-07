package network.clientside;

import java.io.BufferedReader;
import java.io.IOException;

import gui.clientGUI.game.GameWindow;
import gui.clientGUI.game.invite.InviteDeclinedWindow;
import gui.clientGUI.game.invite.InviteReceivedWindow;
import gui.clientGUI.startup.ClientMainWindow;
import gui.utils.PopUp;
import utils.Comm;

public class ReaderThread extends Thread {

	Client client;
	BufferedReader bfdIn;

	public ReaderThread(Client Client) {
		client = Client;
		bfdIn = client.bfdIn;
	}

	public void run() {
		String encodedMessage = "";
		while (client.connected) {
			try {
				encodedMessage = bfdIn.readLine();
				decodeMessageAndExactCommcode(encodedMessage);
			} catch (IOException e) {
			}
		}
		try {
			client.socket.close();
		} catch (IOException e) {}
	}

	public void decodeMessageAndExactCommcode(String encodedMessage) {
		int commcode = Comm.extractCommcode(encodedMessage);
		String message = Comm.extractMessage(encodedMessage);
		switch (commcode) {
		case Comm.LOGIN_CHECK_COMM_CODE:
			String result = message.substring(0, message.indexOf(" "));
			message = message.replace(result + " : ", "");
			if (result.equals("true")) {
				client.loggedIn = true;
				client.liw.dialog.setVisible(false);
				client.liw.dialog.dispose();
				client.openWindows.remove(client.liw.dialog);
				ClientMainWindow mainWindow = new ClientMainWindow(client);
				client.openWindows.add(mainWindow.frame);
				client.sendMessage(Comm.encode("", Comm.LOGIN_COMM_CODE));
			} else {
				client.liw.clear();
				new PopUp(message, client);
			}
			break;
		case Comm.LOGIN_COMM_CODE:

			client.mainWindow.chat.dlv.addEntry(message + " has logged in.");
			if (!(message.equals(client.name))) {
				client.mainWindow.onlinePlayers.addEntry(message);
				client.availablePlayers.add(message);
			} else {
				client.availablePlayers.remove(client.name);
			}

			break;
		case Comm.LOGOUT_COMM_CODE:
			if (message.equals(client.name)) {
				client.connected = false;
			}
			if (message.equals("")) {
				System.exit(0);	//server shut-down
			} else {
				client.availablePlayers.remove(message);
				client.mainWindow.onlinePlayers.removeEntry(message);
				client.mainWindow.chat.dlv.addEntry(message + " has logged out");
			}
			
			break;
		case Comm.ONLINE_PLAYERS_COMM_CODE:
			client.mainWindow.onlinePlayers.addEntry(message);
			break;
		case Comm.AVAILABLE_PLAYERS_COMM_CODE:
			if (!(message.equals(client.name)))
				client.availablePlayers.add(message);
			break;
		case Comm.MESSAGE_COMM_CODE:
			client.mainWindow.chat.dlv.addEntry(message);
			break;
		case Comm.UNAVAILABLE_PLAYER_COMM_CODE:
			client.availablePlayers.remove(message);
			break;
		case Comm.INVITE_COMM_CODE:
			String[] parts = message.split(";");
			new InviteReceivedWindow(parts[1], parts[0], client);
			break;
		case Comm.INVITE_ACCEPTED_COMM_CODE:
			String accepted = message.substring(message.indexOf(";") + 1, message.indexOf(":"));
			String name = message.substring(0, message.indexOf(";"));
			String gamename = message.substring(message.indexOf(":") + 1);
			if (accepted.equals("true")) {
				client.isIngame = true;
				client.availablePlayers.remove(name);
				client.gameWindow = new GameWindow(client, gamename, client.name, name, true);
				client.sendMessage(Comm.encode(gamename + ":" + client.name + "-" + name, Comm.NEW_GAME_COMM_CODE));
			} else {
				new InviteDeclinedWindow(client);
				client.invited = "";
			}
			break;
		case Comm.INVITE_CANCELED_COMM_CODE:
			client.invited = "";
			client.invReceivedWin.canceled = true;
			client.invReceivedWin.frame.setVisible(false);
			client.invReceivedWin.frame.dispose();
			client.openWindows.remove(client.invReceivedWin.frame);
			new PopUp("Invite has been canceled", client);
			break;
		case Comm.PLAYER_MOVE_COMM_CODE:
			client.gameWindow.receiveMove(message);
			break;
		case Comm.GAME_QUIT_COMM_CODE:
			if (client.isIngame) {
				new PopUp("The other player has quit.", client);
				client.gameWindow.quitButton.doClick();
				// TODO send END_GAME
			}
			break;
		}
	}

}
