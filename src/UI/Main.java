package UI;

import Event.observeEvent;
import encryption.keyGen;

public class Main {
	static LoginWindow loginWindow;
	static MainWindow mainW;
	
	public static void main (String args[]) throws Exception {
		loginWindow = new LoginWindow();
		mainW = new MainWindow();
		observeEvent.getInstance().addObserver(mainW);
		observeEvent.getInstance().addObserver(loginWindow);
	}
}
