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
    BuyDAO dao = new BuyDAO();
    
    public BuyControlForm() {
        String[] col = {"주문번호","회원ID","상품명","수량","금액","주문일","상태"};
        String[][] row = new String[0][7];
        model = new DefaultTableModel(row, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
            	return column == 6;
            }
        };
        table = new JTable(model);
        JScrollPane js = new JScrollPane(table);
        setLayout(new BorderLayout());
        add("Center", js);
        print();
        
     // 상태 콤보박스
        String[] status = {"결제완료", "배송준비", "배송중", "완료", "취소"};
        JComboBox<String> statusCombo = new JComboBox<>(status);
        table.getColumnModel()
             .getColumn(6)
             .setCellEditor(new DefaultCellEditor(statusCombo));

        // 상태 변경 이벤트
        model.addTableModelListener(e -> {
            int row2 = e.getFirstRow();
            int col2 = e.getColumn();
            if(col2 == 6)
            {
                int no = Integer.parseInt(model.getValueAt(row2, 0).toString());
                String st = model.getValueAt(row2, 6).toString();
                 dao.buyStatusUpdate(no, st);
                System.out.println("주문 상태 변경: " + no + " → " + st);
            }
        });
    }
    
    public void print() {
        model.setRowCount(0);
        List<BuyVO> list = dao.buyAllData();
        for(BuyVO vo : list) {
            model.addRow(new Object[]{
                vo.getNo(),
                vo.getId(),
                vo.getGno(),
                vo.getAccount(),
                vo.getPrice(),
                vo.getDbday(),
                vo.getStatus()
            });
        }
    }
}