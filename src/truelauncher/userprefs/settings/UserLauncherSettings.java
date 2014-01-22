package truelauncher.userprefs.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import truelauncher.config.AllSettings;
import truelauncher.utils.LauncherUtils;

public class UserLauncherSettings {

	private static File file = new File(LauncherUtils.getDir() + File.separator + AllSettings.getLauncherConfigFolderPath() + File.separator + "userlaunchersettings");
	public static void loadConfig()
	{
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
	public static void saveConfig()
	{
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
