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

package truelauncher.launcher;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import truelauncher.client.ClientLaunch;
import truelauncher.client.ClientUpdateThread;
import truelauncher.config.AllSettings;
import truelauncher.gcomponents.TButton;
import truelauncher.gcomponents.TComboBox;
import truelauncher.gcomponents.TLabel;
import truelauncher.gcomponents.TPasswordField;
import truelauncher.gcomponents.TProgressBar;
import truelauncher.gcomponents.TTextField;
import truelauncher.images.Images;
import truelauncher.userprefs.fields.UserFieldsChoice;
import truelauncher.userprefs.settings.UserLauncherSettings;
import truelauncher.utils.LauncherUtils;

@SuppressWarnings("serial")
public class GUI extends JPanel {

	private static GUI staticgui;

	private TTextField nickfield;
	private TPasswordField passfield;
	private TComboBox listclients;
	private TButton launch;
	private TProgressBar pbar;
	private TButton download;
	private TComboBox listdownloads;
	private LauncherUpdateDialog lu;
	private LauncherSettingsDialog ls;
	private JFrame frame;

	private boolean guiinitfinished = false;
	public GUI(JFrame frame) {
		try {
			staticgui = this;
			//load settings
			AllSettings.load();
			//load user prefs data
			UserLauncherSettings.loadConfig();
			UserFieldsChoice.loadConfig();
			//create gui
			this.frame = frame;
			setLayout(null);
			setBorder(BorderFactory.createBevelBorder(1, Color.GRAY, Color.GRAY));
			initUI();
			// load comboboxes values
			fillClients();
			// load client fields values
			loadPrefs();
			// gui init and settings load finished
			guiinitfinished = true;
		} catch (Exception e) {
			LauncherUtils.logError(e);
		}
	}

	private void initUI() {
		initHeader();
		initSettingsButton();
		initCloseMinimizeButton();
		initTextInputFieldsAndLabels();
		initStartButton();
		initDownloadCenter();
		initLauncherUpdater();
		initLauncherSettings();
	}

	// header
	private int posX = 0, posY = 0;

