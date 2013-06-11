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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class LauncherUtils {
	
	//folder in which swt dependency jar will be stored
	private static String swtpath = "/.true-games.org/SWT/";

	
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
    
    //get java arch begin
    public static int getArch()
    {
    	int bit = 64;
    	bit = Integer.valueOf(System.getProperty("sun.arch.data.model"));
    	return bit;
    }
    //get java arch end
    
    
    
    //get and load swt  begin
    public static InputStream getSWT()
    {
        String OS = System.getProperty("os.name").toLowerCase();
    	int bit = getArch();
        if (OS.contains("win"))
        {
        	return Launcher.class.getResourceAsStream("SWT/swt_windows_"+bit+".jar");
        }
        else if (OS.contains("linux"))
        {
        	return Launcher.class.getResourceAsStream("SWT/swt_linux_"+bit+".jar");
        }
        else if (OS.contains("unix"))
        {
        	return Launcher.class.getResourceAsStream("SWT/swt_mac_"+bit+".jar");
        }
		return null;
    }
    
	public static void setupSWT()
	{
		if (!new File(LauncherUtils.getDir() + swtpath+"swt_"+getArch()+".jar").exists())
		{
			try {
			new File(LauncherUtils.getDir() + swtpath).mkdirs();
			InputStream is = LauncherUtils.getSWT();
			OutputStream out = new FileOutputStream(new File(LauncherUtils.getDir() + swtpath+"swt_"+getArch()+".jar"));
			byte[] buf = new byte[4096];
	        int len;
	        while ((len = is.read(buf)) > 0){out.write(buf, 0, len);}
	        is.close();
	        out.close();
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public static void loadSWT()
	{
		try {
		  addFile(LauncherUtils.getDir() + LauncherUtils.swtpath+"swt_"+LauncherUtils.getArch()+".jar");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	    
    
    private static final Class<?>[] parameters = new Class[]{URL.class};
    public static void addFile(String s) throws IOException {
        File f = new File(s);
        addURL(f.toURI().toURL());
    }

    public static void addURL(URL u) throws IOException {
        URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        Class<?> sysclass = URLClassLoader.class;
        try {
            Method method = sysclass.getDeclaredMethod("addURL",parameters);
            method.setAccessible(true);
            method.invoke(sysloader,new Object[]{ u }); 
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }     
    }
    //get and load swt  end
    
    
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
        if (OS.contains("win")) {cps = ";";} else {cps=":";}
        ProcessBuilder pb = new ProcessBuilder();
		  pb.directory(new File(mcpath));
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
				  +jar
				  );
		  cc.add("net.minecraft.client.Minecraft");
		  cc.add(nick);
		  cc.add("session");
		  cc.add("--workdir");
		  cc.add(mcpath);
		  pb.command(cc);
		  pb.start();
		  
		  }
		  
		  killLauncher();
    	} catch (Exception e) {e.printStackTrace();}
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
    //get Last Launcher version begin
    
	
	//kill Launcher start
	private static void killLauncher()
	{
		System.exit(0);
	}

    


}
