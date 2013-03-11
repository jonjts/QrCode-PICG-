package projectpicg;

import java.awt.Point;

/**
 * 
 */

/**
 * @author Jonas
 *  Essa class representa o quadrado que ajuda a indetificar a imagem do qrCode
 */
public class Square
{
	private Point center;
	private int radiusH;
	private int radiusV;
	private int radiusDL;
	private int radiusDR;
	
	/**
	 * 
	 */
	public Square(int radiusH, int radiusV, int radiusDL, int radiusDR, Point center)
	{
		this.center = center;
		this.radiusH = radiusH;
		this.radiusV = radiusV;
		this.radiusDL = radiusDL;
	}
	
	public Point getCenter()
	{
		return center;
	}
	
	public void setCenter(Point center)
	{
		this.center = center;
	}
	
	public int getRadiusH()
	{
		return radiusH;
	}
	
	public void setRadiusH(int radius)
	{
		this.radiusH = radius;
	}
	
	public int getRadiusV()
	{
		return radiusV;
	}
	
	public void setRadiusV(int radius)
	{
		this.radiusV = radius;
	}
	
	public int getRadiusDL()
	{
		return radiusDL;
	}
	
	public void setRadiusDL(int radius)
	{
		this.radiusDL = radius;
	}
	
	public int getRadiusDR()
	{
		return radiusDR;
	}
	
	public void setRadiusDR(int radius)
	{
		this.radiusDR = radius;
	}

	/**
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean intersect(Integer i, Integer j)
	{
		return (center.x-radiusH<=i && i<=radiusH+center.x) &&
				(center.y-radiusV<=j && j<=radiusV+center.y);
	}
}
