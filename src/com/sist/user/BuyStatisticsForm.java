package com.sist.user;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import com.sist.dao.BuyDAO;           
import com.sist.vo.BuyStatisticsVO;

public class BuyStatisticsForm extends JPanel {
    private JTable statTable;
    private JLabel totalAmountLabel;
    
    private DefaultTableModel tableModel;
    
    public BuyStatisticsForm() {
        // 전체 화면 레이아웃 설정
        setLayout(new BorderLayout());
        
        // 1. 상단: 타이틀 및 전체 총 구매 금액 표시 영역
        JPanel topPanel = new JPanel();
        totalAmountLabel = new JLabel("전체 총 매출 금액: 0원"); // 나중에 데이터 넣을 곳
        totalAmountLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        
        topPanel.add(new JLabel("🏆 구매 통계 순위 🏆  |  "));
        topPanel.add(totalAmountLabel);
        add(topPanel, BorderLayout.NORTH);
        
        // 2. 중앙: 통계 데이터 표 영역
        String[] columnNames = {"순위", "회원ID", "총 구매건수", "총 구매금액"};
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 더블클릭해서 값 수정되는 것 방지
            }
        };
        
        statTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(statTable);
        add(scrollPane, BorderLayout.CENTER);
        
        loadData();
    }
    
    public void loadData() {
        // 1. 기존 테이블 데이터 초기화
        tableModel.setRowCount(0);
        
        // 2. 네 BuyDAO 객체 생성 후 메서드 호출! (이름 맞춤)
        BuyDAO dao = new BuyDAO();
        List<BuyStatisticsVO> list = dao.buyStatisticsData(); 
        
        long grandTotal = 0; // 전체 회원의 결제 금액 합산용
        int rank = 1;        // 순위
        
        // 3. 금액 천 단위 콤마 포맷
        DecimalFormat df = new DecimalFormat("###,###"); 
        
        // 4. DB에서 가져온 리스트를 테이블에 한 줄씩 추가
        for(BuyStatisticsVO vo : list) {
            Object[] rowData = {
                rank + "위",
                vo.getId(),
                vo.getBuy_count() + "회",
                df.format(vo.getTotal_pay()) + "원"
            };
            tableModel.addRow(rowData);
            
            // 전체 누적 금액 합산
            grandTotal += vo.getTotal_pay(); 
            rank++; 
        }
        
        // 5. 상단 라벨에 전체 누적 총 금액 세팅
        totalAmountLabel.setText("전체 누적 총 매출액: " + df.format(grandTotal) + "원");
    }
    
}