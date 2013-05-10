package truelauncher;

import java.net.URL;

import org.eclipse.swt.widgets.Display;

public class LVersionChecker extends Thread {
	
	private GUI gui;
	private Display display;
	private String url;
	private int curversion;
	public LVersionChecker(GUI gui, Display display, String url, int curversion)
	{
		this.gui = gui;
		this.display = display;
		this.url = url;
		this.curversion = curversion;
	}
	
		public void run()
		{
			try {
				int newversion = Integer.valueOf(LauncherUtils.readURLStreamToString(new URL(url).openStream()));
				if (curversion >= newversion)
				{
					display.asyncExec(new Runnable()
					{
						public void run()
						{
							gui.lstatus.setText("Обновлений нет");
						}
					});
				} else
				{
					display.asyncExec(new Runnable()
					{
						public void run()
						{
							gui.lstatus.setText("Есть обновление");
							gui.ldownload.setEnabled(true);
						}
					});
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				display.asyncExec(new Runnable()
				{
					public void run()
					{
						gui.lstatus.setText("Ошибка проверки");
					}
				});
			}
		}

}
