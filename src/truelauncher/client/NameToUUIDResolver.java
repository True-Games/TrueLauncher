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

import truelauncher.client.mojang.profiles.HttpProfileRepository;
import truelauncher.client.mojang.profiles.Profile;
import truelauncher.client.mojang.profiles.ProfileCriteria;

public class NameToUUIDResolver {

	private static final String AGENT = "minecraft";

	public static String resolveUUID(String nick) {
		try {
			HttpProfileRepository repo = new HttpProfileRepository();
			Profile[] profiles = repo.findProfilesByCriteria(new ProfileCriteria(nick, AGENT));
			if (profiles.length == 1) {
				System.out.println(profiles[0].getId());
				return profiles[0].getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "uuid";
	}

}
