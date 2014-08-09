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

package truelauncher.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.codec.Charsets;

import truelauncher.client.auth.Auth;
import truelauncher.client.auth.PlayerAuthData;
import truelauncher.config.AllSettings;
import truelauncher.utils.LauncherUtils;

public class ClientOutputReader extends Thread {

	private Process p;
	private String password;

	public ClientOutputReader(Process p, String password) {
		this.password = password;
		this.p = p;
	}

	@Override
	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), Charsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				if (line.contains("AuthConnector") && !password.isEmpty()) {
					doAuth(line);
				}
			}
		} catch (Exception e) {
			LauncherUtils.logError(e);
		}
	}

	private void doAuth(String message) {
		// loginsystem server string format:
		// AuthConnector|authtype|protocolversion|host|port|nick|token|
		String[] paramarray = message.split("[|]");
		int authtype = Integer.parseInt(paramarray[1]);
		String host = paramarray[3];
		int port = Integer.parseInt(paramarray[4]);
		int protocolversion = Integer.parseInt(paramarray[2]);
		String nick = paramarray[5];
		String token = paramarray[6];
		if (isAddressAllowed(host)) {
			PlayerAuthData padata = new PlayerAuthData(port, protocolversion, nick, token, password);
			if (authtype == 1) {// 1.6.4 and earlier
				Auth.sendAuth1(host, padata);
			} else if (authtype == 2) {// 1.7.2 and newer
				Auth.sendAuth2(host, padata);
			}
		}
	}

	private static boolean isAddressAllowed(String address) {
		return AllSettings.getAllowedAuthAddresses().contains(address);
	}

}
