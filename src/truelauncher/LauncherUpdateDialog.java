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
	int w = 250; int h = 90;
	public TButton ldownload;
	public JProgressBar lpbar;
	public TLabel lstatus;
	TButton later;
	GUI parent;
	
	private LauncherUpdateDialog thisclass = this;
	
	public LauncherUpdateDialog(GUI parent) {
        super(parent.f, true);
        this.parent = parent;
        this.setUndecorated(true);
    }
	
	public void open()
	{
		setResizable(false);
		setSize(w,h);
		setLocationRelativeTo(parent);
		initUI();
		setVisible(true);
	}
	
	
	private void initUI()
	{
		this.setLayout(null);
		
     	lstatus = new TLabel();
     	lstatus.setBounds(0,0,w,25);
     	lstatus.setBackgroundImage(GUI.class.getResourceAsStream(labelimage));
     	lstatus.setText("Доступно обновление лаунчера");
     	lstatus.setHorizontalAlignment(TLabel.CENTER);
     	this.add(lstatus);
     	
  	  	lpbar = new JProgressBar();
  	  	lpbar.setBounds(0,25,w, 15);
     	this.add(lpbar);
  	  	
  	  	
    	ldownload = new TButton();
    	ldownload.setText("Обновить");
    	ldownload.setBounds(0, 40, w, 25);
    	ldownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            lstatus.setText("Скачиваем обновление");
           	ldownload.setEnabled(false);
           	later.setEnabled(false);
            new LUpdateThread(thisclass, AllSettings.getLauncherWebUpdateURLFolder()+"/"+"Launcher.jar").start();
            }
        });
    	this.add(ldownload);

    	
    	
    	later = new TButton();
    	later.setText("Позже");
    	later.setBounds(0,65,w,25);
    	later.addActionListener(new ActionListener() {
 	               @Override
 	               public void actionPerformed(ActionEvent e) {
 	            	   parent.f.getGlassPane().setVisible(false);
 	            	   thisclass.dispose();
 	               }
    	});
    	this.add(later);

	}

}
