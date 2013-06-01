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

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

public class GUI {

 private GUI thisclass = this;
 private ListContainer settingscontainer = new ListContainer();
 private Shell shell;
 private int w = 935;
 private int h = 500;
 int levelh = h-110;
 private String icon = "images/icon.png";
 private String bgimage = "images/bgimage.png";
 private String labelimage = "images/labelbar.png";
 private String textiamge = "images/textfield.png";
 private String explainimage = "images/expbar.png";
 
 public Text nickfield;
 public Text ramfield;
 public Button launch;
 public Label lstatus;
 public ProgressBar pbar;
 public ProgressBar lpbar;
 public Button download;
 public Button ldownload;
 public Combo listdownloads;
 public GUI(Display display)
 {
	 try {
     shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
     initUI(display);
     shell.setText("True-games.org|MinecraftLauncher");
     shell.setSize(w, h);
     shell.setImage(new Image(display, GUI.class.getResourceAsStream(icon)));
     shell.setBackgroundImage(
    		 new Image(display,
    				 new Image(display,GUI.class.getResourceAsStream(bgimage)).getImageData().scaledTo(w, h)
    				  )
    		 );
     java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
     shell.setLocation((screenSize.width-w)/2, (screenSize.height-h)/2);

     shell.open();
     loadTextFields();

     while (!shell.isDisposed()) {
       if (!display.readAndDispatch()) {
         display.sleep();
       }
     }
	 }
	 catch (Exception e)
	 {
		 e.printStackTrace();
	 }
 
 }   
     private void initUI(Display display) {
    	 initTextInputFieldsAndLabels(display);
    //	 initServersStatusFields(display);
    	 initStartButton(display);
    	 initDownloadCenter(display);
    	 initLauncherUpdater(display);
     }
     

     
     private void initTextInputFieldsAndLabels(Display display)
     {
    	 int levelw = 25;
      	 int widgw = 375;
      	 
      	 
    	 Label expbarset = new Label(shell,SWT.CENTER);
    	 expbarset.setBackgroundImage(
    			 new Image(display,
    					 new Image(display,GUI.class.getResourceAsStream(explainimage)).getImageData().scaledTo(widgw, 30)
    					  )
    			 );
    	 expbarset.setText("Основные настройки");
    	 expbarset.setBounds(levelw,levelh-30,widgw,30);
    	 
    	 int lwidgw = widgw/2;
    	 Label labelnick = new Label(shell, SWT.CENTER);
    	 labelnick.setBackgroundImage(
       			 new Image(display,
       					 new Image(display,GUI.class.getResourceAsStream(labelimage)).getImageData().scaledTo(lwidgw, 20)
       					 )
    			 );
    	 labelnick.setText("Ник");
    	 labelnick.setBounds(levelw,levelh,lwidgw,20);
    	 Label labelram = new Label(shell, SWT.CENTER);
    	 labelram.setBackgroundImage(
       			 new Image(display,
       					 new Image(display,GUI.class.getResourceAsStream(labelimage)).getImageData().scaledTo(lwidgw, 20)
       					 )
    			 );
    	 labelram.setText("Память (в мегабайтах)");
    	 labelram.setBounds(levelw+lwidgw,levelh,lwidgw,20);
    	 
    	 int twidgw = widgw/2;
    	 nickfield = new Text(shell, SWT.SINGLE | SWT.CENTER);
    	 nickfield.setBackgroundImage(
       			 new Image(display,
       					 new Image(display, GUI.class.getResourceAsStream(textiamge)).getImageData().scaledTo(twidgw, 20)
       					 )
       			 );
    	 nickfield.setText("NoNickName");
    	 nickfield.setForeground(display.getSystemColor(SWT.COLOR_CYAN));
    	 nickfield.setBounds(levelw,levelh+20,twidgw,20);
    	 ramfield = new Text(shell, SWT.SINGLE | SWT.CENTER);
    	 ramfield.setBackgroundImage(
       			 new Image(display,
       					 new Image(display, GUI.class.getResourceAsStream(textiamge)).getImageData().scaledTo(twidgw, 20)
       					 )
       			 );
    	 ramfield.setText("512");
    	 ramfield.setForeground(display.getSystemColor(SWT.COLOR_CYAN));
    	 ramfield.setBounds(levelw+lwidgw,levelh+20,twidgw,20);
    	 
    	 Button save = new Button(shell, SWT.PUSH);
    	 save.setText("Сохранить настройки");
    	 save.setBounds(levelw,levelh+40,widgw,30);
    	 save.addSelectionListener(new SelectionAdapter() {
             @Override
             public void widgetSelected(SelectionEvent e) {
            	 saveTextFields();
             }
         });
     }

