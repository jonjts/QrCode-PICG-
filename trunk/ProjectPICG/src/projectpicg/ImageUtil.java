package projectpicg;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Jonas
 * 
 */
 class ImageUtil
{

	public static final int IMAGE_JPEG = 0;
	public static final int IMAGE_PNG = 1;
	public static final int TYPE_GRAY = 3;

	/**
	 * Resizes an image.
	 * 
	 * @param image
	 *            The image to resize
	 * @param maxWidth
	 *            The image's max width
	 * @param maxHeight
	 *            The image's max height
	 * @return A resized <code>BufferedImage
	 */
	public static BufferedImage resizeImage(Image image, int type,
			int maxWidth, int maxHeight)
	{
		// Original size
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);

		float aspectRation = (float) imageWidth / imageHeight;

		if (imageWidth > maxWidth || imageHeight > maxHeight)
		{
			imageWidth = maxWidth;
			imageHeight = maxHeight;

			if (maxWidth / maxHeight > aspectRation)
			{
				imageWidth = (int) Math.ceil(maxHeight * aspectRation);
			} else
			{
				imageHeight = (int) Math.ceil(maxWidth / aspectRation);
			}
		}

		return createBufferedImage(image, type, imageWidth, imageHeight);
	}

	/**
         * Esse metodo retorna um BufferedImagem no qual converte um Image para um type
         * com as dimensões pedidas e retorna o BufferedImage em questão
	 * @return Reorna a Imagem do tipo passado como parametro
	 */
	public static BufferedImage createBufferedImage(Image image, int type,
			int w, int h)
	{
		if (type == IMAGE_PNG && hasAlpha(image))
		{
			type = BufferedImage.TYPE_INT_ARGB;
		} else
		{
			type = BufferedImage.TYPE_INT_RGB;
		}

		BufferedImage bi = new BufferedImage(w, h, type);

		Graphics2D g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(image, 0, 0, w, h, null);
		g.dispose();

		return bi;
	}

	/**
	 * Determines if the image has transparent pixels.
	 * 
	 * @param image
	 *            The image to check for transparent pixel.s
	 * @throws InterruptedException
	 */
	public static boolean hasAlpha(Image image)
	{
		try
		{
			PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
			pg.grabPixels();

			return pg.getColorModel().hasAlpha();
		} catch (InterruptedException e)
		{
			return false;
		}
	}

	/**
	 * @return
	 */
	public static double[] calcGreyHistograma(BufferedImage bfImage)
	{
		double[] histogram = new double[256];
		for (Integer i = 0; i < bfImage.getWidth(); i++)
		{
			for (Integer j = 0; j < bfImage.getHeight(); j++)
			{
				int rgbValue = bfImage.getRGB(i, j);
				int color = (rgbValue & 0xff); // all the others blue, alpha
				// must be the same
				// if the image is in grey scale
				histogram[color]++;
			}
		}

		return histogram;
	}

	/**
	 * @param histogram
	 * @return
	 */
	public static double[] calcProbabilidade(double[] histogram, int imageSize)
	{
		double[] probability = new double[256];

		for (int i = 0; i < histogram.length; i++)
		{
			probability[i] = histogram[i] / imageSize;
		}

		return probability;
	}

	/**
	 * Recebe uma imagem e a posicao da cor de corte para para
         * saber quem vai ser preto e quem vai ser branco
	 * @param bfImage
	 * @param corCorte
	 *            
	 */
	public static void applyBlackWhite(BufferedImage bfImage, double corCorte)
	{
		for (Integer i = 0; i < bfImage.getWidth(); i++)
		{
			for (Integer j = 0; j < bfImage.getHeight(); j++)
			{
				int colorValue = bfImage.getRGB(i, j);
				int greyValue = (colorValue & 0xff); // deixar o corValue cinza

				if (greyValue >= corCorte)
				{
					int rgb = ((255 & 0xff) << 24 | ((255 & 0xff) << 16)
							| ((255 & 0xff) << 8) | (255 & 0xff));
					// deixa preto
					bfImage.setRGB(i, j, rgb);
				} else
				{
					// set deixa branco
					bfImage.setRGB(i, j, 0);
				}
			}
		}
	}

	

	/**
	 * 
	 * @return BufferedImage em tom de cinza
	 */
	public static BufferedImage createBufferedImage(int colorType, Image image,
			int w, int h)
	{
		BufferedImage bi = new BufferedImage(w, h, colorType);
		Graphics g = bi.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bi;
	}

	/**
	 * Cria a imagem
	 */
	public static Image createImage(String path)
	{
		File file = new File(path);		
		
		try	{
			return ImageIO.read(file);
		} catch (IOException e)	{
			e.printStackTrace();
		}
		
		return null;
	}

}
