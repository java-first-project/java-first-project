package com.sist.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.*;

import com.sist.dao.MemberDAO;
import com.sist.vo.MemberVO;
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
public class MemberControlForm extends JPanel
implements MouseListener
{
    JTable table;
    DefaultTableModel model;
    MemberDAO dao=new MemberDAO();
    public MemberControlForm()
    {
    	String[] col={"ID","이름","성별","주소","전화","등급"};
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
    	print();
    	add("Center",js);
    	table.addMouseListener(this);
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
    		String[] data= {
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
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table)
		{
			if(e.getClickCount()==2)
			{
				int row=table.getSelectedRow();
				String id=model.getValueAt(row, 0).toString();
				int a=JOptionPane.showConfirmDialog(this, 
					"삭제할까요?","삭제",JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
				if(a==JOptionPane.YES_OPTION)
				{
					//DB연동 
				}
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