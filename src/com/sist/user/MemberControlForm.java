package com.sist.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.*;
import java.util.Arrays;

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
implements MouseListener, ActionListener
{
	 JTable table;
	    DefaultTableModel model;
	    MemberDAO dao = new MemberDAO();
	    JButton b1,b2;
	    JButton btnAdd,btnSave;
	    JLabel la = new JLabel("0 page / 0 pages");
	    int curpage = 1;
	    int totalpage = 0;
    public MemberControlForm()
    {
    	String[] col={"ID","이름","성별","주소","전화","등급"};
    	String[][] row=new String[0][6];
    	model=new DefaultTableModel(row,col) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				
				 return true;
			}
    		
    	};
    	table=new JTable(model);
    	JScrollPane js=new JScrollPane(table);

    	
    	//등급 콤보박스
    	String[] grades = {"NORMAL", "GOLD", "VIP", "MASTER"};
    	JComboBox<String> gradeCombo = new JComboBox<>(grades);
    	table.getColumnModel()
    	     .getColumn(5) // 등급 컬럼
    	     .setCellEditor(new DefaultCellEditor(gradeCombo));
    
    	//등급 변경 이벤트
    	model.addTableModelListener(e -> {
            int row2 = e.getFirstRow();
            int col2 = e.getColumn();
            if(col2 == 5)
            {
                String id = model.getValueAt(row2, 0).toString();
                String grade = model.getValueAt(row2, 5).toString();
                dao.gradeUpdate(id, grade);
            }
        });
    	
    	//회원추가
    	btnAdd = new JButton("회원추가");
    	btnSave = new JButton("저장");
    	JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	top.add(btnAdd);
    	top.add(btnSave); 
    	btnAdd.addActionListener(this);
    	btnSave.addActionListener(this); 
    	
    	
    	
    	//페이징버튼
    	b1 = new JButton("이전");
        b2 = new JButton("다음");
        JPanel pp = new JPanel();
        pp.add(b1); pp.add(la); pp.add(b2);

        setLayout(new BorderLayout());
        add("North", top);
        add("Center", js);
        add("South", pp);

        table.addMouseListener(this);

        b1.addActionListener(this);
        b2.addActionListener(this);
        
    	print();

    }
    public void print()
    {
        for(int i = model.getRowCount()-1; i >= 0; i--)
        {
            model.removeRow(i);
        }
        List<MemberVO> list = dao.memberListData(curpage);
        totalpage = dao.memberTotalPage();
        for(MemberVO vo : list)
        {
            String[] data = {
                vo.getId(),
                vo.getName(),
                vo.getSex(),
                vo.getAddr1(),
                vo.getPhone(),
                vo.getGrade()
            };
            model.addRow(data);
        }
        la.setText(curpage + " page / " + totalpage + " pages");
    	
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == table)
        {
            if(e.getClickCount() == 2)
            {
                int row = table.getSelectedRow();
                String id = model.getValueAt(row, 0).toString();
                int a = JOptionPane.showConfirmDialog(this,
                    "삭제할까요?", "삭제", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                if(a == JOptionPane.YES_OPTION)
                {
                    dao.memberDelete(id);
                    print();
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
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == b1)
        {
            if(curpage > 1)
            {
                curpage--;
                print();
            }
        }
        else if(e.getSource() == b2)
        {
            if(curpage < totalpage)
            {
                curpage++;
                print();
            }
        }
        else if(e.getSource() == btnAdd)
        {
            String[] emptyRow = new String[model.getColumnCount()];
            Arrays.fill(emptyRow, "");
            model.addRow(emptyRow);
        }
		
        else if(e.getSource() == btnSave) // 저장
        {
            int row = model.getRowCount()-1; // 마지막 행
            MemberVO vo = new MemberVO();
            vo.setId(model.getValueAt(row, 0).toString());
            vo.setName(model.getValueAt(row, 1).toString());
            vo.setSex(model.getValueAt(row, 2).toString());
            vo.setAddr1(model.getValueAt(row, 3).toString());
            vo.setPhone(model.getValueAt(row, 4).toString());
            vo.setGrade(model.getValueAt(row, 5).toString());
            dao.memberInsert(vo);
            print();
        }
		
	}
}