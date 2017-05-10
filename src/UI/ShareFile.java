package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.FileServer;

import Event.EventDef;
import Event.observeEvent;
import SecretCloudProxy.Ciphertext;
import SecretCloudProxy.CommonDef;
import SecretCloudProxy.PublicKey;
import SecretCloudProxy.ReencryptionKey;
import UserDefault.UserHelper;
import UserDefault.UserInfo;
import encryption.CommonFileManager;
import encryption.KeyGen;
import encryption.decryptTask;
import encryption.encryptionModule;

import it.unisa.dia.gas.jpbc.Element;

@SuppressWarnings("serial")
public class ShareFile extends JPanel implements ActionListener {
	private JButton backB;
	private JButton comfirmB;
	private JButton startB;
	private JLabel fileL;
	private String fileName;
	private JTextField idTextField;
	private JTextArea nameTextArea;
	private List<String> idArray;
	private String defaultTips = "已添加接收者：\n\n";

	ShareFile() {
		configureLayout();
		idArray = new ArrayList<String>();
	}

	private void configureLayout() {
		setLayout(new BorderLayout(50, 30));
		JPanel jcenter = new JPanel(new BorderLayout(50, 30));

		JPanel titleP = new JPanel(new BorderLayout(5, 0));
		JPanel jb = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		backB = NomalButton("返回", false);
		backB.addActionListener(this);
		jb.add(backB);
		titleP.add(jb, BorderLayout.NORTH);

		JLabel title = new JLabel();
		title.setText("分享文件");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 24));
		title.setForeground(GlobalDef.deepPurple);
		titleP.add(title);

		jcenter.add(titleP, BorderLayout.NORTH);
		JPanel detailP = new JPanel(new BorderLayout(50, 20));
		fileL = NomalLabel("");
		detailP.add(fileL, BorderLayout.NORTH);

		JPanel choseP = new JPanel(new BorderLayout(50, 20));
		JPanel inputP = new JPanel(new BorderLayout(5, 20));
		JLabel lid = NomalLabel("依次输入接收者ID：");
		inputP.add(lid, BorderLayout.WEST);
		idTextField = new JTextField();
		inputP.add(idTextField);
		comfirmB = NomalButton("添加", true);
		comfirmB.addActionListener(this);
		inputP.add(comfirmB, BorderLayout.EAST);
		choseP.add(inputP, BorderLayout.NORTH);

		JScrollPane sp = new JScrollPane();
		nameTextArea = new JTextArea(defaultTips);
		nameTextArea.setEditable(false);
		nameTextArea.setMargin(new Insets(5, 5, 100, 5));
		nameTextArea.setLineWrap(true); // 自动换行
		nameTextArea.setWrapStyleWord(true); // 断行不断字
		sp.setViewportView(nameTextArea);
		choseP.add(sp);

		detailP.add(choseP);

		jcenter.add(detailP);
		JPanel bP = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
		startB = NomalButton("确定共享", true);
		startB.addActionListener(this);
		bP.add(startB);
		jcenter.add(bP, BorderLayout.SOUTH);

		add(jcenter);
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
		if (o == backB) {
			observeEvent.getInstance().setEventTag(EventDef.backToCloud);
		} else if (o == comfirmB) {
			handleComfirm();
		} else if (o == startB) {
			if(idArray != null && !idArray.isEmpty()) {
				handleStartShare();
			}
		}
	}

	private void handleComfirm() {
		String name;

		name = idTextField.getText();
		if (name == null || name.length() == 0) {
			JOptionPane.showMessageDialog(null, "请填写用户名", "提醒", JOptionPane.DEFAULT_OPTION);
			return;
		}
		if (name.equals(UserInfo.getInstance().userName)) {
			JOptionPane.showMessageDialog(null, "不需要给自己分享文件", "提醒", JOptionPane.DEFAULT_OPTION);
			return;
		}
		if (idArray != null) {
			int i = 0;
			while (i < idArray.size()) {
				if (idArray.get(i).equals(name)) {
					JOptionPane.showMessageDialog(null, "该用户已经添加", "提醒", JOptionPane.DEFAULT_OPTION);
					return;
				}
				i++;
			}
		}
		idArray.add(name);
		nameTextArea.setText(nameTextArea.getText() + name + "；");
		idTextField.setText("");
	}
	
	private void handleStartShare() {
		String userName = UserInfo.getInstance().userName;
		// 检查各个配置文件，确保加密使用的密钥、公开参数等文件在
		if (!UserHelper.checkUserInfo(userName)) {
			nameTextArea.setText(nameTextArea.getText() + "\n\n" + "用户配置文件不完整，无法完成操作");
			return;
		}
		
		encryptionModule module;
		try {
			module = new encryptionModule();
			nameTextArea.setText(nameTextArea.getText() + "\n\n" + "成功初始化加密模块");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			nameTextArea.setText(nameTextArea.getText() + "\n\n" + "加密模块初始化失败，无法完成操作。");
			return;
		}
		
		Element sk;
		try {
			byte[] skbyte = (byte[])CommonFileManager.readObjectFromFile(UserInfo.getInstance().keyPath + CommonDef.secretKeyAffix(userName));
			sk = module.newG1ElementFromBytes(skbyte).getImmutable();
			nameTextArea.setText(nameTextArea.getText() + "\n" + "成功获取用户私钥");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			nameTextArea.setText(nameTextArea.getText() + "\n" + "获取用户私钥失败，无法完成操作。");
			return;
		}
		nameTextArea.setText(nameTextArea.getText() + "\n" + "正在向服务器请求条件值和参数...");
		Map<String, byte[]> map = FileServer.getParamsForReencryptionKey(userName, fileName);
		if(map == null) {
			nameTextArea.setText(nameTextArea.getText() + "\n" + "向服务器请求条件值和参数失败，无法完成操作。");
			return;
		}
		byte[] grtByte = map.get("grt");
		Ciphertext conditionCipher = (Ciphertext)CommonFileManager.bytesToObject(map.get("condition"));
		byte[] conditionByte = decryptTask.decryptMsg(module, conditionCipher, sk);
		//终于拿到条件值和grt -_-
		Element condition = module.newGTElementFromBytes(conditionByte);
		Element grt = module.newG1ElementFromBytes(grtByte);
		nameTextArea.setText(nameTextArea.getText() + "\n" + "成功拿到条件值和参数。");
		
		Map<String, byte[]> rkMap = new HashMap<String, byte[]>();
		int j = 0;
		while(j < idArray.size()) {    //一个个为接收者生成重加密密钥
			String reveiverID = idArray.get(j);
			PublicKey pk;
			try {
				pk = (PublicKey) CommonFileManager.readObjectFromFile(UserInfo.getInstance().keyPath + CommonDef.publicKeyAffix(reveiverID));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				nameTextArea.setText(nameTextArea.getText() + "\n" + "找不到" + reveiverID +"的公钥，无法分享，请确认该用户是否存在。");
				idArray.remove(j);
				continue;   //由于remove了一个，所以idArray的长度会减一，这时候 j 就不需要加1了，正好能访问到下一个对象
			}
			nameTextArea.setText(nameTextArea.getText() + "\n" + "正在为" + reveiverID +"生成重加密密钥...");
			ReencryptionKey rk = KeyGen.rkGen(module, sk, pk, grt, condition);
			nameTextArea.setText(nameTextArea.getText() + "\n" + "成功为" + reveiverID +"生成重加密密钥。");
			String rkName = CommonDef.reencryptionKeyAffix(userName, reveiverID, fileName);
			rkMap.put(rkName, CommonFileManager.objectToByteArray(rk));
			
			j++;
		}
		if(rkMap.isEmpty()) {
			nameTextArea.setText(nameTextArea.getText() + "\n" + "操作结束。");
			return;
		}
		String[] receivers = new String[idArray.size()];
		try{
			for(int i = 0; i < idArray.size(); i++) {
				receivers[i] = idArray.get(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			nameTextArea.setText(nameTextArea.getText() + "\n" + "失败");
		}
		//提交重加密密钥
		String res = FileServer.uploadReencryptionKey(userName, fileName, receivers, rkMap);
		if(res != null) {
			nameTextArea.setText(nameTextArea.getText() + "\n" + res);
		}
	}

	public void setFile(String fileName) {
		this.fileName = fileName;
		this.fileL.setText("将要分享的文件为：" + fileName);
		nameTextArea.setText(defaultTips);
		idTextField.setText("");
		idArray.clear();
	}
}
