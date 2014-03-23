package truelauncher.client.auth;

public class PlayerAuthData {

	private int port; 
	private int protocolversion; 
	private String nick;
	private String token;
	private String password;
	public PlayerAuthData(int port, int protocolversion, String nick, String token, String password) {
		this.port = port;
		this.protocolversion = protocolversion;
		this.nick = nick;
		this.token = token;
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public int getProtocolVersion() {
		return protocolversion;
	}

	public String getNick() {
		return nick;
	}

	public String getToken() {
		return token;
	}

	public String getPassword() {
		return password;
	}

}
