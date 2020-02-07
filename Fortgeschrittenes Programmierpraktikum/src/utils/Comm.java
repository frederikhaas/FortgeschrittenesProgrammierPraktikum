package utils;


public class Comm {

	// TODO fields containing all commands
	// TODO encoding : concatenate message to: "COMMCODE_x", where x stands for the COMM_CODE listed in class Comm
	
	public static final int DISCONNECT_COMM_CODE = 0;
	public static final int CLIENT_SENT_NAME_COMM_CODE = 1;
	public static final int CLIENT_SENT_PW_COMM_CODE = 2;
	public static final int LOGIN_CHECK_COMM_CODE = 3;	//sent from UserThread to encode answer for login-data-check
	public static final int LOGOUT_COMM_CODE = 4;
	public static final int MESSAGE_COMM_CODE = 5;
	public static final int INVITE_COMM_CODE = 6;
	public static final int NEW_GAME_COMM_CODE = 7;
	public static final int PLAYER_MOVE_COMM_CODE = 8;
	public static final int GAME_END_COMM_CODE = 9;
	public static final int GAME_QUIT_COMM_CODE = 10;
	public static final int INVITE_ACCEPTED_COMM_CODE = 11;
	public static final int INVITE_CANCELED_COMM_CODE = 12;
	public static final int ONLINE_PLAYERS_COMM_CODE = 13;
	public static final int AVAILABLE_PLAYERS_COMM_CODE = 14;
	public static final int USER_NOT_ONLINE_COMM_CODE = 15;
	public static final int LOGIN_COMM_CODE = 16;
	public static final int UNAVAILABLE_PLAYER_COMM_CODE = 17;
	

	public static String encode(String message, int COMM_CODE) {
		message = "COMMCODE_" + COMM_CODE + " " + message;
		return message;
	}
	
	public static int extractCommcode(String encodedMessage) {
		String codeString = encodedMessage.substring(0, encodedMessage.indexOf(" "));
		int Commcode = Integer.parseInt(codeString.substring(9).trim());
		return Commcode;
	}

	public static String extractMessage(String encodedMessage) {
		return encodedMessage.substring(encodedMessage.indexOf(" ") + 1);
	}
}
