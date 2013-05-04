package truelauncher;

import org.eclipse.swt.widgets.Display;


public class Launcher {

public static GUI gui;
	
	public static void main(String[] args) {
	      Display display = new Display();
	      gui = new GUI(display);
	      display.dispose();
	}

}
