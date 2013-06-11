package truelauncher;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class LauncherUpdateDialog extends Dialog {
	
	private String labelimage = "images/labelbar.png";

	Object result;
	int w; int h;
	public Button ldownload;
	public ProgressBar lpbar;
	public Label lstatus;
	Button later;
	String url;
	
	private LauncherUpdateDialog thisclass = this;
	
	public LauncherUpdateDialog(Shell parent, int w, int h, String url) {
		super(parent);
		this.w = w;
		this.h = h;
		this.url = url;
	}
	
	public Object open () {
		Shell parent = getParent();
		Shell shell = new Shell(parent,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText("Обновление лаунчера");
	    shell.setLocation(
	    		parent.getBounds().x+(parent.getBounds().width-w)/2, 
	    		parent.getBounds().y+(parent.getBounds().height-h)/2
	    		);
		shell.setSize(w,h);
		initUI(shell);
		shell.pack();
		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		return result;
	}
	
	private void initUI(final Shell shell)
	{
	    final Display display = shell.getDisplay();
     	lstatus = new Label(shell, SWT.CENTER);
     	lstatus.setBackgroundImage(
     			new Image(display,
     					new Image(display,GUI.class.getResourceAsStream(labelimage)).getImageData().scaledTo(w, 29)
     					)
     			);
     	lstatus.setText("Доступно обновление лаунчера");
     	lstatus.setBounds(0,0,w,30);
     	
  	  	lpbar = new ProgressBar(shell, SWT.SMOOTH);
  	  	lpbar.setBounds(0,30,w, 15);
     	
  	  	
  	  	
    	ldownload = new Button(shell, SWT.PUSH);
    	ldownload.setText("Обновить");
    	ldownload.setBounds(0, 45, w, 25);
    	ldownload.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            lstatus.setText("Скачиваем обновление");
           	ldownload.setEnabled(false);
           	later.setEnabled(false);
            new LUpdateThread(thisclass, display, url).start();
            }
        });

    	
    	
    	later = new Button(shell, SWT.PUSH);
    	later.setText("Позже");
    	later.setBounds(0,70,w,25);
    	later.addSelectionListener(new SelectionAdapter() {
 	               @Override
 	               public void widgetSelected(SelectionEvent e) {
 	            	   shell.dispose();
 	               }
    	});

         

    	
	}

}
