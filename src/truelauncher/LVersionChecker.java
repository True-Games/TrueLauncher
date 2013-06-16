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

import java.net.URL;


public class LVersionChecker extends Thread {
	
	private GUI gui;
	private String url;
	private int curversion;
	public LVersionChecker(GUI gui, String url, int curversion)
	{
		this.gui = gui;
		this.url = url;
		this.curversion = curversion;
	}
	
		public void run()
		{
			try {
				int newversion = Integer.MAX_VALUE;
				newversion = Integer.valueOf(LauncherUtils.readURLStreamToString(new URL(url).openStream()));
				if (curversion < newversion)
				{
					gui.lu.open();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
        		LauncherUtils.logError(e);
			}
		}

}
