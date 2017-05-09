package UI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import UI.FileTable.FileTable;
import UI.FileTable.FileTableModel;

@SuppressWarnings("serial")
public class ReceiveShare extends JPanel{
	FileTable fileTable;
	String[] tableHeader = {"文件名", "大小", "发送者", "发送时间"};
	
	ReceiveShare() {
		setLayout(new BorderLayout(10, 0));
		
		//titleP
		JPanel titleP = new JPanel(new BorderLayout(10, 10));
		
		JPanel buttonP = new JPanel(new GridLayout(1,2,1,0));
		JButton downB = NomalButton("下载");
		JButton deleteB = NomalButton("删除");
		buttonP.add(downB);
		buttonP.add(deleteB);
		
		titleP.add(buttonP, BorderLayout.EAST);
		JPanel jc = new JPanel();
		titleP.add(jc, BorderLayout.NORTH);
		JPanel jd = new JPanel();
		titleP.add(jd, BorderLayout.SOUTH);
		
		add(titleP, BorderLayout.NORTH);
		
		//listP
		JScrollPane SP = new JScrollPane();
		fileTable = new FileTable();
		SP.setViewportView(fileTable);
        
		FileTableModel model = new FileTableModel(null, tableHeader);
		fileTable.setModel(model);
        // 设置table 列宽
		fileTable.getColumnModel().getColumn(0).setPreferredWidth(300);
		fileTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		fileTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		
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

