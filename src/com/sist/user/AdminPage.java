package com.sist.user;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AdminPage extends JFrame {

    JTabbedPane tab = new JTabbedPane();

    // 회원
    JTable memberTable;
    DefaultTableModel memberModel;

    // 주문
    JTable orderTable;
    DefaultTableModel orderModel;

    public AdminPage() {

        setTitle("관리자 페이지");

        tab.add("회원관리", memberPanel());
        tab.add("주문관리", orderPanel());

        add(tab);

        setSize(900, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // =========================
    // 회원 관리 패널
    // =========================
    public JPanel memberPanel() {

        JPanel p = new JPanel(new BorderLayout());

        memberModel = new DefaultTableModel(
                new String[]{"ID", "이름", "등급", "총구매", "상태"}, 0
        );

        memberTable = new JTable(memberModel);

        // 더미 데이터
        memberModel.addRow(new Object[]{"hong", "홍길동", "BRONZE", 50000, "정상"});
        memberModel.addRow(new Object[]{"kim", "김철수", "GOLD", 300000, "정상"});

        // 등급 콤보박스
        String[] grades = {"BRONZE", "SILVER", "GOLD", "DIAMOND"};
        JComboBox<String> gradeCombo = new JComboBox<>(grades);

        memberTable.getColumnModel()
                .getColumn(2)
                .setCellEditor(new DefaultCellEditor(gradeCombo));

        // 상태 변경 이벤트
        memberModel.addTableModelListener(e -> {

            int row = e.getFirstRow();
            int col = e.getColumn();

            if (col == 2) {
                String id = memberTable.getValueAt(row, 0).toString();
                String grade = memberTable.getValueAt(row, 2).toString();

                System.out.println("등급 변경: " + id + " → " + grade);
            }
        });

        // 클릭 이벤트 (탈퇴 처리)
        memberTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                int row = memberTable.rowAtPoint(e.getPoint());
                int col = memberTable.columnAtPoint(e.getPoint());

                if (col == 4) {
                    String id = memberTable.getValueAt(row, 0).toString();
                    System.out.println("회원 삭제: " + id);
                }
            }
        });

        p.add(new JScrollPane(memberTable), BorderLayout.CENTER);
        return p;
    }

    // =========================
    // 주문 관리 패널
    // =========================
    public JPanel orderPanel() {

        JPanel p = new JPanel(new BorderLayout());

        orderModel = new DefaultTableModel(
                new String[]{"주문ID", "회원ID", "상품", "수량", "금액", "상태"}, 0
        );

        orderTable = new JTable(orderModel);

        // 더미 주문 데이터
        orderModel.addRow(new Object[]{"O001", "hong", "사과", 2, 5000, "결제완료"});
        orderModel.addRow(new Object[]{"O002", "kim", "바나나", 1, 3000, "배송중"});

        // 상태 콤보박스
        String[] status = {"결제완료", "배송준비", "배송중", "완료", "취소"};
        JComboBox<String> statusCombo = new JComboBox<>(status);

        orderTable.getColumnModel()
                .getColumn(5)
                .setCellEditor(new DefaultCellEditor(statusCombo));

        // 상태 변경 이벤트
        orderModel.addTableModelListener(e -> {

            int row = e.getFirstRow();
            int col = e.getColumn();

            if (col == 5) {
                String orderId = orderTable.getValueAt(row, 0).toString();
                String st = orderTable.getValueAt(row, 5).toString();

                System.out.println("주문 상태 변경: " + orderId + " → " + st);
            }
        });

        p.add(new JScrollPane(orderTable), BorderLayout.CENTER);
        return p;
    }

    // =========================
    public static void main(String[] args) {
    	try
        {
        	UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        }catch(Exception ex) {}
        new AdminPage();
    }
}
