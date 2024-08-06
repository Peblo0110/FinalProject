package final_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class WeatherPane extends InfoPane{
	
	public WeatherPane(int interval, int length, int height) {
		super(interval, length, height);
		weatherSetup("Calgary", "CA");
	}
	public WeatherPane(int interval, int length, int height, String city, String location) {
		super(interval, length, height);
		// TODO Auto-generated constructor stub
		weatherSetup(city, location);
	}
	
	private void weatherSetup(String city, String Location) throws InterruptedException {
		//TODO Below is loosely based on MyApp1.java in https://github.com/mjza/SubwayScreen
		//Execute external JAR
		Process process = null;
		InputStream output = null;
		String weather;
		try {
			String[] weatherJARInput = {""};
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
			try {
				weather = reader.readLine();
			}
			catch (Exception E) {
				throw new Exception(); //TODO
			}
			//Check to see if weather was found
			if(weather == null) {
				throw new Exception(); //TODO
			}
			//Do Regex Stuff TODO
			//Cleanup
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			throw new Exception();//TODO
		}
	}
	@Override
	public void next() {
		//do nothing
	}
}
