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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import truelauncher.main.GUI;
import truelauncher.utils.LauncherUtils;

public class ConfigUpdater {

	protected static void startConfigUpdater(final File configfile)
	{
		//update config
		Thread update = new Thread(){
			public void run()
			{
				try {
					if (isUpdateNeeded())
					{
						updateConfig(configfile);
						Thread.sleep(1000);
						AllSettings.reload();
						GUI.refreshClients();
					}
				} catch (Exception e) {
					LauncherUtils.logError(e);
				}
			}
		};
		update.start();
	}
	
	private static boolean isUpdateNeeded()
	{
		try {
			URL url = new URL(AllSettings.getLauncherWebUpdateURLFolder()+"clientsversion");
			int latest = Integer.valueOf(LauncherUtils.readURLStreamToString(url.openStream()));
			if (AllSettings.getClientConfigVersion() < latest)
			{
				return true;
			}
		}  catch (Exception e) {
			LauncherUtils.logError(e);
		}
		return false;
	}
	
	private static void updateConfig(final File configfile) throws IOException
	{
		URL url = new URL(AllSettings.getLauncherWebUpdateURLFolder()+configfile.getName());
		URLConnection conn = url.openConnection();

		if ((conn instanceof HttpURLConnection)) {
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.connect();
		}
		InputStream inputstream = conn.getInputStream();

		FileOutputStream writer = new FileOutputStream(configfile);
		byte[] buffer = new byte[153600];

		int bufferSize = 0;
		while ((bufferSize = inputstream.read(buffer)) > 0) {
			writer.write(buffer, 0, bufferSize);
			buffer = new byte[153600];
		}

		writer.close();
		inputstream.close();
	}
	
}
