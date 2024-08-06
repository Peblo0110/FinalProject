/**
 * THIS CODE ORIGINATES FROM STACK OVERFLOW:
 * https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
 * Frankly this code is much better than what I could've written by myself.
 * No point in reinventing the wheel...
 * This code does not handle PDFs... in fact on 3rd party libraries allow such functionality.
 */

package final_project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel(String path) {
       try {                
          image = ImageIO.read(new File(path));
       } catch (IOException ex) {
            // handle exception...
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
    }
}