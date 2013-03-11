/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectpicg;

import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

/**
 *
 * @author Jonas
 */
public class NewClass{
    
    
    public static void main(String[] args){
        JFrame frame = new JFrame("oi");
        frame.getContentPane().add(new OpenImagePanel());
        frame.setVisible(true);
        
    }
}
