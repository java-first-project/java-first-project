package com.sist.temp;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import com.sist.dao.MemberDAO;
import com.sist.vo.MemberVO;
public class MemberList extends JFrame{
    JTable table;
    DefaultTableModel model;
    public MemberList()
    {
    	String[] col={"id","이름","성별","주소","전화","등급"};
    	Object[][] row=new Object[0][6];
    	model=new DefaultTableModel(row,col) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return column==5;
			}

			
    		
    	};
    	table=new JTable(model);
    	JScrollPane js=
    			new JScrollPane(table);
    	add("Center",js);
    	
    	
    	table.getModel().addTableModelListener(e -> {

    	    int row1 = e.getFirstRow();
    	    int col1 = e.getColumn();

    	    if (row1 < 0 || col1 < 0) return; // 중요

    	    Object value = table.getValueAt(row1, col1);
    	    System.out.println("변경값: " + value);
    	    // DB 연동 
    	});
    	setSize(640,480);
    	setVisible(true);
    	print();
    	String[] grades = {"BRONZE", "SILVER", "GOLD", "DIAMOND"};
    	JComboBox<String> combo = new JComboBox<>(grades);

    	TableColumn tc=table.getColumnModel().getColumn(5);
    	tc.setCellEditor(new DefaultCellEditor(combo));
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void print()
    {
    	for(int i=model.getRowCount()-1;i>=0;i--)
    	{
    		model.removeRow(i);
    	}
    	
    	MemberDAO dao=new MemberDAO();
    	List<MemberVO> list=
    			dao.memberListData();
    	
    	for(MemberVO vo:list)
    	{
    		Object[] obj= {
    				vo.getId(),
    				vo.getName(),
    				vo.getSex(),
    				vo.getAddr1(),
    				vo.getPhone(),
    				vo.getGrade()
    		};
    		model.addRow(obj);
    	}
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
        {
        	UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        }catch(Exception ex) {}
		new MemberList();
	}

}