     private void initStartButton(Display display)
     {
    	 int levelw = 400;
      	 int widgw = 170;
    	 
      	 Label expbarset = new Label(shell,SWT.CENTER);
    	 expbarset.setBackgroundImage(
    			 new Image(display,
    					 new Image(display,GUI.class.getResourceAsStream(explainimage)).getImageData().scaledTo(widgw, 30)
    					  )
    			 );
    	 expbarset.setText("Выбор клиента");
    	 expbarset.setBounds(levelw,levelh-30,widgw,30);
      	 
    	final Combo listclients = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
 	    List<String> servclientslist = settingscontainer.getClients();
  	    for (String servname : servclientslist)
 	    {
 	    	listclients.add(servname);
 	    }
  	    

  	  	
 	    listclients.setBounds(levelw,levelh,widgw,30);
 	    listclients.addSelectionListener(
 	    		new SelectionAdapter() {
 	               @Override
 	               public void widgetSelected(SelectionEvent e) {
 	             	  	File cfile = new File(LauncherUtils.getDir()+"/"+settingscontainer.getCheckFileByName(listclients.getText()));
 	              	  	if (cfile.exists()) {
 	              	  		launch.setEnabled(true);
 	              	  		launch.setText("Запустить Minecraft");
 	              	  		} else {
 	              	  		launch.setEnabled(false);
 	                  	    launch.setText("Клиент не найден\nЗапуск Minecraft невозможен");
 	              	  		}
 	              }
 	           });
    	 
         launch = new Button(shell, SWT.PUSH);
    	 launch.setText("Запустить Minecraft");
    	 launch.setBounds(levelw, levelh+27, widgw, 43);
         
    	 launch.addSelectionListener(new SelectionAdapter() {
             @Override
             public void widgetSelected(SelectionEvent e) {
            	 	String lfolder = settingscontainer.getClientFolderByName(listclients.getText());
            	 	String nick = nickfield.getText();
            	 	int ram = Integer.valueOf(ramfield.getText());
            	 	int mclvers = settingscontainer.getLaunchVersionByName(listclients.getText());
            	 	LauncherUtils.launchMC(lfolder, nick, ram, mclvers);
             }
         });

    	 
   	    //select and check client for existance
   	    listclients.select(0);
   	  	File cfile = new File(LauncherUtils.getDir()+"/"+settingscontainer.getCheckFileByName(listclients.getText()));
   	  	if (cfile.exists()) {
   	  		launch.setEnabled(true);
   	  		launch.setText("Запустить Minecraft");
   	  		} else {
   	  		launch.setEnabled(false);
       	    launch.setText("Клиент не найден");
   	  		}
     }
     
