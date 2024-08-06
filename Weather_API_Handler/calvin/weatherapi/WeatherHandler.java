package calvin.weatherapi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WeatherHandler {
	//TODO extract Patterns and move to here
	private static final Pattern DELIM = Pattern.compile("\\Z");
	public static void main(String[] args) {
		String city = "Calgary";
		String country = null;
		URLConnection url = null;
		String report = null;
		if(args.length == 2) {
			city = args[0];
			country = "," + args[1];
		}
		else if(args.length == 1) {
			city = args[0];
		}
		
		try {
			if(country == null) {
				url = new URL(String.format("https://wttr.in/%s",city)).openConnection();
			}else {
				url = new URL(String.format("https://wttr.in/%s%s",city, country)).openConnection();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(1); //TODO 1: Bad URL, should never hit
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2); //TODO 2: Failed to open connection
		}
		if(url != null) {
			Scanner readReport;
			try {
				readReport = new Scanner(url.getInputStream());
				readReport.useDelimiter(DELIM);
				report = readReport.next();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(3);//TODO 3: Failed to open returned HTML(?)
			}
		}
		//Parse report for stuff we want, then print it
		if(report != null) {
			String formattedOutput = "";
			
			//crop to everything after second "Weather report"
			report = report.substring(report.indexOf("Weather report: ", 50));
			//Replace tags, everything that isn't a word, \n, whitespace, +, - or ()
			report = report.replaceAll("[\\<][^\\<]*[\\>]", "");
			report = report.replaceAll("[^\\w\\n\\s\\(\\)+-]", "");
			//skip two lines
			report = report.substring(report.indexOf("\n") + 1);
			report = report.substring(report.indexOf("\n") + 1);
			//grep for sky conditions, then add
			Matcher matcher = Pattern.compile("(([ ][\\w]+){1,7})[\\n]").matcher(report);
			if(matcher.find()) {
				formattedOutput += "SC: "; //Sky conditions
				formattedOutput += report.substring(matcher.start(), matcher.end()).trim();
				formattedOutput += "\t";
			}
			//grep for temperature
			//TODO minor bug, if the temperature is close to 0, feels like temperature may have different sign
			//compared to actual temperature
			matcher = Pattern.compile("[\\+|-][\\d]+\\([\\d]+\\) [C|F]").matcher(report);
			if(matcher.find()) {
				formattedOutput += "TP: "; //Temperature
				formattedOutput += report.substring(matcher.start(), matcher.end()).trim();
				formattedOutput += "\t";
			}
			//grep for windspeed
			matcher = Pattern.compile("[\\d+] kmh").matcher(report);
			if(matcher.find()) {
				formattedOutput += "WS: "; //Wind speed
				formattedOutput += report.substring(matcher.start(), matcher.end()).trim();
				formattedOutput += "\t";
			}
			//grep for percipitation
			matcher = Pattern.compile("[\\d+] (mm|cm)").matcher(report);
			if(matcher.find()) {
				formattedOutput += "P: "; //Percipitation
				formattedOutput += report.substring(matcher.start(), matcher.end()).trim();
				formattedOutput += "\t";
			}
			if(formattedOutput.equals("")) {
				System.exit(4); //TODO 4: no valid regex matches -> website update?
			}
			System.out.println(formattedOutput);
		}
		else {
			System.exit(5);//TODO 5: nothing in report, should not happen.
		}
	}

}
