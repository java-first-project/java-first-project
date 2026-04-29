package com.sist.user;
import java.util.List;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
// 10,60, 920, 480
public class AdminPageForm extends JPanel{
    JTabbedPane tp=new JTabbedPane();
    ControlPanel cp;
    MemberControlForm mcf=new MemberControlForm();
    public AdminPageForm(ControlPanel cp)
    {
    	this.cp=cp;
    	// JPanel : FlowLayout 
    	setLayout(null);
    	tp.addTab("회원관리", mcf);
    	tp.addTab("회원검색", new JPanel());
    	tp.addTab("상품관리", new JPanel());
    	tp.addTab("구매관리", new JPanel());
    	tp.addTab("등급관리", new JPanel());
    	tp.setTabPlacement(tp.LEFT);
    	tp.setBounds(10,15,920,480);
    	add(tp);
    }
}
