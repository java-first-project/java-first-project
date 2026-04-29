package com.sist.user;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// detail.jsp
import javax.swing.*;

import com.sist.dao.BoardDAO;
import com.sist.vo.BoardVO;
public class BoardDetail extends JPanel 
implements ActionListener
{
     JLabel titleLa,nameLa,noLa,subLa,dayLa,hitLa;
     JLabel name,no,sub,day,hit;
     JTextArea ta;
     JButton b1,b2,b3; // 수정 / 삭제 / 목록 
     ControlPanel cp;
     public  BoardDetail(ControlPanel cp)
     {
    	 this.cp=cp;
    	 titleLa=new JLabel("게시판",JLabel.CENTER);// <table>
     	 titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30)); //<h3></h3>
     	 setLayout(null);
    	 titleLa.setBounds(180, 15, 620, 50);
    	 add(titleLa);
    	 
    	 noLa=new JLabel("번호",JLabel.CENTER);
    	 noLa.setBounds(180, 75 , 80, 30);
    	 no=new JLabel("",JLabel.CENTER);
    	 no.setBounds(265, 75, 120, 30);
    	 add(noLa);add(no);
    	 
    	 dayLa=new JLabel("작성일",JLabel.CENTER);
    	 dayLa.setBounds(390, 75 , 80, 30);
    	 day=new JLabel("",JLabel.CENTER);
    	 day.setBounds(475, 75, 200, 30);
    	 add(dayLa);add(day);
    	 
    	 nameLa=new JLabel("이름",JLabel.CENTER);
    	 nameLa.setBounds(180, 110 , 80, 30);
    	 name=new JLabel("",JLabel.CENTER);
    	 name.setBounds(265, 110, 120, 30);
    	 add(nameLa);add(name);
    	 
    	 hitLa=new JLabel("조회수",JLabel.CENTER);
    	 hitLa.setBounds(390, 110 , 80, 30);
    	 hit=new JLabel("",JLabel.CENTER);
    	 hit.setBounds(475, 110, 200, 30);
    	 add(hitLa);add(hit);
    	 
    	 subLa=new JLabel("제목",JLabel.CENTER);
    	 subLa.setBounds(180, 145 , 80, 30);
    	 sub=new JLabel("");
    	 sub.setBounds(265, 145, 400, 30);
    	 add(subLa);add(sub);
    	 
    	 ta=new JTextArea();
    	 ta.setEditable(false); // 비활성화 
    	 ta.setBounds(180, 180, 485, 250);
    	 add(ta);
    	 
    	 JPanel p=new JPanel();
    	 b1=new JButton("수정");
    	 b2=new JButton("삭제");
    	 b3=new JButton("목록");
    	 p.add(b1);p.add(b2);p.add(b3);
    	 p.setBounds(180, 440, 485, 35);
    	 add(p);
    	 
    	 b3.addActionListener(this);
    	 b2.addActionListener(this);
     }
     public void print(int num)
     {
    	 BoardDAO dao=BoardDAO.newInstance();
    	 BoardVO vo=dao.board_detail(num);
    	 no.setText(String.valueOf(vo.getNo()));
    	 name.setText(vo.getName());
    	 sub.setText(vo.getSubject());
    	 day.setText(vo.getDbday());
    	 hit.setText(String.valueOf(vo.getHit()));
    	 ta.setText(vo.getContent());
    	 
     }
	 @Override
	 public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b3)
		{
			cp.card.show(cp, "BLIST");
			cp.bList.print();
		}
		else if(e.getSource()==b2)
		{
			cp.bDelete.pf.setText("");
			cp.card.show(cp, "BDELETE");
		}
	 }
}
