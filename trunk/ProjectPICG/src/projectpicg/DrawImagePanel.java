package projectpicg;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * @author Jonas
 */ 
public class DrawImagePanel extends JComponent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6577653551692801854L;
	
	private Image image;
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		
		
		Graphics graphics = g.create();
		if(image!=null)
		{	
			BufferedImage bf = ImageUtil.resizeImage(image, 
					BufferedImage.TYPE_INT_RGB,
					getWidth(),
					getHeight());
			graphics.drawImage( bf, 0, 0, this);
			graphics.dispose();
		}
		else
		{
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, getWidth(), getHeight());
			graphics.dispose();
		}
		
	}

	/**
	 * This function get the Image that is is being drawed. 
	 * @return the image that is being drawed.
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 *  Set the BufferedImage that will be drawed. 
	 */
	public void setImage(Image image)
	{
		this.image = image;
		repaint();		
	}

}
