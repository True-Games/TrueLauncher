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

package truelauncher.userprefs.fields;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import truelauncher.config.AllSettings;
import truelauncher.utils.CryptoUtils;
import truelauncher.utils.LauncherUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserFieldsChoice {

	private static File file = new File(LauncherUtils.getDir() + File.separator + AllSettings.getLauncherConfigFolderPath() + File.separator + "userfieldschoice");
	private static JSONConfig jsonconfig = new JSONConfig();
	public static void loadConfig()
	{
		if (file.exists())
		{
			try {
				Gson gson = new GsonBuilder().create();
				Reader gsonreader = new InputStreamReader(new FileInputStream(file));
				jsonconfig = gson.fromJson(gsonreader, JSONConfig.class);
				nick = jsonconfig.nick;
				if (!jsonconfig.password.isEmpty()) {
					password = CryptoUtils.decryptString(jsonconfig.password);
				}
				launchclient = jsonconfig.launchclient;
				downloadclient = jsonconfig.downloadclient;
			} catch (Exception e) {
				LauncherUtils.logError(e);
			}
		}
		loadLegacy();
	}
	private static void loadLegacy() {
		try {
			String ps = LauncherUtils.getDir();
			File config = new File(ps + File.separator + AllSettings.getLauncherConfigFolderPath() + File.separator + "launcherdata");
			if (config.exists()) {
				Scanner scanner = new Scanner(config);
				String nickstring = scanner.nextLine();
				String passwordstring = scanner.nextLine();
				nick = nickstring;
				if (!passwordstring.isEmpty()) {
					password = CryptoUtils.decryptString(passwordstring);
				}
				scanner.close();
				config.delete();
			}
		} catch (Exception e) {
			LauncherUtils.logError(e);
		}

	}
	public static void saveBlock1Config()
	{
		try {
			jsonconfig.nick = nick;
			if (!password.isEmpty()) {
				jsonconfig.password = CryptoUtils.encryptString(password);
			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(file);
			writer.write(gson.toJson(jsonconfig));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LauncherUtils.logError(e);
		}
	}
	public static void saveBlock23Config()
	{
		try {
			jsonconfig.launchclient = launchclient;
			jsonconfig.downloadclient = downloadclient;
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writer = new FileWriter(file);
			writer.write(gson.toJson(jsonconfig));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LauncherUtils.logError(e);
		}
	}

	public static String nick = "NoNickName";
	public static String password = "";
	public static String launchclient = "none";
	public static String downloadclient = "none";

}
