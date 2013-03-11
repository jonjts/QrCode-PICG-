package projectpicg;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Jonas
 *
 */
public class OpenImagePanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8246582376658451317L;

	DrawImagePanel originalImage;
	DrawImagePanel resultImage;
	JTextField fileField;

	/**
	 * 
	 */
	public OpenImagePanel()
	{
		init();
	}

	private void init()
	{
		GridBagLayout gb = new GridBagLayout();
		setLayout(gb);
		GridBagConstraints constr = new GridBagConstraints();
		constr.insets = new Insets(5, 5, 5, 5);
		constr.gridx = 0;
		constr.gridy = 0;
		constr.weightx = 100;
		constr.gridwidth = 3;
		constr.fill = GridBagConstraints.HORIZONTAL;
		add(getPathPanel(), constr);
		constr.gridx -= 2;
		constr.gridy++;
		constr.weightx = 100;
		constr.weighty = 100;
		constr.gridwidth = 1;
		constr.fill = GridBagConstraints.BOTH;
		originalImage = new DrawImagePanel();
		add(originalImage, constr);
		constr.gridx++;
		resultImage = new DrawImagePanel();
		add(resultImage, constr);
	}

	private JPanel getPathPanel()
	{
		JPanel panel = new JPanel();

		GridBagLayout gb = new GridBagLayout();
		panel.setLayout(gb);

		GridBagConstraints constr = new GridBagConstraints();
		constr.insets = new Insets(5, 5, 0, 0);
		constr.gridx = 0;
		constr.gridy = 0;
		constr.weightx = 100;
		constr.gridwidth = 2;
		constr.fill = GridBagConstraints.HORIZONTAL;
		fileField = new JTextField(100);

		panel.add(fileField, constr);
		constr.gridwidth = 1;
		constr.gridx++;
		constr.gridx++;
		constr.weightx = 0;
		constr.fill = GridBagConstraints.NONE;
		JButton openButton = new JButton(new OpenAction());
		panel.add(openButton, constr);

		return panel;
	}

	private class OpenAction extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8981675480493690983L;

		/**
		 * 
		 */
		public OpenAction()
		{
			super("abrir");
		}

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(null);

			File fileReaded = fileChooser.getSelectedFile();

			if (fileReaded != null)
			{
				
				fileField.setText(fileReaded.getAbsolutePath());
				
				Image image = ImageUtil.createImage(fileReaded.getPath());

				originalImage.setImage(image);

				int height = image.getHeight(null);
				int width = image.getWidth(null);

				//converte a imagem para tom de cinza
				BufferedImage bfImage = ImageUtil.createBufferedImage(
						BufferedImage.TYPE_BYTE_GRAY, image, width, height);

				// aplica o algoritimo de Otsu
				Otsu.apply(bfImage);

				//pegar os quadrados
				List<Square> squares = QRUtil.findSquares(0, 0, bfImage);

				BufferedImage bfImage2 = ImageUtil.createBufferedImage(
						BufferedImage.TYPE_INT_RGB, bfImage, width, height);

				paintDLDSquares(squares, bfImage2);

                                
                                
				resultImage.setImage(bfImage2);

			}
		}

		private void paintDLDSquares(List<Square> squares,
				BufferedImage bfImage)
		{
			for (Square square : squares)
			{
				Point center = square.getCenter();
				int size = square.getRadiusH() / 3;
				int x = center.x;
				int y = center.y;
				bfImage.setRGB(x, y, Color.red.getRGB());
				Graphics g = bfImage.getGraphics();
				g.setColor(Color.RED);
				g.fillRect(x, y, size, size);
				g.dispose();
				
			}
		}

	}
}
