package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Event.EventDef;
import Event.observeEvent;

@SuppressWarnings("serial")
public class UploadFile extends JPanel implements ActionListener {
	JButton backB;
	JButton choseB;
	JButton startB;
	JTextArea t;
	
	UploadFile() {
		configureLayout();
		backB.addActionListener(this);
		choseB.addActionListener(this);
		startB.addActionListener(this);
	}
	
	private void configureLayout() {
		setLayout(new BorderLayout(50, 30));
		JPanel jn = new JPanel(new BorderLayout(50, 10));
		
		JPanel titleP = new JPanel(new BorderLayout(5, 0));
		JPanel jb = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		backB = NomalButton("返回", false);
		jb.add(backB);
		titleP.add(jb, BorderLayout.NORTH);
		
		JLabel title = new JLabel();
		title.setText("上传文件");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 24));
		title.setForeground(GlobalDef.deepPurple);
		titleP.add(title);
		
		JPanel jc = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
		choseB = NomalButton("选择文件", true);
		jc.add(choseB);
		titleP.add(jc, BorderLayout.SOUTH);
		jn.add(titleP, BorderLayout.NORTH);
		
		JScrollPane sp = new JScrollPane();
		t = new JTextArea("上传文件路径：\n/usr/k/vlnfbka/kdfbnvabjkak/vkcbakafsnvkabhd/fbkvl/nabs/fbvk/dnfbkak/dfbnvabjkakvk.doc\n上传结果：上传成功/失败", 10, 30);
		t.setEditable(false);
		t.setLineWrap(true);				  //自动换行
		t.setWrapStyleWord(true);			  //断行不断字
		sp.setViewportView(t);
		jn.add(sp);
		
		JPanel bP = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
		startB = NomalButton("开始上传", true);
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
		}
		else {
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
		}
		else if (o == choseB) {
			
		}
		else if (o == startB) {
			
		}
	}
}
