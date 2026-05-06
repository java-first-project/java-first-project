package com.sist.user;

import java.awt.*;

import java.util.*;
import java.util.List;

import com.sist.dao.*;
import com.sist.vo.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class MemberFindForm  extends JPanel implements ActionListener {
    
    JTable table;
    DefaultTableModel model;
    TableColumn column;
    JComboBox box;
    JTextField tf;
    JButton b;
    MemberDAO dao = new MemberDAO();
    
    public MemberFindForm()
    {
        JLabel titleLa = new JLabel("회원 검색", JLabel.CENTER);
        titleLa.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        
        box = new JComboBox();
        box.addItem("ID");
        box.addItem("이름");
        box.addItem("전화");
        box.addItem("등급");
        
        tf = new JTextField(20);
        b = new JButton("검색");
        
        String[] col = {"ID", "이름", "성별", "주소", "전화", "등급"};
        String[][] row = new String[0][6];
        
        model = new DefaultTableModel(row, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        JScrollPane js = new JScrollPane(table);
        
        // 컬럼 너비
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // 이름
        table.getColumnModel().getColumn(2).setPreferredWidth(50);  // 성별
        table.getColumnModel().getColumn(3).setPreferredWidth(300); // 주소
        table.getColumnModel().getColumn(4).setPreferredWidth(120); // 전화
        table.getColumnModel().getColumn(5).setPreferredWidth(80);  // 등급
        
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowVerticalLines(false);
        table.setRowHeight(30);
        table.getTableHeader().setBackground(Color.pink);
        
        setLayout(null);
        titleLa.setBounds(10, 15, 920, 50);
        add(titleLa);
        
        JPanel p = new JPanel();
        p.add(box); p.add(tf); p.add(b);
        p.setBounds(10, 70, 400, 35);
        add(p);
        
        js.setBounds(10, 110, 900, 350);
        add(js);
        
        tf.addActionListener(this);
        b.addActionListener(this);
    }
    
    public void print(String col, String fd)
    {
        for(int i = model.getRowCount()-1; i >= 0; i--)
        {
            model.removeRow(i);
        }
        
        List<MemberVO> list = dao.memberFind(col, fd);
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
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b || e.getSource() == tf)
        {
            String fd = tf.getText();
            if(fd.trim().length() < 1)
            {
                tf.requestFocus();
                return;
            }
            
            String[] columns = {"id", "name", "phone", "grade"};
            int index = box.getSelectedIndex();
            print(columns[index], fd);
        }
    }
}