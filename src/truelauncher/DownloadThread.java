package truelauncher;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Display;

public class DownloadThread extends Thread {



	    private GUI gui;
		private Display display;
	    private String urlfrom;
	    private String clientto;
	    private String unpackto;
	    private String tempfolder;
	    DownloadThread(GUI gui,Display display, String urlfrom, String clientto, String unpackto)
	    {
	    	try {
	    this.gui = gui;
		this.display = display;
	    this.urlfrom = urlfrom;
	    this.tempfolder = clientto;
	    this.clientto = clientto +"/"+new File(new URL(this.urlfrom).getFile()).getName();
	    this.unpackto = LauncherUtils.getDir()+File.separator+unpackto;
	    	} catch (Exception e) {e.printStackTrace();}
	    }
	    
	 
	       public void filedownloader(String urlfrom, String clientto) throws Exception 
	       {
	                if (!((new File(tempfolder)).exists())) {
	                    (new File(tempfolder)).mkdirs();
	                }
	                URL url = new URL(urlfrom);
	                
	                URLConnection conn = url.openConnection();
	                
	               if ((conn instanceof HttpURLConnection)) {
	                conn.setRequestProperty("Cache-Control",
								"no-cache");
			conn.connect();}
			InputStream inputstream = conn.getInputStream();

	        FileOutputStream writer = new FileOutputStream(clientto);
	        byte[] buffer = new byte[153600];
	                
	        int downloadedAmount = 0;
			final int totalAmount = conn.getContentLength();
			display.asyncExec(new Runnable(){
				public void run()
				{
					gui.pbar.setMaximum(totalAmount);
					gui.pbar.setMinimum(0);
				}
			});
			int bufferSize = 0;
			while ((bufferSize = inputstream.read(buffer)) > 0) {
			writer.write(buffer, 0, bufferSize);
			buffer = new byte[153600];
			downloadedAmount += bufferSize;
			final int pbam = downloadedAmount;
			display.asyncExec(new Runnable(){
				public void run()
				{
					gui.pbar.setSelection(pbam);
				}
			});
	                }

	                writer.close();
	                inputstream.close();
	        }

	        @Override
	        public void run() {
	            try {

	                filedownloader(urlfrom, clientto);
	                Zip zip = new Zip();
	                zip.unpack(clientto, unpackto);
	                display.asyncExec(new Runnable()
	                {
	                	public void run()
	                	{
	                		gui.download.setEnabled(true);
	                	}
	                });
	            } catch (Exception ex) {
	                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
	                display.asyncExec(new Runnable()
	                {
	                	public void run()
	                	{
	                		gui.download.setEnabled(true);
	                	}
	                });
	            }
	        }
	    }

