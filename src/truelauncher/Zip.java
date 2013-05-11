package truelauncher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;
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
        final ZipFile zip = new ZipFile(path);
        display.asyncExec(new Runnable()
        {
        	public void run()
        	{
        		gui.pbar.setSelection(0);
        		gui.pbar.setMinimum(0);
        		gui.pbar.setMaximum(zip.size());
        	}
        	
        });
        Enumeration<? extends ZipEntry> entries = zip.entries();
        LinkedList<ZipEntry> zfiles = new LinkedList<ZipEntry>();
        int unpackedfiles = 0;
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            if (entry.isDirectory()) {
                new File(dir_to + "/" + entry.getName()).mkdirs();
            	unpackedfiles+=1;
            	pbupdate(unpackedfiles);
            } else {
                zfiles.add(entry);
            }
        }
        for (ZipEntry entry : zfiles) {
            InputStream in = zip.getInputStream(entry);
            OutputStream out = new FileOutputStream(dir_to + "/" + entry.getName());
            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) >= 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        	unpackedfiles+=1;
        	pbupdate(unpackedfiles);
        }
        zip.close();
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