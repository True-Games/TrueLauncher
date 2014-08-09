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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import truelauncher.config.AllSettings;
import truelauncher.events.Event;
import truelauncher.events.EventBus;
import truelauncher.utils.LauncherUtils;
import truelauncher.utils.Zip;

public class ClientUpdateThread extends Thread {
	// Thread for downloading clients

	private String client;

	public ClientUpdateThread(String client) {
		this.client = client;
	}

	public void filedownloader(String urlfrom, String clientto) throws Exception {

		URL url = new URL(urlfrom);
		URLConnection conn = url.openConnection();
		if ((conn instanceof HttpURLConnection)) {
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.connect();
		}

		InputStream inputstream = conn.getInputStream();

		FileOutputStream writer = new FileOutputStream(clientto);
		byte[] buffer = new byte[153600];

		long downloadedAmount = 0;
		final long totalAmount = conn.getContentLength();

		ClientDownloadStartedEvent startedevent = new ClientDownloadStartedEvent(totalAmount);
		EventBus.callEvent(startedevent);

		int bufferSize = 0;
		while ((bufferSize = inputstream.read(buffer)) > 0) {
			writer.write(buffer, 0, bufferSize);
			buffer = new byte[153600];
			downloadedAmount += bufferSize;

			ClientDownloadRunningEvent runningevent = new ClientDownloadRunningEvent(downloadedAmount);
			EventBus.callEvent(runningevent);
		}

		writer.close();
		inputstream.close();
	}

	@Override
	public void run() {
		try {
			//prepare some variables
			String downloadurl = AllSettings.getClientDownloadLinkByName(client);
			String tempfile = LauncherUtils.getDir() + File.separator + AllSettings.getClientsTempFolderPath() + File.separator + new File(new URL(downloadurl).getFile()).getName();
			String destination = LauncherUtils.getDir() + File.separator + AllSettings.getClientFolderByName(client);

			ClientDownloadStageChangeEvent changeevent;

			// remove old zip file
			changeevent = new ClientDownloadStageChangeEvent("Прибираемся");
			EventBus.callEvent(changeevent);
			new File(tempfile).delete();

			// download packed zip
			changeevent = new ClientDownloadStageChangeEvent("Скачиваем клиент");
			EventBus.callEvent(changeevent);
			new File(tempfile).getParentFile().mkdirs();
			filedownloader(downloadurl, tempfile);

			// delete old client
			changeevent = new ClientDownloadStageChangeEvent("Удаляем старый клиент");
			EventBus.callEvent(changeevent);
			deleteDirectory(new File(destination));
			new File(destination).mkdirs();

			// unpack new client
			changeevent = new ClientDownloadStageChangeEvent("Распаковываем клиент");
			EventBus.callEvent(changeevent);
			Zip zip = new Zip();
			zip.unpack(tempfile, destination);

			// clean garbage
			changeevent = new ClientDownloadStageChangeEvent("Прибираемся");
			EventBus.callEvent(changeevent);
			new File(tempfile).delete();

			// finish donwload
			ClientDownloadFinishedEvent finishevent = new ClientDownloadFinishedEvent(client);
			EventBus.callEvent(finishevent);


		} catch (final Exception ex) {

			LauncherUtils.logError(ex);

		}
	}

	public void deleteDirectory(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				deleteDirectory(f);
			}
			file.delete();
		} else {
			file.delete();
		}
	}

	public static class ClientDownloadStartedEvent extends Event {

		private long clientfilesize;

		public ClientDownloadStartedEvent(long clientfilesize) {
			this.clientfilesize = clientfilesize;
		}

		public long getClientFileSize() {
			return clientfilesize;
		}

	}

	public static class ClientDownloadRunningEvent extends Event {
		
		private long currentDownloadSize;

		public ClientDownloadRunningEvent(long currentDownloadSize) {
			this.currentDownloadSize = currentDownloadSize;
		}

		public long getDownloadedAmount() {
			return currentDownloadSize;
		}

	}

	public static class ClientDownloadStageChangeEvent extends Event {
		
		private String stage;

		public ClientDownloadStageChangeEvent(String stage) {
			this.stage = stage;
		}

		public String getStage() {
			return stage;
		}

	}

	public static class ClientDownloadFinishedEvent extends Event {
		
		private String client;

		public ClientDownloadFinishedEvent(String client) {
			this.client = client;
		}

		public String getClient() {
			return client;
		}

	}

	public static class ClientDownloadFailedEvent extends Event {
	}

}
