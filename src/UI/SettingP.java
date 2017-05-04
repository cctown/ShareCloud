package UI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")

public class SettingP extends JPanel implements ActionListener {
	public static String keyPath = "/Users/chencaixia/SecretCloud/key/";
	public static String downloadPath = "/Users/chencaixia/SecretCloud/download";
	public static String encryptPath = "/Users/chencaixia/SecretCloud/encrypt";
	public static String decryptPath = "/Users/chencaixia/SecretCloud/decrypt";
	
	private JButton downB;
	private JButton encryptB;
	private JButton decryptB;
	private JButton keyB;
	private JTextField downT;
	private JTextField encryptT;
	private JTextField decryptT;
	private JTextField keyT;
	
	private String name[] = {"下载文件存放位置", "加密结果存放位置", "解密结果存放位置", "公私密钥存放位置"};
	SettingP() {
		configureLayout();
		downB.addActionListener(this);
		encryptB.addActionListener(this);
		decryptB.addActionListener(this);
		keyB.addActionListener(this);
		
		downT.setText(downloadPath);
		encryptT.setText(encryptPath);
		decryptT.setText(decryptPath);
		keyT.setText(keyPath);
	}
	
	private void configureLayout() {
		setLayout(new BorderLayout(70, 100));
		JPanel centerP = new JPanel(new BorderLayout(70, 100));
		JPanel pathP = new JPanel(new GridLayout(name.length, 1, 5, 30));
		
		JPanel down = new JPanel();
		down.setLayout(new BorderLayout(10, 20));
		downB = NomalButton("修改");
		down.add(downB, BorderLayout.EAST);
		downT = new JTextField();
		downT.setEditable(false);
		down.add(downT);
		JLabel downL = NomalLabel(name[0]);
		down.add(downL, BorderLayout.WEST);
		pathP.add(down);
		
		JPanel encrypt = new JPanel();
		encrypt.setLayout(new BorderLayout(10, 20));
		encryptB = NomalButton("修改");
		encrypt.add(encryptB, BorderLayout.EAST);
		encryptT = new JTextField();
		encryptT.setEditable(false);
		encrypt.add(encryptT);
		JLabel encryptL = NomalLabel(name[1]);
		encrypt.add(encryptL, BorderLayout.WEST);
		pathP.add(encrypt);
		
		JPanel decrypt = new JPanel();
		decrypt.setLayout(new BorderLayout(10, 20));
		decryptB = NomalButton("修改");
		decrypt.add(decryptB, BorderLayout.EAST);
		decryptT = new JTextField();
		decryptT.setEditable(false);
		decrypt.add(decryptT);
		JLabel decryptL = NomalLabel(name[2]);
		decrypt.add(decryptL, BorderLayout.WEST);
		pathP.add(decrypt);
		
		JPanel key = new JPanel();
		key.setLayout(new BorderLayout(10, 20));
		keyB = NomalButton("修改");
		key.add(keyB, BorderLayout.EAST);
		keyT = new JTextField();
		keyT.setEditable(false);
		key.add(keyT);
		JLabel keyL = NomalLabel(name[3]);
		key.add(keyL, BorderLayout.WEST);
		pathP.add(key);
		
		centerP.add(pathP);
		JPanel nP = new JPanel();
		centerP.add(nP, BorderLayout.NORTH);
		JPanel eP = new JPanel();
		centerP.add(eP, BorderLayout.EAST);
		JPanel wP = new JPanel();
		centerP.add(wP, BorderLayout.WEST);
		
		add(centerP, BorderLayout.NORTH);
		JPanel P1 = new JPanel();
		add(P1, BorderLayout.WEST);
		JPanel P2 = new JPanel();
		add(P2, BorderLayout.EAST);
		JPanel P3 = new JPanel();
		add(P3, BorderLayout.SOUTH);
		JPanel P4 = new JPanel();
		add(P4);
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
		
		if(o == downB) {
			JFileChooser jfile = new JFileChooser();
			jfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(jfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				downT.setText(jfile.getSelectedFile().getPath());
				downloadPath = downT.getText();
			}
		}
		else if(o == encryptB) {
			JFileChooser jfile = new JFileChooser();
			jfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(jfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				encryptT.setText(jfile.getSelectedFile().getPath());
				encryptPath = encryptT.getText();
			}
		}
		else if(o == decryptB) {
			JFileChooser jfile = new JFileChooser();
			jfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(jfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				decryptT.setText(jfile.getSelectedFile().getPath());
				decryptPath = decryptT.getText();
			}
		}
		else if(o == keyB) {
			JFileChooser jfile = new JFileChooser();
			jfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(jfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				keyT.setText(jfile.getSelectedFile().getPath());
				keyPath = keyT.getText();
			}
		}
	}
}
