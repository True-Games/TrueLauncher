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
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import truelauncher.config.AllSettings;
import truelauncher.gcomponents.TButton;
import truelauncher.gcomponents.TLabel;
import truelauncher.images.Images;

@SuppressWarnings("serial")
public class LauncherUpdateDialog  extends JDialog {

	private String labelimage = "labelbar.png";

	int w = 250; int h = 90;
	public TButton updateNowButton;
	public JProgressBar updateProgressBar;
	public TLabel updateStatusLabel;
	public TButton updateLaterButton;
	public JPanel panel;

	private LauncherUpdateDialog thisclass = this;

	public LauncherUpdateDialog() {
		super();
		setUndecorated(true);
		setLayout(null);
	}

	public void open(GUI parent) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setSize(w,h);
		setLocationRelativeTo(parent);
		initUI();
		setVisible(true);
	}


	private void initUI() {
		panel = new JPanel();
		panel.setBounds(0, 0, w, h);
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.GRAY));
		this.add(panel);

		updateStatusLabel = new TLabel();
		updateStatusLabel.setBounds(2,2,w-4,24);
		updateStatusLabel.setBackgroundImage(Images.class.getResourceAsStream(labelimage));
		updateStatusLabel.setText("Доступно обновление лаунчера");
		updateStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(updateStatusLabel);

		updateProgressBar = new JProgressBar();
		updateProgressBar.setBounds(2,26,w-4, 14);
		panel.add(updateProgressBar);


		updateNowButton = new TButton();
		updateNowButton.setText("Обновить");
		updateNowButton.setBounds(2, 40, w-4, 25);
		updateNowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatusLabel.setText("Скачиваем обновление");
				updateNowButton.setEnabled(false);
				updateLaterButton.setEnabled(false);
				new LauncherUpdateThread(thisclass, AllSettings.getLauncherWebUpdateURLFolder()+"Launcher.jar").start();
			}
		});
		panel.add(updateNowButton);



		updateLaterButton = new TButton();
		updateLaterButton.setText("Позже");
		updateLaterButton.setBounds(2,65,w-4,23);
		updateLaterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI.closeUpdateWindow();
			}
		});
		panel.add(updateLaterButton);

	}

}
