package UI;

import java.awt.CardLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Event.EventDef;
import Event.observeEvent;
import UserDefault.UserInfo;

@SuppressWarnings("serial")

public class MainWindow extends JFrame implements Observer {
	public static String loginUserName;
	private JPanel p;
	private homeP home;
	private categoryP cp;
	private String cardName[] = {"home", "category"};
	private CardLayout card = new CardLayout();
	MainWindow(String userName) throws Exception {
		loginUserName = userName;
		setTitle("密云 - " + userName);
		setSize(GlobalDef.bigWindowWidth, GlobalDef.bigWindowHeight);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		home = new homeP(userName);
		cp = new categoryP(userName);
		p = new JPanel(card);
		p.add(cardName[0], home);
		p.add(cardName[1], cp);
		add(p);
		
		observeEvent.getInstance().addObserver(cp);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof observeEvent){
			if (arg == EventDef.homeTap) {
		    	card.show(p, cardName[0]);
		    }
		    else if (arg == EventDef.bigCloudTap) {
		    	card.show(p, cardName[1]);
		    	cp.changeToPage(GlobalDef.cloud);
		    }
		    else if (arg == EventDef.bigShareTap) {
		    	card.show(p, cardName[1]);
		    	cp.changeToPage(GlobalDef.share);
		    }
		    else if (arg == EventDef.bigReceiveTap) {
		    	card.show(p, cardName[1]);
		    	cp.changeToPage(GlobalDef.receive);
		    }
		    else if (arg == EventDef.bigToolTap) {
		    	card.show(p, cardName[1]);
		    	cp.changeToPage(GlobalDef.tool);
		    }
		    else if (arg == EventDef.bigSetTap) {
		    	card.show(p, cardName[1]);
		    	cp.changeToPage(GlobalDef.set);
		    }
			if (arg == EventDef.loginSuccess) {
				setVisible(true);
		    }
			else if (arg == EventDef.exitTap) {
		    	setVisible(false);
		    }
		}
	}
}
