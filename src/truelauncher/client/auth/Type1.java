/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 */

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
