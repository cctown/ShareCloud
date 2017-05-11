package UI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.FileServer;

import SecretCloudProxy.CommonDef;
import SecretCloudProxy.CommonFileManager;
import SecretCloudProxy.ReencryptionCipher;
import UI.FileTable.FileTable;
import UI.FileTable.FileTableModel;
import UserDefault.UserHelper;
import UserDefault.UserInfo;
import encryption.DES;
import encryption.encryptionModule;
import encryption.shareCipherTask;
import it.unisa.dia.gas.jpbc.Element;

@SuppressWarnings("serial")
public class ReceiveShare extends JPanel implements ActionListener {
	private FileTable fileTable;
	private String[] tableHeader = { "文件名", "大小", "发送者", "发送时间" };
	private JButton downB;
	private JButton deleteB;
	private JButton reflashB;

	ReceiveShare() {
		configureLayout();
	}

	private void configureLayout() {
		setLayout(new BorderLayout(10, 0));

		// titleP
		JPanel titleP = new JPanel(new BorderLayout(10, 10));

		JPanel buttonP = new JPanel(new GridLayout(1, 3, 1, 0));
		downB = NomalButton("下载");
		downB.addActionListener(this);
		deleteB = NomalButton("删除");
		deleteB.addActionListener(this);
		reflashB = NomalButton("刷新");
		reflashB.addActionListener(this);
		buttonP.add(downB);
		buttonP.add(deleteB);
		buttonP.add(reflashB);

		titleP.add(buttonP, BorderLayout.EAST);
		JPanel jc = new JPanel();
		titleP.add(jc, BorderLayout.NORTH);
		JPanel jd = new JPanel();
		titleP.add(jd, BorderLayout.SOUTH);

		add(titleP, BorderLayout.NORTH);

		// listP
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o == downB) {
			int selectedRow = fileTable.getSelectedRow();
			if (selectedRow == -1) { // -1表示没有选中行
				JOptionPane.showMessageDialog(null, "请先选择文件", "提醒", JOptionPane.DEFAULT_OPTION);
				return;
			}
			handleDownload(selectedRow);
		} else if (o == deleteB) {
			handleDelete();
		} else if (o == reflashB) {
			reflashFileList();
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

	private void handleDelete() {
		 FileServer.testDownLoad("代理重加密论文方案概述.pptx", "/Users/chencaixia/files/房子.jpg");
//		 FileServer.testDownLoad("代理重加密论文方案概述.pptx", "/Users/chencaixia/files/代理重加密论文方案概述.pptx");
	}

	private void handleDownload(int selectedRow) {
		String userName = UserInfo.getInstance().userName;
		// 检查各个配置文件，确保加密使用的密钥、公开参数等文件在
		if (!UserHelper.checkUserInfo(userName)) {
			JOptionPane.showMessageDialog(null, "用户配置文件不完整，无法完成操作", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}

		encryptionModule module;
		try {
			module = new encryptionModule();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "加密模块初始化失败，无法完成操作", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Element sk;
		try {
			byte[] skbyte = (byte[]) CommonFileManager
					.readObjectFromFile(UserInfo.getInstance().getSecretKeyPath() + CommonDef.secretKeyAffix(userName));
			sk = module.newG1ElementFromBytes(skbyte).getImmutable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "获取用户私钥失败，无法完成操作", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String author = (String) fileTable.getValueAt(selectedRow, 2);
		String fileName = (String) fileTable.getValueAt(selectedRow, 0);
		
		String cipherPath = FileServer.downloadCipher(author, fileName);
		String reCipherPath = FileServer.downloadReencryptionCipher(author, userName, fileName);
		byte[] cipher;
		byte[] desKey;
		try {
			// 原文件的des密文
			cipher = CommonFileManager.getBytesFromFilepath(cipherPath);
			// des密钥的重加密密文
			ReencryptionCipher reencryptionCipher = (ReencryptionCipher) CommonFileManager.readObjectFromFile(reCipherPath);
			// 解密得到des密钥
			desKey = shareCipherTask.decryptShareMsg(module, reencryptionCipher, sk);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "下载失败，无法完成操作", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		byte[] fileBytes;
		try {
			// 用des密钥解密得到原文
			fileBytes = DES.decrypt(cipher, desKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "解密原文失败", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			String finallyPath = UserInfo.getInstance().getDownloadPath() + fileName;
			// 保存原文
			CommonFileManager.saveBytesToFilepath(fileBytes, finallyPath);
			JOptionPane.showMessageDialog(null, "下载完成，结果保存在" + finallyPath, "提醒", JOptionPane.DEFAULT_OPTION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "保存文件失败", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void reflashFileList() {
		String id = UserInfo.getInstance().userName;
		List<Map<String, String>> fileList = FileServer.getReceiveFile(id);
		String tableInfoList[][];
		if (fileList == null) {
			return;
		} else {
			int i = 0;
			tableInfoList = new String[fileList.size()][4];
			Map<String, String> map;
			while (i < fileList.size()) {
				map = fileList.get(i);
				tableInfoList[i][0] = map.get("fileName");
				tableInfoList[i][1] = map.get("size");
				tableInfoList[i][2] = map.get("author");
				tableInfoList[i][3] = map.get("date");
				i++;
			}
		}
		FileTableModel model = new FileTableModel(tableInfoList, tableHeader);
		fileTable.setModel(model);
	}
}
