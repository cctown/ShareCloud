package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import Event.EventDef;
import Event.observeEvent;

@SuppressWarnings("serial")

public class homeP extends JPanel implements ActionListener {
	private JPanel banner;
	private JPanel iconPanel;
	private JButton exitButton;
	private NomalPanel cloud;
	private NomalPanel share;
	private NomalPanel receive;
	private NomalPanel tool;
	private NomalPanel set;
	
	public homeP() {
		setLayout(new BorderLayout(100, 10));
		JPanel jw = new JPanel();
		add(jw, BorderLayout.WEST);
		banner = bannerP();
        add(banner, BorderLayout.NORTH);
        
		JPanel je = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		add(je, BorderLayout.EAST);
		
		JPanel jc = new JPanel();
        jc.setLayout(new BorderLayout(5, 50));
        
        JPanel jn = new JPanel();
        jc.add(jn, BorderLayout.NORTH);
        
        iconPanel =  iconPanel();
		jc.add(iconPanel);
		
		JPanel js = new JPanel();
        jc.add(js, BorderLayout.SOUTH);
		
        add(jc);
        
		exitButton.addActionListener(this);
	}
	
	private JPanel bannerP() {
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout(20, 50));
		
		JPanel jc = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		JLabel iconLabel = new JLabel();
		iconLabel.setIcon(new ImageIcon("images/待定.png"));
		iconLabel.setSize(542, 105);
		jc.add(iconLabel);
		jp.add(jc, BorderLayout.SOUTH);
		
		JPanel je = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		exitButton = new JButton("退出帐号");
		exitButton.setSelected(false);
		exitButton.setOpaque(true);
		exitButton.setBorderPainted(false);
		exitButton.setBackground(GlobalDef.loginGray);
		exitButton.setSize(86, 29);
		exitButton.setForeground(GlobalDef.deepPurple);
		exitButton.setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 12));
		je.add(exitButton);
		
		jp.add(je, BorderLayout.EAST);
		return jp;
	}
	
	private JPanel iconPanel() {
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(1, 5, 10, 15));
		cloud = new NomalPanel(GlobalDef.cloud, GlobalDef.cloudImage_0);
		share = new NomalPanel(GlobalDef.share, GlobalDef.shareImage_0);
		receive = new NomalPanel(GlobalDef.receive, GlobalDef.receiveImage_0);
		tool = new NomalPanel(GlobalDef.tool, GlobalDef.toolImage_0);
		set = new NomalPanel(GlobalDef.set, GlobalDef.setImage_0);
		
		jp.add(cloud);
		jp.add(share);
		jp.add(receive);
		jp.add(tool);
		jp.add(set);
		
		return jp;
	}
	
	private class NomalPanel extends JPanel implements MouseListener {
		NomalPanel (String title, String image) {
			addMouseListener(this);
			setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
			JLabel icon = new JLabel();
			icon.setIcon(new ImageIcon(image));
			add(icon);
			JLabel label = NomalLabel(title);
			add(label);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			NomalPanel o = (NomalPanel)e.getSource();
			if (o == cloud) {
				observeEvent.getInstance().setEventTag(EventDef.bigCloudTap);
			}
			else if (o == share) {
				observeEvent.getInstance().setEventTag(EventDef.bigShareTap);
			}
			else if (o == receive) {
				observeEvent.getInstance().setEventTag(EventDef.bigReceiveTap);
			}
			else if (o == tool) {
				observeEvent.getInstance().setEventTag(EventDef.bigToolTap);
			}
			else if (o == set) {
				observeEvent.getInstance().setEventTag(EventDef.bigSetTap);
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
	
	private JLabel NomalLabel(String title) {
		JLabel label = new JLabel();
		label.setText(title);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 14));
		label.setForeground(GlobalDef.deepPurple);
		return label;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == exitButton) {
			observeEvent.getInstance().setEventTag(EventDef.exitTap);
		}
	}
}
