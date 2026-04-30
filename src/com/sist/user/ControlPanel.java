package com.sist.user;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

// 화면 변경 
public class ControlPanel extends JPanel{
	
	// 1. HOME 
	HomePanel hp;
	GoodsDetailForm gdf;
	JoinPanel jp;
	AdminPageForm af;
	MyPageForm mf;
	BoardList bList;
	BoardInsert bInsert;
	BoardDetail bDetail;
	BoardDelete bDelete;
	BoardEdit bEdit;
	CardLayout card=new CardLayout();
	String myId;
    public ControlPanel()
    {
    	setBackground(Color.cyan);
    	setLayout(card);
    	hp=new HomePanel(this);
    	gdf=new GoodsDetailForm(this);
    	jp=new JoinPanel(this);
    	af=new AdminPageForm(this);
    	mf=new MyPageForm(this);
    	bList=new BoardList(this);
    	bInsert=new BoardInsert(this);
    	bDetail=new BoardDetail(this);
    	bDelete=new BoardDelete(this);
    	bEdit=new BoardEdit(this);
    	add("HOME",hp);
    	add("DETAIL",gdf);
    	add("JOIN",jp);
    	add("ADMIN",af);
    	add("MYPAGE",mf);
    	add("BLIST",bList);
    	add("BINSERT",bInsert);
    	add("BDETAIL",bDetail);
    	add("BDELETE",bDelete);
    	add("BEDIT", bEdit);
    }
}