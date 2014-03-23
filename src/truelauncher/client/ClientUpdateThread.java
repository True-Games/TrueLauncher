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
import truelauncher.gcomponents.TButton;
import truelauncher.gcomponents.TComboBox;
import truelauncher.gcomponents.TProgressBar;
import truelauncher.launcher.GUI;
import truelauncher.utils.LauncherUtils;
import truelauncher.utils.Zip;


public class ClientUpdateThread extends Thread {
	// Thread for downloading clients

	private TComboBox selectionbox;
	private TButton downloadbutton;
	private TProgressBar progressbar;
	private String client;

	public ClientUpdateThread(TComboBox selectionbox, TButton downloadbutton, TProgressBar progressbar, String client) {
		this.selectionbox = selectionbox;
		this.downloadbutton = downloadbutton;
		this.progressbar = progressbar;
		this.client = client;
	}

	public void filedownloader(String urlfrom, String clientto)
			throws Exception {

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

		progressbar.setMaximum((int) totalAmount);
		progressbar.setMinimum(0);

		int bufferSize = 0;
		while ((bufferSize = inputstream.read(buffer)) > 0) {
			writer.write(buffer, 0, bufferSize);
			buffer = new byte[153600];
			downloadedAmount += bufferSize;
			final long pbda = downloadedAmount;

			progressbar.setValue((int) pbda);

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


			// remove old zip file
			downloadbutton.setText("Прибираемся");
			new File(tempfile).delete();

			// download packed zip
			downloadbutton.setText("Скачиваем клиент");
			new File(tempfile).getParentFile().mkdirs();
			filedownloader(downloadurl, tempfile);

			// delete old client
			downloadbutton.setText("Удаляем старый клиент");
			deleteDirectory(new File(destination));
			new File(destination).mkdirs();

			// unpack new cient
			downloadbutton.setText("Распаковываем клиент");
			Zip zip = new Zip(progressbar);
			zip.unpack(tempfile, destination);

			// clean garbage
			downloadbutton.setText("Прибираемся");
			new File(tempfile).delete();

			// show finish message
			downloadbutton.setText("Клиент установлен");
			selectionbox.setEnabled(true);

			//recheck client
			GUI.checkClient(client);

		} catch (final Exception ex) {

			downloadbutton.setText("Ошибка");
			selectionbox.setEnabled(true);
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

}
