package truelauncher;

import java.util.ArrayList;
import java.util.List;

public class ListContainer {

	
	
	private String[][] servers = 
		{
			{"Arelate","mc.true-games.org","25565"},
			{"Dohao","mc.true-games.org","25566"},
			{"Valhalla","mc.true-games.org","25567"}
			
		};
	
	
	
	
	
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
}
