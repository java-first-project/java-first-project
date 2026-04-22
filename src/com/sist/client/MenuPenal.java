package com.sist.client;

import java.awt.*;
import javax.swing.*;

public class MenuPenal extends JPanel{
	JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
	
	public MenuPenal() {
		b1 = new JButton("홈");
		b2 = new JButton("회원목록");
		b3 = new JButton("채팅");
		b4 = new JButton("로그인");
		b5 = new JButton("게시판");
		b6 = new JButton("마이페이지");
		
		setLayout(new GridLayout(1, 8, 5, 5));
		
		add(b1); add(b2); add(b3); 
		add(b4); add(b5); add(b6);
	}
}
