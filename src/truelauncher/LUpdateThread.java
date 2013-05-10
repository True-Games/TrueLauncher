package truelauncher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;

public class LUpdateThread extends Thread {

	private String lpath = Launcher.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	
    private GUI gui;
	private Display display;
    private String urlfrom;
	LUpdateThread(GUI gui,Display display, String urlfrom)
    {
    this.gui = gui;
	this.display = display;
    this.urlfrom = urlfrom;
    }
	
    public void ldownloader(String urlfrom, String lto) throws Exception 
    {
             URL url = new URL(urlfrom);
             URLConnection conn = url.openConnection();
             
            if ((conn instanceof HttpURLConnection)) {
             conn.setRequestProperty("Cache-Control",
							"no-cache");
		conn.connect();}
		InputStream inputstream = conn.getInputStream();

     FileOutputStream writer = new FileOutputStream(lto);
     byte[] buffer = new byte[153600];
             
     int downloadedAmount = 0;
		final int totalAmount = conn.getContentLength();
		display.asyncExec(new Runnable(){
			public void run()
			{
				gui.lpbar.setMaximum(totalAmount);
				gui.lpbar.setMinimum(0);
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
				gui.lpbar.setSelection(pbam);
			}
		});
             }

             writer.close();
             inputstream.close();
     }
    
    public void lcopy(String from) throws Exception
    {
    	InputStream in = new FileInputStream(from);
    	OutputStream out = new FileOutputStream(lpath);
    	

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0){
          out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    
    public void lrestart() throws Exception
    {
    	ArrayList<String> lls = new ArrayList<String>();
    	lls.add("java");
    	lls.add("-jar");
    	lls.add(new File(lpath).getName());
    	ProcessBuilder lpb = new ProcessBuilder();
    	lpb.directory(new File(".").getAbsoluteFile());
    	lpb.command(lls);
    	lpb.start();    	
    }
	
	public void run()
	{
		try {
			String temppath = System.getProperty("java.io.tmpdir")+"MCLauncherTemp.jar";
			ldownloader(urlfrom, temppath);
			lcopy(temppath);
			lrestart();
			Runtime.getRuntime().exit(0);
		} catch (Exception e)
		{
			e.printStackTrace();
			display.asyncExec(new Runnable()
			{
				public void run()
				{
					gui.ldownload.setEnabled(true);
					gui.lstatus.setText("Ошибка обновления");
				}
			});
		}
	}
}
