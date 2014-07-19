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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import truelauncher.gcomponents.TButton;
import truelauncher.userprefs.settings.UserLauncherSettings;

@SuppressWarnings("serial")
public class LauncherSettingsDialog extends JDialog {

	int w = 400; int h = 100;

	public LauncherSettingsDialog() {
		super();
		this.setUndecorated(true);
		this.setLayout(null);
	}

	public void open(GUI parent) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setSize(w, h);
		setLocationRelativeTo(parent);
		initUI();
		setVisible(true);
	}

	private void initUI() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, w, h);
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.GRAY));
		this.add(panel);

		JPanel lupanel = new JPanel();
		lupanel.setLayout(null);
		lupanel.setOpaque(false);
		lupanel.setBounds(5, 5, 380, 20);
		final JCheckBox lucbox = new JCheckBox();
		lucbox.setOpaque(false);
		lucbox.setBounds(0, 0, 20, 20);
		lucbox.setSelected(UserLauncherSettings.updatelauncher);
		lupanel.add(lucbox);
		JLabel lulabel = new JLabel();
		lulabel.setOpaque(false);
		lulabel.setBounds(25, 0, 350, 20);
		lulabel.setText("Проверять обновления лаунчера");
		lupanel.add(lulabel);
		panel.add(lupanel);

		JPanel cupanel = new JPanel();
		cupanel.setLayout(null);
		cupanel.setOpaque(false);
		cupanel.setBounds(5, 25, 380, 20);
		final JCheckBox cucbox = new JCheckBox();
		cucbox.setOpaque(false);
		cucbox.setBounds(0, 0, 20, 20);
		cucbox.setSelected(UserLauncherSettings.updateclient);
		cupanel.add(cucbox);
		JLabel culabel = new JLabel();
		culabel.setOpaque(false);
		culabel.setBounds(25, 0, 350, 20);
		culabel.setText("Проверять обновления клиентов");
		cupanel.add(culabel);
		panel.add(cupanel);

		JPanel lepanel = new JPanel();
		lepanel.setLayout(null);
		lepanel.setOpaque(false);
		lepanel.setBounds(5, 45, 380, 20);
		final JCheckBox lecbox = new JCheckBox();
		lecbox.setOpaque(false);
		lecbox.setBounds(0, 0, 20, 20);
		lecbox.setSelected(UserLauncherSettings.doerrlog);
		lepanel.add(lecbox);
		JLabel lelabel = new JLabel();
		lelabel.setOpaque(false);
		lelabel.setBounds(25, 0, 350, 20);
		lelabel.setText("Вести лог ошибок");
		lepanel.add(lelabel);
		panel.add(lepanel);

		TButton save = new TButton();
		save.setOpaque(false);
		save.setBounds(2, 70, 198, 29);
		save.setText("Сохранить");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserLauncherSettings.updatelauncher = lucbox.isSelected();
				UserLauncherSettings.updateclient = cucbox.isSelected();
				UserLauncherSettings.doerrlog = lecbox.isSelected();
				UserLauncherSettings.saveConfig();
			}
		});
		panel.add(save);
		TButton close = new TButton();
		close.setOpaque(false);
		close.setBounds(200, 70, 198, 28);
		close.setText("Закрыть");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI.closeSettingsWindow();
			}
		});
		panel.add(close);
	}

}
