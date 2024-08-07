package final_project;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
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
	private final JLabel currentAd;
	private final JLabel map;
	public MapPane(int interval, int length, int height) {
		super(interval, length, height);
		MAX_ADS = adsDir.listFiles().length;
		currentAd = new JLabel();
		currentAd.setSize(length, height);
		currentAd.setBounds(0,0,length,height);
		currentAd.setVisible(false);
		container.add(currentAd);
		map = new JLabel();
		map.setSize(length, height);
		map.setIcon(new ImageIcon("./data/Trains.png"));//TODO
		map.setVisible(false);
		MapHelperClass.init(length, height);
		MapHelperClass.populateButtons();
		/*for(JLabel button : MapHelperClass.buttons) {
			button.setVisible(false);
			container.add(button);
		}*/
		container.add(MapHelperClass.pane);
	}
	
	//Assumes all ads are uniquely named
	private void displayAd(int adnum) {
		File[] fileList = adsDir.listFiles();
		File adToDisplay = fileList[adnum];
		//can't do pdfs
		if(adToDisplay.getPath().matches("pdf$")) {
			return;
		}
		try {
			BufferedImage ad = ImageIO.read(adToDisplay);
			currentAd.setIcon(new ImageIcon(ad));;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentAd.setVisible(true);
		reload();
	}
	public void displayMap() {
		MapHelperClass.populateButtons();
		MapHelperClass.pane.setVisible(true);
		reload();
		/*
		MapHelperClass.populateButtons();
		for(JLabel but : MapHelperClass.buttons) {
			but.setVisible(true);
		}
		BufferedImage map = null;
		try {
			map = ImageIO.read(new File("./data/Trains.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentAd.setIcon(new ImageIcon(map));
		currentAd.setVisible(true);
		container.setComponentZOrder(currentAd, 0);*/
	}
	
	@Override
	public void next() {
		if(count == 2) {
			currentAd.setVisible(false);
			count = 0;
			adNumber++;
			if(adNumber > MAX_ADS-1) {
				adNumber = 0;
			}
			displayMap();
		}
		else if(count == 0) {
			map.setVisible(false);
			for(JLabel but : MapHelperClass.buttons) {
				but.setVisible(false);
			}
			this.displayAd(adNumber);
			count++;
		}
		else {
			count++;
		}
	}
}
