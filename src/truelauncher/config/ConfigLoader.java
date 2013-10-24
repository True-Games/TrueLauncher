package truelauncher.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

import truelauncher.utils.LauncherUtils;

public class ConfigLoader {

	protected static void loadConfig(File configfile) throws FileNotFoundException
	{
		if (configfile.exists())
		{
			Scanner in = new Scanner(configfile);
			int clientsnumber = Integer.valueOf(in.nextLine().split("[=]")[1]);
			in.nextLine();
			AllSettings.clientfolders = new String[clientsnumber][5];
			for (int i = 0; i < clientsnumber; i++)
			{
				String client = in.nextLine();
				client = client.replace("\"", "");
				AllSettings.clientfolders[i] = client.split("\\,");
			}
			in.nextLine();
			AllSettings.tempfolder = in.nextLine();
			AllSettings.tempfolder = AllSettings.tempfolder.replace("\"", "");
			AllSettings.downloadclients = new String[clientsnumber][3];
			for (int i = 0; i < clientsnumber; i++)
			{
				String client = in.nextLine();
				client = client.replace("\"", "");
				AllSettings.downloadclients[i] = client.split("\\,");
			}
			in.nextLine();
			while (in.hasNextLine())
			{
				String lib = in.nextLine();
				lib = lib.replace("\"", "");
				AllSettings.clientlibs.add(lib);
			}
			in.close();
		}
		else
		{
			try {
				configfile.getParentFile().mkdirs();
				BufferedInputStream in = new BufferedInputStream(ConfigLoader.class.getResourceAsStream(configfile.getName()));
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(configfile));
				byte[] buf = new byte[4096];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
				loadConfig(configfile);
			}
   			catch (Exception e) {LauncherUtils.logError(e);}
			//load predefined config from laucnher and reload settings
			//loadConfig();
		}
	}
	
}
