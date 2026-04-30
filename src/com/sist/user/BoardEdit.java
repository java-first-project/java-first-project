package com.sist.user;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.sist.dao.BoardDAO;
import com.sist.vo.BoardVO;

public class BoardEdit extends JPanel implements ActionListener {
	JPanel confirmPwdPanel, editPanel;
	JLabel checkTitleLa, checkLa;
	JPasswordField pf;
	JButton b1, b2, b3, b4;//비번확인, 비번취소, 수정확인, 수정취소
	ControlPanel cp;
	
	JLabel editTitleLa, nameLa, subLa, contLa, pwdLa;
    JTextField nameTf, subTf;
    JPasswordField pwdPf;
    JTextArea ta;

	public BoardEdit(ControlPanel cp) {
		this.cp = cp;       
		setLayout(null);
        confirmPwdPanel = new JPanel();
        confirmPwdPanel.setLayout(null);
        confirmPwdPanel.setBounds(0, 0, 800, 600); // 전체 크기

        checkTitleLa = new JLabel("비밀번호 확인", JLabel.CENTER);
        checkTitleLa.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        checkTitleLa.setBounds(10, 15, 620, 50);
        confirmPwdPanel.add(checkTitleLa);

        checkLa = new JLabel("비밀번호");
        pf = new JPasswordField();
        checkLa.setBounds(230, 75, 80, 30);
        pf.setBounds(315, 75, 150, 30);
        confirmPwdPanel.add(checkLa);
        confirmPwdPanel.add(pf);

        b1 = new JButton("확인");
        b2 = new JButton("취소");
        JPanel p1 = new JPanel();
        p1.add(b1); p1.add(b2);
        p1.setBounds(230, 115, 235, 35);
        confirmPwdPanel.add(p1);

        add(confirmPwdPanel);
        
        
        
        editPanel = new JPanel();
        editPanel.setLayout(null);
        editPanel.setBounds(0, 0, 800, 600);

        editTitleLa = new JLabel("수정하기", JLabel.CENTER);
        editTitleLa.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        editTitleLa.setBounds(10, 15, 620, 50);
        editPanel.add(editTitleLa);

        nameLa = new JLabel("이름", JLabel.CENTER);
        nameTf = new JTextField();
        nameLa.setBounds(180, 70, 80, 30);
        nameTf.setBounds(265, 70, 150, 30);
        editPanel.add(nameLa); editPanel.add(nameTf);

        subLa = new JLabel("제목", JLabel.CENTER);
        subTf = new JTextField();
        subLa.setBounds(180, 105, 80, 30);
        subTf.setBounds(265, 105, 450, 30);
        editPanel.add(subLa); editPanel.add(subTf);

        contLa = new JLabel("내용", JLabel.CENTER);
        ta = new JTextArea();
        JScrollPane js = new JScrollPane(ta);
        contLa.setBounds(180, 140, 80, 30);
        js.setBounds(265, 140, 450, 250);
        editPanel.add(contLa); editPanel.add(js);
        
        pwdLa = new JLabel("비밀번호", JLabel.CENTER);
        pwdPf = new JPasswordField();
        pwdLa.setBounds(180, 395, 80, 30);
        pwdPf.setBounds(265, 395, 150, 30);
        editPanel.add(pwdLa); editPanel.add(pwdPf);
        
        b3 = new JButton("수정");
        b4 = new JButton("취소");
        JPanel p2 = new JPanel();
        p2.add(b3); p2.add(b4);
        p2.setBounds(180, 435, 535, 35);
        editPanel.add(p2);

        add(editPanel);
        editPanel.setVisible(false);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == b2 || e.getSource() == b4) {
			pf.setText("");
	        nameTf.setText("");
	        subTf.setText("");
	        ta.setText("");
	        pwdPf.setText("");
			
			cp.card.show(cp, "BLIST");
			confirmPwdPanel.setVisible(true);
			editPanel.setVisible(false);
		} else if (e.getSource() == b1) {
			BoardDAO dao=BoardDAO.newInstance();
			String pwd=
				String.valueOf(pf.getPassword());
			if(pwd.trim().length()<1)
			{
				pf.requestFocus();
				return;
			}
			String no=cp.bDetail.no.getText();
			boolean bCheck=
				dao.board_edit_check(Integer.parseInt(no), pwd);
			if(bCheck==true)
			{
				pf.setText("");
				confirmPwdPanel.setVisible(false);
				editPanel.setVisible(true);
				
				BoardVO vo = dao.board_detail(Integer.parseInt(no));
			    nameTf.setText(vo.getName());
			    subTf.setText(vo.getSubject());
			    ta.setText(vo.getContent());
			}
			else
			{
				JOptionPane.showMessageDialog(this, 
						"비밀번호가 틀립니다");
				pf.setText("");
				pf.requestFocus();
			}
		} else if (e.getSource() == b3) {
			String name=nameTf.getText();
			//입력된 값 읽기  ==> 오라클 : NOT NULL
			if(name.trim().length()<1) // 유효성 검사 
			{
				// 입력이 안된 경우
				nameTf.requestFocus();
				return;
			}
			
			String subject=subTf.getText();
			//입력된 값 읽기  ==> 오라클 : NOT NULL
			if(subject.trim().length()<1) // 유효성 검사 
			{
				// 입력이 안된 경우
				subTf.requestFocus();
				return;
			}
			
			String content=ta.getText();
			//입력된 값 읽기  ==> 오라클 : NOT NULL
			if(content.trim().length()<1) // 유효성 검사 
			{
				// 입력이 안된 경우
				ta.requestFocus();
				return;
			}
			// char[] => 문자열 변경 => String.valueOf
			String pwd=
				String.valueOf(pwdPf.getPassword());
			//입력된 값 읽기  ==> 오라클 : NOT NULL
			if(pwd.trim().length()<1) // 유효성 검사 
			{
				// 입력이 안된 경우
				pwdPf.requestFocus();
				return;
			}
			String no=cp.bDetail.no.getText();
			
			BoardVO vo=new BoardVO();
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd);
			vo.setNo(Integer.parseInt(no));
			
			// 데이터베이스 연동 
			BoardDAO dao=BoardDAO.newInstance();
			dao.board_edit(vo);
			
			pf.setText("");
	        nameTf.setText("");
	        subTf.setText("");
	        ta.setText("");
	        pwdPf.setText("");
	        
	        confirmPwdPanel.setVisible(true);
	        editPanel.setVisible(false);
			
			// 이동 => boardList로 이동 
			cp.card.show(cp, "BLIST");
			cp.bList.print();
		}
	}
}
