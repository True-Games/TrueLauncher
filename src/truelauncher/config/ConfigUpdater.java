package truelauncher.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import truelauncher.utils.LauncherUtils;

public class ConfigUpdater {

	protected static void updateConfig(final File configfile)
	{
		//update config
		Thread update = new Thread(){
			public void run()
			{
				try {
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
				} catch (Exception e) {LauncherUtils.logError(e);}
			}
		};
		update.start();
	}
	
}
