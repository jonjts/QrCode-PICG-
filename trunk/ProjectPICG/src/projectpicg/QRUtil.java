package projectpicg;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonas
 * 
 */
public class QRUtil
{
	public static int VERTICAL_FIND = 1;
	public static int HORIZONTAL_FIND = 2;
	public static int DIAGONAL_L_FIND = 3;
	public static int DIAGONAL_R_FIND = 4;

	public static List<Square> findSquares(int startX, int startY, BufferedImage bfImage)
	{
		ArrayList<Square> squares = new ArrayList<Square>();
		final Color[] modelRatio = { Color.BLACK, Color.WHITE, Color.BLACK};
		int witdh = bfImage.getWidth();
		int height = bfImage.getHeight();

		int pirceWidth = witdh - startX; // Calculo a largura que irar ser feita a busca
		int pirceHeight = height - startY; // Calculo a tamanho que irar ser feita a busca
		int maxSizeOfSquare = Math.min(pirceHeight / 2, pirceWidth / 2);

		int startHeight = 0;
		int startWidth = 0;

		for (Integer i = startWidth; i < bfImage.getWidth(); i++)
		{
			for (Integer j = startHeight; j < bfImage.getHeight(); j++)
			{
                            //verifico de ja possuo um quadrado em i, j
				if (!isOnSquare(squares, i, j))
				{
					Square square = findPatern(i, j, bfImage,maxSizeOfSquare, modelRatio);
					if (square != null)
					{
						squares.add(square);
					}
				}
			}
		}

		return squares;
	}

	/**
	 * @param squares
	 * @param i
	 * @param j
	 * @return
	 */
	private static boolean isOnSquare(ArrayList<Square> squares, Integer i,
			Integer j)
	{
		for (Square square : squares)
		{
			if (square.intersect(i, j))
				return true;
		}
		return false;
	}

	private static Square findPatern(int startX, int startY,
			BufferedImage bfImage, int maxPixelSize,
			Color[] patern)
	{
             // verifica o parceiro na esquerda
		Integer radiusH = checkPattern(bfImage, HORIZONTAL_FIND, startX, startY, patern, maxPixelSize);

		if (radiusH != null)
		{

			Integer radiusV = checkPattern(
					// checking lower pattern
					bfImage, VERTICAL_FIND, startX, startY, patern,
					maxPixelSize);

			if (radiusV != null)
			{

				Integer radiusDL = checkPattern(
						// checking diagonal LEFT pattern
						bfImage, DIAGONAL_L_FIND, startX, startY, patern,
						maxPixelSize);

				if (radiusDL != null)
				{

					Integer radiusDR = checkPattern(
							// checking diagonal RIGHT pattern
							bfImage, DIAGONAL_R_FIND, startX, startY, patern,
							maxPixelSize);

					if (radiusDR != null) {
                                        return new Square(radiusH, radiusV, radiusDL,
                                                        radiusDR, new Point(startX, startY));
                                    }
				}

			}
		}

		return null;
	}

