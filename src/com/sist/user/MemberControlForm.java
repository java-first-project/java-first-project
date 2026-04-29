package com.sist.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.*;
/*
 *   **ID      NOT NULL VARCHAR2(20)  
	PWD     NOT NULL VARCHAR2(10)  
	**NAME    NOT NULL VARCHAR2(51)  
	**SEX              VARCHAR2(6)   
	POST    NOT NULL VARCHAR2(7)   
	**ADDR1   NOT NULL VARCHAR2(200) 
	ADDR2            VARCHAR2(200) 
	**PHONE            VARCHAR2(14)  
	CONTENT          CLOB          
	ISADMIN          CHAR(1)       
	**REGDATE          DATE  
 */
public class MemberControlForm extends JPanel{
    JTable table;
    DefaultTableModel model;
    public MemberControlForm()
    {
    	String[] col={"ID","이름","성별","주소","전화","등록일"};
    	String[][] row=new String[0][6];
    	model=new DefaultTableModel(row,col) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
    		
    	};
    	table=new JTable(model);
    	JScrollPane js=new JScrollPane(table);
    	setLayout(new BorderLayout());
    	add("Center",js);
    }
}
