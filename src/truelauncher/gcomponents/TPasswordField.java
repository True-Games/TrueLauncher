package truelauncher.gcomponents;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class TPasswordField extends JPasswordField {

	private boolean renderImage = false;
	private Image bgimage;

	public void setBackgroundImage(InputStream is) {
		try {
			bgimage = ImageIO.read(is);
			bgimage = bgimage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
			renderImage = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		if (renderImage) {
			g.drawImage(bgimage, 0, 0, null);
		}
		super.paintComponent(g);
	}

}
