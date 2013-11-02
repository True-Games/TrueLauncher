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

import truelauncher.utils.LauncherUtils;

public class Auth {

	public static void sendAuth1(String hostname, int port, String token, String password)
	{
		try {
			//TODO: send authstring as handshake packet
			//Socket socket = new Socket(hostname, port);
		} catch (Exception e) {
			LauncherUtils.logError(e);
		}
	}
	
	public static void sendAuth2(String hostname, int port, String token, String password)
	{
		
	}
	
}
