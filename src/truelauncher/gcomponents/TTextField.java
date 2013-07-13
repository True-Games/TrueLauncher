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

package truelauncher.gcomponents;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TTextField extends JTextField {
	
	
	public TTextField()
	{
		
	}
	
	private boolean renderImage = false;
	private Image bgimage;
	
	public void setBackgroundImage(InputStream is,int w,int h)
	{
		try {
			bgimage = ImageIO.read(is);
			bgimage = bgimage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			renderImage = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (renderImage)
		{
			g.drawImage(bgimage, 0, 0, null);
		}
		super.paintComponent(g);
	}
	
}
