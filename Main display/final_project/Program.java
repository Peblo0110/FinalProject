package final_project;

import javax.swing.*;

public class Program {
	static JWindow mainWindow = new JWindow();
	public static void main(String[] args) {
		//Set up
		mainWindow.setSize(1200, 800);
		StationPane station = new StationPane(12,1200,400, "ABCD");//TODO
		WeatherPane weather = new WeatherPane(86400,400,800); //TODO
		MapPane map = new MapPane(10,800,800);//TODO
		mainWindow.add(station.getContainer());
		mainWindow.add(weather.getContainer());
		mainWindow.add(map.getContainer());
		mainWindow.setVisible(true);
		
		
		//Close window
		mainWindow.dispose();
	}

}
