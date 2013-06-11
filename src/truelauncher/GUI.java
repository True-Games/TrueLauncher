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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

public class GUI {

 private GUI thisclass = this;
 private ListContainer settingscontainer = new ListContainer();
 private Shell shell;
 private int w = 640;
 private int h = 320;
 int levelh = h-110;
 private String icon = "images/icon.png";
 private String bgimage = "images/bgimage.png";
 private String labelimage = "images/labelbar.png";
 private String textiamge = "images/textfield.png";
 private String explainimage = "images/expbar.png";
 
 public Text nickfield;
 public Text ramfield;
 public Button launch;
 public ProgressBar pbar;
 public Button download;
 public Combo listdownloads;
 public LauncherUpdateDialog lu;
 
 public GUI(Display display)
 {
	 try {
     shell = new Shell(display,  SWT.CLOSE | SWT.TITLE | SWT.MIN);
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
    	 initStartButton(display);
    	 initDownloadCenter(display);
    	 showLauncherUpdateWindow(display);
     }
     

     //1й блок
     private void initTextInputFieldsAndLabels(Display display)
     {
    	 int levelw = 50;
      	 int widgw = 200;
      	 
      	 Composite tifields = new Composite(shell, SWT.TRANSPARENT);
      	 tifields.setBounds(levelw, levelh-25, widgw, 95);
      	 
      	 //Плашка объясениния
    	 Label expbarset = new Label(tifields,SWT.CENTER);
    	 expbarset.setBackgroundImage(
    			 new Image(display,
    					 new Image(display,GUI.class.getResourceAsStream(explainimage)).getImageData().scaledTo(widgw, 25)
    					  )
    			 );
    	 expbarset.setText("Основные настройки");
    	 expbarset.setBounds(0,0,widgw,25);
    	 
    	 
    	 //Плашка ника
    	 int lnw = 70;
    	 int lnh = 20;
    	 Label labelnick = new Label(tifields, SWT.CENTER);
    	 labelnick.setBackgroundImage(
       			 new Image(display,
       					 new Image(display,GUI.class.getResourceAsStream(labelimage)).getImageData().scaledTo(lnw, lnh)
       					 )
    			 );
    	 labelnick.setText("Ник");
    	 labelnick.setBounds(0,25,lnw,lnh);

    	 //Поле ника
    	 int inw = 130;
    	 int inh = 20;
    	 nickfield = new Text(tifields, SWT.SINGLE | SWT.CENTER | SWT.IMAGE_PNG);
    	 nickfield.setBackgroundImage(
       			 new Image(display,
       					 new Image(display, GUI.class.getResourceAsStream(textiamge)).getImageData().scaledTo(inw, inh)
       					 )
       			 );
    	 nickfield.setText("NoNickName");
    	 nickfield.setForeground(display.getSystemColor(SWT.COLOR_CYAN));
    	 nickfield.setBounds(70,25,inw,inh);

    	 //Плашка памяти
    	 int lrw = 70;
    	 int lrh = 20;
    	 Label labelram = new Label(tifields, SWT.CENTER);
    	 labelram.setText("Память");
    	 labelram.setBounds(0,45,lrw,lrh);
    	 labelram.setBackgroundImage(
       			 new Image(display,
       					 new Image(display,GUI.class.getResourceAsStream(labelimage)).getImageData().scaledTo(lrw, lrh)
       					 )
    			 );

       	 //Поле памяти
    	 int irw = 130;
    	 int irh = 20;
    	 ramfield = new Text(tifields, SWT.SINGLE | SWT.CENTER);
    	 ramfield.setText("512");
    	 ramfield.setForeground(display.getSystemColor(SWT.COLOR_CYAN));
    	 ramfield.setBounds(70,45,irw,irh);
    	 ramfield.setBackgroundImage(
       			 new Image(display,
       					 new Image(display, GUI.class.getResourceAsStream(textiamge)).getImageData().scaledTo(irw, irh)
       					 )
       			 );
    	 
    	 //Кнопка сохранить
    	 Button save = new Button(tifields, SWT.PUSH);
    	 save.setText("Сохранить настройки");
    	 save.setBounds(0,65,widgw,30);
    	 save.addSelectionListener(new SelectionAdapter() {
             @Override
             public void widgetSelected(SelectionEvent e) {
            	 saveTextFields();
             }
         });
     }

     
     //блок 2
     private void initStartButton(Display display)
     {
    	 int levelw = 250;
      	 int widgw = 170;
    	 
      	Composite sb = new Composite(shell, SWT.TRANSPARENT);
      	sb.setBounds(levelw, levelh-25, widgw, 95);
      	 
      	 //плашка объяснений
      	 Label expbarset = new Label(sb,SWT.CENTER);
    	 expbarset.setBackgroundImage(
    			 new Image(display,
    					 new Image(display,GUI.class.getResourceAsStream(explainimage)).getImageData().scaledTo(widgw, 25)
    					  )
    			 );
    	 expbarset.setText("Выбор клиента");
    	 expbarset.setBounds(0,0,widgw,25);
      	 
    	//список клиентов на запуск
    	final Combo listclients = new Combo(sb, SWT.NONE | SWT.READ_ONLY);
 	    List<String> servclientslist = settingscontainer.getClients();
  	    for (String servname : servclientslist)
 	    {
 	    	listclients.add(servname);
 	    }
  	    listclients.setBounds(0,25,widgw,27);
 	    listclients.addSelectionListener(
 	    		new SelectionAdapter() {
 	               @Override
 	               public void widgetSelected(SelectionEvent e) {
 	             	  	File cfile = new File(LauncherUtils.getDir()+"/"+settingscontainer.getJarByName(listclients.getText()));
 	              	  	if (cfile.exists()) {
 	              	  		launch.setEnabled(true);
 	              	  		launch.setText("Запустить Minecraft");
 	              	  		} else {
 	                  	    launch.setText("Клиент не найден");
 	              	  		launch.setEnabled(false);
 	              	  		}
 	              }
 	           });
    	 
 	     //кнопка запуска майна
         launch = new Button(sb, SWT.PUSH);

    	 launch.setBounds(0, 52, widgw, 43);
    	 launch.setText("Запустить Minecraft");
         
    	 launch.addSelectionListener(new SelectionAdapter() {
             @Override
             public void widgetSelected(SelectionEvent e) {
            	 	String lfolder = settingscontainer.getClientFolderByName(listclients.getText());
            	 	String nick = nickfield.getText();
            	 	int ram = Integer.valueOf(ramfield.getText());
            	 	int mclvers = settingscontainer.getLaunchVersionByName(listclients.getText());
            	 	String jar = new File(settingscontainer.getJarByName(listclients.getText())).getName();
            	 	LauncherUtils.launchMC(lfolder, nick, ram, mclvers, jar);
             }
         });

    	 
   	    //select and check client for existance
   	    listclients.select(0);
   	  	File cfile = new File(LauncherUtils.getDir()+"/"+settingscontainer.getJarByName(listclients.getText()));
   	  	if (cfile.exists()) {
   	  		launch.setEnabled(true);
   	  		launch.setText("Запустить Minecraft");
   	  		} else {
      	    launch.setText("Клиент не найден");
         	launch.setEnabled(false);
   	  		}
     }
     
     //блок 3
     private void initDownloadCenter(final Display display)
     {
    	 int levelw = 420;
      	 int widgw = 170;
      	 
      	 Composite dc = new Composite(shell, SWT.TRANSPARENT);
      	 dc.setBounds(levelw, levelh-25, widgw, 95);
      	 
    	 Label expbarset = new Label(dc,SWT.CENTER);
    	 expbarset.setBackgroundImage(
    			 new Image(display,
    					 new Image(display,GUI.class.getResourceAsStream(explainimage)).getImageData().scaledTo(widgw, 25)
    					  )
    			 );
    	 expbarset.setText("Скачивание клиентов");
    	 expbarset.setBounds(0,0,widgw,25);
    	 
     	listdownloads = new Combo(dc, SWT.NONE | SWT.READ_ONLY);
 	    List<String> servdownloadlist = settingscontainer.getDownloads();
  	    for (String servname : servdownloadlist)
 	    {
  	    	listdownloads.add(servname);
 	    }
  	    listdownloads.setBounds(0,25,widgw,27);
    	listdownloads.select(0);
    	listdownloads.addSelectionListener(new SelectionAdapter() {
    		 @Override
             public void widgetSelected(SelectionEvent e) {
    			 download.setText("Скачать клиент");
    			 pbar.setSelection(0);
    			 download.setEnabled(true);
    		 }
    	});
  	  	pbar = new ProgressBar(dc, SWT.SMOOTH);
  	  	pbar.setBounds(0,53,widgw, 15);
  	  
    	download = new Button(dc, SWT.PUSH);
    	download.setText("Скачать клиент");
    	download.setBounds(0, 68, widgw, 27);
         
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
     
     
     //инициализация проверки версии лаунчера
     private void showLauncherUpdateWindow(Display display)
     {
      	 int ww = 250;
      	 int wh = 85;
    	 lu = new LauncherUpdateDialog(shell,ww,wh, settingscontainer.getLUpdateURLFolder()+"Launcher.jar");
    	 (new LVersionChecker(thisclass, display, settingscontainer.getLUpdateURLFolder()+"version", settingscontainer.getLVerison())).start();
    	// lu.open();
    	 
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
