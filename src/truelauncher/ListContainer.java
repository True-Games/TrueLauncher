package truelauncher;

import java.util.ArrayList;
import java.util.List;

public class ListContainer {

	
	//For server status combobox
	//1 - name, 2-ip, 3 - port
	private String[][] servers = 
		{
			{"Arelate [Classic 1.5.1]","mc.true-games.org","25565"},
			{"Dohao [Classic 1.5.1]","mc.true-games.org","25566"},
			{"Valhalla [Classic 1.5.1]","mc.true-games.org","25567"},
			{"TestServer [HiTech 1.5.1]","mc.true-games.org", "65000"}
		};
	
	//For launch combobox
	//1 - name, 2- launchfolder
	private String[][] clientfolders = 
		{
			{".minecraft client",".minecraft"},
			{"Classic 1.5.1",".true-games.org/runcients/classic"},
			{"HiTech 1.5.1",".true-games.org/runclients/hitech"}
		};
	
	//1 - name, 2 - downloadlink, 3 - folderto
	private String[][] downloadclients = 
	{
		{"Classic 1.5.1","http://download.true-games.org/minecraft/clients/classic.zip",".true-games.org/runcients/classic"},
		{"Hitech 1.5.1","http://download.true-games.org/minecraft/clients/hitech.zip",".true-games.org/runclients/hitech"}
	};
	
	private String tempfolder = "/.true-games.org/packedclients";
	
		
	public List<String> getServers()
			{
		
				List<String> servnames =new ArrayList<String>();
				for (int i=0; i<servers.length;i++)
				{
					servnames.add(servers[i][0]);
				}
				return servnames;
			}
	
	public String getServerIpByName(String name)
	{
		String ip = "unknown";
		for (int i=0; i<servers.length;i++)
		{
			if (servers[i][0].equals(name))
			{
				ip = servers[i][1];
			}
		}
		return ip;
	}
	
	public int getServerPortByName(String name)
	{
		int port = 25565;
		for (int i=0; i<servers.length;i++)
		{
			if (servers[i][0].equals(name))
			{
				port = Integer.valueOf(servers[i][2]);
			}
		}
		return port;
	}
	
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
}
