package final_project;

public class Train {
	private final String Color;
	private String stopUID;
	private boolean backwards;
	private final int trainNumber;
	
	public Train(int trainNum, String lineclr, String UID, boolean backwards) {
		trainNumber = trainNum;
		Color = lineclr;
		stopUID = UID;
		this.backwards = backwards;
	}
	
	public void updateStop() {
		try {
			String line = RouteDatabase.getNextStation(stopUID, backwards);
			String newUID = RouteDatabase.getDataFromRow(line, "StationCode");
			if(!newUID.equals("")) {
				if(Integer.valueOf(stopUID.substring(1)) > Integer.valueOf(newUID.substring(1))) {
					backwards = true;
				}
				else {
					backwards = false;
				}
				stopUID = newUID;
			}
		} catch (BadUIDException e) {
			e.printStackTrace();
		}
		
	}
	public void setStop(String UID) {
		stopUID = UID;
	}
	public String getStop() {
		return stopUID;
	}
	public boolean getBackwards() {
		return backwards;
	}
	public void setBackwards(boolean boo) {
		backwards = boo;
	}
}
