package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")

public class CryptToolbox extends JPanel implements ActionListener{
	private JButton keyB;
	private JButton fileB;
	private JButton startB;
	private JTextArea t;
	private JRadioButton encrypt;
	private JRadioButton decrypt;
	
	private String keyTips;
	private String fileTips;
	
	CryptToolbox() {
		configureLayout();
		encrypt.addActionListener(this);
		decrypt.addActionListener(this);
		keyB.addActionListener(this);
		fileB.addActionListener(this);
		startB.addActionListener(this);
		keyTips = "如果您不修改选择的密钥，将使用默认密钥进行加解密，默认密钥所在路径为"+SettingP.keyPath;
		fileTips = "请选择需要加密或解密的文件。";
		t.setText(keyTips + "\n\n" + fileTips);
	}
	
	private void configureLayout() {
		setLayout(new BorderLayout(70, 50));
		JPanel centerP = new JPanel(new BorderLayout(10, 10));
		
		JPanel choseP = new JPanel(new BorderLayout(10, 10));
		
		JPanel cb = new JPanel(new GridLayout(1, 3, 5, 0));
		
		encrypt = new JRadioButton("加密", true);
		encrypt.setFont(new java.awt.Font(GlobalDef.TableFontName, 0, 14));
		encrypt.setForeground(GlobalDef.deepPurple);
		decrypt = new JRadioButton("解密", false);
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
		
		JPanel bt = new JPanel(new GridLayout(1, 2, 15, 0));
		keyB = NomalButton("选择密钥");
		fileB = NomalButton("选择文件");
		bt.add(keyB);
		bt.add(fileB);
		choseP.add(bt, BorderLayout.EAST);
		
		centerP.add(choseP, BorderLayout.NORTH);
		
		JScrollPane sp = new JScrollPane();
		t = new JTextArea("显示使用的密钥路径，提醒不修改时使用默认私钥。\n显示将要加密的文件路径。\n加密结果：加密成功/加密失败\n加密成功之后显示文件保存路径。", 10, 30);
		t.setEditable(false);
		t.setLineWrap(true);				  //自动换行
		t.setWrapStyleWord(true);			  //断行不断字
		sp.setViewportView(t);
		centerP.add(sp);
		
		JPanel bP = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
		startB = NomalButton("开始加密");
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
		}
		else if (o == decrypt) {
			startB.setText("开始解密");
		}
		else if (o == keyB) {
			JFileChooser jfile = new JFileChooser();
			jfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(jfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				keyTips = "选择的密钥为" + jfile.getSelectedFile().getPath();
				t.setText(keyTips + "\n\n" + fileTips);
			}
		}
		else if (o == fileB) {
			JFileChooser jfile = new JFileChooser();
			jfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(jfile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				fileTips = "选择的文件为" +jfile.getSelectedFile().getPath();
				t.setText(keyTips + "\n\n" + fileTips);
			}
		}
		else if (o == startB) {
			
		}
	}
}
