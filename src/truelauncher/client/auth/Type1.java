package truelauncher.client.auth;

import java.io.DataOutputStream;
import java.io.IOException;

public class Type1 {

	protected static void writeAuthPacket(DataOutputStream dos, PlayerAuthData padata) throws IOException {
		//
		// fake 1.6.4 handshake packet changes format.
		// nick = AuthConnector
		// host = authpacket(nick + token + password)
		//

		// write packet id
		dos.write(2);
		// write protocolVersion
		dos.writeByte(padata.getProtocolVersion());
		// write name;
		writeString(dos, "AuthConnector");
		// write authpacket instead of hostname
		String authpacket = padata.getNick() + "|" + padata.getToken() + "|" + padata.getPassword();
		writeString(dos, authpacket);
		// write port
		dos.writeInt(padata.getPort());
	}

	private static void writeString(DataOutputStream dos, String string) throws IOException {
		dos.writeShort(string.length());
		dos.writeChars(string);
	}

}
