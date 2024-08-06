package final_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class RouteDatabase {
	private static String[] redLine;
	private static String[] blueLine;
	private static String[] greenLine;
	
	public static void loadData() {
		LinkedList<String> red = new LinkedList<String>();
		LinkedList<String> blue = new LinkedList<String>();
		LinkedList<String> green = new LinkedList<String>();
		try {
			File stations = new File("./data/subway.csv");
			Scanner fileRead = new Scanner(stations);
			while(fileRead.hasNextLine()) {
				String line = fileRead.nextLine();
				char color = line.split(",")[1].toCharArray()[0];
				switch (color){
				case 'R':
					red.add(line);
					break;
				case 'B':
					blue.add(line);
					break;
				case 'G':
					green.add(line);
				default:
					break;
				}
			}
			fileRead.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		redLine = new String[red.size()];
		for(int i = 0; i < red.size(); i++) {
			redLine[i] = red.get(i);
		}
		blueLine = new String[blue.size()];
		for(int i = 0; i < blue.size(); i++) {
			blueLine[i] = blue.get(i);
		}
		greenLine = new String[green.size()];
		for(int i = 0; i < green.size(); i++) {
			greenLine[i] = green.get(i);
		}
	}
	public static String getNextStation(String UID, boolean backwards) throws BadUIDException {
		String ret = null;
		String[] line = null;
		switch (UID.charAt(0)){
		case 'R':
			line = redLine;
			break;
		case 'B':
			line = blueLine;
			break;
		case 'G':
			line = greenLine;
			break;
		default:
			throw new BadUIDException("Not a valid color.");
		}
		if(!backwards) {
			for(int i = 0; i < line.length; i++) {
				String[] data = line[i].split(",");
				if(data[3].equals(UID)) {
					if(i + 1 < line.length) {
						ret = line[i+1];
					}
					else {
						ret = line[i-1];
					}
				}
			}
		}
		else {
			for(int i = line.length-1; i > -1; i--) {
				String[] data = line[i].split(",");
				if(data[3].equals(UID)) {
					if(i - 1 > -1) {
						ret = line[i-1];
					}
					else {
						ret = line[i+1];
					}
				}
			}
		}
		if(ret == null) {
			throw new BadUIDException("UID not found.");
		}
		return ret;
	}
	public static String getRow(String UID) throws BadUIDException {
		String[] line = null;
		switch (UID.charAt(0)){
		case 'R':
			line = redLine;
			break;
		case 'B':
			line = blueLine;
			break;
		case 'G':
			line = greenLine;
			break;
		default:
			throw new BadUIDException("Not a valid color.");
		}
		for(int i = 0; i < line.length; i++) {
			if(line[i].split(",")[3].equals(UID)) {
				return line[i];
			}
		}
		throw new BadUIDException("Not a valid UID");
	}
	public static String getDataFromRow(String row, String data) {
		String[] splitRow = row.split(",");
		switch(data) {
		case "Row":
			return splitRow[0];
		case "Line":
			return splitRow[1];
		case "StationNumber":
			return splitRow[2];
		case "StationCode":
			return splitRow[3];
		case "StationName":
			return splitRow[4];
		case "X":
			return splitRow[5];
		case "Y":
			return splitRow[6];
		case "Common Stations":
			String ret = "";
			for(int i = 7; i < splitRow.length;i++) {
				ret += splitRow[i];
				ret += " ";
			}
			return ret;
		default:
			return "bad";
		}
	}
}
