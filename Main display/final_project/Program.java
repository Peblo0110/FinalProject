package final_project;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class Program {
	public static JFrame mainWindow = new JFrame();
	public static void main(String[] args) {
		//Set up
		mainWindow.setLayout(null);
		mainWindow.setSize(1500, 800);
		Dimension dimen = new Dimension();
		dimen.height = mainWindow.getHeight();
		dimen.width = mainWindow.getWidth();
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
		Container c = mainWindow.getContentPane();
		station = new StationPane(15,dimen.width,200, TrainSimDriver.getTrain(1));
		WeatherPane weather = new WeatherPane(3600,200,dimen.height-200,city,country);
		MapPane map = new MapPane(5,dimen.width-200,dimen.height-200);
		station.getContainer().setBounds(0, dimen.height-200, station.length, station.height);
		weather.getContainer().setBounds(dimen.width-200, 0,weather.length, weather.height);
		map.getContainer().setBounds(0,0,dimen.width-200,dimen.height-200);
		c.add(station.getContainer());
		c.add(weather.getContainer());
		c.add(map.getContainer());
		station.reload();
		weather.reload();
		map.reload();
		//Start timers
		timerHelper(station);
		timerHelper(weather);
		timerHelper(map);
	/*
		mainWindow.setVisible(true);
		//testing
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.exit(0);
			}
		}, 000);*/
		
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
