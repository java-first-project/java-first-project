package com.sist.user;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.sist.dao.*;
import com.sist.vo.*;
public class JoinPanel extends JPanel
implements ActionListener,MouseListener
{
    JLabel tLa,iLa,pLa1,nLa,sLa,pLa,aLa1,aLa2,telLa,cLa;
    JTextField idtf,nametf,posttf,addrtf1,addrtf2,teltf;
    JTextArea cta;
    JButton b1,b2,b3,b4;
    JRadioButton rb1,rb2;
    JComboBox box;
    JPasswordField pf;
    ControlPanel cp;
    IdCheckFrame idf=new IdCheckFrame();
    PostFindFrame post=new PostFindFrame();
    // 데이터베이스 연동 
    MemberDAO dao=new MemberDAO();
    public JoinPanel(ControlPanel cp)
    {
    	this.cp=cp;
    	setLayout(null);
    	tLa=new JLabel("회원 가입",JLabel.CENTER);
    	tLa.setFont(new Font("맑은 고딕",Font.BOLD,35));
    	tLa.setBounds(10, 15, 930, 40);
    	add(tLa);
    	
    	iLa=new JLabel("<html><font color=red>※</font>아이디</html>",JLabel.RIGHT);
    	iLa.setBounds(150, 80, 90, 30);
    	add(iLa);
    	
    	idtf=new JTextField();
    	idtf.setBounds(265, 80, 200, 30);
    	add(idtf);
    	idtf.setEnabled(false);
    	
    	b1=new JButton("아이디 중복체크");
    	b1.setBounds(470, 80, 150, 30);
    	add(b1);
    	/////////////////////////////////////////////////////////////// id
    	pLa1=new JLabel("<html><font color=red>※</font>비밀번호</html>",JLabel.RIGHT);
    	pLa1.setBounds(150, 115, 90, 30);
    	add(pLa1);
    	
    	pf=new JPasswordField();
    	pf.setBounds(265, 115, 200, 30);
    	add(pf);
    	///////////////////////////////////////////////////////////////
    	nLa=new JLabel("<html><font color=red>※</font>이름</html>",JLabel.RIGHT);
    	nLa.setBounds(150, 150, 90, 30);
    	add(nLa);
    	
    	nametf=new JTextField();
    	nametf.setBounds(265, 150, 200, 30);
    	add(nametf);
    	////////////////////////////////////////////////////////////////
    	sLa=new JLabel("성별",JLabel.RIGHT);
    	sLa.setBounds(150, 185, 90, 30);
    	add(sLa);
    	
    	rb1=new JRadioButton("남자");
    	rb1.setBounds(265, 185, 70, 30);
    	add(rb1);
    	
    	rb2=new JRadioButton("여자");
    	rb2.setBounds(340, 185, 70, 30);
    	add(rb2);
    	
    	ButtonGroup bg=new ButtonGroup();
    	bg.add(rb1); bg.add(rb2);
    	
    	rb1.setSelected(true);
    	
    	////////////////////////////////////////////////////////////
    	pLa=new JLabel("<html><font color=red>※</font>우편번호</html>",JLabel.RIGHT);
    	pLa.setBounds(150, 220, 90, 30);
    	add(pLa);
    	
    	posttf=new JTextField();
    	posttf.setBounds(265, 220, 200, 30);
    	add(posttf);
    	
    	posttf.setEnabled(false);
    	posttf.setHorizontalAlignment(JLabel.CENTER);
    	
    	b2=new JButton("우편번호 검색");
    	b2.setBounds(470, 220, 150, 30);
    	add(b2);
    	////////////////////////////////////////////////////////////
    	aLa1=new JLabel("<html><font color=red>※</font>주소</html>",JLabel.RIGHT);
    	aLa1.setBounds(150, 255, 90, 30);
    	add(aLa1);
    	
    	addrtf1=new JTextField();
    	addrtf1.setEnabled(false);
    	addrtf1.setBounds(265, 255, 450, 30);
    	add(addrtf1);
    	
    	aLa2=new JLabel("상세주소",JLabel.RIGHT);
    	aLa2.setBounds(150, 285, 90, 30);
    	add(aLa2);
    	
    	addrtf2=new JTextField();
    	addrtf2.setBounds(265, 285, 450, 30);
    	add(addrtf2);
    	///////////////////////////////////////////////////////////
    	telLa=new JLabel("전화",JLabel.RIGHT);
    	telLa.setBounds(150, 320, 90, 30);
    	add(telLa);
    	
    	box=new JComboBox();
    	box.addItem("010");
    	box.setBounds(265, 320, 90, 30);
    	add(box);
    	
    	teltf=new JTextField();
    	teltf.setBounds(370, 320, 200, 30);
    	add(teltf);
    	///////////////////////////////////////////////////////////
    	cLa=new JLabel("소개",JLabel.RIGHT);
    	cLa.setBounds(150, 355, 90, 30);
    	add(cLa);
    	
    	cta=new JTextArea();
    	JScrollPane js=new JScrollPane(cta);
    	js.setBounds(265, 355, 450,170);
    	add(js);
    	
    	b3=new JButton("회원가입");
    	b4=new JButton("취소");
    	
    	JPanel p=new JPanel();
    	p.add(b3);p.add(b4);
    	
    	p.setBounds(150, 520, 565, 35);
    	add(p);
    	
    	setSize(960, 600);
    	b1.addActionListener(this);
    	b2.addActionListener(this);
    	b3.addActionListener(this);
    	b4.addActionListener(this);
    	
    	// 우편번호 , 아이디 중복 
    	post.b1.addActionListener(this);
    	post.tf.addActionListener(this);
    	post.b2.addActionListener(this);
    	post.table.addMouseListener(this);
    	// CRUD 
    	// id 중복 체크 
    	idf.b1.addActionListener(this);// 중복 체크
    	idf.b2.addActionListener(this);// 확인 
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b4)
		{
			cp.card.show(cp, "HOME");
		}
		else if(e.getSource()==b1)
		{
			// 초기화 => new사용하지 않는다 => hide/show
			// web => new 
			idf.tf.setText("");
			idf.tf.requestFocus();
			idf.setVisible(true);
			idf.la3.setText("");
			idf.b2.setVisible(false);
			
		}
		else if(e.getSource()==b2)
		{
			for(int i=post.model.getRowCount()-1;i>=0;i--)
			{
				post.model.removeRow(i);
			}
			post.tf.setText("");
			post.setVisible(true);
			
		}
		// 우편번호 검색 
		else if(e.getSource()==post.b1||
				e.getSource()==post.tf)
		{
			// Like => Regexp_like =>  index가 적용이 안될 수 있다
			// 1. %가 앞에 있으면 안됨 
			// 2. 제어 => 함수 
			// 입력값 받기 
			String dong=post.tf.getText();
			if(dong.trim().length()<1)
			{
				// 입력이 안된 상태 => 유효성 검사 : 자바스크립트
				post.tf.requestFocus();
				return;
			}
			// 입력이 된 경우 => 
			// MemberDAO연결해서 데이터값 가지고 오기
			int count=dao.postFindCount(dong);
			if(count==0) // 검색 결과가 없는 경우 
			{
				JOptionPane.showMessageDialog(this,
						"검색 결과가 없습니다");
				post.tf.setText("");
				post.tf.requestFocus();
			}
			else
			{
				List<ZipcodeVO> list=dao.postFind(dong);
				for(ZipcodeVO vo:list)
				{
					String[] data={
						vo.getZipcode(),
						vo.getAddress()
					};
					post.model.addRow(data);
				}
			}
			
		}
		else if(e.getSource()==post.b2)
		{
			post.setVisible(false);// hide / show 
		}
		else if(e.getSource()==idf.b1)
		{
			// 입력값 읽기
			String id=idf.tf.getText();
			if(id.trim().length()<1)
			{
				idf.tf.requestFocus();
				idf.la3.setText("아이디를 입력하세요");
				return;
			}
			// 데이터베이스 연동 
			int count=dao.memberIdCheck(id);
			if(count==0)
			{
				idf.la3.setText("사용가능한 아이디입니다");
				idf.b2.setVisible(true);
				
			}
			else
			{
				idf.la3.setText("이미 사용중인 아이디입니다");
				idf.tf.setText("");
				idf.tf.requestFocus();
			}
		}
		else if(e.getSource()==idf.b2)
		{
			String id=idf.tf.getText();
			idtf.setText(id);
			idf.setVisible(false);
		}
		// 회원가입 
		else if(e.getSource()==b3)
		{
			String id=idtf.getText();
			String pwd=String.valueOf(pf.getPassword());
			String name=nametf.getText();
			String sex="남자";
			if(rb1.isSelected())
				sex="남자";
			else
				sex="여자";
			String post=posttf.getText();
			String addr1=addrtf1.getText();
			String addr2=addrtf2.getText();
			String phone=teltf.getText();
			String content=cta.getText();
			
			MemberVO vo=new MemberVO();
			vo.setId(id);
			vo.setPwd(pwd);
			vo.setName(name);
			vo.setSex(sex);
			vo.setPost(post);
			vo.setAddr1(addr1);
			vo.setAddr2(addr2);
			vo.setPhone(phone);
			vo.setContent(content);
			
			// 데이터베이스 연동 
			int check=dao.memberJoin(vo);
			// => executeUpdate() 
			// => 변경 갯수 (1 => 추가 / 변경 / 삭제)
			// => 0이면 변경된 데이터가 없다 
			if(check>0) // 정상적으로 추가 
			{
				JOptionPane.showMessageDialog(this, 
						"🎉 회원 가입을 축하합니다 🎉");
				cp.card.show(cp, "HOME");
			}
			else // 실패 
			{
				JOptionPane.showMessageDialog(this, 
						"회원 가입에 실패하셨습니다\n다시 가입하세요");
			}
			
		}
		// 취소
		else if(e.getSource()==b4)
		{
			cp.card.show(cp, "HOME");
		}
			
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==post.table)
		{
			if(e.getClickCount()==2)
			{
				int row=post.table.getSelectedRow();
				// 값 첨부 
				String zip=
					post.model.getValueAt(row, 0).toString();
				String addr=
					post.model.getValueAt(row, 1).toString();
				
				posttf.setText(zip);
				addrtf1.setText(addr);
				
				post.setVisible(false);
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}