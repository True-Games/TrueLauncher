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
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import truelauncher.gcomponents.TButton;
import truelauncher.gcomponents.TComboBox;
import truelauncher.gcomponents.TLabel;
import truelauncher.gcomponents.TTextField;

@SuppressWarnings("serial")
public class GUI extends JPanel {

	private GUI thisclass = this;
	
	public TTextField nickfield;
	public TTextField ramfield;
	public TComboBox listclients; 
	public TButton launch;
	public JProgressBar pbar;
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
     }
     

     //1й блок
     private void initTextInputFieldsAndLabels()
     {
    	 int y = AllSettings.h - 130;
    	 int levelw = 30;
      	 int widgw = 220;
      	 
      	 
      	 JPanel tifields = new JPanel();
      	 tifields.setLayout(null);
      	 tifields.setBounds(levelw, y, widgw, 95);
      	 
      	 //Плашка объясениния
    	 TLabel expbarset = new TLabel();
    	 expbarset.setBackgroundImage(GUI.class.getResourceAsStream(AllSettings.explainimage),widgw, 25);
    	 expbarset.setText("Основные настройки");
      	 expbarset.setHorizontalAlignment(TButton.CENTER);
    	 expbarset.setBounds(0,0,widgw,25);
    	 tifields.add(expbarset);
    	 
    	 
    	 //Плашка ника
    	 int lnw = 80;
    	 int lnh = 20;
    	 TLabel labelnick = new TLabel();
    	 labelnick.setBackgroundImage(GUI.class.getResourceAsStream(AllSettings.labelimage),widgw, lnh);
    	 labelnick.setText("Ник");
      	 labelnick.setHorizontalAlignment(TButton.CENTER);
    	 labelnick.setBounds(0,25,lnw,lnh);
    	 tifields.add(labelnick);

    	 //Поле ника
    	 int inw = 140;
    	 int inh = 20;
    	 nickfield = new TTextField();
    	 nickfield.setText("NoNickName");
    	 nickfield.setHorizontalAlignment(TButton.CENTER);
    	 nickfield.setBounds(lnw,25,inw,inh);
    	 tifields.add(nickfield);

    	 //Плашка памяти
    	 int lrw = 80;
    	 int lrh = 20;
    	 TLabel labelram = new TLabel();
    	 labelram.setText("Память");
      	 labelram.setHorizontalAlignment(TButton.CENTER);
    	 labelram.setBounds(0,45,lrw,lrh);
    	 labelram.setBackgroundImage(GUI.class.getResourceAsStream(AllSettings.labelimage),widgw, lrh);
    	 tifields.add(labelram);

       	 //Поле памяти
    	 int irw = 140;
    	 int irh = 20;
    	 ramfield = new TTextField();
    	 ramfield.setText("400");
      	 ramfield.setHorizontalAlignment(TButton.CENTER);
    	 ramfield.setBounds(lrw,45,irw,irh);
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

     
     //блок 2
     private void initStartButton()
     {
    	 int y = AllSettings.h - 130;
    	 int levelw = 250;
      	 int widgw = 240;
    	 
      	JPanel sb = new JPanel();
      	sb.setLayout(null);
      	sb.setBounds(levelw, y , widgw, 95);
      	 
      	 //плашка объяснений
      	TLabel expbarset = new TLabel();
      	expbarset.setText("Выбор клиента");
      	expbarset.setHorizontalAlignment(TButton.CENTER);
    	expbarset.setBackgroundImage(GUI.class.getResourceAsStream(AllSettings.explainimage), widgw, 25);
    	expbarset.setBounds(0,0,widgw,25);
      	 
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
            	 	String lfolder = AllSettings.getClientFolderByName(listclients.getSelectedItem().toString());
            	 	String nick = nickfield.getText();
            	 	int ram = Integer.valueOf(ramfield.getText());
            	 	int mclvers = AllSettings.getClientLaunchVersionByName(listclients.getSelectedItem().toString());
            	 	String jar = AllSettings.getClientJarByName(listclients.getSelectedItem().toString());
            	 	ArrayList<String> libs = AllSettings.getClientLibsByName(listclients.getSelectedItem().toString());
            	 	LauncherUtils.launchMC(lfolder, nick, ram, mclvers,libs, jar);
             }
       });
	   LauncherUtils.checkClientJarExist(thisclass);
       sb.add(launch);

   
       this.add(sb);
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
     
     

     

     //блок 3
     private void initDownloadCenter()
     {
    	 int y = AllSettings.h - 130;
    	 int levelw = 490;
      	 int widgw = 220;
      	 
      	 JPanel dc = new JPanel();
      	 dc.setLayout(null);
      	 dc.setBounds(levelw, y, widgw, 95);
      	 
    	 TLabel expbarset = new TLabel();
    	 expbarset.setBackgroundImage(GUI.class.getResourceAsStream(AllSettings.explainimage),widgw, 25);
    	 expbarset.setText("Скачивание клиентов");
    	 expbarset.setHorizontalAlignment(TLabel.CENTER);
    	 expbarset.setBounds(0,0,widgw,25);
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
  	    
  	  	pbar = new JProgressBar();
  	  	pbar.setBounds(0,55,widgw, 15);
  	    dc.add(pbar);
  	  
    	download = new TButton();
    	download.setText("Скачать клиент");
    	download.setHorizontalAlignment(TButton.CENTER);
    	download.setBounds(0, 70, widgw, 25);
         
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
     

     private void showLauncherUpdateWindow()
     {
      	 int ww = 250;
      	 int wh = 125;
    	 lu = new LauncherUpdateDialog(f, thisclass, AllSettings.getLauncherWebUpdateURLFolder()+"/"+"Launcher.jar", ww, wh);
    	 (new LVersionChecker(thisclass, AllSettings.getLauncherWebUpdateURLFolder()+"/"+"version", AllSettings.getLauncherVerison())).start();
    	 
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

     

     


