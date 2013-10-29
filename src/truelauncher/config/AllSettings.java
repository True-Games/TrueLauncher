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

package truelauncher.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import truelauncher.utils.LauncherUtils;

public class AllSettings {
	
	//TODO: rewrite config structure
	public static void load() throws FileNotFoundException
	{
		final File configfile = new File(LauncherUtils.getDir() + File.separator + AllSettings.getLauncherConfigFolderPath()+File.separator+"clients");
		ConfigLoader.loadConfig(configfile);
		ConfigUpdater.updateConfig(configfile);
	}

	//For client launch
	//1 - name, 2 - launchfolder, 3 - minecraft jar file, 4 - mainclass , 5 - cmdargs
	protected static LinkedHashMap<String, ClientLaunchData> clientslaunchdata = new LinkedHashMap<String, ClientLaunchData>();
	//path to the folder where all libs are stored (all libs should end with .jar) (this is relative to client launchfolder)
	protected static String libsfolder = "libraries";

	//For client download
	//folder in which clients .zip file will be downloaded
	protected static String tempfolder = ".true-games.org/packedclients";
	//1 - name, 2 - downloadlink, 3 - folderto
	protected static LinkedHashMap<String, ClientDownloadData> clientsdownloaddata = new LinkedHashMap<String, ClientDownloadData>();

	//for launcher updater
	//launcher version
	private static int lversion = 20;
	//laucnher folder update URL;
	//folder structure
	//{folder}/Laucnher.jar - launcher location
	//{folder}/version - launcher version
	//{folder}/clients - client config file
	private static String lupdateurlfolder = "http://download.true-games.org/minecraft/launcher/";
	
	//folder in which configurations will be stored
	private static String configfolder = ".true-games.org/configdata";
	//folder for error logging
	public static String errFolder = ".true-games.org/errLog";

	//main frame size
	public static int w = 740;
	public static int h = 340;
	//images
	public static String lname = "True-games.org|MinecraftLauncher";
	public static String icon = "icon.png";
	public static String bgimage = "bgimage.png";
	public static String labelimage = "labelbar.png";
	public static String textimage = "textfield.png";
	public static String explainimage = "expbar.png";
	public static String mclaunchimage = "mclaunch.png";
	public static String close = "close.png";
	public static String hide = "hide.png";

	//code to get those values
	
	//gui block 2 vars begin
	public static List<String> getClientsList()
	{
		return  new ArrayList<String>(clientslaunchdata.keySet());
	}
	
	public static String getClientFolderByName(String name)
	{
		return clientslaunchdata.get(name).getLaunchFolder();
	}
	
	public static String getClientJarByName(String name)
	{
		return getClientFolderByName(name) + File.separator + clientslaunchdata.get(name).getJarFile();
	}
	
	public static String getClientMainClassByName(String name)
	{
		return clientslaunchdata.get(name).getMainClass();
	}
	
	public static String getClientCmdArgsByName(String name)
	{
		return clientslaunchdata.get(name).getCmdArgs();
	}
	
	public static String getClientLibsFolder() 
	{
		return libsfolder;
	}
	//gui block 2 vars end
	
	
	//gui block 3 vars begin
	public static List<String> getClientListDownloads()
	{
		return new ArrayList<String>(clientsdownloaddata.keySet());
	}
	
	public static String getClientDownloadLinkByName(String name)
	{
		return clientsdownloaddata.get(name).getDownloadLink();
	}
	
	public static String getClientUnpackToFolderByName(String name)
	{
		return clientsdownloaddata.get(name).getUnpackFolder();
	}
	//gui block 3 vars end
	
	//folder for packed clients begin
	public static String getCientTempFolderPath()
	{
		return tempfolder;
	}
	//folder for packed clients end
	
	
	//Lacunher vars begin
	public static String getLauncherConfigFolderPath()
	{
		return configfolder;
	}
	
	public static String getLauncherWebUpdateURLFolder()
	{
		return lupdateurlfolder;
	}
	public static int getLauncherVerison()
	{
		return lversion;
	}
	//Lacunher vars end


}
