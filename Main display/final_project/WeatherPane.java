package final_project;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;

import javax.swing.JLabel;

public class WeatherPane extends InfoPane{
	private final String CITY;
	private final String COUNTRY;
	private String TEMP;
	private String PERCIPITATION;
	private String WINDSPEED;
	private String SKY_CONDITIONS;
	private String FEELS_LIKE;
	private String UNIT;
	private String RAIN_UNIT;
	private JLabel info;
	
	public WeatherPane(int interval, int length, int height) {
		super(interval, length, height);
		try {
			weatherSetup("Calgary", "CA");
		} catch (InterruptedException | IOException | WeatherInfoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		info = new JLabel();
		info.setSize(length, height);
		loadInfo();
		CITY = "Calgary";
		COUNTRY = "CA";
		container.setBackground(Color.blue);
	}
	public WeatherPane(int interval, int length, int height, String city, String location) {
		super(interval, length, height);
		try {
			weatherSetup(city, location);
		} catch (InterruptedException | IOException | WeatherInfoException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		info = new JLabel();
		info.setSize(length, height/2);
		info.setBounds(length/4, height/2, length/2, height/2);
		loadInfo();
		CITY = city;
		COUNTRY = location;
		container.add(info);
		info.setVisible(true);
	}
	
	private void weatherSetup(String city, String location) throws InterruptedException, IOException, WeatherInfoException {
		//TODO Below is loosely based on MyApp1.java in https://github.com/mjza/SubwayScreen
		//Execute external JAR
		Process process = null;
		InputStream output = null;
		String weather;
		try {
			String[] weatherJARInput = {"java", "-jar", "./executables/WeatherHandler.jar", city, location};
			process = new ProcessBuilder(weatherJARInput).start();
			output = process.getInputStream();
		}
		catch(Exception E){
			E.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(output));
		final Process finalProcess = process;
		if(finalProcess != null) {
			finalProcess.waitFor();
		}
		//Read output from JAR
		//if successful
		if(finalProcess.exitValue() == 0) {
			Matcher matcher;
			weather = reader.readLine();
			//Check to see if weather was found
			//Do Regex Stuff TODO
			String[] weatherArray = weather.split("\t");
			for(int i = 0; i < weatherArray.length; i++) {
				weather = weatherArray[i];
				switch(weather.substring(0, 3)) {
				case "SC:":
					SKY_CONDITIONS = weather.substring(4);
					break;
				case "TP:":
					if(weather.indexOf("+") != -1) {
						TEMP = weather.substring(weather.indexOf("+"), weather.indexOf("("));
						FEELS_LIKE = "+" + weather.substring(weather.indexOf("(") + 1, weather.indexOf(")"));
					}
					else {
						TEMP = weather.substring(weather.indexOf("-"), weather.indexOf("("));
						FEELS_LIKE = "-" + weather.substring(weather.indexOf("(") + 1, weather.indexOf(")"));
					}
					if(weather.contains("C")) {
						UNIT = "C";
					}
					else {
						UNIT = "F";
					}
					break;
				case "WS:":
					WINDSPEED = weather.substring(4, weather.indexOf("k"));
					break;
				case "P: ":
					//if measurement is cm
					if(weather.contains("c")) {
						PERCIPITATION = weather.substring(3, weather.indexOf("m")-1);
						RAIN_UNIT = "cm";
					}
					else {
						PERCIPITATION = weather.substring(3, weather.indexOf("m"));
						RAIN_UNIT = "mm";
					}
					break;
				default:
						break;
				}
				
			}
			//Cleanup
			output.close();
		}
		else {
			System.out.println(finalProcess.exitValue());
			throw new WeatherInfoException(finalProcess.exitValue());//TODO
		}
	}
	private void loadInfo() {
		//Lazy
		String labelText = "<html><center>" + SKY_CONDITIONS + "<br>" + TEMP + UNIT + "<br>Feels like" + FEELS_LIKE + UNIT + "<br>" 
				+ WINDSPEED + "km/h<br>" + PERCIPITATION + " " + RAIN_UNIT;
		info.setText(labelText);
	}
	@Override
	public void next() {
		try {
			weatherSetup(CITY, COUNTRY);
		} catch (InterruptedException | IOException | WeatherInfoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadInfo();
		reload();
	}
}
