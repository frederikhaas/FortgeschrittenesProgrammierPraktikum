package network.clientside;

import java.awt.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import gui.clientGUI.game.GameWindow;
import gui.clientGUI.game.invite.InviteReceivedWindow;
import gui.clientGUI.startup.ClientMainWindow;
import gui.clientGUI.startup.LoginWindow;
import utils.BufferedLineWriter;

public class Client {

	public int port;
	public String name;
	
	// state
	public boolean connected = false;
	public boolean loggedIn = false;

	// communication
	public BufferedLineWriter bfdOut;
	BufferedReader bfdIn;
	public Socket socket;
	
	// game
	public boolean isIngame = false;
	public String invited = "";
	public Vector<String> availablePlayers = new Vector<String>();
	public ArrayList<Window> openGameStartup = new ArrayList<Window>();
	public boolean starting = false;

	//frontend
	public ArrayList<Window> openWindows = new ArrayList<Window>();
	public LoginWindow liw;
	public GameWindow gameWindow;
	public ClientMainWindow mainWindow;
	public InviteReceivedWindow invReceivedWin;
	
	public Client(int Port) {
		port = Port;
		try {
			socket = new Socket("localhost", port);
			bfdOut = new BufferedLineWriter(new OutputStreamWriter(socket.getOutputStream()));
			bfdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			connected = true;
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
	}
	
	public void sendMessage(String encodedMessage) {	//encoded means smth. like /COMMAND_COMMANDTYPE+messagetext+/COMMAND_COMMANDTYPE -> decoding happens via COMMANDTYPE
		try {
			bfdOut.writelnAndFlush(encodedMessage);
		} catch (IOException e) {}
	}

}
