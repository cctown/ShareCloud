package UI;

import Event.observeEvent;

public class Main {
	static LoginWindow loginWindow;
	static MainWindow mainW;
	public static void main (String args[]) throws Exception {
		loginWindow = new LoginWindow();
		loginWindow.setVisible(true);
		observeEvent.getInstance().addObserver(loginWindow);
		
		mainW = new MainWindow();
		mainW.setVisible(false);
		observeEvent.getInstance().addObserver(mainW);
	}
}
