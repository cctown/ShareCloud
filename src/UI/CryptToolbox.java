package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Event.EventDef;
import Event.observeEvent;
import SecretCloudProxy.CommonFileManager;
import UserDefault.UserInfo;
import encryption.DES;

@SuppressWarnings("serial")

public class CryptToolbox extends JPanel implements ActionListener, Observer {
	private JButton keyB;
	private JButton fileB;
	private JButton startB;
	private JButton genKeyB;
	private JTextArea t;
	private JRadioButton encrypt;
	private JRadioButton decrypt;
	
	private Boolean op = true;
	private String keyPath;
	private String filePath;
	
	CryptToolbox() {
		configureLayout();
	}
	
	private void configureLayout() {
		setLayout(new BorderLayout(70, 50));
		JPanel centerP = new JPanel(new BorderLayout(10, 10));
		JPanel choseP = new JPanel(new BorderLayout(10, 10));
		JPanel cb = new JPanel(new GridLayout(1, 3, 5, 0));
		encrypt = new JRadioButton("加密", true);
		encrypt.addActionListener(this);
		encrypt.setFont(new java.awt.Font(GlobalDef.TableFontName, 0, 14));
		encrypt.setForeground(GlobalDef.deepPurple);
		decrypt = new JRadioButton("解密", false);
		decrypt.addActionListener(this);
		decrypt.setFont(new java.awt.Font(GlobalDef.TableFontName, 0, 14));
		decrypt.setForeground(GlobalDef.deepPurple);
		ButtonGroup BG = new ButtonGroup();
		BG.add(encrypt);
		BG.add(decrypt);
		
		JLabel label = NomalLabel("操作类型：");
		cb.add(label);
		cb.add(encrypt);
		cb.add(decrypt);
		choseP.add(cb, BorderLayout.WEST);
		
		JPanel bt = new JPanel(new GridLayout(1, 3, 15, 0));
		fileB = NomalButton("选择文件");
		fileB.addActionListener(this);
		keyB = NomalButton("选择密钥");
		keyB.addActionListener(this);
		genKeyB = NomalButton("创建新密钥");
		genKeyB.addActionListener(this);
		bt.add(fileB);
		bt.add(keyB);
		bt.add(genKeyB);
		choseP.add(bt, BorderLayout.EAST);
		centerP.add(choseP, BorderLayout.NORTH);
		
		JScrollPane sp = new JScrollPane();
		t = new JTextArea("", 10, 30);
		t.setEditable(false);
		t.setMargin(new Insets(5, 5, 100, 5));
		t.setLineWrap(true);				  //自动换行
		t.setWrapStyleWord(true);			  //断行不断字
		sp.setViewportView(t);
		centerP.add(sp);
		
		JPanel bP = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
		startB = NomalButton("开始加密");
		startB.addActionListener(this);
		bP.add(startB);
		centerP.add(bP, BorderLayout.SOUTH);
		
		add(centerP);
		JPanel P1 = new JPanel();
		add(P1, BorderLayout.WEST);
		JPanel P2 = new JPanel();
		add(P2, BorderLayout.EAST);
		JPanel P3 = new JPanel();
		add(P3, BorderLayout.SOUTH);
		JPanel P4 = new JPanel();
		add(P4, BorderLayout.NORTH);
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
	
	private JLabel NomalLabel(String title) {
		JLabel l = new JLabel();
		l.setText(title);
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setFont(new java.awt.Font(GlobalDef.TableFontName, 0, 14));
		l.setForeground(GlobalDef.deepPurple);
		return l;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == encrypt) {
			startB.setText("开始加密");
			op = true;
		}
		else if (o == decrypt) {
			startB.setText("开始解密");
			op = false;
		}
		else if (o == keyB) {
			JFileChooser jfile = new JFileChooser();
			jfile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if(jfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				keyPath = jfile.getSelectedFile().getPath();
				t.setText(t.getText() + "\n\n" + "选择的密钥为" + keyPath);
			}
		}
		else if (o == fileB) {
			JFileChooser jfile = new JFileChooser();
			jfile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if(jfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				filePath = jfile.getSelectedFile().getPath();
				t.setText(t.getText() + "\n\n" + "选择的文件为" + filePath);
			}
		}
		else if(o == genKeyB) {
			generateNewKey();
		}
		else if (o == startB) {
			startOperation();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o instanceof observeEvent) {
			if (arg == EventDef.readyToMainWindow) {
				defaultDESkeyInit();
			}
		}
	}
	
