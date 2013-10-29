package truelauncher.config;

public class ClientData {

	private String launchfolder = ".minecraft";
	private String jarfile = "minecraft.jar";
	private String mainclass = "net.minecraft.client.main.Main";
	private String cmdargs = "--username {USERNAME} --accessToken token";
	private String downloadlink = "fake.fake/minecraft.zip";
	public ClientData(String launchfolder, String jarfile, String mainclass, String cmdargs, String downloadlink)
	{
		this.launchfolder = launchfolder;
		this.jarfile = jarfile;
		this.mainclass = mainclass;
		this.cmdargs = cmdargs;
		this.downloadlink = downloadlink;
	}
	
	protected String getLaunchFolder()
	{
		return launchfolder;
	}

	protected String getJarFile()
	{
		return jarfile;
	}

	protected String getMainClass()
	{
		return mainclass;
	}

	protected String getCmdArgs()
	{
		return cmdargs;
	}
	
	protected String getDownloadLink()
	{
		return downloadlink;
	}
	
}
