package final_project;
import java.util.Timer;
import javax.swing.*;

public class InfoPane {
	private int interval;
	private int length;
	private int height;
	private Timer timer;
	private JPanel container;
	
	public InfoPane(int interval, int length, int height) {
		this.interval = interval;
		this.height = height;
		this.length = length;
	}
	public void display() {
		this.container.setVisible(true);
	};
	public void reload() {
		this.container.setVisible(false);
		this.container.setVisible(true);
	};
	public void next() {
		//TODO override me!
	}
	public void quit() {
		//TODO
	};
	public int getInterval() {
		return interval;
	};
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public JPanel getContainer() {
		return this.container;
	}
}
