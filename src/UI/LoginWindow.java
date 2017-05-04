package UI;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Event.EventDef;
import Event.observeEvent;

@SuppressWarnings("serial")

public class LoginWindow extends JFrame implements MouseListener, ActionListener, Observer {
	private JPanel panel;
	private LoginPanel lp;
	private SignupPanel sp;
	
	private JTextField nameTextField;
	private JTextField passwordTextField;
	private JTextField confirmTextField;
	private JLabel signupLabel;
	private JLabel loginLabel;
	private JButton loginButton;
	private JButton signupButton;
	private CardLayout card = new CardLayout();
	private String cardName[] = {"登录", "注册"};
	
	public LoginWindow () {
		setTitle("登录");
		setSize(385, 484);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel(card);
		lp = new LoginPanel();
		sp = new SignupPanel();
		panel.add(lp, cardName[0]);
		panel.add(sp, cardName[1]);
		add(panel);
		
		signupLabel.addMouseListener(this);
		loginLabel.addMouseListener(this);
		loginButton.addActionListener(this);
		signupButton.addActionListener(this);
		
		setVisible(true);
	}
	
	private class LoginPanel extends JPanel {
		LoginPanel () {
			setBackground(GlobalDef.loginGray);
			setLayout(null);
			
			JLabel iconLabel = new JLabel();
			iconLabel.setIcon(new ImageIcon("images/登录俩人.png"));
			add(iconLabel);
			iconLabel.setBounds(118, 74, 150, 144);
			
			JLabel nameLabel = new NomalLabel("用户名");
			add(nameLabel);
			nameLabel.setBounds(69, 258, 43, 20);
			
			JLabel passwordLabel = new NomalLabel("密码");
			add(passwordLabel);
			passwordLabel.setBounds(75, 301, 29, 20);
			
			signupLabel = new NomalLabel("还没有帐号？来注册吧");
			add(signupLabel);
			signupLabel.setBounds(124, 408, 149, 28);
			
			nameTextField = new JTextField();
			add(nameTextField);
			nameTextField.setBounds(122, 250, 172, 36);
			
			passwordTextField = new JTextField();
			add(passwordTextField);
			passwordTextField.setBounds(122, 293, 172, 36);
			
			loginButton = new NomalButton("登录");
			add(loginButton);
			loginButton.setBounds(127, 350, 153, 38);
		}
	}
	
	private class SignupPanel extends JPanel {
		SignupPanel () {
			setBackground(GlobalDef.loginGray);
			setLayout(null);
			
			JLabel BannerLabel = new NomalLabel("请填写注册信息");
			BannerLabel.setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 20));
			add(BannerLabel);
			BannerLabel.setBounds(122, 54, 141, 28);
			
			JLabel nameLabel = new NomalLabel("用户名");
			add(nameLabel);
			nameLabel.setBounds(70, 164, 43, 20);
			
			JLabel passwordLabel = new NomalLabel("密码");
			add(passwordLabel);
			passwordLabel.setBounds(77, 223, 29, 20);
			
			JLabel comfirmLabel = new NomalLabel("确认密码");
			add(comfirmLabel);
			comfirmLabel.setBounds(63, 279, 57, 20);
			
			nameTextField = new JTextField();
			add(nameTextField);
			nameTextField.setBounds(130, 156, 172, 36);
			
			passwordTextField = new JTextField();
			add(passwordTextField);
			passwordTextField.setBounds(130, 215, 172, 36);
			
			confirmTextField = new JTextField();
			add(confirmTextField);
			confirmTextField.setBounds(130, 271, 172, 36);
			
			signupButton = new NomalButton("注册");
			add(signupButton);
			signupButton.setBounds(133, 343, 153, 38);
			
			loginLabel = new NomalLabel("返回登录");
			add(loginLabel);
			loginLabel.setBounds(175, 405, 64, 28);
		}
	}
	
	private class NomalButton extends JButton {
		NomalButton (String title) {
			setText(title);
			setOpaque(true);
			setBorderPainted(false);
			setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 14));
			setBackground(GlobalDef.deepPurple);
			setForeground(GlobalDef.loginGray);
		}
	}
	
	private class NomalLabel extends JLabel{
		NomalLabel (String title) {
			super();
			setText(title);
			setHorizontalAlignment(SwingConstants.CENTER);
			setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 13));
			setForeground(GlobalDef.deepPurple);
			setVisible(true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o == signupLabel) {
			card.show(panel, cardName[1]);
			this.setTitle("注册");
		}
		else if (o == loginLabel) {
			card.show(panel, cardName[0]);
			this.setTitle("登录");
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == loginButton) {
			observeEvent.getInstance().setEventTag(EventDef.loginSuccess);
		}
		else if (o == signupButton) {
			
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof observeEvent){
			if (arg == EventDef.loginSuccess) {
				setVisible(false);
		    }
			else if (arg == EventDef.exitTap) {
		    	setVisible(true);
		    }
		}
	}
}