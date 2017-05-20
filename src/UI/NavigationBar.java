package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import Event.EventDef;
import Event.observeEvent;

@SuppressWarnings("serial")

public class NavigationBar extends JPanel {
	public String currentP;
	
	private NomalPanel home;
	private NomalPanel cloud;
	private NomalPanel share;
	private NomalPanel receive;
	private NomalPanel tool;
//	private NomalPanel set;
	
	NavigationBar () {
		setLayout(new BorderLayout(10, 0));
		setBackground(GlobalDef.loginGray);
		
		gJpanel top = new topJP();
		gJpanel bt = new btJP();
		gJpanel je = new gJpanel();
		gJpanel jw = new gJpanel();
		
		add(top, BorderLayout.NORTH);
		add(bt, BorderLayout.EAST);
		add(je);
		add(jw, BorderLayout.WEST);
	}
	
	private class topJP extends gJpanel {
		topJP () {
			setLayout(new BorderLayout(5, 3));
			
			home = new NomalPanel("主页", "images/小主页.png");
			add(home);
			
			gJpanel je = new gJpanel();
			gJpanel jw = new gJpanel();
			gJpanel js = new gJpanel();
			gJpanel jn = new gJpanel();
			JLabel icon = new JLabel();
			icon.setIcon(new ImageIcon(GlobalDef.lineImage));
			js.add(icon);
			
			add(js, BorderLayout.SOUTH);
			add(jw, BorderLayout.WEST);
			add(jn, BorderLayout.NORTH);
			add(je, BorderLayout.EAST);
		}
	}
	
	private class btJP extends gJpanel {
		btJP () {
			setLayout(new GridLayout(13, 1, 15, 0));
			
			cloud = new NomalPanel(GlobalDef.cloud, GlobalDef.cloudImage_1);
			share = new NomalPanel(GlobalDef.share, GlobalDef.shareImage_1);
			receive = new NomalPanel(GlobalDef.receive, GlobalDef.receiveImage_1);
			tool = new NomalPanel(GlobalDef.tool, GlobalDef.toolImage_1);
//			set = new NomalPanel(GlobalDef.set, GlobalDef.setImage_1);
			
			add(cloud);
			add(share);
			add(receive);
			add(tool);
//			add(set);
		}
	}
	
	private class NomalPanel extends gJpanel implements MouseListener {
		String tag;
		JLabel label;
		JLabel icon;
		
		NomalPanel (String title, String image) {
			setLayout(new FlowLayout(FlowLayout.LEADING, 10, 5));
			addMouseListener(this);
			tag = title;
//			setBackground(Color.blue);
			gJpanel jp = new gJpanel();
			jp.setLayout(new FlowLayout(FlowLayout.CENTER, 7, 0));
			icon = new JLabel();
			icon.setIcon(new ImageIcon(image));
			jp.add(icon);
			label = new NomalLabel(title);
			jp.add(label);
			
			add(jp);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			NomalPanel o = (NomalPanel)e.getSource();
			if (o.tag == currentP) {
				return;
			}
			if (o == home) {
				observeEvent.getInstance().setEventTag(EventDef.homeTap);
			}
			else {
				changeToPage(o.tag);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {	
		}
	}
	
	private class NomalLabel extends JLabel{
		NomalLabel (String title) {
			setText(title);
			setHorizontalAlignment(SwingConstants.CENTER);
			setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 14));
			setForeground(GlobalDef.deepPurple);
		}
	}
	
	private class gJpanel extends JPanel {
		gJpanel() {
			setBackground(GlobalDef.loginGray);
		}
	}
	
	public void changeToPage(String p) {
		if (p == currentP) {
			return;
		}
		else if (p == GlobalDef.cloud) {
			cloud.label.setForeground(GlobalDef.selecetdGray);
			cloud.icon.setIcon(new ImageIcon(GlobalDef.cloudImage_2));
			observeEvent.getInstance().setEventTag(EventDef.cloudTap);
		}
		else if (p == GlobalDef.share) {
			share.label.setForeground(GlobalDef.selecetdGray);
			share.icon.setIcon(new ImageIcon(GlobalDef.shareImage_2));
			observeEvent.getInstance().setEventTag(EventDef.shareTap);
		}
		else if (p == GlobalDef.receive) {
			receive.label.setForeground(GlobalDef.selecetdGray);
			receive.icon.setIcon(new ImageIcon(GlobalDef.receiveImage_2));
			observeEvent.getInstance().setEventTag(EventDef.receiveTap);
		}
		else if (p == GlobalDef.tool) {
			tool.label.setForeground(GlobalDef.selecetdGray);
			tool.icon.setIcon(new ImageIcon(GlobalDef.toolImage_2));
			observeEvent.getInstance().setEventTag(EventDef.toolTap);
		}
//		else if (p == GlobalDef.set) {
//			set.label.setForeground(GlobalDef.selecetdGray);
//			set.icon.setIcon(new ImageIcon(GlobalDef.setImage_2));
//			observeEvent.getInstance().setEventTag(EventDef.setTap);
//		}
		else {
			System.out.println("目标页错误，请检查参数");
			return;
		}
		resetCurrentPColor(currentP);
		currentP = p;
	}
	
	private void resetCurrentPColor(String t) {
		if (t == GlobalDef.cloud) {
			cloud.label.setForeground(GlobalDef.deepPurple);
			cloud.icon.setIcon(new ImageIcon(GlobalDef.cloudImage_1));
		}
		else if (t == GlobalDef.share) {
			share.label.setForeground(GlobalDef.deepPurple);
			share.icon.setIcon(new ImageIcon(GlobalDef.shareImage_1));
		}
		else if (t == GlobalDef.receive) {
			receive.label.setForeground(GlobalDef.deepPurple);
			receive.icon.setIcon(new ImageIcon(GlobalDef.receiveImage_1));
		}
		else if (t == GlobalDef.tool) {
			tool.label.setForeground(GlobalDef.deepPurple);
			tool.icon.setIcon(new ImageIcon(GlobalDef.toolImage_1));
		}
//		else if (t == GlobalDef.set) {
//			set.label.setForeground(GlobalDef.deepPurple);
//			set.icon.setIcon(new ImageIcon(GlobalDef.setImage_1));
//		}
	}
}
