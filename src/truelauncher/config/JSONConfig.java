package truelauncher.config;

import java.util.LinkedHashMap;

public class JSONConfig {

	
	private String tempfolder = "temp";
	private String libsfolder = "libraries";
	private LinkedHashMap<String, ClientData> clientdata = new LinkedHashMap<String, ClientData>();
	
	protected String getTempFolder()
	{
		return tempfolder;
	}
	protected String getLibsFolder()
	{
		return libsfolder;
	}
	protected LinkedHashMap<String, ClientData> getClientDataMap()
	{
		LinkedHashMap<String, ClientData> r = new LinkedHashMap<String, ClientData>();
		r.putAll(clientdata);
		return r;
	}
	
}
