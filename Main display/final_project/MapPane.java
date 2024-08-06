package final_project;

public class MapPane extends InfoPane{
	boolean map = false;
	int mode = 0; //TODO
	public MapPane(int interval, int length, int height) {
		super(interval, length, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void next() {
		if(!map) {
			map = true;
			//TODO show map
		}
		else {
			map = false;
			//TODO ads
		}
	}
}
