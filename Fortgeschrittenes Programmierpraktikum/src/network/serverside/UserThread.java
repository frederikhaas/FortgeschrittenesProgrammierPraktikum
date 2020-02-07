package network.serverside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Collection;

import utils.BufferedLineWriter;
import utils.Comm;

public class UserThread extends Thread {

	Socket socket;
	Server server;
	BufferedLineWriter bfdOut;
	BufferedReader bfdIn;
	boolean connected = true;

	String userName = "";
	String playingWith = "";
	String gamename;

	public UserThread(Socket Socket, Server Server) {
		socket = Socket;
		server = Server;
		try {
			bfdOut = new BufferedLineWriter(new OutputStreamWriter(socket.getOutputStream()));
			bfdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO difficult to tell client anything...
		}
	}

	public void run() {
		String encodedMessage = "";
		while (connected) {
			try {
				encodedMessage = bfdIn.readLine();
				decodeMessageAndExactCommcode(encodedMessage);
				// TODO decode message and act depending on commandcode
			} catch (IOException e) {
			}
		}
	}

	public void broadcastMessage(String encodedMessage) {
		String message = Comm.extractMessage(encodedMessage);
		int commcode = Comm.extractCommcode(encodedMessage);
		if (commcode == Comm.LOGIN_COMM_CODE) {
			server.scw.chat.addEntry(userName + " has logged in");
		} else if (commcode == Comm.LOGOUT_COMM_CODE) {
			server.scw.chat.addEntry(userName + " has logged out");
		} else if (commcode == Comm.MESSAGE_COMM_CODE) {
			server.scw.chat.addEntry(message);
		}
		for (UserThread user : server.onlineUsers.values()) {
			user.sentMessageToClient(encodedMessage);
		}
	}

	public void sentMessageToClient(String encodedMessage) {
		try {
			bfdOut.writelnAndFlush(encodedMessage);
		} catch (IOException e) {
		}
	}

	public void sendList(Collection<String> list, int commcode) {
		for (String item : list) {
			sentMessageToClient(Comm.encode(item, commcode));
		}
	}

	private void decodeMessageAndExactCommcode(String encodedMessage) {
		int commcode = Comm.extractCommcode(encodedMessage);
		String message = Comm.extractMessage(encodedMessage);
		switch (commcode) {
		case Comm.DISCONNECT_COMM_CODE:
			connected = false;
			break;
		case Comm.CLIENT_SENT_NAME_COMM_CODE:
			userName = message;
			break;
		case Comm.CLIENT_SENT_PW_COMM_CODE:
			server.checkLoginData(userName, message, this);
			break;
		case Comm.LOGIN_COMM_CODE:
			server.onlineUsers.put(userName, this);
			server.availableUsers.put(userName, this);
			sendList(server.onlineUsers.keySet(), Comm.ONLINE_PLAYERS_COMM_CODE);
			sendList(server.availableUsers.keySet(), Comm.AVAILABLE_PLAYERS_COMM_CODE);
			broadcastMessage(Comm.encode(userName, Comm.LOGIN_COMM_CODE));
			server.scw.onlinePlayers.addEntry(userName);
			break;
		case Comm.LOGOUT_COMM_CODE:
			server.availableUsers.remove(userName);
			server.scw.onlinePlayers.removeEntry(userName);
			broadcastMessage(Comm.encode(userName, Comm.LOGOUT_COMM_CODE));
			server.onlineUsers.remove(userName);
			connected = false;
			break;
		case Comm.MESSAGE_COMM_CODE:
			broadcastMessage(encodedMessage);
			break;
		case Comm.INVITE_COMM_CODE:
			String[] parts = message.split(";");
			UserThread invited = server.availableUsers.get(parts[1]);// message is simply the game and the name of the
																		// invited
			server.availableUsers.remove(parts[1]);
			server.availableUsers.remove(userName);
			invited.sentMessageToClient(Comm.encode(parts[0] + ";" + userName, Comm.INVITE_COMM_CODE));
			broadcastMessage(Comm.encode(userName, Comm.UNAVAILABLE_PLAYER_COMM_CODE));
			broadcastMessage(Comm.encode(parts[1], Comm.UNAVAILABLE_PLAYER_COMM_CODE));
			break;
		case Comm.INVITE_ACCEPTED_COMM_CODE:
			String accepted = message.substring(message.indexOf(";") + 1, message.indexOf(":"));
			String name = message.substring(0, message.indexOf(";"));
			server.onlineUsers.get(name).sentMessageToClient(Comm.encode(userName + message.substring(message.indexOf(";")), Comm.INVITE_ACCEPTED_COMM_CODE));
			if (accepted.equals("false")) {
				server.availableUsers.put(userName, this);
				server.availableUsers.put(name, server.onlineUsers.get(name));
				broadcastMessage(Comm.encode(userName, Comm.AVAILABLE_PLAYERS_COMM_CODE));
				broadcastMessage(Comm.encode(name, Comm.AVAILABLE_PLAYERS_COMM_CODE));
			} else {
				playingWith = name;
			}
			break;
		case Comm.INVITE_CANCELED_COMM_CODE:
			server.onlineUsers.get(message).sentMessageToClient(Comm.encode("", Comm.INVITE_CANCELED_COMM_CODE));
			broadcastMessage(Comm.encode(message, Comm.AVAILABLE_PLAYERS_COMM_CODE));
			break;
		case Comm.NEW_GAME_COMM_CODE: // sent by invite-sender together with game-data in format : decodedMessage =
										// gamename:player1-player2
			broadcastMessage(Comm.encode(userName, Comm.UNAVAILABLE_PLAYER_COMM_CODE));
			server.availableUsers.remove(userName);
			playingWith = message.substring(message.indexOf("-") + 1);
			if (!(playingWith.equals("pc"))) {
				server.availableUsers.remove(playingWith);
				broadcastMessage(Comm.encode(playingWith, Comm.UNAVAILABLE_PLAYER_COMM_CODE));
			}
			message = message.replace(":", " : ").replace("-", " - ");
			if (!(server.runningGames.contains(message))) {
				server.runningGames.add(message);
				server.scw.games.addEntry(message);
			}
			break;
		case Comm.PLAYER_MOVE_COMM_CODE:
			server.onlineUsers.get(playingWith).sentMessageToClient(encodedMessage);
			break;
		case Comm.GAME_QUIT_COMM_CODE:
			server.availableUsers.put(userName, this);
			for (String game : server.runningGames) {
				if (game.contains(message)) {
					server.runningGames.remove(game);
					server.scw.games.removeEntry(game);
					break;
				}
			}
			if (!(message.equals("pc"))) {
				server.onlineUsers.get(message).sentMessageToClient(Comm.encode("", Comm.GAME_QUIT_COMM_CODE));
				broadcastMessage(Comm.encode(message, Comm.AVAILABLE_PLAYERS_COMM_CODE));
			}
			break;
		default:
			// maybe send error message back to client
		}
	}
}
