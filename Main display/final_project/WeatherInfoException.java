package final_project;

public class WeatherInfoException extends Exception {
	private final int type;
	private final String message;
	public WeatherInfoException() {
		super();
		type = 0;
		message = "";
	}
	public WeatherInfoException(String msg) {
		super(msg);
		type = 0;
		message = msg;
	}
	public WeatherInfoException(int type) {
		super();
		this.type = type;
		message = messageUsingType();
	}
	
	private String messageUsingType() {
		switch (type){
		case 0:
			return "Generic Weather error.";
		case 1:
			return "Bad URL.";
		case 2:
			return "Failed to open connection.";
		case 3:
			return "Failed to open returned HTML.";
		case 4:
			return "No valid Weather information found";
		case 5:
			return "Uh oh....";
		default:
			return "Unknown error";
		}
	}
	public void printWeatherException() {
		System.out.println(message);
	}
}
