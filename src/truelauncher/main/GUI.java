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

package truelauncher.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import truelauncher.client.ClientLaunch;
import truelauncher.client.ClientUpdateThread;
import truelauncher.config.AllSettings;
import truelauncher.gcomponents.TButton;
import truelauncher.gcomponents.TComboBox;
import truelauncher.gcomponents.TLabel;
import truelauncher.gcomponents.TProgressBar;
import truelauncher.gcomponents.TTextField;
import truelauncher.images.Images;
import truelauncher.launcher.LauncherVersionChecker;
import truelauncher.launcher.LauncherUpdateDialog;
import truelauncher.utils.CryptoUtils;
import truelauncher.utils.LauncherUtils;

@SuppressWarnings("serial")
public class GUI extends JPanel {

	private static GUI staticgui;
	
	private TTextField nickfield;
	private TTextField passfield;
	private TComboBox listclients; 
	private TButton launch;
	private TProgressBar pbar;
	private TButton download;
	private TComboBox listdownloads;
	private LauncherUpdateDialog lu;
	private JFrame frame;
	
	private int posX=0,posY=0;
 
	public GUI(JFrame frame)
	{
		try {
    		AllSettings.load();
			this.frame = frame;
			this.setLayout(null);
			//border
			this.setBorder(BorderFactory.createBevelBorder(1, Color.GRAY, Color.GRAY));
			//init GUI
			initUI();
			//load fields values
			loadTextFields();
		    staticgui = this;
		}
		catch (Exception e)
		{
			LauncherUtils.logError(e);
		}
	}  
 
 
 
 
     private void initUI() {
    	 initHeader();
    	 showCloseMinimizeButton();
    	 initTextInputFieldsAndLabels();
    	 initStartButton();
    	 initDownloadCenter();
    	 initLauncherUpdater();
     }
     
