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

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;



public class Launcher {

	
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	JFrame frame = new JFrame();
     	       frame.setResizable(false);
    	       frame.setTitle(AllSettings.lname);
    	       frame.setSize(AllSettings.w, AllSettings.h);
    	       frame.setLocationRelativeTo(null);
    	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    	       try {
				frame.setIconImage(ImageIO.read(GUI.class.getResourceAsStream(AllSettings.icon)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                GUI gui = new GUI(frame);
                gui.setVisible(true);
                frame.add(gui);
                frame.setVisible(true);
            }
        });
    }

}
	



