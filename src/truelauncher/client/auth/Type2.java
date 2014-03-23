package truelauncher.client.auth;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.codec.Charsets;

public class Type2 {

	protected static void writeAuthPacket(DataOutputStream dos, final int port, final int protocolversion, final String nick, final String token, final String password) throws IOException {
		//
		//fake 1.7.2 handshake packet format.
		//host = authpacket(AuthConnector + nick + token + password)
		//

		//create frame buffer
		ByteArrayOutputStream frame = new ByteArrayOutputStream();
		DataOutputStream frameOut = new DataOutputStream(frame);
		String authpacket = "AuthConnector|"+nick+"|"+token+"|"+password;
		//write handshake packet to frame
		//write packet id
		writeVarInt(frameOut, 0x00);
		//write protocolVersion
		writeVarInt(frameOut, protocolversion);
		//write authpacket instead of hostname
		writeString(frameOut, authpacket);
		//write port
		frameOut.writeShort(port);
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