     //header
     private void initHeader()
     {
			JLabel drag = new JLabel();
			drag.setBounds(0, 0, AllSettings.w, 15);
			drag.setOpaque(false);
			drag.setBackground(new Color(0,0,0,0));
			drag.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					posX=e.getX();
					posY=e.getY();
				}
			});
			drag.addMouseMotionListener(new MouseAdapter() {
			     public void mouseDragged(MouseEvent evt) {
					frame.setLocation(evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
			     }
			});
			this.add(drag);
     }
     
     //close and minimize buttonis block
     private void showCloseMinimizeButton()
     {
      	 JPanel cmb = new JPanel();
      	 cmb.setLayout(null);
      	 cmb.setBounds(AllSettings.w - 75,15,60,25);
      	 
      	 TButton minimize = new TButton();
      	 minimize.setBounds(0,0,25,25);
      	 minimize.setBackgroundImage(AllSettings.hide);
      	 minimize.addActionListener(new ActionListener()
      	 {
      		 @Override 
      		 public void actionPerformed(ActionEvent e)
      		 {
      			 frame.setExtendedState(JFrame.ICONIFIED);
      		 }
      	 });
      	 cmb.add(minimize);
      	 
      	 TButton close = new TButton();
      	 close.setBounds(35, 0, 25, 25);
      	 close.setBackgroundImage(AllSettings.close);
      	 close.addActionListener(new ActionListener()
      	 {
      		 @Override 
      		 public void actionPerformed(ActionEvent e)
      		 {
      			 System.exit(0);
      		 }
      	 });
      	 cmb.add(close);
      	 cmb.setOpaque(false);
      	 cmb.setBackground(new Color(0,0,0,0));
      	 
      	 this.add(cmb);
     }

     //block 1 (nickname chooser)
     private void initTextInputFieldsAndLabels()
     {
    	 int y = AllSettings.h - 110;
    	 int levelw = 30;
      	 int widgw = 220;
      	 
      	 
      	 JPanel tifields = new JPanel();
      	 tifields.setLayout(null);
      	 tifields.setBounds(levelw, y, widgw, 95);
      	 tifields.setOpaque(false);
      	 tifields.setBackground(new Color(0,0,0,0));
      	 
      	 //Плашка объясениния
    	 TLabel expbarset = new TLabel();
    	 expbarset.setBounds(0,0,widgw,25);
    	 expbarset.setBackgroundImage(Images.class.getResourceAsStream(AllSettings.explainimage));
    	 expbarset.setText("Основные настройки");
      	 expbarset.setHorizontalAlignment(TButton.CENTER);
    	 tifields.add(expbarset);
    	 
    	 
    	 //Плашка ника
    	 int lnw = 80;
    	 int lnh = 20;
    	 TLabel labelnick = new TLabel();
    	 labelnick.setBounds(0,25,lnw,lnh);
    	 labelnick.setBackgroundImage(Images.class.getResourceAsStream(AllSettings.labelimage));
    	 labelnick.setText("Ник");
      	 labelnick.setHorizontalAlignment(TButton.CENTER);
    	 tifields.add(labelnick);
    	 //Поле ника
    	 int inw = 140;
    	 nickfield = new TTextField();
    	 nickfield.setBounds(lnw,25,inw,lnh);
    	 nickfield.setText("NoNickName");
    	 nickfield.setHorizontalAlignment(TButton.CENTER);
    	 tifields.add(nickfield);

    	 //Плашка пароля
    	 int lrw = 80;
    	 int lrh = 20;
    	 TLabel labelpass = new TLabel();
    	 labelpass.setBounds(0,45,lrw,lrh);
    	 labelpass.setText("Пароль");
      	 labelpass.setHorizontalAlignment(TButton.CENTER);
    	 labelpass.setBackgroundImage(Images.class.getResourceAsStream(AllSettings.labelimage));
    	 tifields.add(labelpass);
       	 //Поле пароля
    	 int irw = 140;
    	 passfield = new TTextField();
    	 passfield.setBounds(lrw,45,irw,lrh);
    	 passfield.setText("");
    	 passfield.setHorizontalAlignment(TButton.CENTER);
    	 tifields.add(passfield);
    	 
    	 //Кнопка сохранить
    	 TButton save = new TButton();
    	 save.setText("Сохранить настройки");
    	 save.setBounds(0,65,widgw,30);
    	 save.addActionListener(
    			 new ActionListener() {
    				 @Override
    				 public void actionPerformed(ActionEvent e) {
    					 saveTextFields();
    				 }
         });
    	 tifields.add(save);
    	 
    	 this.add(tifields);
     }

     
     //block 2 (clients start)
     private void initStartButton()
     {
    	 int y = AllSettings.h - 110;
    	 int levelw = 250;
      	 int widgw = 240;
    	 
      	JPanel sb = new JPanel();
      	sb.setLayout(null);
      	sb.setBounds(levelw, y , widgw, 95);
      	sb.setOpaque(false);
      	sb.setBackground(new Color(0,0,0,0));
      	 
      	 //плашка объяснений
      	TLabel expbarset = new TLabel();
       	expbarset.setBounds(0,0,widgw,25);
      	expbarset.setText("Выбор клиента");
      	expbarset.setHorizontalAlignment(TButton.CENTER);
    	expbarset.setBackgroundImage(Images.class.getResourceAsStream(AllSettings.explainimage));
      	 
    	sb.add(expbarset);
    	 
    	listclients = new TComboBox();
 	    List<String> servclientslist = AllSettings.getClientsList();
  	    for (String servname : servclientslist)
 	    {
 	    	listclients.addItem(servname);
 	    }
  	    listclients.setBounds(0,25,widgw,30);
  	    listclients.setAlignmentY(JComboBox.CENTER_ALIGNMENT);
 	    listclients.addActionListener(
 	    		new ActionListener() {
 	    			@Override
 	    			public void actionPerformed(ActionEvent e) {
 	    				checkClientJarExistInternal();
 	    			}
 	    		});
 	   sb.add(listclients);
 	    
 	   //кнопка запуска майна
       launch = new TButton();
       launch.setBounds(0, 55, widgw, 40);
       launch.setText("Запустить Minecraft");
       launch.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
            	// selection name
            	String clientname = listclients.getSelectedItem().toString();
     			// laucnher folder
     			String mcpath = LauncherUtils.getDir() + File.separator + AllSettings.getClientFolderByName(clientname);
     			// nickname
     			String nick = nickfield.getText();
     			// RAM
     			int ram = 512;
     			if (System.getProperty("sun.arch.data.model").contains("64"))
     			{
     				ram = 1024;
     			}
     			String mem = Integer.valueOf(ram) + "M";
     			// password (empty for now)
     			String password = passfield.getText();
     			// location of jar file
     			String jar = LauncherUtils.getDir()+ File.separator + AllSettings.getClientJarByName(clientname);
     			// mainclass
     			String mainclass = AllSettings.getClientMainClassByName(clientname);
     			// cmdargs
     			String cmdargs = AllSettings.getClientCmdArgsByName(clientname);
     			//launch minecraft (mcpach, nick, mem, jar, mainclass, cmdargs)
            	ClientLaunch.launchMC(mcpath, nick, password, mem, jar, mainclass, cmdargs);
             }
       });
	   checkClientJarExistInternal();
       sb.add(launch);

   
       this.add(sb);
     }

     //block 3 (clients download)
     private void initDownloadCenter()
     {
    	 int y = AllSettings.h - 110;
    	 int levelw = 490;
      	 int widgw = 220;
      	 
      	 JPanel dc = new JPanel();
      	 dc.setLayout(null);
      	 dc.setBounds(levelw, y, widgw, 95);
      	 dc.setOpaque(false);
      	 dc.setBackground(new Color(0,0,0,0));
      	 
    	 TLabel expbarset = new TLabel();
    	 expbarset.setBounds(0,0,widgw,25);
    	 expbarset.setBackgroundImage(Images.class.getResourceAsStream(AllSettings.explainimage));
    	 expbarset.setText("Скачивание клиентов");
    	 expbarset.setHorizontalAlignment(TLabel.CENTER);
    	 dc.add(expbarset);
    	 
     	listdownloads = new TComboBox();
 	    List<String> servdownloadlist = AllSettings.getClientsList();
  	    for (String servname : servdownloadlist)
 	    {
  	    	listdownloads.addItem(servname);
 	    }
  	    listdownloads.setBounds(0,25,widgw,30);
  	    listdownloads.addActionListener(new ActionListener() {
    		 @Override
             public void actionPerformed(ActionEvent e) {
    			 download.setText("Скачать клиент");
    			 pbar.setValue(0);
    			 download.setEnabled(true);
    		 }
    	});
  	    dc.add(listdownloads);
  	    
  	  	pbar = new TProgressBar();
  	  	pbar.setBounds(0,55,widgw, 16);
  	    dc.add(pbar);
  	  
    	download = new TButton();
    	download.setBounds(0, 70, widgw, 25);
    	download.setText("Скачать клиент");
    	download.setHorizontalAlignment(TButton.CENTER);
             	download.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
            	 //lock gui
            	 listdownloads.setEnabled(false);
            	 download.setEnabled(false);
            	 //get client name
            	 String client = listdownloads.getSelectedItem().toString();
            	 //url to donwload from
            	 String urlfrom = AllSettings.getClientDownloadLinkByName(client);
            	 //temp zip file
            	 String tempfile = null;
            	 try {
            		 tempfile = LauncherUtils.getDir() + File.separator + AllSettings.getClientsTempFolderPath() + File.separator + new File(new URL(urlfrom).getFile()).getName();
            	 } catch (MalformedURLException e1) {}
            	 //client destination
            	 String destination = LauncherUtils.getDir() + File.separator + AllSettings.getClientFolderByName(client);
            	 //run client update
            	 new ClientUpdateThread(listdownloads, download, pbar, urlfrom, tempfile, destination).start();    	 
             }
         });
  	    dc.add(download);
    	
    	this.add(dc);
     }

     //Init laucnher updater
     private void initLauncherUpdater()
     {
    	 lu = new LauncherUpdateDialog();
    	 new LauncherVersionChecker().start();
     }
     
     //load nick and password
     private void loadTextFields()
     {
         try {
             String ps = LauncherUtils.getDir();
             File config = new File(ps + File.separator + AllSettings.getLauncherConfigFolderPath()+ File.separator + "launcherdata");
             if (config.exists()) {
            	 Scanner scanner = new Scanner(config);
                 String nick = scanner.nextLine();
                 String passwordstring = scanner.nextLine();
                 nickfield.setText(nick);
                 if (passwordstring.isEmpty())
                 {
                	 passfield.setText("");
                 } else
                 {
                	 passfield.setText(CryptoUtils.decryptString(passwordstring));
                 }
                 scanner.close();
             }
         } catch (Exception e) {
        	 LauncherUtils.logError(e);
         }
     }
     //save nick and password
     private void saveTextFields()
     {
         try {
        	 String ps = LauncherUtils.getDir();
        	 new File(ps + File.separator + AllSettings.getLauncherConfigFolderPath()).mkdirs(); 
             File config = new File(ps + File.separator + AllSettings.getLauncherConfigFolderPath()+ File.separator + "launcherdata");
             PrintWriter writer = new PrintWriter(config);
             String nick = nickfield.getText();
             writer.println(nick);
        	 String password = passfield.getText();
             if (password.isEmpty())
             {
            	 writer.println("");
             } else
             {
            	 writer.println(CryptoUtils.encryptString(password));
             }
             writer.flush();
             writer.close();
         } catch (Exception e) {
        	 LauncherUtils.logError(e);
         }
     }

     //check client jars exist
     private void checkClientJarExistInternal()
     {
    	File cfile = new File(LauncherUtils.getDir()+File.separator+AllSettings.getClientJarByName(listclients.getSelectedItem().toString()));
		if (cfile.exists()) {
			launch.setEnabled(true);
    	 	launch.setText("Запустить Minecraft");
		} else {
			launch.setText("Клиент не найден");
			launch.setEnabled(false);
		}
     }

     @Override
     public void paintComponent(Graphics g) {
    	 try {
			Image bg = ImageIO.read(Images.class.getResourceAsStream(AllSettings.bgimage));
			bg = bg.getScaledInstance(AllSettings.w, AllSettings.h, Image.SCALE_SMOOTH);
			g.drawImage(bg, 0, 0, null);
    	 } catch (IOException e) {
    		 e.printStackTrace();
    	 }
     }
     
     
     //static access
     //check client jar
     public static void checkClientJarExist()
     {
    	 staticgui.checkClientJarExistInternal();
     }
     //open launcher update window
     public static void openUpdateWindow()
     {
    	 staticgui.frame.getGlassPane().setVisible(true);
    	 staticgui.lu.open(staticgui);
     }
     //close launcher update window
     public static void closeUpdateWindow()
     {
    	 staticgui.lu.dispose();
    	 staticgui.frame.getGlassPane().setVisible(false);
     }
     
}