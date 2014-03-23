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

package truelauncher.launcher;

import java.net.URL;

import truelauncher.config.AllSettings;
import truelauncher.userprefs.settings.UserLauncherSettings;
import truelauncher.utils.LauncherUtils;


public class LauncherVersionChecker extends Thread {

	@Override
	public void run()
	{
		if (UserLauncherSettings.updatelauncher) {
			try {
				Thread.sleep(1000);
				int newversion = Integer.MAX_VALUE;
				newversion = Integer.valueOf(LauncherUtils.readURLStreamToString(new URL(AllSettings.getLauncherWebUpdateURLFolder()+"version").openStream()));
				if (AllSettings.getLauncherVerison() < newversion)
				{
					GUI.openUpdateWindow();
				}
			} catch (Exception e) {
	        	LauncherUtils.logError(e);
			}
		}
	}

}
