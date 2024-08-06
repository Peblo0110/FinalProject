package final_project;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class Program {
	static JWindow mainWindow = new JWindow();
	public static void main(String[] args) {
		//Set up
		mainWindow.setLayout(null);
		mainWindow.setSize(1200, 800);
		StationPane station;
		String city = "Vancouver";
		String country = "CA";
		for(int i = 0; i < args.length; i++) {
			if(args[i].toLowerCase().equals("--city")) {
				if(i+1 < args.length) {
					city = args[i+1];
					i++;
				}
				else {
					System.out.println("No city provided");
				}
			}
			else if(args[i].toLowerCase().equals("--country")) {
				if(i+1 < args.length) {
					country = args[i+1];
					i++;
				}
				else {
					System.out.println("No country provided");
				}
			}
		}
		TrainSimDriver.init();
		RouteDatabase.loadData();
		
		station = new StationPane(15,1200,400, TrainSimDriver.getTrain(1));
		WeatherPane weather = new WeatherPane(3600,400,800,city,country);
		MapPane map = new MapPane(5,800,800);
		mainWindow.add(station.getContainer());
		mainWindow.add(weather.getContainer());
		mainWindow.add(map.getContainer());
		station.getContainer().setBounds(0, 700, 1200, 100);
		weather.getContainer().setBounds(800,0,400,800);
		map.getContainer().setBounds(0,0,800,800);
		station.reload();
		weather.reload();
		map.reload();
		//Start timers
		timerHelper(station);
		timerHelper(weather);
		timerHelper(map);
		
		mainWindow.setVisible(true);
		//testing
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				mainWindow.repaint();
			}
		}, 500);
	}
	private static void timerHelper(InfoPane pane) {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				pane.next();
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, pane.getInterval()*1000, pane.getInterval()*1000);
	}

}
