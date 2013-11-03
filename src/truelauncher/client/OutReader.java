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

package truelauncher.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import truelauncher.utils.LauncherUtils;

public class OutReader extends Thread {
	
	private Process p;
	private String password;
	public OutReader(Process p, String password)
	{
		this.password = password;
		this.p = p;
	}

	public void run()
	{
		try {
			InputStream is = p.getErrorStream();
			BufferedReader reader = new BufferedReader (new InputStreamReader(is));
			String line;
			while ((line = reader.readLine ()) != null) 
			{
				if (line.contains("AuthConnector")) 
				{
					onLoginFinished(line);
				}
			}
		} catch (Exception e) {
			LauncherUtils.logError(e);
		}
	}
	

	private void onLoginFinished(String message)
	{
		//loginsystem string format: AuthConnector|authtype|host|port|nick|token
		System.out.println("Login");
		String[] paramarray = message.split("[|]");
		int authtype = Integer.valueOf(paramarray[1]);
		if (authtype == 1)
		{//1.6.4 and earlier
			Auth.sendAuth1(paramarray[2], Integer.valueOf(paramarray[3]), paramarray[4], paramarray[5], password);
		} else
		if (authtype == 2)
		{//1.7.2 and newer (this is not supported currently)
			Auth.sendAuth2(paramarray[2], Integer.valueOf(paramarray[3]), paramarray[4], paramarray[5], password);
		}
	}


}
