package truelauncher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.swt.widgets.Display;




public class Zip {
	
    private GUI gui;
	private Display display;
	
    Zip(GUI gui,Display display){
    	this.gui = gui;
    	this.display = display;
    }
    
     public void unpack(String path, String dir_to) throws IOException {
    	final ZipFile zipFile = new ZipFile(path);
        display.asyncExec(new Runnable()
        {
        	public void run()
        	{
        		gui.pbar.setSelection(0);
        		gui.pbar.setMinimum(0);
        		gui.pbar.setMaximum(zipFile.size());
        	}
        	
        });
        
        int unpackedfiles = 0;
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements())
        {
        	ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) 
            {//Directory
                new File(dir_to + File.separator + entry.getName()).mkdirs();
            	unpackedfiles+=1;
            	pbupdate(unpackedfiles);
            } else
            {//File
                InputStream in = zipFile.getInputStream(entry);
                OutputStream out = new FileOutputStream(dir_to + File.separator + entry.getName());
                byte[] buffer = new byte[4096];
            	try {
            		int len;
            		while ((len = in.read(buffer)) >= 0) {
            			out.write(buffer, 0, len);
            		}
            		unpackedfiles+=1;
            		pbupdate(unpackedfiles);
            	} catch (Exception e) 
            	{
            		e.printStackTrace(); LauncherUtils.logError(e);
            	} finally
            	{
                    in.close();
                    out.close();
            	}
            }
        }
        
        zipFile.close();
    }
     
    private void pbupdate(final int selection)
    {
        display.asyncExec(new Runnable()
        {
        	public void run()
        	{
        		gui.pbar.setSelection(selection);
        	}
        	
        });
    }
    
}