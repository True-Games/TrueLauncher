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

package truelauncher.userprefs.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;

import truelauncher.config.AllSettings;
import truelauncher.utils.LauncherUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserLauncherSettings {

	private static File file = new File(LauncherUtils.getDir() + File.separator + AllSettings.getLauncherConfigFolderPath() + File.separator + "userlaunchersettings");
	public static void loadConfig() {
		if (file.exists())
		{
			try {
				Gson gson = new GsonBuilder().create();
				Reader gsonreader = new InputStreamReader(new FileInputStream(file));
				JSONConfig jsonconfig = gson.fromJson(gsonreader, JSONConfig.class);
				updatelauncher = jsonconfig.checklauncherupdates;
				updateclient = jsonconfig.checkclientsupdates;
				doerrlog = jsonconfig.writeerrorlog;
			} catch (Exception e) {
				LauncherUtils.logError(e);
			}
		}
	}
	public static void saveConfig() {
		try {
			JSONConfig jsonconfig = new JSONConfig();
			jsonconfig.checkclientsupdates = updateclient;
			jsonconfig.checklauncherupdates = updatelauncher;
			jsonconfig.writeerrorlog = doerrlog;
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(file);
			writer.write(gson.toJson(jsonconfig));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LauncherUtils.logError(e);
		}
	}

	public static boolean updatelauncher = true;
	public static boolean updateclient = true;
	public static boolean doerrlog = true;

}
