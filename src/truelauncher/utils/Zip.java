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

package truelauncher.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import truelauncher.main.GUI;



public class Zip {
	
    private GUI gui;
	
    public Zip(GUI gui){
    	this.gui = gui;
    }
    
     public void unpack(String path, String dir_to) throws IOException {
    	final ZipFile zipFile = new ZipFile(path);
        		
    	gui.pbar.setValue(0);
        gui.pbar.setMinimum(0);
        gui.pbar.setMaximum(zipFile.size());
        
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
            		LauncherUtils.logError(e);
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
       		gui.pbar.setValue(selection);
    }
    
}