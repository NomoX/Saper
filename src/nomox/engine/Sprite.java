package nomox.engine;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	private BufferedImage image;
	
	public Sprite() {
		this.image = null;
	}
	public Sprite(BufferedImage image) {
		this.image = image;
	}
	
	public Sprite fromResources(String path) {
		//image = Toolkit.getDefaultToolkit().getImage(getClass().getResource(path));
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	public Sprite getRect(Rectangle rect) {
		// дістаєм частину зображення за коордтнатами
		return new Sprite(image.getSubimage(rect.x, rect.y, rect.width, rect.height)); // ret. new sprite
	}
	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}
	
	public void draw(int x,int y) {
		Engine.g.drawImage(image,x,y,null);
	}
	protected Image getImage() {
		return image;
	}
}