     private void initDownloadCenter(final Display display)
     {
    	 int levelw = 570;
      	 int widgw = 170;
    	 Label expbarset = new Label(shell,SWT.CENTER);
    	 expbarset.setBackgroundImage(
    			 new Image(display,
    					 new Image(display,GUI.class.getResourceAsStream(explainimage)).getImageData().scaledTo(widgw, 30)
    					  )
    			 );
    	 expbarset.setText("Скачивание клиентов");
    	 expbarset.setBounds(levelw,levelh-30,widgw,30);
    	 
     	listdownloads = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
 	    List<String> servdownloadlist = settingscontainer.getDownloads();
  	    for (String servname : servdownloadlist)
 	    {
  	    	listdownloads.add(servname);
 	    }
  	    listdownloads.setBounds(levelw,levelh,widgw,30);
    	listdownloads.select(0);
    	listdownloads.addSelectionListener(new SelectionAdapter() {
    		 @Override
             public void widgetSelected(SelectionEvent e) {
    			 download.setText("Скачать клиент");
    			 pbar.setSelection(0);
    			 download.setEnabled(true);
    		 }
    	});
  	  	pbar = new ProgressBar(shell, SWT.SMOOTH);
  	  	pbar.setBounds(levelw,levelh+28,widgw, 15);
  	  
    	download = new Button(shell, SWT.PUSH);
    	download.setText("Скачать клиент");
    	download.setBounds(levelw, levelh+43, widgw, 27);
         
    	download.addSelectionListener(new SelectionAdapter() {
             @Override
             public void widgetSelected(SelectionEvent e) {
            	 listdownloads.setEnabled(false);
            	 String name = listdownloads.getText();
            	 String URL = settingscontainer.getDownloadLinkByName(name);
            	 String clientto = settingscontainer.getFolderToByName(name);
            	 new ClientUpdateThread(thisclass,display,URL,LauncherUtils.getDir()+settingscontainer.getTempFolderPath(),clientto).start();
            	 download.setEnabled(false);
             }
         });
     }
     
     private void initLauncherUpdater(final Display display)
     {
       	 int levelw = 740;
      	 int widgw = 170;
    	 Label expbarset = new Label(shell,SWT.CENTER);
    	 expbarset.setBackgroundImage(
    			 new Image(display,
    					 new Image(display,GUI.class.getResourceAsStream(explainimage)).getImageData().scaledTo(widgw, 30)
    					  )
    			 );
    	 expbarset.setText("Обновление лаунчера");
    	 expbarset.setBounds(levelw,levelh-30,widgw,30);  	
    	 
     	lstatus = new Label(shell, SWT.CENTER);
     	lstatus.setBackgroundImage(
     			new Image(display,
     					new Image(display,GUI.class.getResourceAsStream(labelimage)).getImageData().scaledTo(widgw, 29)
     					)
     			);
     	lstatus.setText("Проверка обновлений");
     	lstatus.setBounds(levelw,levelh,widgw,29);
     	
  	  	lpbar = new ProgressBar(shell, SWT.SMOOTH);
  	  	lpbar.setBounds(levelw,levelh+28,widgw, 15);
     	
    	ldownload = new Button(shell, SWT.PUSH);
    	ldownload.setText("Обновить");
    	ldownload.setBounds(levelw, levelh+43, widgw, 27);
    	ldownload.setEnabled(false);
         
    	ldownload.addSelectionListener(new SelectionAdapter() {
             @Override
             public void widgetSelected(SelectionEvent e) {
            	 ldownload.setEnabled(false);
            	 (new LUpdateThread(thisclass, display, settingscontainer.getLUpdateURLFolder()+"Launcher.jar")).start();
             }
         });
    	
    	(new LVersionChecker(thisclass, display, settingscontainer.getLUpdateURLFolder()+"version", settingscontainer.getLVerison())).start();
    	

     }
     
     
     private void loadTextFields()
     {
         try {
             String ps = LauncherUtils.getDir();
             if ((new File(ps + settingscontainer.getConfigFolderPath()+"/config")).exists()) {
                 Scanner inFile = new Scanner(new File(ps + settingscontainer.getConfigFolderPath()+"/config"));
                 nickfield.setText(inFile.nextLine());
                 ramfield.setText(inFile.nextLine());
                 inFile.close();
             }
         } catch (Exception e) {
         }
     }
     
     private void saveTextFields()
     {
     String ps = LauncherUtils.getDir();
     if (!((new File(ps + settingscontainer.getConfigFolderPath()+"/config")).exists())) {
         (new File(ps + settingscontainer.getConfigFolderPath())).mkdirs();
     }
     try {
         PrintWriter wrt = new PrintWriter(new File(ps + settingscontainer.getConfigFolderPath()+"/config"));
         wrt.println(nickfield.getText());
         wrt.println(ramfield.getText());
         wrt.flush();
         wrt.close();
     } catch (Exception e) {}
     }
     
     

 
}