	private static Integer checkPattern(BufferedImage bfImage, int findType,
			int origenX, int origenY, Color[] df, int maxPixelSize)
	{

		int widht = bfImage.getWidth();
		int height = bfImage.getHeight();

		int colorValue = bfImage.getRGB(origenX, origenY);

		// checking the origen color
		if (colorValue == Color.BLACK.getRGB())
		{
			if (df[0] != Color.BLACK) {
                        return null;
                    }
		} else if (colorValue == Color.WHITE.getRGB()) {
                if (df[0] != Color.WHITE)
                        return null;
            }

		List<Integer> patternSizeSide1 = new ArrayList<Integer>();
		Integer patternSize = 0;
		int paternIndx = 2;
		int pixelIndx = 0;
		for (; pixelIndx < maxPixelSize; pixelIndx++)
		{
			if (findType == HORIZONTAL_FIND && (origenX - pixelIndx > 0))
			{
				colorValue = bfImage.getRGB(origenX - pixelIndx, origenY);
			} else if (findType == VERTICAL_FIND && (origenY - pixelIndx > 0))
			{
				colorValue = bfImage.getRGB(origenX, origenY - pixelIndx);
			} else if (findType == DIAGONAL_L_FIND && (origenY - pixelIndx > 0)
					&& (origenX - pixelIndx > 0))
			{
				colorValue = bfImage.getRGB(origenX - pixelIndx, origenY
						- pixelIndx);
			} else if (findType == DIAGONAL_R_FIND
					&& ((origenY + pixelIndx < height) && (origenX - pixelIndx > 0)))
			{
				colorValue = bfImage.getRGB(origenX + pixelIndx, origenY
						- pixelIndx);
			} else if (paternIndx == 0)
			{
				int sum = MathArray.sum(patternSizeSide1, 0,
						patternSizeSide1.size() - 1);
				patternSize = pixelIndx - sum;
				patternSizeSide1.add(patternSize);
				break;
			} else
			{
				return null;
			}

			if (colorValue == Color.BLACK.getRGB()
					&& df[paternIndx] != Color.BLACK)
			{
				//paternIndx--;
				int sum = MathArray.sum(patternSizeSide1, 0,
						patternSizeSide1.size() - 1);
				patternSize = pixelIndx - sum;
				patternSizeSide1.add(patternSize);
			} else if (colorValue == Color.WHITE.getRGB()
					&& df[paternIndx] != Color.WHITE)
			{
				paternIndx--;
				int sum = MathArray.sum(patternSizeSide1, 0,
						patternSizeSide1.size() - 1);
				patternSize = pixelIndx - sum;
				patternSizeSide1.add(patternSize);
			}

			if (paternIndx < 0)
				break;
		}

		if (paternIndx > 0)
			return null;

		paternIndx = 2;
		int pixelIndx2 = 0;
		List<Integer> patternSizeSide2 = new ArrayList<Integer>();
		for (; pixelIndx2 < maxPixelSize; pixelIndx2++)
		{
			if (findType == HORIZONTAL_FIND && (origenX + pixelIndx2 < widht))
			{
				colorValue = bfImage.getRGB(origenX + pixelIndx2, origenY);
			} else if (findType == VERTICAL_FIND
					&& (origenY + pixelIndx2 < height))
			{
				colorValue = bfImage.getRGB(origenX, origenY + pixelIndx2);
			} else if (findType == DIAGONAL_L_FIND
					&& ((origenY + pixelIndx2 < height) && (origenX
							+ pixelIndx2 < widht)))
			{
				colorValue = bfImage.getRGB(origenX + pixelIndx2, origenY
						+ pixelIndx2);
			} else if (findType == DIAGONAL_R_FIND
					&& ((origenY + pixelIndx2 < height) && (origenX
							- pixelIndx2 > 0)))
			{
				colorValue = bfImage.getRGB(origenX - pixelIndx2, origenY
						+ pixelIndx2);
			} else if (paternIndx == 0)
			{
				int sum = MathArray.sum(patternSizeSide2, 0,
						patternSizeSide2.size() - 1);
				patternSize = pixelIndx2 - sum;
				patternSizeSide2.add(patternSize);
				break;
			} else
			{
				return null;
			}

			if (colorValue == Color.BLACK.getRGB()
					&& df[paternIndx] != Color.BLACK)
			{
				paternIndx--;
				int sum = MathArray.sum(patternSizeSide2, 0,
						patternSizeSide2.size() - 1);
				patternSize = pixelIndx2 - sum;
				patternSizeSide2.add(patternSize);

			} else if (colorValue == Color.WHITE.getRGB()
					&& df[paternIndx] != Color.WHITE)
			{
				paternIndx--;
				int sum = MathArray.sum(patternSizeSide2, 0,
						patternSizeSide2.size() - 1);
				patternSize = pixelIndx2 - sum;
				patternSizeSide2.add(patternSize);
			}

			if (paternIndx < 0)
				break;
		}

		if (paternIndx > 0)
			return null;

		for (Integer pSizeSide1 : patternSizeSide1) // checking the proportion
		{
			Integer pSizeSide2 = patternSizeSide2.get(patternSizeSide1
					.indexOf(pSizeSide1));
			double proportion = ((double) pSizeSide1) / pSizeSide2;
			if (!(0.6 < proportion && proportion < 1.4))
				return null;
		}

		return Math.max(pixelIndx, pixelIndx2);
	}

}
