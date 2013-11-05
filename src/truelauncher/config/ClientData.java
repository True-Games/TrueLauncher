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

public class ClientData {

	private String launchfolder = ".minecraft";
	private String jarfile = "minecraft.jar";
	private String mainclass = "net.minecraft.client.main.Main";
	private String cmdargs = "--username {USERNAME} --accessToken token";
	private String downloadlink = "fake.fake/minecraft.zip";
	private int version = 1;
	
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
	
	protected int getVersion()
	{
		return version;
	}
	
}
