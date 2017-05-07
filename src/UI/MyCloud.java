package UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Event.EventDef;
import Event.observeEvent;
import UI.FileTable.FileTable;
import UI.FileTable.FileTableModel;

@SuppressWarnings("serial")
public class MyCloud extends JPanel implements ActionListener, Observer {
	private JPanel j;
	private CardLayout card = new CardLayout();
	private cloud cloud;
	private UploadFile up;
	private ShareFile share;
	private String cardName[] = {"cloud", "up", "share"};
	JButton upB;
	JButton downB;
	JButton shareB;
	JButton deleteB;
	
	MyCloud(String name) {
		setLayout(new BorderLayout());
		j = new JPanel();
		j.setLayout(card);
		
		cloud = new cloud();
		up = new UploadFile(name);
		share = new ShareFile();
		j.add(cardName[0], cloud);
		j.add(cardName[1], up);
		j.add(cardName[2], share);
		
		upB.addActionListener(this);
		downB.addActionListener(this);
		shareB.addActionListener(this);
		deleteB.addActionListener(this);
		
		add(j);
	}
	
	private class cloud extends JPanel {
		cloud() {
			setLayout(new BorderLayout(10, 0));
			
			//titleP
			JPanel titleP = new JPanel(new BorderLayout(10, 10));
			JPanel buttonP = new JPanel(new GridLayout(1,4,1,0));
			upB = NomalButton("上传文件");
			downB = NomalButton("下载");
			shareB = NomalButton("分享");
			deleteB = NomalButton("删除");
			buttonP.add(upB);
			buttonP.add(downB);
			buttonP.add(shareB);
			buttonP.add(deleteB);
			
			titleP.add(buttonP, BorderLayout.EAST);
			JPanel jc = new JPanel();
			titleP.add(jc, BorderLayout.NORTH);
			JPanel jd = new JPanel();
			titleP.add(jd, BorderLayout.SOUTH);
			
			add(titleP, BorderLayout.NORTH);
			
			//listP
			JScrollPane SP = new JScrollPane();
			FileTable tbFile = new FileTable();
			SP.setViewportView(tbFile);
	        
			String list[][] = {{"测试1", "2017-4", "1002"},{"测试2", "2017-3", "1003"},{"测试3", "2017-6", "1005"},{"测试4", "2017-5", "1004"},{"测试5", "2017-4", "1000"}};
			String[] tableHeader = {"文件名", "修改日期", "大小"};
			FileTableModel model = new FileTableModel(list, tableHeader);
	        tbFile.setModel(model);
	        // 设置table 列宽
	        tbFile.getColumnModel().getColumn(0).setPreferredWidth(300);
	        tbFile.getColumnModel().getColumn(1).setPreferredWidth(150);
	        tbFile.getColumnModel().getColumn(2).setPreferredWidth(100);
			
			add(SP);
		}
	}
	
	private JButton NomalButton(String title) {
		JButton b = new JButton();
		b.setText(title);
		b.setSize(66, 29);
		b.setOpaque(true);
		b.setBorderPainted(false);
		b.setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 13));
		b.setBackground(GlobalDef.deepPurple);
		b.setForeground(GlobalDef.loginGray);
		return b;
	}
	
	public void pageToCloud() {
		card.show(j, cardName[0]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == upB) {
			card.show(j, cardName[1]);
		}
		else if (o == downB) {
			
		}
		else if (o == shareB) {
			card.show(j, cardName[2]);
		}
		else if (o == deleteB) {
			
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof observeEvent){
		    if (arg == EventDef.backToCloud) {
		    	card.show(j, cardName[0]);
		    }
		}
	}
}
