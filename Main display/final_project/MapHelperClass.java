package final_project;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class MapHelperClass {
	private static final double XMAX = 1200;
	private static final double YMAX = 700;//hardcoded
	private static int length;
	private static int height;
	public static JLabel[] buttons;
	public static JLayeredPane pane;
	public static JLabel mapLabel;
	public static void init(int l, int h) {
		length = l;
		height = h;
		buttons = new JLabel[12];
		//TODO
		pane = new JLayeredPane();
		pane.setSize(l, h);
		pane.setBounds(0,0,Program.mainWindow.getWidth()-200, Program.mainWindow.getHeight()-200);
		for(int i = 0; i < 12; i++) {
			buttons[i] = new JLabel();
			buttons[i].setBackground(Color.yellow);
			buttons[i].setOpaque(true);
			pane.add(buttons[i], i);
		}
		mapLabel = new JLabel();
		mapLabel.setSize(pane.getWidth(), pane.getHeight());
		mapLabel.setBounds(pane.getBounds());
		BufferedImage map = null;
		try {
			map = ImageIO.read(new File("./data/Trains.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapLabel.setIcon(new ImageIcon(map));
		pane.add(mapLabel, 13);
	}
	
	public static void populateButtons() {
		for(int i = 0; i < 12; i++) {
			//find where train is
			String UID = TrainSimDriver.getTrain(i+1).getStop();
			//Query DB for row
			String row = null;
			try {
				row = RouteDatabase.getRow(UID);
			} catch (BadUIDException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//find points
			double x = Double.valueOf(RouteDatabase.getDataFromRow(row, "X"));
			double y = Double.valueOf(RouteDatabase.getDataFromRow(row, "Y"));
			//Compute adjust factor, then map to an int point
			double xfactor = length/XMAX;
			double yfactor = height/YMAX;
			int centerX = (int) (xfactor * x);
			int centerY = (int) (yfactor * y);
			//Update JLabel
			buttons[i].setSize(20,20);
			buttons[i].setBounds(centerX-10, centerY-10, 10, 10);
			buttons[i].setVisible(false);
			buttons[i].setVisible(true);
		}
	}
}
