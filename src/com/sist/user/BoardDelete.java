package com.sist.user;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import com.sist.dao.*;
import com.sist.vo.*;
public class BoardDelete extends JPanel
implements ActionListener
{
	JLabel titleLa,la;
	JPasswordField pf;
	JButton b1,b2;
	ControlPanel cp;
	public BoardDelete(ControlPanel cp)
	{
		 this.cp=cp;
		 titleLa=new JLabel("삭제하기",JLabel.CENTER);// <table>
    	 titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30)); //<h3></h3>
    	 setLayout(null);
   	     titleLa.setBounds(10, 15, 620, 50);
   	     add(titleLa);
   	     
   	     la=new JLabel("비밀번호");
   	     pf=new JPasswordField();
   	     b1=new JButton("삭제");
   	     b2=new JButton("취소");
   	     
   	     // 배치 
   	     la.setBounds(230, 75, 80, 30);
   	     pf.setBounds(315, 75, 150, 30);
   	     
   	     JPanel p=new JPanel();
   	     p.add(b1);p.add(b2);
   	     
   	     p.setBounds(230, 115, 235, 35);
   	     add(p);
   	     
   	     add(la);
   	     add(pf);
   	     
   	     b1.addActionListener(this);
   	     b2.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b1)
		{
			// List 
			BoardDAO dao=BoardDAO.newInstance();
			String pwd=
				String.valueOf(pf.getPassword());
			if(pwd.trim().length()<1)
			{
				pf.requestFocus();
				return; // 강제 입력 
			}
			String no=cp.bDetail.no.getText();
			boolean bCheck=
				dao.board_delete(Integer.parseInt(no), pwd);
			if(bCheck==true)
			{
				cp.card.show(cp, "BLIST");
				cp.bList.print();
			}
			else
			{
				JOptionPane.showMessageDialog(this, 
						"비밀번호가 틀립니다");
				pf.setText("");
				pf.requestFocus();
			}
			
		}
		else if(e.getSource()==b2)
		{
			cp.card.show(cp, "BDETAIL");
		}
	}
}
