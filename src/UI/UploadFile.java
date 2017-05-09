package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.FileServer;

import Event.EventDef;
import Event.observeEvent;
import SecretCloudProxy.Ciphertext;
import SecretCloudProxy.CommonDef;
import SecretCloudProxy.PublicKey;
import SecretCloudProxy.ShareCipher;
import UserDefault.UserHelper;
import UserDefault.UserInfo;
import encryption.CommonFileManager;
import encryption.DES;
import encryption.encryptTask;
import encryption.encryptionModule;
import encryption.shareCipherTask;
import it.unisa.dia.gas.jpbc.Element;

@SuppressWarnings("serial")
public class UploadFile extends JPanel implements ActionListener {
	JButton backB;
	JButton choseFileB;
	JButton choseKeyB;
	JButton startB;
	JTextArea t;
	String filePath;
	String keyPath;

	UploadFile() {
		configureLayout();
		keyPath = UserInfo.getInstance().DESkeyPath + UserInfo.getInstance().defaultDESkeyName;
		File keyfile = new File(keyPath);
		if (!keyfile.exists()) {
			try {
				DES.generateDefaultKeyToPath(keyPath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				t.setText("");
			}
		}
		t.setText("上传之前系统会使用密钥对你的文件进行加密。如果您不修改选择的密钥，将使用默认密钥进行加解密，默认密钥所在路径为" + keyPath + "\n\n" + "请选择需要上传的文件。");
	}

	private void configureLayout() {
		setLayout(new BorderLayout(50, 30));
		JPanel jn = new JPanel(new BorderLayout(50, 10));

		JPanel titleP = new JPanel(new BorderLayout(5, 0));
		JPanel jb = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		backB = NomalButton("返回", false);
		backB.addActionListener(this);
		jb.add(backB);
		titleP.add(jb, BorderLayout.NORTH);

		JLabel title = new JLabel();
		title.setText("上传文件");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 24));
		title.setForeground(GlobalDef.deepPurple);
		titleP.add(title);

		JPanel jc = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
		choseFileB = NomalButton("选择文件", true);
		choseFileB.addActionListener(this);
		jc.add(choseFileB);
		choseKeyB = NomalButton("选择密钥", true);
		choseKeyB.addActionListener(this);
		jc.add(choseKeyB);
		titleP.add(jc, BorderLayout.SOUTH);
		jn.add(titleP, BorderLayout.NORTH);

		JScrollPane sp = new JScrollPane();
		t = new JTextArea();
		
		t.setEditable(false);
		t.setMargin(new Insets(5, 5, 100, 5));
		t.setLineWrap(true); // 自动换行
		t.setWrapStyleWord(true); // 断行不断字
		sp.setViewportView(t);
		jn.add(sp);

		JPanel bP = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
		startB = NomalButton("开始上传", true);
		startB.addActionListener(this);
		bP.add(startB);
		jn.add(bP, BorderLayout.SOUTH);

		add(jn);

		JPanel j = new JPanel();
		add(j, BorderLayout.NORTH);
		JPanel js = new JPanel();
		add(js, BorderLayout.SOUTH);
		JPanel jw = new JPanel();
		add(jw, BorderLayout.WEST);
		JPanel je = new JPanel();
		add(je, BorderLayout.EAST);
	}

	private JButton NomalButton(String title, Boolean deep) {
		JButton b = new JButton();
		b.setText(title);
		b.setSize(66, 29);
		b.setOpaque(true);
		b.setBorderPainted(false);
		b.setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 13));
		if (deep == true) {
			b.setBackground(GlobalDef.deepPurple);
			b.setForeground(GlobalDef.loginGray);
		} else {
			b.setBackground(GlobalDef.loginGray);
			b.setForeground(GlobalDef.deepPurple);
		}

		return b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == backB) {
			observeEvent.getInstance().setEventTag(EventDef.backToCloud);
		} else if (o == choseFileB) {
			JFileChooser jfile = new JFileChooser();
			jfile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (jfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				filePath = jfile.getSelectedFile().getPath();
				t.setText(t.getText() + "\n\n" + "选择的文件为" + filePath);
			}
		} else if (o == choseKeyB) {
			JFileChooser jfile = new JFileChooser();
			jfile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (jfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				keyPath = jfile.getSelectedFile().getPath();
				t.setText(t.getText() + "\n\n" + "选择的密钥为" + keyPath);
			}
		} else if (o == startB) {
			startOperation(UserInfo.getInstance().userName);
		}
	}

	private void startOperation(String id) {
		// 检查各个配置文件，确保加密使用的密钥、公开参数等文件在
		if (!UserHelper.checkUserInfo(id)) {
			t.setText(t.getText() + "\n\n" + "用户配置文件不完整，无法加密文件上传");
			return;
		}
		String fileName;
		byte[] keyBytes;
		byte[] fileBytes;
		byte[] cipher;
		// 获取指定路径的密钥
		try {
			keyBytes = CommonFileManager.getBytesFromFilepath(keyPath);
			t.setText(t.getText() + "\n\n" + "成功读取密钥");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			t.setText(t.getText() + "\n\n" + "选中密钥有误，选中密钥路径为：" + keyPath + "，请检查该路径并重新选择");
			return;
		}
		// 获取指定路径的明文
		try {
			fileBytes = CommonFileManager.getBytesFromFilepath(filePath);
			File file = new File(filePath);
			fileName = file.getName();
			t.setText(t.getText() + "\n" + "成功读取源文件");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			t.setText(t.getText() + "\n\n" + "选中文件有误，选中文件路径为：" + filePath + "请检查该路径并重新选择");
			return;
		}

		// 加密DES密钥，用于代理重加密分享
		encryptionModule module;
		try {
			module = new encryptionModule();
			t.setText(t.getText() + "\n" + "成功初始化加密模块");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			t.setText(t.getText() + "\n\n" + "加密模块初始化失败，无法加密文件上传。");
			return;
		}
		PublicKey pk;
		try {
			pk = (PublicKey) CommonFileManager.readObjectFromFile(UserInfo.getInstance().keyPath + CommonDef.publicKeyAffix(id));
			t.setText(t.getText() + "\n" + "成功获取用户公钥");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			t.setText(t.getText() + "\n\n" + "公钥文件丢失，无法加密文件上传。");
			return;
		}
		
		// 加密源文件
		t.setText(t.getText() + "\n" + "开始加密源文件");
		try {
			cipher = DES.encrypt(fileBytes, keyBytes);
			t.setText(t.getText() + "\n" + "成功加密源文件");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			t.setText(t.getText() + "\n\n" + "加密源文件失败，详细信息如下：" + e.getMessage());
			return;
		}
		
		Element condition = module.newGTRandomElement().getImmutable();
		t.setText(t.getText() + "\n" + "开始加密密钥");
		ShareCipher DEScipher = shareCipherTask.encryptShareMsg(module, keyBytes, pk, condition);
		t.setText(t.getText() + "\n" + "成功加密密钥");
		// 加密条件值t
		t.setText(t.getText() + "\n" + "开始加密访问条件值");
		Ciphertext tCipher = encryptTask.encryptMsg(module, condition.toBytes(), pk, condition);
		t.setText(t.getText() + "\n" + "成功加密访问条件值");
		t.setText(t.getText() + "\n" + "开始上传文件");
		if(FileServer.uploadFile(id, fileName, cipher, DEScipher, tCipher)) {
			t.setText(t.getText() + "\n" + "上传成功！");
			observeEvent.getInstance().setEventTag(EventDef.getUserFiles);
		}
		else {
			t.setText(t.getText() + "\n" + "上传失败！");
		}
	}
}
