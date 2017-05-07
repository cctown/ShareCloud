package UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Event.EventDef;
import Event.observeEvent;

@SuppressWarnings("serial")

public class categoryP extends JPanel implements Observer {
	private detailP detail;
	private NavigationBar bar;
	
	categoryP(String userName) throws Exception {
		setLayout(new BorderLayout());
		bar = new NavigationBar();
		add(bar, BorderLayout.WEST);
		
		detail = new detailP();
		add(detail);
		
		JPanel je = new JPanel();
		add(je, BorderLayout.EAST);
	}
	
	private class detailP extends JPanel {
		String cardName[] = {"cloud", "share", "receive", "tool", "set"};
		MyCloud cloud;
		MyShare share;
		ReceiveShare receive;
		CryptToolbox tool;
		SettingP set;
		CardLayout card = new CardLayout();
		
		detailP() throws Exception {
			setLayout(card);
			cloud = new MyCloud();
			share = new MyShare();
			receive = new ReceiveShare();
			tool = new CryptToolbox();
			set = new SettingP();
			
			add(cardName[0], cloud);
			add(cardName[1], share);
			add(cardName[2], receive);
			add(cardName[3], tool);
			add(cardName[4], set);
			
			observeEvent.getInstance().addObserver(cloud);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof observeEvent){
		    if (arg == EventDef.cloudTap) {
		    	detail.card.show(detail, detail.cardName[0]);
		    	detail.cloud.pageToCloud();
		    }
		    else if (arg == EventDef.shareTap) {
		    	detail.card.show(detail, detail.cardName[1]);
		    }
		    else if (arg == EventDef.receiveTap) {
		    	detail.card.show(detail, detail.cardName[2]);
		    }
		    else if (arg == EventDef.toolTap) {
		    	detail.card.show(detail, detail.cardName[3]);
		    }
		    else if (arg == EventDef.setTap) {
		    	detail.card.show(detail, detail.cardName[4]);
		    }
		}
	}
	
	public void changeToPage(String p) {
		bar.changeToPage(p);
		if (p == GlobalDef.cloud) {
			detail.card.show(detail, detail.cardName[0]);
			detail.cloud.pageToCloud();
		}
		else if (p == GlobalDef.share) {
			detail.card.show(detail, detail.cardName[1]);
		}
		else if (p == GlobalDef.receive) {
			detail.card.show(detail, detail.cardName[2]);
		}
		else if (p == GlobalDef.tool) {
			detail.card.show(detail, detail.cardName[3]);
		}
		else if (p == GlobalDef.set) {
			detail.card.show(detail, detail.cardName[4]);
		}
	}
}