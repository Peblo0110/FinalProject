package final_project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MapPane extends InfoPane{
	private int count = 0; //TODO
	private int adNumber = 0;
	private final int MAX_ADS;
	private final File adsDir = new File("./ads");
	private JLabel currentAd = null;
	public MapPane(int interval, int length, int height) {
		super(interval, length, height);
		MAX_ADS = adsDir.listFiles().length;
	}
	
	//Assumes all ads are uniquely named
	private void displayAd(int adnum) {
		File[] fileList = adsDir.listFiles();
		File adToDisplay = fileList[adnum];
		if(adToDisplay == null) {
			System.out.print("can't find image");
			return;
		}
		//can't do pdfs
		if(adToDisplay.getPath().matches("pdf$")) {
			return;
		}
		if(currentAd == null) {
		try {
			BufferedImage ad = ImageIO.read(adToDisplay);
			currentAd = new JLabel(new ImageIcon(ad));
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		else {
			try {
				BufferedImage ad = ImageIO.read(adToDisplay);
				currentAd.imageUpdate(ad, 0, 0, 0, 800, 800);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		currentAd.setBounds(0,0,length,height);
		currentAd.setVisible(true);
		container.add(currentAd);
		reload();
	}
	
	@Override
	public void next() {
		if(count == 2) {
			currentAd.setVisible(false);
			//TODO show map
			count = 0;
			adNumber++;
			if(adNumber > MAX_ADS) {
				adNumber = 0;
			}
		}
		else if(count == 0) {
			this.displayAd(adNumber);
			count++;
		}
	}
}
