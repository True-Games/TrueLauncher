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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.codec.Charsets;

public class Type2 {

	protected static void writeAuthPacket(DataOutputStream dos, PlayerAuthData padata) throws IOException {
		//
		//fake 1.7.2 handshake packet format.
		//host = authpacket(AuthConnector + nick + token + password)
		//

		//create frame buffer
		ByteArrayOutputStream frame = new ByteArrayOutputStream();
		DataOutputStream frameOut = new DataOutputStream(frame);
		String authpacket = "AuthConnector|"+padata.getNick()+"|"+padata.getToken()+"|"+padata.getPassword();
		//write handshake packet to frame
		//write packet id
		writeVarInt(frameOut, 0x00);
		//write protocolVersion
		writeVarInt(frameOut, padata.getProtocolVersion());
		//write authpacket instead of hostname
		writeString(frameOut, authpacket);
		//write port
		frameOut.writeShort(padata.getPort());
		//write state
		writeVarInt(frameOut, 2);
		//now write frame to real socket
		//write length
		writeVarInt(dos, frame.size());
		//write packet
		frame.writeTo(dos);
		frame.reset();
		//close frames
		frameOut.close();
		frame.close();
	}

	private static void writeString(DataOutputStream dos, String string) throws IOException {
		byte[] bytes = string.getBytes(Charsets.UTF_8);
		writeVarInt(dos, bytes.length);
		dos.write(bytes);
	}

	private static void writeVarInt(DataOutputStream dos, int varint) throws IOException {
		int part;
		while (true) {
			part = varint & 0x7F;
			varint >>>= 7;
			if (varint!= 0) {
				part |= 0x80;
			}
			dos.writeByte(part);
			if (varint == 0) {
				break;
			}
		}
	}

}
