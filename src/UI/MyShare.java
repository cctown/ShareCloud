package UI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import UI.FileTable.FileTable;
import UI.FileTable.FileTableModel;

@SuppressWarnings("serial")
public class MyShare extends JPanel{
	MyShare() {
		setLayout(new BorderLayout(10, 0));
		
		//titleP
		JPanel titleP = new JPanel(new BorderLayout(10, 10));
		
		JPanel buttonP = new JPanel(new GridLayout(1,2,1,0));
		JButton deleteB = NomalButton("取消分享");
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
        
		String list[][] = {{"测试5", "1000", "小修", "2017-5"}, {"测试2", "1000", "小修", "2013-4"}, {"测试3", "1000", "花花", "2017-4"}, {"测试1", "2048", "小修", "2017-4"}, {"测试4", "1000", "小修", "2017-4"}};
		String[] tableHeader = {"文件名", "大小", "接收者", "发送日期"};
		FileTableModel model = new FileTableModel(list, tableHeader);
        tbFile.setModel(model);
        // 设置table 列宽
        tbFile.getColumnModel().getColumn(0).setPreferredWidth(300);
        tbFile.getColumnModel().getColumn(1).setPreferredWidth(150);
        tbFile.getColumnModel().getColumn(2).setPreferredWidth(100);
		
		add(SP);
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
}


