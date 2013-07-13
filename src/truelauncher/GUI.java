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

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import truelauncher.gcomponents.TButton;
import truelauncher.gcomponents.TComboBox;
import truelauncher.gcomponents.TLabel;
import truelauncher.gcomponents.TProgressBar;
import truelauncher.gcomponents.TTextField;

@SuppressWarnings("serial")
public class GUI extends JPanel {

	private GUI thisclass = this;
	
	public TTextField nickfield;
	public TTextField ramfield;
	public TComboBox listclients; 
	public TButton launch;
	public TProgressBar pbar;
	public TButton download;
	public TComboBox listdownloads;
	public LauncherUpdateDialog lu;
	public Frame f;
 
	public GUI(Frame f)
	{
		try {
			this.f = f;
			this.setLayout(null);	
			initUI();
			loadTextFields();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}  
 
 
 
 
     private void initUI() {
    	 initTextInputFieldsAndLabels();
    	 initStartButton();
    	 initDownloadCenter();
    	 showLauncherUpdateWindow();
    	 showCloseMinimizeButton();
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
    	 expbarset.setBackgroundImage(GUI.class.getResourceAsStream(AllSettings.explainimage));
    	 expbarset.setText("Основные настройки");
      	 expbarset.setHorizontalAlignment(TButton.CENTER);
    	 tifields.add(expbarset);
    	 
    	 
    	 //Плашка ника
    	 int lnw = 80;
    	 int lnh = 20;
    	 TLabel labelnick = new TLabel();
    	 labelnick.setBounds(0,25,lnw,lnh);
    	 labelnick.setBackgroundImage(GUI.class.getResourceAsStream(AllSettings.labelimage));
    	 labelnick.setText("Ник");
      	 labelnick.setHorizontalAlignment(TButton.CENTER);
    	 tifields.add(labelnick);

    	 //Поле ника
    	 int inw = 140;
    	 int inh = 20;
    	 nickfield = new TTextField();
    	 nickfield.setBounds(lnw,25,inw,inh);
    	 nickfield.setText("NoNickName");
    	 nickfield.setHorizontalAlignment(TButton.CENTER);
    	 tifields.add(nickfield);

    	 //Плашка памяти
    	 int lrw = 80;
    	 int lrh = 20;
    	 TLabel labelram = new TLabel();
    	 labelram.setBounds(0,45,lrw,lrh);
    	 labelram.setText("Память");
      	 labelram.setHorizontalAlignment(TButton.CENTER);
    	 labelram.setBackgroundImage(GUI.class.getResourceAsStream(AllSettings.labelimage));
    	 tifields.add(labelram);

       	 //Поле памяти
    	 int irw = 140;
    	 int irh = 20;
    	 ramfield = new TTextField();
    	 ramfield.setBounds(lrw,45,irw,irh);
    	 ramfield.setText("400");
      	 ramfield.setHorizontalAlignment(TButton.CENTER);
    	 tifields.add(ramfield);
    	 
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
    	expbarset.setBackgroundImage(GUI.class.getResourceAsStream(AllSettings.explainimage));
      	 
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
 	      			LauncherUtils.checkClientJarExist(thisclass);
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
            	 	//laucnher folder
            	 	String lfolder = AllSettings.getClientFolderByName(listclients.getSelectedItem().toString());
            	 	//nickname
            	 	String nick = nickfield.getText();
            	 	//RAM
            	 	int ram = Integer.valueOf(ramfield.getText());
            	 	//minecraft launch version
            	 	int mclvers = AllSettings.getClientLaunchVersionByName(listclients.getSelectedItem().toString());
            	 	//location of jar file
            	 	String jar = AllSettings.getClientJarByName(listclients.getSelectedItem().toString());
            	 	//libs locations
            	 	ArrayList<String> libs = AllSettings.getClientLibs();
            	 	//forge present flag
            	 	int forgepresent = AllSettings.getClientForgePresent(listclients.getSelectedItem().toString());
            	 	ClientLaunch.launchMC(lfolder, nick, ram, mclvers,libs, jar, forgepresent);
             }
       });
	   LauncherUtils.checkClientJarExist(thisclass);
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
    	 expbarset.setBackgroundImage(GUI.class.getResourceAsStream(AllSettings.explainimage));
    	 expbarset.setText("Скачивание клиентов");
    	 expbarset.setHorizontalAlignment(TLabel.CENTER);
    	 dc.add(expbarset);
    	 
     	listdownloads = new TComboBox();
 	    List<String> servdownloadlist = AllSettings.getClientListDownloads();
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
            	 listdownloads.setEnabled(false);
            	 String name = listdownloads.getSelectedItem().toString();
            	 String URL = AllSettings.getClientDownloadLinkByName(name);
            	 String clientto = AllSettings.getClientUnpackToFolderByName(name);
            	 String tempfolder = AllSettings.getCientTempFolderPath();
            	 new ClientUpdateThread(thisclass,URL,tempfolder,clientto).start();
            	 download.setEnabled(false);
             }
         });
  	    dc.add(download);
    	
    	this.add(dc);
     }
     
     //close and minimize buttonis block
     private void showCloseMinimizeButton()
     {
      	 JPanel cmb = new JPanel();
      	 cmb.setLayout(null);
      	 cmb.setBounds(AllSettings.w - 70,10,60,25);
      	 
      	 TButton minimize = new TButton();
      	 minimize.setBounds(0,0,25,25);
      	 minimize.setBackgroundImage(AllSettings.hide);
      	 minimize.addActionListener(new ActionListener()
      	 {
      		 @Override 
      		 public void actionPerformed(ActionEvent e)
      		 {
      			 f.setExtendedState(JFrame.ICONIFIED);
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

     private void showLauncherUpdateWindow()
     {
      	 int ww = 250;
      	 int wh = 125;
    	 lu = new LauncherUpdateDialog(f, thisclass, AllSettings.getLauncherWebUpdateURLFolder()+"/"+"Launcher.jar", ww, wh);
    	 (new LVersionChecker(thisclass, AllSettings.getLauncherWebUpdateURLFolder()+"/"+"version", AllSettings.getLauncherVerison())).start();
    	 
     }
     
     
     
     private void loadTextFields()
     {
         try {
             String ps = LauncherUtils.getDir();
             if ((new File(ps + File.separator + AllSettings.getLauncherConfigFolderPath()+ File.separator + "config")).exists()) {
                 Scanner inFile = new Scanner(new File(ps + File.separator + AllSettings.getLauncherConfigFolderPath()+ File.separator + "config"));
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
     if (!((new File(ps + File.separator + AllSettings.getLauncherConfigFolderPath()+ File.separator + "config")).exists())) {
         (new File(ps + File.separator + AllSettings.getLauncherConfigFolderPath())).mkdirs();
     }
     try {
         PrintWriter wrt = new PrintWriter(new File(ps+ File.separator + AllSettings.getLauncherConfigFolderPath()+File.separator+"config"));
         wrt.println(nickfield.getText());
         wrt.println(ramfield.getText());
         wrt.flush();
         wrt.close();
     } catch (Exception e) {}
     }
     
     
     @Override
	 public void paintComponent(Graphics g) {
    	 try {
    		 Image bg = ImageIO.read(GUI.class.getResourceAsStream(AllSettings.bgimage));
    		 bg = bg.getScaledInstance(AllSettings.w, AllSettings.h, Image.SCALE_SMOOTH);
			g.drawImage(bg, 0, 0, null);
    	 } catch (IOException e) {
			e.printStackTrace();
    	 }
     }
     
     
}     

     

     


