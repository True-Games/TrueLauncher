package truelauncher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LauncherUtils {
	
	
	private static String swtpath = "/.true-games.org/SWT/";

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
    
    public static int getArch()
    {
    	int bit = 64;
    	bit = Integer.valueOf(System.getProperty("sun.arch.data.model"));
    	return bit;
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
	
    public static String getSwtPath()
    {
    	return swtpath;
    }
    
    public static void launchMC(String path, String nickin, int memin)
    {
       String nick = nickin;
       String mem = memin + "M";
       String ps = LauncherUtils.getDir();
       String OS = System.getProperty("os.name").toLowerCase();
       String mcpath = ps+"/"+path;
        
           try {
            if (OS.contains("win")) {
          Runtime.getRuntime().exec("cmd /c cd "+mcpath+" && start javaw -Xmx" + mem + " -Djava.library.path=bin/natives -cp bin/minecraft.jar;bin/jinput.jar;bin/lwjgl.jar;bin/lwjgl_util.jar net.minecraft.client.Minecraft " + nick);
             }
           else
            {
          Runtime.getRuntime().exec(new String[]{"/bin/sh","-c","cd "+mcpath+" ; java -Xmx" + mem + " -Djava.library.path=bin/natives -cp bin/minecraft.jar:bin/jinput.jar:bin/lwjgl.jar:bin/lwjgl_util.jar net.minecraft.client.Minecraft " + nick});  
            }
           
        } catch (Exception ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    

    


}
