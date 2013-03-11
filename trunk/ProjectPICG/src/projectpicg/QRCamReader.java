package projectpicg;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;


/**
 * @author emarx
 *
 */
public class QRCamReader extends Applet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5142832336977416129L;

	public QRCamReader()
	{
		setLayout(new BorderLayout(0,0));
		setSize(new Dimension (400, 400));
		add(new OpenImagePanel());
	}
}
