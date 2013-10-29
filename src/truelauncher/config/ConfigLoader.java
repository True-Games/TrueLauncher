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

package truelauncher.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import truelauncher.utils.LauncherUtils;

public class ConfigLoader {

	private static int failedcounter = 0;
	protected static void loadConfig(File configfile) throws Exception
	{
		if (failedcounter == 3) 
		{
			//we failed to load config 3 times in a row, giving up
			throw new Exception("failed to load config 3 times in a row");
		}
		

		if (configfile.exists())
		{
			//load gson builder
			Gson gson = new GsonBuilder().create();
			Reader gsonreader = new InputStreamReader(new FileInputStream(configfile));
			//try to parse config
			try {
				JSONConfig jsonconfig = gson.fromJson(gsonreader, JSONConfig.class);
				//check if we have at least 1 client in config()
				if (jsonconfig.getClientDataMap().size() > 0)
				{
					//load settings
					AllSettings.tempfolder = jsonconfig.getTempFolder();
					AllSettings.libsfolder = jsonconfig.getLibsFolder();
					AllSettings.clientsdata = jsonconfig.getClientDataMap();
				} else
				{
					//no clients in config, WTF?
					//increment fail counter
					failedcounter++;
					//copy config from jar
					copyConfigFromJar(configfile);
					//now load it
					loadConfig(configfile);
				}
			} catch (Exception e) {
				//increment fail counter
				failedcounter++;
				//copy config from jar
				copyConfigFromJar(configfile);
				//now load it
				loadConfig(configfile);
			}
		}
		else
		{
			//increment fail counter
			failedcounter++;
			//copy config from jar
			copyConfigFromJar(configfile);
			//now load it
			loadConfig(configfile);
		}
	}
	
	
	private static void copyConfigFromJar(File configfile)
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
		}
		catch (Exception e) {
			LauncherUtils.logError(e);
		}
	}
	
}
