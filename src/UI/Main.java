package UI;

import Event.observeEvent;

public class Main {
	static LoginWindow loginWindow;
	
	public static void main (String args[]) throws Exception {
		loginWindow = new LoginWindow();
		observeEvent.getInstance().addObserver(loginWindow);
	}
}
