package truelauncher.client.auth;

import java.io.DataOutputStream;
import java.io.IOException;

public class Type1 {

	protected static void writeAuthPacket(DataOutputStream dos, final int port, final int protocolversion, final String nick, final String token, final String password) throws IOException {
		//
		// fake 1.6.4 handshake packet changes format.
		// nick = AuthConnector
		// host = authpacket(nick + token + password)
		//

		// write packet id
		dos.write(2);
		// write protocolVersion
		dos.writeByte(protocolversion);
		// write name;
		writeString(dos, "AuthConnector");
		// write authpacket instead of hostname
		String authpacket = nick + "|" + token + "|" + password;
		writeString(dos, authpacket);
		// write port
		dos.writeInt(port);
	}

	private static void writeString(DataOutputStream dos, String string) throws IOException {
		dos.writeShort(string.length());
		dos.writeChars(string);
	}

}
