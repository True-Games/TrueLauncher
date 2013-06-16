package truelauncher;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

import truelauncher.gcomponents.TButton;
import truelauncher.gcomponents.TLabel;

@SuppressWarnings("serial")
public class LauncherUpdateDialog  extends JDialog {
	
	private String labelimage = "images/labelbar.png";

	Object result;
	int w; int h;
	public TButton ldownload;
	public JProgressBar lpbar;
	public TLabel lstatus;
	TButton later;
	String url;
	GUI parent;
	
	private LauncherUpdateDialog thisclass = this;
	
	public LauncherUpdateDialog(Frame aFrame, GUI parent, String url , int w, int h) {
        super(aFrame, true);
        this.parent = parent;
        this.w = w;
        this.h = h;
 	}
	
	public void open()
	{
		setResizable(false);
		setTitle("Обновление лаунчера");
		setSize(w,h);
		setLocationRelativeTo(parent);
		initUI();
		setVisible(true);
	}
	
	
	private void initUI()
	{
		this.setLayout(null);
		
     	lstatus = new TLabel();
     	lstatus.setBackgroundImage(GUI.class.getResourceAsStream(labelimage),w, 29);
     	lstatus.setText("Доступно обновление лаунчера");
     	lstatus.setHorizontalAlignment(TLabel.CENTER);
     	lstatus.setBounds(0,0,w,30);
     	this.add(lstatus);
     	
  	  	lpbar = new JProgressBar();
  	  	lpbar.setBounds(0,30,w, 15);
     	this.add(lpbar);
  	  	
  	  	
    	ldownload = new TButton();
    	ldownload.setText("Обновить");
    	ldownload.setBounds(0, 45, w, 25);
    	ldownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            lstatus.setText("Скачиваем обновление");
           	ldownload.setEnabled(false);
           	later.setEnabled(false);
            new LUpdateThread(thisclass, url).start();
            }
        });
    	this.add(ldownload);

    	
    	
    	later = new TButton();
    	later.setText("Позже");
    	later.setBounds(0,70,w,25);
    	later.addActionListener(new ActionListener() {
 	               @Override
 	               public void actionPerformed(ActionEvent e) {
 	            	   thisclass.dispose();
 	               }
    	});
    	this.add(later);

	}

}
