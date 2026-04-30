package com.sist.user;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.*;
import com.sist.dao.BuyDAO;
import com.sist.vo.BuyVO;

public class BuyControlForm extends JPanel {
    JTable table;
    DefaultTableModel model;
    
    public BuyControlForm() {
        String[] col = {"주문번호","회원ID","상품명","수량","금액","주문일","상태"};
        String[][] row = new String[0][7];
        model = new DefaultTableModel(row, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane js = new JScrollPane(table);
        setLayout(new BorderLayout());
        add("Center", js);
        print();
    }
    
    public void print() {
        model.setRowCount(0);
        BuyDAO dao = new BuyDAO();
        List<BuyVO> list = dao.buyAllData();
        for(BuyVO vo : list) {
            model.addRow(new Object[]{
                vo.getNo(),
                vo.getId(),
                vo.getGno(),
                vo.getAccount(),
                vo.getPrice(),
                vo.getDbday()
            });
        }
    }
}