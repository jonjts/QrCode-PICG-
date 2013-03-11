/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectpicg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.List;

/**
 *
 * @author Jonas
 */
public class QrCode {

    public static byte[] decode(File file) throws Exception {

        Image image = ImageUtil.createImage(file.getPath());
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

            byte[] a = ((DataBufferByte) (bfImage2).getRaster().getDataBuffer()).getData();
            return a;
    }

    private static void paintDLDSquares(List<Square> squares,
            BufferedImage bfImage) {
        for (Square square : squares) {
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
