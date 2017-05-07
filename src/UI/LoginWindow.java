package UI;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import org.apache.commons.httpclient.HttpException;

import com.LoginServer;

import Event.EventDef;
import Event.observeEvent;
import UserDefault.UserInfo;

@SuppressWarnings("serial")

public class LoginWindow extends JFrame implements MouseListener, ActionListener, Observer {
	private JPanel panel;
	private LoginPanel lp;
	private SignupPanel sp;
	
	private JTextField nameTextField;
	private JPasswordField passwordTextField;
	private JTextField signupNameT;
	private JPasswordField signupPasswordT;
	private JPasswordField signupConfirmT;
	private JLabel signupLabel;
	private JLabel loginLabel;
	private JButton loginButton;
	private JButton signupButton;
	private CardLayout card = new CardLayout();
	private String cardName[] = { "登录", "注册" };

	public LoginWindow() {
		setTitle("密云 - 登录");
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
		LoginPanel() {
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

			passwordTextField = new JPasswordField();
			add(passwordTextField);
			passwordTextField.setBounds(122, 293, 172, 36);

			loginButton = new NomalButton("登录");
			add(loginButton);
			loginButton.setBounds(127, 350, 153, 38);
		}
	}

	private class SignupPanel extends JPanel {
		SignupPanel() {
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

			signupNameT = new JTextField();
			add(signupNameT);
			signupNameT.setBounds(130, 156, 172, 36);

			signupPasswordT = new JPasswordField();
			add(signupPasswordT);
			signupPasswordT.setBounds(130, 215, 172, 36);

			signupConfirmT = new JPasswordField();
			add(signupConfirmT);
			signupConfirmT.setBounds(130, 271, 172, 36);

			signupButton = new NomalButton("注册");
			add(signupButton);
			signupButton.setBounds(133, 343, 153, 38);

			loginLabel = new NomalLabel("返回登录");
			add(loginLabel);
			loginLabel.setBounds(175, 405, 64, 28);
		}
	}

	private class NomalButton extends JButton {
		NomalButton(String title) {
			setText(title);
			setOpaque(true);
			setBorderPainted(false);
			setFont(new java.awt.Font(GlobalDef.DefaultFontName, 0, 14));
			setBackground(GlobalDef.deepPurple);
			setForeground(GlobalDef.loginGray);
		}
	}

	private class NomalLabel extends JLabel {
		NomalLabel(String title) {
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
			turnToSignup();
		} else if (o == loginLabel) {
			turnToLogin();
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
			handleLogin();
		} else if (o == signupButton) {
			handleSignup();
		}
	}

	private void handleLogin() {
		String name;
		char[] password;

		name = nameTextField.getText();
		if (name == null || name.length() == 0) {
			JOptionPane.showMessageDialog(null, "请填写用户名", "提醒", JOptionPane.DEFAULT_OPTION);
			return;
		}
		password = passwordTextField.getPassword();
		if (password == null || password.length == 0) {
			JOptionPane.showMessageDialog(null, "请填写密码", "提醒", JOptionPane.DEFAULT_OPTION);
			return;
		}
		LoginServer.login(name, password);
	}

	private void handleSignup() {
		String name;
		char[] password;
		char[] comfirmPassword;

		name = signupNameT.getText();
		if (name == null || name.length() == 0) {
			JOptionPane.showMessageDialog(null, "请填写用户名", "提醒", JOptionPane.DEFAULT_OPTION);
			return;
		}
		if (name.length() > 10) {
			JOptionPane.showMessageDialog(null, "用户名最长10个字", "提醒", JOptionPane.DEFAULT_OPTION);
			return;
		}
		password = signupPasswordT.getPassword();
		if (password == null || password.length == 0) {
			JOptionPane.showMessageDialog(null, "请填写密码", "提醒", JOptionPane.DEFAULT_OPTION);
			return;
		}
		comfirmPassword = signupConfirmT.getPassword();
		if (comfirmPassword == null || comfirmPassword.length == 0) {
			JOptionPane.showMessageDialog(null, "请填写确认密码", "提醒", JOptionPane.DEFAULT_OPTION);
			return;
		}
		char[] savePassword = password.clone();
		Arrays.sort(password);
		Arrays.sort(comfirmPassword);
		if (!Arrays.equals(password, comfirmPassword)) {
			JOptionPane.showMessageDialog(null, "两次密码输入不相同，请重新输入", "提醒", JOptionPane.DEFAULT_OPTION);
			signupPasswordT.setText("");
			signupConfirmT.setText("");
			return;
		}
		LoginServer.signup(name, savePassword);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof observeEvent) {
			if (arg == EventDef.loginSuccess) {
				MainWindow mainW;
				try {
					mainW = new MainWindow(nameTextField.getText());
					observeEvent.getInstance().addObserver(mainW);
					mainW.setVisible(true);
					setVisible(false);
					clearInput();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (arg == EventDef.signupSuccess) {
				JOptionPane.showMessageDialog(null, "注册成功，返回登录", "提醒", JOptionPane.DEFAULT_OPTION);
				turnToLogin();
			} else if (arg == EventDef.exitTap) {
				setVisible(true);
				clearInput();
			}
		}
	}

	private void turnToLogin() {
		card.show(panel, cardName[0]);
		clearInput();
		this.setTitle("密云 - 登录");
	}

	private void turnToSignup() {
		card.show(panel, cardName[1]);
		clearInput();
		this.setTitle("密云 - 注册");
	}

	private void clearInput() {
		nameTextField.setText("");
		passwordTextField.setText("");
		signupNameT.setText("");
		signupPasswordT.setText("");
		signupConfirmT.setText("");
	}
}