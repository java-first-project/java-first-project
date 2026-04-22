package com.sist.client;
import java.util.*;
import java.awt.*;
import javax.swing.*;
public class ControllerPenal extends JPanel{
	CardLayout card=new CardLayout();
	UserMainForm mf;
	HomePanel hp=new HomePanel();
    public ControllerPenal(UserMainForm mf)
    {
    	this.mf=mf;
    	setLayout(card);
    	add("HOME",hp);

    }
}