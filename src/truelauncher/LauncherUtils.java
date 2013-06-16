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

package truelauncher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class LauncherUtils {
	

	
	//get Directory (Addpata for win and home for linux) begin
    public static String getDir() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            String appData = System.getenv("APPDATA");
            String Dir = new File(appData).toString();
            return Dir;
        } else if (OS.contains("linux") || OS.contains("unix")) {
            String Directory = System.getProperty("user.home", ".");
            String Dir = new File(Directory).toString();
            return Dir;
        }
        return null;
    }
	//get Directory end
    
    
    
    //Launch minecraft begin
    public static void launchMC(String path, String nickin, int memin, int lvers, String jar)
    {
    	try {
        String nick = nickin;
        String mem = memin + "M";
        String ps = LauncherUtils.getDir();
        String OS = System.getProperty("os.name").toLowerCase();
        String mcpath = ps+File.separator+path;
        String cps;
        if (OS.contains("win")) {
        		cps = ";";
        	} else {
        		cps=":";
        	}
	      ProcessBuilder pb = new ProcessBuilder();
	      pb.directory(new File(mcpath).getCanonicalFile());
		  if (lvers == 1) {
		  List<String> cc = new ArrayList<String>(); 
		  cc.add("java");
		  cc.add("-Xmx"+mem);
		  cc.add("-Djava.library.path=libraries/natives");
		  cc.add("-cp");
		  cc.add("libraries/net/sf/jopt-simple/jopt-simple/4.4/jopt-simple-4.4.jar"+cps
				  +"libraries/org/ow2/asm/asm-all/4.1/asm-all-4.1.jar"+cps
				  +"libraries/org/lwjgl/lwjgl/lwjgl/2.9.0/lwjgl-2.9.0.jar"+cps
				  +"libraries/org/lwjgl/lwjgl/lwjgl_util/2.9.0/lwjgl_util-2.9.0.jar"+cps
				  +"libraries/org/lwjgl/lwjgl/lwjgl/2.9.0/jinput-2.0.5.jar"+cps
				  +"libraries/net/java/jutils/jutils/1.0.0/jutils-1.0.0.jar"+cps
				  +LauncherUtils.getDir()+File.separator+jar
				  );
		  cc.add("net.minecraft.client.Minecraft");
		  cc.add(nick);
		  cc.add("session");
		  cc.add("--workdir");
		  cc.add(mcpath);
		  pb.command(cc);
	      pb.inheritIO(); //Do not remove this
		  pb.start();
		  }
		  
    	} catch (Exception e) {
    		e.printStackTrace();
    		LauncherUtils.logError(e);
    	}
    }
    //Launch minecraft end
    
    
    //get Last Launcher version begin
	public static String readURLStreamToString(InputStream in) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		InputStreamReader read = new InputStreamReader(in, "UTF-8");

		while (true) {
			int c = read.read();
			if (c != -1 && c != 10) {
				buf.append((char) c);
			} else {
				break;
			}
		}
		return buf.toString();
	}
    //get Last Launcher version end
	
	
	//check client exist begin
	public static void checkClientJarExist(GUI gui)
	{
  	  	File cfile = new File(LauncherUtils.getDir()+File.separator+AllSettings.getClientJarByName(gui.listclients.getSelectedItem().toString()));
   	  	if (cfile.exists()) {
   	  			gui.launch.setEnabled(true);
   	  			gui.launch.setText("Запустить Minecraft");
   	  		} else {
   	  			gui.launch.setText("Клиент не найден");
   	  			gui.launch.setEnabled(false);
   	  		}
	}
	//checkclientexist end
	
	//Log error to file start
	public static void logError(Exception err)
	{
		File errLogFile = new File(LauncherUtils.getDir() + File.separator + AllSettings.errFolder + File.separator + "LError.log");
		if (!(errLogFile.exists())) {new File(LauncherUtils.getDir() + File.separator + AllSettings.errFolder + File.separator).mkdirs(); }
	     try {
	         FileOutputStream fos = new FileOutputStream(errLogFile);  
	         PrintStream ps = new PrintStream(fos);  
	         err.printStackTrace(ps);
	         ps.flush();
	         ps.close();
	         fos.close();
	     } catch (Exception e) {}
		
	}
	//Log error to file end
}
