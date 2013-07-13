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
import java.util.ArrayList;
import java.util.List;

public class ClientLaunch {

	//Launch minecraft begin
    public static void launchMC(String pathin, String nickin, int memin, int lvers, ArrayList<String> libsin ,String jarin, int forgepresent)
    {
    	try {
        String nick = nickin;
        String mem = memin + "M";
        String mcpath = LauncherUtils.getDir()+File.separator+pathin;
        String cps;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
        	cps = ";";
        } else {
        	cps=":";
        }
	  	String libs = "";
		for (String lib : libsin)
		{
			  libs+=lib+cps;
		}
		String jar = LauncherUtils.getDir()+File.separator+jarin;

	      ProcessBuilder pb = new ProcessBuilder();
	      pb.directory(new File(mcpath).getCanonicalFile());
		  List<String> cc = new ArrayList<String>(); 
		  cc.add("java");
		  cc.add("-Xmx"+mem);
		  cc.add("-Djava.library.path=libraries/natives");
		  cc.add("-Dfml.ignoreInvalidMinecraftCertificates=true");
		  cc.add("-Dfml.ignorePatchDiscrepancies=true");
		  cc.add("-cp");
		  cc.add(libs+jar);
		  if (lvers == 1) { //1.5 and older
			  	cc.add("net.minecraft.client.Minecraft");
			  	cc.add(nick);
			  	cc.add("session");
		  }
		  else if (lvers == 2) { //1.6 and newer
			  if (forgepresent == 0)
			  {//normal minecraft
				  cc.add("net.minecraft.client.main.Main");
			  }
			  else if
			  (forgepresent == 1)
			  {//minecraft with forge
				  cc.add("net.minecraft.launchwrapper.Launch");
				  cc.add("--tweakClass");
				  cc.add("cpw.mods.fml.common.launcher.FMLTweaker");
			  }
			  
			  cc.add("--username");
			  cc.add(nick);
			  cc.add("--session");
			  cc.add("session");
			  cc.add("--version");
			  cc.add("1.6.2");
		  }
		  
		  cc.add("--gameDir");
		  cc.add(mcpath);
		  cc.add("--assetsDir");
		  cc.add(mcpath+File.separator+"assets");

		  
		  pb.command(cc);
	      pb.inheritIO(); //Do not remove this
		  pb.start();
		  
    	} catch (Exception e) {
    		e.printStackTrace();
    		LauncherUtils.logError(e);
    	}
    }
    //Launch minecraft end
    
	
}
