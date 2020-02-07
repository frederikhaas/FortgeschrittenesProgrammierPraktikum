package network.serverside;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gui.serverGUI.ServerControlWindow;
import utils.Comm;

public class Server {

	// connection
	public int port;
	ServerSocket serverSocket = null;

	// state
	boolean running = true;

	// data
	Map<String, String> loginData = new HashMap<String, String>();
	Map<String, UserThread> onlineUsers = new HashMap<String, UserThread>();
	Map<String, UserThread> availableUsers = new HashMap<String, UserThread>();
	ArrayList<String> runningGames = new ArrayList<String>();

	// front-end
	public ServerControlWindow scw;

	public Server(int port) {
		this.port = port;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO handle missing serversocket
		}
	}

	public void runServer() {
		try {
			while (running) {
				Socket socket = serverSocket.accept();
				UserThread userThread = new UserThread(socket, this);	
				userThread.start();
				//userThread handles login
				//userThread puts data into server-lists, so server can handle new connections
			}
		} catch (IOException e) {
			// do nothing
		}
		//once server stops running logout every user and close socket
	}
	
	public void checkLoginData(String name, String pw, UserThread user) {
		if (!(loginData.containsKey(name))) {
			loginData.put(name, pw);
			user.sentMessageToClient(Comm.encode("true : Welcome " + name, Comm.LOGIN_CHECK_COMM_CODE));
		} else if (onlineUsers.containsKey(name)) {
			user.sentMessageToClient(Comm.encode("false : this user is already logged in", Comm.LOGIN_CHECK_COMM_CODE));
		} else if (!(loginData.get(name).equals(pw))) {
			user.sentMessageToClient(Comm.encode("false : wrong login-data", Comm.LOGIN_CHECK_COMM_CODE));
		} else {
			user.sentMessageToClient(Comm.encode("true : Welcome back" + name, Comm.LOGIN_CHECK_COMM_CODE));
		}
	}
	
	public void forceLogoutAll() {
		for(UserThread user : onlineUsers.values()) {
			user.sentMessageToClient(Comm.encode("", Comm.LOGOUT_COMM_CODE));
		}
	}
	
}
