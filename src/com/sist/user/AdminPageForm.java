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
    BuyControlForm bcf=new BuyControlForm(); 
    
    GoodsControlForm gcf;
    
    MemberFindForm mff = new MemberFindForm();
    BuyStatisticsForm bsf = new BuyStatisticsForm();
    
    public AdminPageForm(ControlPanel cp)
    {
    	this.cp=cp;
    	// JPanel : FlowLayout 
    	gcf = new GoodsControlForm(cp);
    	setLayout(null);
    	tp.addTab("회원관리", mcf);
    	tp.addTab("회원검색", mff);
    	tp.addTab("상품관리", gcf);
    	tp.addTab("구매관리", bcf);
    	tp.addTab("구매통계", bsf);
    	tp.setTabPlacement(tp.LEFT);
    	tp.setBounds(10,15,920,480);
    	add(tp);
    }
}
