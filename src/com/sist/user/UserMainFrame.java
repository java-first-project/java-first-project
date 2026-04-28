package com.sist.user;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.sist.dao.MemberDAO;
import com.sist.vo.MemberVO;
public class UserMainFrame extends JFrame
implements ActionListener
{
    MenuPanel mp=new MenuPanel();
    ControlPanel cp;
    static boolean bLogin=false;
    static char isAdmin='n';
    Login login=new Login();
    public UserMainFrame()
    {
    	cp=new ControlPanel();
    	mp.init();
    	setLayout(null);
    	mp.setBounds(250, 15, 700, 45);
    	cp.setBounds(10,70, 980, 580);
    	add(mp);
    	add(cp);
    	
    	setSize(1024, 700);
    	setVisible(true);
    	mp.b3.addActionListener(this);
    	mp.b2.addActionListener(this);
    	mp.b1.addActionListener(this);
    	mp.b6.addActionListener(this);
    	// login
    	login.b1.addActionListener(this);// 로그인 
    	login.b2.addActionListener(this);// 취소 
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
        {
        	UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        }catch(Exception ex) {}
        new UserMainFrame();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==mp.b3)
		{
			cp.card.show(cp, "JOIN");
		}
		else if(e.getSource()==mp.b1)
		{
			cp.card.show(cp, "HOME");
		}
		else if(e.getSource()==mp.b2)
		{
			login.tf.setText("");
			login.pf.setText("");
			login.setVisible(true);
		}
		// 로그인 
		else if(e.getSource()==login.b2)
		{
			login.setVisible(false);
		}
		else if(e.getSource()==login.b1)
		{
			// 입력값 읽기 
			String id=login.tf.getText();
			if(id.trim().length()<1)
			{
				login.tf.requestFocus();
				return;
			}
			String pwd=String.valueOf(login.pf.getPassword());
			if(pwd.trim().length()<1)
			{
				login.pf.requestFocus();
				return;
			}
			// 데이터 연결 
			MemberDAO dao=new MemberDAO();
			MemberVO vo=dao.isLogin(id, pwd);
			
			if(vo.getMsg().equals("NOID"))
			{
				JOptionPane.showMessageDialog(this, 
						"아이디 존재하지 않습니다");
				login.tf.setText("");
				login.pf.setText("");
				login.tf.requestFocus();
			}
			else if(vo.getMsg().equals("NOPWD"))
			{
				JOptionPane.showMessageDialog(this, 
						"비밀번호가 틀립니다");
				login.pf.setText("");
				login.pf.requestFocus();
			}
			else
			{
				String s=vo.getIsadmin().equals("y")?"관리자":"일반사용자";
				String title=vo.getId()+"("
						     +s
						     +")";
				setTitle(title);
				UserMainFrame.bLogin=true;
				UserMainFrame.isAdmin=vo.getIsadmin().charAt(0);
				mp.init();
				login.setVisible(false);
			}
		}
		else if(e.getSource()==mp.b6)
		{
			dispose();
			System.exit(0);
		}
		
	}

}