package projectpicg;

import java.awt.image.BufferedImage;

/*
 * @author Jonas
 */
public class Otsu
{
	/**
	 * Aplica o algoritimo de OTsu
	 * @param bfImage Buffer da imagem que vai sofrer os efeitos de Otsu
	 * @return a imagem binarizada
	 */
	public static BufferedImage apply(BufferedImage bfImage)
	{
		double[] colorHistograma = ImageUtil.calcGreyHistograma(bfImage);
		double[] colorProbabilidade = ImageUtil.calcProbabilidade(colorHistograma,
				bfImage.getHeight() * bfImage.getWidth());

		double corCorte = 0;
		double maxVarianca = 0;

		//percorre todas as cores de 0 a 255
                //para achar a posição onde á a maxima varianca
		for (int i = 0; i <= 255; i++)
		{
			//mede a probabilidade da cor na regiao 1
			double w1 = MathArray.sum(colorProbabilidade, 0, i);
			double w2 = MathArray.sum(colorProbabilidade, i + 1, 255);

			// mede a probabilidade da cor na região 2
			double u1 = MathArray.getMedia(colorHistograma, 0, i);
			double u2 = MathArray.getMedia(colorHistograma, i + 1, 255);

			double varianca = w1 * w2 * Math.pow(u1 - u2, 2);

			if (maxVarianca < varianca)
			{
				maxVarianca = varianca;
                                //seta a posicao da maior varianca encontrada
                                //ate o momento
				corCorte = i;
			}
		}
                //binariza a imagem
		ImageUtil.applyBlackWhite(bfImage, corCorte);

		return bfImage;
	}

}
