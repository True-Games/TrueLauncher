package truelauncher;

import org.eclipse.swt.widgets.Display;


public class Launcher {

	
	public static void main(String[] args) {
		LauncherUtils.setupSWT();
		LauncherUtils.loadSWT();
	      Display display = new Display();
	      new GUI(display);
	      display.dispose();
	}
	



}
