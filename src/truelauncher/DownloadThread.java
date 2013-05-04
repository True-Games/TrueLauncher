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

public class DownloadThread extends Thread {



	    private GUI gui = null;
	    private String urlfrom;
	    private String clientto;
	    private String unpackto;
	    DownloadThread(GUI gui, String urlfrom, String clientto, String unpackto)
	    {
	    this.gui = gui;
	    this.urlfrom = urlfrom;
	    this.clientto = clientto;
	    this.unpackto = unpackto;
	    }
	    
	 
	       public void filedownloader(String urlfrom, String clientto) {
	            try {
	                
	                String ps = LauncherUtils.getDir();
	                if (!((new File(ps + "/.true-games.org/packedclients")).exists())) {
	                    (new File(ps + "/.true-games.org/packedclients")).mkdirs();
	                }
	                URL url = new URL(urlfrom);
	                
	                URLConnection conn = url.openConnection();
	                
	               if ((conn instanceof HttpURLConnection)) {
	                conn.setRequestProperty("Cache-Control",
								"no-cache");
			conn.connect();}
			InputStream inputstream = conn.getInputStream();

	                FileOutputStream writer = new FileOutputStream(clientto);
	                byte[] buffer = new byte[65536];
	                
	                int downloadedAmount = 0;
			int totalAmount = conn.getContentLength();
			int bufferSize;
			while ((bufferSize = inputstream.read(buffer, 0, buffer.length)) != -1) {
			writer.write(buffer, 0, bufferSize);
			downloadedAmount += bufferSize;
	           //     gui.setpb((int) (((double) downloadedAmount / totalAmount) * 100));
	                }

	            //    gui.setb1txt("<html><body>Minecraft ������<br>��� ����������</body></html>");

	                writer.close();
	                inputstream.close();
	            } catch (Exception e) {
	                
	            }
	        }

	        @Override
	        public void run() {
	            try {

	                this.filedownloader(urlfrom, clientto);
	                Zip zip = new Zip();
	                zip.unpack(clientto, unpackto);
	              //  gui.setb1txt("Minecraft ����������");
	              //  gui.checkclassic();
	            } catch (IOException ex) {
	                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }

