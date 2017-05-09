package UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.FileServer;

import Event.EventDef;
import Event.observeEvent;
import UI.FileTable.FileTable;
import UI.FileTable.FileTableModel;
import UserDefault.UserInfo;

@SuppressWarnings("serial")
public class MyCloud extends JPanel implements ActionListener, Observer {
	private JPanel j;
	private CardLayout card = new CardLayout();
	private cloud cloud;
	private UploadFile up;
	private ShareFile share;
	private String cardName[] = {"cloud", "up", "share"};
	private FileTable fileTable;
	private String[] tableHeader = {"文件名", "修改时间", "大小"};
	private JButton upB;
	private JButton downB;
	private JButton shareB;
	private JButton deleteB;
	
	MyCloud() {
		configureLayout();
	}
	
	private void configureLayout() {
		setLayout(new BorderLayout());
		j = new JPanel();
		j.setLayout(card);
		
		cloud = new cloud();
		up = new UploadFile();
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
			fileTable = new FileTable();
			SP.setViewportView(fileTable);
	        
			FileTableModel model = new FileTableModel(null, tableHeader);
	        fileTable.setModel(model);
	        // 设置table 列宽
	        fileTable.getColumnModel().getColumn(0).setPreferredWidth(300);
	        fileTable.getColumnModel().getColumn(1).setPreferredWidth(150);
	        fileTable.getColumnModel().getColumn(2).setPreferredWidth(100);
	        ListSelectionModel selectionModel = fileTable.getSelectionModel();
	        selectionModel.addListSelectionListener(new ListSelectionListener() {
	        	public void valueChanged(ListSelectionEvent e) {
		            //事件处理代码
		          }
	        });
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
			handleDownload();
		}
		else if (o == shareB) {
			handleShare();
		}
		else if (o == deleteB) {
			handleDelete();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof observeEvent){
		    if (arg == EventDef.backToCloud) {
		    	card.show(j, cardName[0]);
		    } else if((arg == EventDef.getUserFiles)) {
		    	reflashFileList();
		    }
		}
	}
	
	private void handleDownload() {
		int selectedRow = fileTable.getSelectedRow();
		if(selectedRow == -1) {   //-1表示没有选中行
			JOptionPane.showMessageDialog(null, "请先选择文件", "提醒", JOptionPane.DEFAULT_OPTION);
			return;
		}
	}
	
	private void handleShare() {
		int selectedRow = fileTable.getSelectedRow();
		if(selectedRow == -1) {   //-1表示没有选中行
			JOptionPane.showMessageDialog(null, "请先选择文件", "提醒", JOptionPane.DEFAULT_OPTION);
			return;
		}
		String fileName = (String) fileTable.getValueAt(selectedRow, 0);
		share.setFile(fileName);
		card.show(j, cardName[2]);
	}
	
	private void handleDelete() {
		int selectedRow = fileTable.getSelectedRow();
		if(selectedRow == -1) {   //-1表示没有选中行
			JOptionPane.showMessageDialog(null, "请先选择文件", "提醒", JOptionPane.DEFAULT_OPTION);
			return;
		}
	}
	
	private void reflashFileList() {
		String id = UserInfo.getInstance().userName;
		List<Map<String, String>> fileList =  FileServer.getFileInfoForUser(id);
		String tableInfoList[][];
		if(fileList == null) {
			return;
		}
		else {
			int i = 0;
			tableInfoList = new String[fileList.size()][3];
			Map<String, String> map;
			while(i < fileList.size()) {
				map = fileList.get(i);
				tableInfoList[i][0] = map.get("fileName");
				tableInfoList[i][1] = map.get("date");
				tableInfoList[i][2] = map.get("size");
				i++;
			}
		}
		FileTableModel model = new FileTableModel(tableInfoList, tableHeader);
        fileTable.setModel(model);
	}
}
