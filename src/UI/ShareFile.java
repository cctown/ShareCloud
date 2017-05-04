package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Event.EventDef;
import Event.observeEvent;

@SuppressWarnings("serial")
public class ShareFile extends JPanel implements ActionListener {
	JButton backB;
	JButton comfirmB;
	JButton startB;
	JLabel file;
	JTextField idt;
	JTextArea t;
	
	ShareFile() {
		configureLayout();
		backB.addActionListener(this);
		comfirmB.addActionListener(this);
		startB.addActionListener(this);
	}
	
	private void configureLayout() {
		setLayout(new BorderLayout(50, 30));
		JPanel jcenter = new JPanel(new BorderLayout(50, 30));
		
		JPanel titleP = new JPanel(new BorderLayout(5, 0));
		JPanel jb = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		backB = NomalButton("返回", false);
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
		file = NomalLabel("选中的共享文件：afsnvkabhdf.doc");
		detailP.add(file, BorderLayout.NORTH);
		
		JPanel choseP = new JPanel(new BorderLayout(50, 20));
		JPanel inputP = new JPanel(new BorderLayout(5, 20));
		JLabel lid = NomalLabel("依次输入接收者ID：");
		inputP.add(lid, BorderLayout.WEST);
		idt = new JTextField();
		inputP.add(idt);
		comfirmB = NomalButton("确定", true);
		inputP.add(comfirmB, BorderLayout.EAST);
		choseP.add(inputP, BorderLayout.NORTH);
		
		JPanel nameP = new JPanel(new BorderLayout(10, 20));
		JPanel nj = new JPanel(new BorderLayout());
		JLabel namel = NomalLabel("接收者：");
		nj.add(namel, BorderLayout.NORTH);
		JPanel cj = new JPanel();
		nj.add(cj);
		nameP.add(nj, BorderLayout.WEST);
		JScrollPane sp = new JScrollPane();
		t = new JTextArea("小红；小明；小花；小修", 10, 30);
		t.setEditable(false);
		t.setLineWrap(true);				  //自动换行
		t.setWrapStyleWord(true);			  //断行不断字
		sp.setViewportView(t);
		nameP.add(sp);
		choseP.add(nameP);
		
		detailP.add(choseP);
		
		jcenter.add(detailP);
		JPanel bP = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
		startB = NomalButton("确定共享", true);
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
		}
		else {
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
		}
		else if (o == comfirmB) {
			
		}
		else if (o == startB) {
			
		}
	}
}
