package final_project;

public class StationPane extends InfoPane{

	String station;
	
	public StationPane(int interval, int length, int height, String currentStation) {
		super(interval, length, height);
		this.station = currentStation;
	}
	
	@Override
	public void next() {
		
	}
}
