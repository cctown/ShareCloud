package UI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.FileServer;

import Event.EventDef;
import Event.observeEvent;
import UI.FileTable.FileTable;
import UI.FileTable.FileTableModel;
import UserDefault.UserInfo;

@SuppressWarnings("serial")
public class MyShare extends JPanel implements ActionListener {
	private FileTable fileTable;
	private String[] tableHeader = {"文件名", "大小", "接收者", "发送时间"};
	private JButton deleteB;
	private JButton reflashB;
	
	MyShare() {
		setLayout(new BorderLayout(10, 0));
		
		//titleP
		JPanel titleP = new JPanel(new BorderLayout(10, 10));
		
		JPanel buttonP = new JPanel(new GridLayout(1,2,1,0));
		deleteB = NomalButton("取消分享");
		deleteB.addActionListener(this);
		reflashB = NomalButton("刷新");
		reflashB.addActionListener(this);
		buttonP.add(deleteB);
		buttonP.add(reflashB);
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o == deleteB) {
			int selectedRow = fileTable.getSelectedRow();
			if(selectedRow == -1) {   //-1表示没有选中行
				JOptionPane.showMessageDialog(null, "请先选择文件", "提醒", JOptionPane.DEFAULT_OPTION);
				return;
			}
		}
		else if (o == reflashB) {
			reflashFileList();
		}
	}
	
	public void reflashFileList() {
		String id = UserInfo.getInstance().userName;
		List<Map<String, String>> fileList =  FileServer.getSharedFileForUser(id);
		String tableInfoList[][];
		if(fileList == null) {
			return;
		}
		else {
			int i = 0;
			tableInfoList = new String[fileList.size()][4];
			Map<String, String> map;
			while(i < fileList.size()) {
				map = fileList.get(i);
				tableInfoList[i][0] = map.get("fileName");
				tableInfoList[i][1] = map.get("size");
				tableInfoList[i][2] = map.get("receiver");
				tableInfoList[i][3] = map.get("date");
				i++;
			}
		}
		FileTableModel model = new FileTableModel(tableInfoList, tableHeader);
        fileTable.setModel(model);
	}
}


