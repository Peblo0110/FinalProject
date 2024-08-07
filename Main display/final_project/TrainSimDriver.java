package final_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrainSimDriver {
	private static final Pattern TRAINNUM = Pattern.compile("T[\\d]{1,2}");
	private static final Pattern TRAINSTATION = Pattern.compile("[^T][\\d][\\d]");
	private static final Pattern TRAINFB = Pattern.compile("F|B");
	private static Timer timer;
	private static Train[] trainList;
	private static BufferedReader reader;
	public static void init() {
		trainList = new Train[12];
		Process process = null;
		//https://github.com/mjza/SubwayScreen/blob/main/src/ca/ucalgary/edu/ensf380/MyApp2.java
		//lines 74-107
		/**
		 * Copyright (c) 2022-2023 Mahdi Jaberzadeh Ansari and others.
		 * 
		 * Permission is hereby granted, free of charge, to any person obtaining
		 * a copy of this software and associated documentation files (the
		 * "Software"), to deal in the Software without restriction, including
		 * without limitation the rights to use, copy, modify, merge, publish,
		 * distribute, sublicense, and/or sell copies of the Software, and to
		 * permit persons to whom the Software is furnished to do so, subject to
		 * the following conditions:
		 *	
		 *	The above copyright notice and this permission notice shall be
		 *	included in all copies or substantial portions of the Software.
		 *	
		 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
		 *	EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
		 *	MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
		 *	NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
		 *	LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
		 *	OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
		 *	WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
		 */
		 // Launch the executable jar file
        try {
        	String[] command = {"java", "-jar", "./executables/SubwaySimulator.jar", "--in", "./data/subway.csv", "--out", "./out"};
        	process = new ProcessBuilder(command).start();
            InputStream inputStream = process.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add a shutdown hook to stop the process when the application is closed
        /**final Process finalProcess = process;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (finalProcess != null) {
                finalProcess.destroy();
            }
        }));*/
        TimerTask task =  new TimerTask() {
        	@Override
        	public void run() {
        		TrainSimDriver.updateTrains();
        	}
        };
        updateTrains();
        timer = new Timer();
        timer.schedule(task, 15100, 15100);
	}
	public static void updateTrains() {
		Scanner scan = new Scanner(reader);
		if(!scan.hasNext()) {
			return;
		}
		for(int j=0;j<4;j++) {
			String line = scan.nextLine();
		Matcher numMatcher = TRAINNUM.matcher(line);
		Matcher stationMatcher = TRAINSTATION.matcher(line);
		Matcher forwardBackwardMatcher = TRAINFB.matcher(line);
		for(int i = 0; i < 4; i++) {
			if(!numMatcher.find() || !stationMatcher.find() || !forwardBackwardMatcher.find()) {
				break;
			}
			System.out.print(i);
			int trainNum = Integer.valueOf(line.substring(numMatcher.start() + 1, numMatcher.end()));
			String UID = line.substring(stationMatcher.start(), stationMatcher.end());
			boolean backward = false;
			if(line.charAt(forwardBackwardMatcher.start()) != 'F') {
				backward = true;
			}
			if(trainList[trainNum-1] == null) {
				String color = String.valueOf(UID.charAt(0));
				if(color.equals("R")) {
					color = "RED";
				}
				else if(color.equals("B")) {
					color = "BLUE";
				}
				else {
					color = "GREEN";
				}
				trainList[trainNum-1] = new Train(trainNum, color, UID, backward);
				
			}
			trainList[trainNum-1].setBackwards(backward);
			trainList[trainNum-1].setStop(UID);
		}
		}
	}

	public static Train getTrain(int trainNum) {
		return trainList[trainNum-1];
	}
}
