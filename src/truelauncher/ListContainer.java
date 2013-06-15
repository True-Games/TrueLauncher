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

import java.util.ArrayList;
import java.util.List;

public class ListContainer {

	
	//For launch combobox
	//1 - name, 2- launchfolder, 3 - launch type (1 - 1.5.2 and older, 2 - 1.6 and newer (not yet done)), 4 - file to check for existance
	private String[][] clientfolders = 
		{
			{"Classic 1.5.2",".true-games.org/runclients/classic", "1", ".true-games.org/runclients/classic/minecraft.jar"},
			{"HiTech 1.5.2",".true-games.org/runclients/hitech", "1", ".true-games.org/runclients/hitech/minecraft.jar"}
		};
	
	//For download combobox
	//1 - name, 2 - downloadlink, 3 - folderto
	private String[][] downloadclients = 
	{
		{"Classic 1.5.2","http://download.true-games.org/minecraft/clients/classic.zip",".true-games.org/runclients/classic"},
		{"Hitech 1.5.2","http://download.true-games.org/minecraft/clients/hitech.zip",".true-games.org/runclients/hitech"}
	};
	
	//folder in which clients .zip file will be downloaded
	private String tempfolder = ".true-games.org/packedclients";
	//folder in which configuration will be stored
	private String configfolder = "/.true-games.org/configdata";
	//launcher version
	private int lversion = 6;
	//laucnher update URL;
	private String lupdateurlfolder = "http://download.true-games.org/minecraft/launcher/";
	

	public List<String> getClients()
	{

		List<String> servnames =new ArrayList<String>();
		for (int i=0; i<clientfolders.length;i++)
		{
			servnames.add(clientfolders[i][0]);
		}
		return servnames;
	}
	
	public String getClientFolderByName(String name)
	{
		String folder = "minecraft";
		for (int i=0; i<clientfolders.length;i++)
		{
			if (clientfolders[i][0].equals(name))
			{
				folder = clientfolders[i][1];
			}
		}
		return folder;
	}
	
	public int getLaunchVersionByName(String name)
	{
		int version = 1;
		for (int i=0; i<clientfolders.length;i++)
		{
			if (clientfolders[i][0].equals(name))
			{
				version = Integer.valueOf(clientfolders[i][2]);
			}
		}
		return version;
	}
	
	public String getJarByName(String name)
	{
		String folder = "fail";
		for (int i=0; i<clientfolders.length;i++)
		{
			if (clientfolders[i][0].equals(name))
			{
				folder = clientfolders[i][3];
			}
		}
		return folder;
	}
	
	
	public List<String> getDownloads()
	{

		List<String> servlinks =new ArrayList<String>();
		for (int i=0; i<downloadclients.length;i++)
		{
			servlinks.add(downloadclients[i][0]);
		}
		return servlinks;
	}
	
	public String getDownloadLinkByName(String name)
	{
		String link = "";
		for (int i=0; i<downloadclients.length;i++)
		{
			if (downloadclients[i][0].equals(name))
			{
				link = downloadclients[i][1];
			}
		}
		return link;
	}
	
	public String getFolderToByName(String name)
	{
		String fldto = "";
		for (int i=0; i<downloadclients.length;i++)
		{
			if (downloadclients[i][0].equals(name))
			{
				fldto = downloadclients[i][2];
			}
		}
		return fldto;
	}
	
	public String getTempFolderPath()
	{
		return tempfolder;
	}
	
	public String getConfigFolderPath()
	{
		return configfolder;
	}
	
	public String getLUpdateURLFolder()
	{
		return lupdateurlfolder;
	}
	public int getLVerison()
	{
		return lversion;
	}
}
