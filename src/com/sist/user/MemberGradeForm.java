package com.sist.user;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import com.sist.dao.MemberDAO;
import com.sist.vo.MemberVO;
public class MemberGradeForm extends JPanel{
    JTable table;
    DefaultTableModel model;
    MemberDAO dao=new MemberDAO();
    public MemberGradeForm()
    {
    	String[] col={"ID","이름","성별","주소","전화","등급"};
    	String[][] row=new String[0][6];
    	model=new DefaultTableModel(row,col) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return column==5;
			}
    		
    	};
    	table=new JTable(model);
    	JScrollPane js=new JScrollPane(table);
    	setLayout(new BorderLayout());
    	add("Center",js);
    	print();
    	
    	table.getModel().addTableModelListener(
    	  e->{
    		   int row1=e.getFirstRow();
    		   int col1=e.getColumn();
    		   if(row1 < 0 || col1 <0) return; 
    		   Object value=table.getValueAt(row1, col1);
    		   String id=model.getValueAt(row1, 0).toString();
    		   System.out.println("변경값:"+value+",ID:"+id);
    		   dao.gradeUpdate(id, (String)value);
    		   print();
    	  }
    	);
    	String[] grades={"BRONZE","SILVER","GOLD","DIAMOND"};
    	JComboBox<String> combo=
    			new JComboBox<String>(grades);
    	TableColumn tc=table.getColumnModel().getColumn(5);
    	tc.setCellEditor(new DefaultCellEditor(combo));
    	
    }
    public void print()
    {
    	for(int i=model.getRowCount()-1;i>=0;i--)
    	{
    		model.removeRow(i);
    	}
    	List<MemberVO> list=dao.memberListData();
    	for(MemberVO vo:list)
    	{
    		Object[] data= {
    			vo.getId(),
    			vo.getName(),
    			vo.getSex(),
    			vo.getAddr1(),
    			vo.getPhone(),
    			vo.getGrade()
    		};
    		model.addRow(data);
    	}
    	
    }
}
