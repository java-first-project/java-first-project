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
	CardLayout card=new CardLayout();
	String myId;
    public ControlPanel()
    {
    	setBackground(Color.cyan);
    	setLayout(card);
    	hp=new HomePanel(this);
    	gdf=new GoodsDetailForm(this);
    	jp=new JoinPanel(this);
    	add("HOME",hp);
    	add("DETAIL",gdf);
    	add("JOIN",jp);
    }
}