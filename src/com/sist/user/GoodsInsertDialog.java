package com.sist.user;

import javax.swing.*;
import java.awt.*;
import com.sist.vo.GoodsVO;


//[관리자] 상품 관리 > 상품 등록 
//상품 정보를 입력받고 유효성 검사
public class GoodsInsertDialog extends JDialog{
	JTextField tfName = new JTextField(15);
    JTextField tfSub = new JTextField(15);
    JTextField tfPrice = new JTextField(15);
    JTextField tfDiscount = new JTextField(15);
    JTextField tfFirstPrice = new JTextField(15);
    JTextField tfDelivery = new JTextField(15);
    JTextField tfPoster = new JTextField(15);

    JButton btnSave = new JButton("등록");
    JButton btnCancel = new JButton("취소");

    // 팝업 경고 대신 폼 내부에 직접 띄울 에러 메시지 라벨
    JLabel lblError = new JLabel(" "); 

    boolean isRegistered = false;
    GoodsVO vo = new GoodsVO();

    public GoodsInsertDialog(JFrame parent) {
        super(parent, "상품 등록", true);
        setLayout(new BorderLayout());

        // 입력 필드
        JPanel centerPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        centerPanel.add(new JLabel(" 상품명:")); centerPanel.add(tfName);
        centerPanel.add(new JLabel(" 설명:")); centerPanel.add(tfSub);
        centerPanel.add(new JLabel(" 가격:")); centerPanel.add(tfPrice);
        centerPanel.add(new JLabel(" 할인율(%):")); centerPanel.add(tfDiscount);
        centerPanel.add(new JLabel(" 정가:")); centerPanel.add(tfFirstPrice);
        centerPanel.add(new JLabel(" 배송비:")); centerPanel.add(tfDelivery);
        centerPanel.add(new JLabel(" 포스터 URL:")); centerPanel.add(tfPoster);

        // 하단 버튼 및 에러 메시지 패널
        JPanel bottomPanel = new JPanel(new BorderLayout());
        lblError.setForeground(Color.RED);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        bottomPanel.add(lblError, BorderLayout.NORTH);
        bottomPanel.add(btnPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setSize(350, 320);
        setLocationRelativeTo(parent);

        // 등록 버튼 이벤트
        btnSave.addActionListener(e -> {
            // 유효성 검사
            if(tfName.getText().trim().isEmpty()) {
                lblError.setText("상품명을 입력해주세요.");
                return;
            }
            
            int discount = 0;
            try {
                String discountText = tfDiscount.getText().trim();
                if(!discountText.isEmpty()) {
                    discount = Integer.parseInt(discountText);
                }
            } catch(NumberFormatException ex) {
                lblError.setText("할인율은 숫자만 입력 가능합니다.");
                return;
            }

            // GoodsVO에 값 셋팅
            vo.setGoods_name(tfName.getText());
            vo.setGoods_sub(tfSub.getText());
            vo.setGoods_price(tfPrice.getText());
            vo.setGoods_discount(discount);
            vo.setGoods_first_price(tfFirstPrice.getText());
            vo.setGoods_delivery(tfDelivery.getText());
            vo.setGoods_poster(tfPoster.getText());

            isRegistered = true;
            dispose(); // 창 닫기
        });

        // 취소 버튼 이벤트
        btnCancel.addActionListener(e -> dispose());
    }

    public boolean isRegistered() { return isRegistered; }
    public GoodsVO getGoodsVO() { return vo; }
}
