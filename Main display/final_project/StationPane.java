package final_project;

import java.awt.Color;

import javax.swing.JLabel;

public class StationPane extends InfoPane{

	JLabel[] stopNames;
	private Train trn;
	
	public StationPane(int interval, int length, int height, Train train) {
		super(interval, length, height);
		trn = train;
		stopNames = new JLabel[5];
		String tmpUID = trn.getStop();
		boolean tmpBackwards = trn.getBackwards();
		for(int i = 0; i < 5; i++) {
			stopNames[i] = new JLabel();
			stopNames[i].setSize(240,100);
			stopNames[i].setBounds(240*i, 0, 240, 100);
			stopNames[i].setText("WHERE IT AT");
			stopNames[i].setBackground(Color.red);
			container.add(stopNames[i]);
			stopNames[i].setVisible(true);
		}
		container.setBackground(Color.red);
		container.setVisible(true);
		this.update();
	}
	
	public void update() {
		String tmpUID = trn.getStop();
		boolean tmpBackwards = trn.getBackwards();
		for(int i = 0; i < 5; i++) {
			String line = null;
			try {
				line = RouteDatabase.getRow(tmpUID);
			} catch (BadUIDException e) {
				e.printStackTrace();
			}
			String stopName = RouteDatabase.getDataFromRow(line, "StationName");
			stopNames[i].setText(stopName);
			stopNames[i].invalidate();
			try {
				String tmpline = RouteDatabase.getNextStation(tmpUID, tmpBackwards);
				String tmpUID2 = RouteDatabase.getDataFromRow(tmpline, "StationCode");
				tmpBackwards = this.backwardsHelper(tmpUID2, tmpUID);
				tmpUID = tmpUID2;
			} catch (BadUIDException e) {
				e.printStackTrace();
			}
		}
		reload();//super function
	}
	
	private boolean backwardsHelper(String UID1, String UID2) {
		if(Integer.valueOf(UID1.substring(1)) > Integer.valueOf(UID2.substring(1))) {
			return false;
		}
		else {
			return true;
		}
	}
	
	@Override
	public void next() {
		update();
	}
}
