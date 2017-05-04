package UI;

import java.awt.CardLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Event.EventDef;
import Event.observeEvent;

@SuppressWarnings("serial")

public class MainWindow extends JFrame implements Observer {
	private JPanel p;
	private homeP home = new homeP();
	private categoryP cp = new categoryP();
	private String cardName[] = {"home", "category"};
	private CardLayout card = new CardLayout();
	MainWindow() {
		setTitle("密云-开发中");
		setSize(GlobalDef.bigWindowWidth, GlobalDef.bigWindowHeight);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
