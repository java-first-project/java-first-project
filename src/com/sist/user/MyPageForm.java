package com.sist.user;
import java.util.List;

import com.sist.commons.ImageChange;
import com.sist.dao.*;
import com.sist.vo.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URL;

import javax.swing.*;
import javax.swing.table.*;
public class MyPageForm extends JPanel implements ActionListener,MouseListener{
   JTable table;
   DefaultTableModel model;
   JButton btn;
   ControlPanel cp;
   GoodsDAO dao=new GoodsDAO();
   public MyPageForm(ControlPanel cp)
   {
	   this.cp=cp;
	   btn=new JButton("목록");
	   String[] col= {"번호","","상품명","수량","가격","구매일"};
	   Object[][] row=new Object[0][6];
	   // 익명의 클래스 => 상속없이 오버라이딩시 사용
	   /*
	    *   클래스
	    *     = 인터페이스 : 의존성(결합성)을 낮게 만든다
	    *       ------- Spring
	    *       => POJO : 일반 클래스 => 독립적으로 사용
	    *     = 내부 클래스
	    *       --------
	    *       멤버클래스 => 쓰레드 , 네트워크 => 다른 클래스에서 변수/메소드 공유
	    *       익명의 클래스 => 윈도우 => 웹 사용빈도가 없다 
	    * 
	    */
	   model=new DefaultTableModel(row,col) {

		@Override
		public boolean isCellEditable(int row, int column) {
			// TODO Auto-generated method stub
			return false;
		}
        // 테이블에 이미지 출력
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// TODO Auto-generated method stub
			if(getRowCount()>0)
			{
				Object obj=getValueAt(0, columnIndex);
				if(obj!=null)
				{
					return obj.getClass();
				}
			}
			return Object.class;
		}
		   
	   };
	   // table => 외부 관리 / 데이터 관리 => model
	   // MVC => Spring / Spring-Boot
	   // => Redux , Pinia , Vuex (MVVM)
	   table=new JTable(model);
	   table.getTableHeader().setReorderingAllowed(false);
	   table.setRowHeight(35);
	   JScrollPane js=new JScrollPane(table);
	   setLayout(null);
	   btn.setBounds(50,15,100,30);
	   js.setBounds(50,50,800,450);
	   add(btn);
	   add(js);
	   
	   btn.addActionListener(this);
	   table.addMouseListener(this);
   }
   public void print()
   {
	   // 테이블 지우기
	   for(int i=model.getRowCount()-1;i>=0;i--)
	   {
		   model.removeRow(i);		   
	   }
	   // 데이터 추가
	   List<BuyVO> list=dao.buyListData(cp.myId);
	   for(BuyVO vo:list)
	   {
		   try
		   {
			   URI uri=new URI(vo.getGvo().getGoods_poster());
			   URL url=uri.toURL();
			   // 버전이 변경 => 경고
			   Image image=
					   ImageChange.getImage(new ImageIcon(url),30,30);
			   Object[] data= {
				  vo.getNo(),
				  new ImageIcon(image),
				  vo.getGvo().getGoods_name(),
				  vo.getAccount(),
				  vo.getPrice(),
				  vo.getDbday()
				  
			   };
			   model.addRow(data);
		   }catch(Exception ex) {}
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
			String no=model.getValueAt(row,0).toString();
			//JOptionPane.showMessageDialog(this,"선택된 번호:"+no);
			int a=JOptionPane.showConfirmDialog(this,"취소할까요?","취소",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(a==JOptionPane.YES_NO_OPTION)
			{
				// 데이터 연동 
				dao.buyDelete(Integer.parseInt(no));
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
	if(e.getSource()==btn)
	{
		cp.card.show(cp,"HOME");
	}
   }
}