	private void defaultDESkeyInit() {
		keyPath = UserInfo.getInstance().getDESkeyPath() + UserInfo.getInstance().getDefaultDESkeyName();
		File keyfile = new File(keyPath);
		String keyTips = "如果您不修改选择的密钥，将使用默认密钥进行加解密，默认密钥所在路径为" + keyPath;
		if (!keyfile.exists()) {
			try {
				DES.generateDefaultKeyToPath(keyPath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				keyTips = "默认密钥丢失，请选择新的密钥。";
			}
		}
		t.setText(keyTips);
		String fileTips = "请选择需要加密或解密的文件。";
		t.setText(t.getText() + "\n\n" + fileTips);
	}
	
	private void generateNewKey() {
		String inputS;
		String name;
		inputS = JOptionPane.showInputDialog("请随机输入一串字符用于生成密钥");
		if(inputS == null) {
			return;
		}
		name = JOptionPane.showInputDialog("请为该密钥起个名字");
		if(name == null) {
			return;
		}
		if(name.equals("")) {
			t.setText(t.getText() + "\n\n名字不能为空，生成新密钥失败。");
			return;
		}
		String finallyPath;
		try {
			finallyPath = checkSameFileName(UserInfo.getInstance().getDESkeyPath() + name, ".dat");
			DES.generateKeyFromBytesToPath(inputS.getBytes(), finallyPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			t.setText(t.getText() + "\n\n" + "DES密钥生成失败，详细信息如下：\n\n" + e.getMessage());
			return;
		}
		t.setText(t.getText() + "\n\n" + "成功生成新的DES密钥，结果保存在" + finallyPath + "，请前去查看。\n您可以使用该密钥来对文件进行加解密。");
	}
	
	private void startOperation() {
		byte[] key;
		byte[] fileBytes;
		byte[] cipher;
		String fileName;
		String fileAffix;
		//获取指定路径的密钥
		try {
			key = CommonFileManager.getBytesFromFilepath(keyPath);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			t.setText(t.getText() + "\n\n" + "选中密钥有误，选中密钥路径为：" + keyPath + "，请检查该路径并重新选择");
			return;
		}
		//获取指定路径的明文
		try {
			fileBytes = CommonFileManager.getBytesFromFilepath(filePath);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			t.setText(t.getText() + "\n\n" + "选中文件有误，选中文件路径为：" + filePath + "，请检查该路径并重新选择");
			return;
		}
		File file = new File(filePath);
		String fullName = file.getName();
		fileName = fullName.substring(0, fullName.lastIndexOf("."));
		fileAffix = fullName.substring(fullName.lastIndexOf("."));   //获取后缀名
		String finallyPath;
		if(op) { //加密
			try {
				cipher = DES.encrypt(fileBytes, key);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				t.setText(t.getText() + "\n\n加密失败，详细信息如下：\n\n" + e1.getMessage());
				return;
			}
			try {
				finallyPath = checkSameFileName(UserInfo.getInstance().getEncryptPath() + fileName + "_加密结果", fileAffix);
				CommonFileManager.saveBytesToFilepath(cipher, finallyPath);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				t.setText(t.getText() + "\n\n保存文件失败，详细信息如下：\n\n" + e1.getMessage());
				return;
			}
			t.setText(t.getText() + "\n\n" + "加密完成，结果保存在" + finallyPath + "，请前去查看。");
		}
		else {  //解密
			try {
				cipher = DES.decrypt(fileBytes, key);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				t.setText(t.getText() + "\n\n解密失败，详细信息如下：\n\n" + e1.getMessage());
				return;
			}
			try {
				finallyPath = checkSameFileName(UserInfo.getInstance().getDecryptPath() + fileName + "_解密结果", fileAffix);
				CommonFileManager.saveBytesToFilepath(cipher, finallyPath);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				t.setText(t.getText() + "\n\n保存文件失败，详细信息如下：\n\n" + e1.getMessage());
				return;
			}
			t.setText(t.getText() + "\n\n" + "解密完成，结果保存在" + finallyPath + "，请前去查看。\n如果解密结果不正确，请检查是否使用了正确的密钥进行解密。");
		}
	}
	
	private String checkSameFileName(String fileName, String fileAffix) {
		String finallyPath = fileName + fileAffix;
		File dir = new File(finallyPath);
		int i = 0;
		while(dir.exists() && !dir.isDirectory()){    //有同名文件存在
			i++;
			finallyPath = fileName + "(" + i + ")" + fileAffix;
			dir = new File(finallyPath);
        }
		return finallyPath;
	}
}
