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
			AllSettings.tempfolder = in.nextLine().split("[=]")[1];
			AllSettings.libsfolder = in.nextLine().split("[=]")[1];
			int clientsnumber = Integer.valueOf(in.nextLine().split("[=]")[1]);
			in.nextLine();
			for (int i = 0; i < clientsnumber; i++)
			{
				String[] launchdata = in.nextLine().replace("\"", "").split("\\,");
				ClientLaunchData clientlaunchdataobject = new ClientLaunchData(launchdata[1],launchdata[2],launchdata[3],launchdata[4]);
				AllSettings.clientslaunchdata.put(launchdata[0], clientlaunchdataobject);
			}
			in.nextLine();
			for (int i = 0; i < clientsnumber; i++)
			{
				String[] downloaddata = in.nextLine().replace("\"", "").split("\\,");
				ClientDownloadData clientdownloaddataobject = new ClientDownloadData(downloaddata[1],downloaddata[2]);
				AllSettings.clientsdownloaddata.put(downloaddata[0], clientdownloaddataobject);
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
		}
	}
	
}