	private void initHeader() {
		JLabel drag = new JLabel();
		drag.setBounds(0, 0, GUISettings.w, 20);
		drag.setOpaque(false);
		drag.setBackground(new Color(0, 0, 0, 0));
		drag.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
			}
		});
		drag.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				frame.setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY);
			}
		});
		this.add(drag);
	}

	// settings button
	private void initSettingsButton() {
		JPanel sb = new JPanel();
		sb.setLayout(null);
		sb.setBounds(20, 20, 25, 25);
		sb.setOpaque(false);
		sb.setBackground(new Color(0, 0, 0, 0));

		TButton settings = new TButton();
		settings.setBounds(0, 0, 25, 25);
		settings.setOpaque(false);
		settings.setBackground(new Color(0, 0, 0, 0));
		settings.setBackgroundImage(GUISettings.options);
		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openSettingsWindow();
			}
		});
		sb.add(settings);

		this.add(sb);
	}

	// close and minimize buttonis block
	private void initCloseMinimizeButton() {
		JPanel cmb = new JPanel();
		cmb.setLayout(null);
		cmb.setBounds(GUISettings.w - 75, 20, 60, 25);
		cmb.setOpaque(false);
		cmb.setBackground(new Color(0, 0, 0, 0));

		TButton minimize = new TButton();
		minimize.setBounds(0, 0, 25, 25);
		minimize.setOpaque(false);
		minimize.setBackground(new Color(0, 0, 0, 0));
		minimize.setBackgroundImage(GUISettings.hide);
		minimize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(frame.getExtendedState() | Frame.ICONIFIED);
			}
		});
		cmb.add(minimize);

		TButton close = new TButton();
		close.setBounds(35, 0, 25, 25);
		close.setOpaque(false);
		close.setBackground(new Color(0, 0, 0, 0));
		close.setBackgroundImage(GUISettings.close);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		cmb.add(close);

		this.add(cmb);
	}

	// block 1 (nickname chooser)
	private void initTextInputFieldsAndLabels() {
		int y = GUISettings.h - 110;
		int levelw = 30;
		int widgw = 220;
		JPanel tifields = new JPanel();
		tifields.setLayout(null);
		tifields.setBounds(levelw, y, widgw, 95);
		tifields.setOpaque(false);
		tifields.setBackground(new Color(0, 0, 0, 0));

		// Плашка объясениния
		TLabel expbarset = new TLabel();
		expbarset.setBounds(0, 0, widgw, 25);
		expbarset.setBackgroundImage(Images.class.getResourceAsStream(GUISettings.explainimage));
		expbarset.setText("Основные настройки");
		expbarset.setHorizontalAlignment(SwingConstants.CENTER);
		tifields.add(expbarset);

		// Плашка ника
		int lnw = 80;
		int lnh = 20;
		TLabel labelnick = new TLabel();
		labelnick.setBounds(0, 25, lnw, lnh);
		labelnick.setBackgroundImage(Images.class.getResourceAsStream(GUISettings.labelimage));
		labelnick.setText("Ник");
		labelnick.setHorizontalAlignment(SwingConstants.CENTER);
		tifields.add(labelnick);
		// Поле ника
		int inw = 140;
		nickfield = new TTextField();
		nickfield.setBounds(lnw, 25, inw, lnh);
		nickfield.setText("NoNickName");
		nickfield.setHorizontalAlignment(SwingConstants.CENTER);
		tifields.add(nickfield);

		// Плашка пароля
		int lrw = 80;
		int lrh = 20;
		TLabel labelpass = new TLabel();
		labelpass.setBounds(0, 45, lrw, lrh);
		labelpass.setText("Пароль");
		labelpass.setHorizontalAlignment(SwingConstants.CENTER);
		labelpass.setBackgroundImage(Images.class.getResourceAsStream(GUISettings.labelimage));
		tifields.add(labelpass);
		// Поле пароля
		int irw = 140;
		passfield = new TPasswordField();
		passfield.setBounds(lrw, 45, irw, lrh);
		passfield.setText("");
		passfield.setHorizontalAlignment(SwingConstants.CENTER);
		tifields.add(passfield);

		// Кнопка сохранить
		TButton save = new TButton();
		save.setText("Сохранить настройки");
		save.setBounds(0, 65, widgw, 30);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserFieldsChoice.nick = staticgui.nickfield.getText();
				UserFieldsChoice.password = new String(staticgui.passfield.getPassword());
				UserFieldsChoice.saveBlock1Config();
			}
		});
		tifields.add(save);

		this.add(tifields);
	}

	// block 2 (clients start)
	private void initStartButton() {
		int y = GUISettings.h - 110;
		int levelw = 250;
		int widgw = 240;

		JPanel sb = new JPanel();
		sb.setLayout(null);
		sb.setBounds(levelw, y, widgw, 95);
		sb.setOpaque(false);
		sb.setBackground(new Color(0, 0, 0, 0));

		// плашка объяснений
		TLabel expbarset = new TLabel();
		expbarset.setBounds(0, 0, widgw, 25);
		expbarset.setText("Выбор клиента");
		expbarset.setHorizontalAlignment(SwingConstants.CENTER);
		expbarset.setBackgroundImage(Images.class.getResourceAsStream(GUISettings.explainimage));
		sb.add(expbarset);

		listclients = new TComboBox();
		listclients.setBounds(0, 25, widgw, 30);
		listclients.setAlignmentY(Component.CENTER_ALIGNMENT);
		listclients.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (guiinitfinished && listclients.getItemCount() > 0) {
					checkClientInternal(listclients.getSelectedItem().toString());
					UserFieldsChoice.launchclient = listclients.getSelectedItem().toString();
					UserFieldsChoice.saveBlock23Config();
				}
			}
		});
		sb.add(listclients);

		// кнопка запуска майна
		launch = new TButton();
		launch.setBounds(0, 55, widgw, 40);
		launch.setText("Запустить Minercraft");
		launch.setEnabled(false);
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
				if (System.getProperty("sun.arch.data.model").contains("64")) {
					ram = 1024;
				}
				String mem = Integer.valueOf(ram) + "M";
				// password
				String password = new String(passfield.getPassword());
				// location of jar file
				String jar = LauncherUtils.getDir() + File.separator + AllSettings.getClientJarByName(clientname);
				// mainclass
				String mainclass = AllSettings.getClientMainClassByName(clientname);
				// cmdargs
				String cmdargs = AllSettings.getClientCmdArgsByName(clientname);
				// launch minecraft (mcpach, nick, mem, jar, mainclass, cmdargs)
				ClientLaunch.launchMC(mcpath, nick, password, mem, jar, mainclass, cmdargs);
			}
		});
		sb.add(launch);

		this.add(sb);
	}

	// block 3 (clients download)
	private void initDownloadCenter() {
		int y = GUISettings.h - 110;
		int levelw = 490;
		int widgw = 220;

		JPanel dc = new JPanel();
		dc.setLayout(null);
		dc.setBounds(levelw, y, widgw, 95);
		dc.setOpaque(false);
		dc.setBackground(new Color(0, 0, 0, 0));

		TLabel expbarset = new TLabel();
		expbarset.setBounds(0, 0, widgw, 25);
		expbarset.setBackgroundImage(Images.class.getResourceAsStream(GUISettings.explainimage));
		expbarset.setText("Скачивание клиентов");
		expbarset.setHorizontalAlignment(SwingConstants.CENTER);
		dc.add(expbarset);

		listdownloads = new TComboBox();
		listdownloads.setBounds(0, 25, widgw, 30);
		listdownloads.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (guiinitfinished && listdownloads.getItemCount() > 0) {
					download.setText("Скачать клиент");
					pbar.setValue(0);
					download.setEnabled(true);
					UserFieldsChoice.downloadclient = listdownloads.getSelectedItem().toString();
					UserFieldsChoice.saveBlock23Config();
				}
			}
		});
		dc.add(listdownloads);

		pbar = new TProgressBar();
		pbar.setBounds(0, 55, widgw, 16);
		dc.add(pbar);

		download = new TButton();
		download.setBounds(0, 70, widgw, 25);
		download.setText("Скачать клиент");
		download.setEnabled(false);
		download.setHorizontalAlignment(SwingConstants.CENTER);
		download.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// lock gui
				listdownloads.setEnabled(false);
				download.setEnabled(false);
				// get client name
				String client = listdownloads.getSelectedItem().toString();
				// run client update
				new ClientUpdateThread(listdownloads, download, pbar, client).start();
			}
		});
		dc.add(download);

		this.add(dc);
	}

	// Init laucnher updater
	private void initLauncherUpdater() {
		lu = new LauncherUpdateDialog();
		new LauncherVersionChecker().start();
	}

	// Init Launcher settings
	private void initLauncherSettings() {
		ls = new LauncherSettingsDialog();
	}


	// Some methods
	// Fill clients comboboxes
	private void fillClients() {
		for (String servname : AllSettings.getClientsList()) {
			if (AllSettings.getClientRemovedStatusByName(servname)) {
				File cfile = new File(LauncherUtils.getDir() + File.separator + AllSettings.getClientJarByName(servname));
				if (!cfile.exists()) {
					continue;
				}
			}
			listclients.addItem(servname);
		}
		for (String servname : AllSettings.getClientsList()) {
			if (!AllSettings.getClientRemovedStatusByName(servname)) {
				listdownloads.addItem(servname);
			}
		}
		if (listclients.getItemCount() > 0) {
			checkClientInternal(listclients.getSelectedItem().toString());
		}
	}

	//load client prefs
	private void loadPrefs() {
		nickfield.setText(UserFieldsChoice.nick);
		passfield.setText(UserFieldsChoice.password);
		for (int i = 0; i < listclients.getItemCount(); i++) {
			String clientname = listclients.getItemAt(i);
			if (clientname.equals(UserFieldsChoice.launchclient)) {
				listclients.setSelectedIndex(i);
				break;
			}
		}
		for (int i = 0; i < listdownloads.getItemCount(); i++) {
			String clientname = listdownloads.getItemAt(i);
			if (clientname.equals(UserFieldsChoice.downloadclient)) {
				listdownloads.setSelectedIndex(i);
				break;
			}
		}
		if (listclients.getItemCount() > 0) {
			checkClientInternal(listclients.getSelectedItem().toString());
		}
	}

	// check client jar and version
	private void checkClientInternal(String client) {
		File cfile = new File(LauncherUtils.getDir() + File.separator + AllSettings.getClientJarByName(client));
		File versionfile = new File(LauncherUtils.getDir() + File.separator + AllSettings.getClientFolderByName(client) + File.separator + "clientversion");
		// first check the jar
		if (cfile.exists()) {
			if (UserLauncherSettings.updateclient) {
				// now check the version
				try {
					Scanner scan = new Scanner(versionfile);
					int currentversion = scan.nextInt();
					scan.close();
					if (currentversion < AllSettings.getClientVersionByName(client)) {
						launch.setEnabled(false);
						launch.setText("Требуется обновление");
					} else {
						launch.setEnabled(true);
						launch.setText("✔ Запустить Minecraft");
					}
				} catch (Exception e) {
					LauncherUtils.logError(e);
					launch.setEnabled(true);
					launch.setText("✘ Запустить Minecraft");
				}
			} else {
				launch.setEnabled(true);
				launch.setText("✔ Запустить Minecraft");
			}
		} else {
			launch.setText("☠ Клиент не найден");
			launch.setEnabled(false);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		try {
			Image bg = ImageIO.read(Images.class.getResourceAsStream(GUISettings.bgimage));
			bg = bg.getScaledInstance(GUISettings.w, GUISettings.h, Image.SCALE_SMOOTH);
			g.drawImage(bg, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// static access

	//check if gui is ready
	public static boolean isGUIReady() {
		return staticgui.guiinitfinished;
	}

	// check client jar an version
	public static void checkClient(String client) {
		staticgui.checkClientInternal(client);
	}

	// open launcher update window
	public static void openUpdateWindow() {
		while (!GUI.isGUIReady()) {
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		}
		staticgui.frame.getGlassPane().setVisible(true);
		staticgui.lu.open(staticgui);
	}

	// close launcher update window
	public static void closeUpdateWindow() {
		staticgui.lu.dispose();
		staticgui.frame.getGlassPane().setVisible(false);
	}

	// open settings window
	public static void openSettingsWindow() {
		while (!GUI.isGUIReady()) {
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		}
		staticgui.frame.getGlassPane().setVisible(true);
		staticgui.ls.open(staticgui);
	}

	// close settings window
	public static void closeSettingsWindow() {
		staticgui.ls.dispose();
		staticgui.frame.getGlassPane().setVisible(false);
	}

	// reinit client comboboxes
	public static void refreshClients() {
		while (!GUI.isGUIReady()) {
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		}
		staticgui.listclients.removeAllItems();
		staticgui.listdownloads.removeAllItems();
		staticgui.fillClients();
	}

}