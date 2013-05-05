package truelauncher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.eclipse.swt.widgets.Display;

public class ServerStatusThread implements Runnable {
	private GUI gui;
	private Display display;
	private String ip;
	private int port;
	
	public ServerStatusThread(GUI gui,Display display, String ip, int port)
	{
		this.gui = gui;
		this.display = display;
		this.ip = ip;
		this.port = port;	
	}
	
	private String readString(DataInputStream is, int d) throws IOException
	{
		short word = is.readShort();
		if (word > d) throw new IOException();
		if (word < 0) throw new IOException();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < word; i++)
		{
			res.append(is.readChar());
		}
		return res.toString();
	}
	
	private String pollServer(String ip, int port)
	{
		Socket soc = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		try
		{
			soc = new Socket();
			soc.setSoTimeout(3000);
			soc.setTcpNoDelay(true);
			soc.setTrafficClass(18);
			soc.connect(new InetSocketAddress(ip, port), 3000);
			dis = new DataInputStream(soc.getInputStream());
			dos = new DataOutputStream(soc.getOutputStream());
			dos.write(254);

			if (dis.read() != 255)
			{
				throw new IOException("Bad message");
			}
			String servc = readString(dis, 256);
			servc.substring(3);
			if (servc.substring(0,1).equalsIgnoreCase("§") && servc.substring(1,2).equalsIgnoreCase("1"))
			{
				String[] res = servc.split("\u0000");
				String ret = "Онлайн: "+ res[4] + " из " + res[5];
				return ret;
				
			}
			else 
			{
				String[] res =  servc.split("§");
				String ret = "Онлайн: "+ res[1] + " из " + res[2];
				return ret;
			}

		} catch (Exception e)
		{
			
		} finally
		{
			try { dis.close();  } catch (Exception e) {}
			try { dos.close();  } catch (Exception e) {}
			try { soc.close();  } catch (Exception e) {}
		}
		return "Оффлайн";
	}

	@Override
	public void run() {
		final String status = pollServer(ip,port);
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				gui.status.setText(status);
			}
		});

	}
}
