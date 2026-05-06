package com.sist.user;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import com.sist.dao.GoodsDAO;
import com.sist.vo.GoodsVO;

public class GoodsControlForm extends JPanel implements MouseListener {
    
    JTable table;
    DefaultTableModel model;
    GoodsDAO dao = new GoodsDAO();
    
    public GoodsControlForm()
    {
        String[] col = {"NO", "상품명", "설명", "가격", "할인율", "정가", "배송비", "조회수"};
        String[][] row = new String[0][8];
        
        model = new DefaultTableModel(row, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        JScrollPane js = new JScrollPane(table);
        
        // 상단 등록 버튼
        JButton btnAdd = new JButton("상품 등록");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(btnAdd);
        
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(js, BorderLayout.CENTER);
        
        table.addMouseListener(this);
        
        btnAdd.addActionListener(e -> {
            //추후연결
        });
        
        print(); // 마지막에 한번만 호출
    }
    
    public void print()
    {
        // 기존 데이터 초기화
        for(int i = model.getRowCount()-1; i >= 0; i--)
        {
            model.removeRow(i);
        }
        
        List<GoodsVO> list = dao.goodsAllList();
        for(GoodsVO vo : list)
        {
            String[] data = {
                String.valueOf(vo.getNo()),
                vo.getGoods_name(),
                vo.getGoods_sub(),
                vo.getGoods_price(),
                vo.getGoods_discount() + "%",
                vo.getGoods_first_price(),
                vo.getGoods_delivery(),
                String.valueOf(vo.getHit())
            };
            model.addRow(data);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == table)
        {
        	if(e.getClickCount() == 2)
        	{
        	    int row = table.getSelectedRow();
        	    int no = Integer.parseInt(model.getValueAt(row, 0).toString());
        	    String name = model.getValueAt(row, 1).toString();

        	    int confirm = JOptionPane.showConfirmDialog(
        	        this,
        	        "[" + name + "] 을 삭제할까요?",
        	        "삭제",
        	        JOptionPane.YES_NO_OPTION,
        	        JOptionPane.WARNING_MESSAGE
        	    );
        	    if(confirm == JOptionPane.YES_OPTION)
        	    {
        	        dao.goodsDelete(no);
        	        print();
        	    }
        	}
        }
    }
    
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}