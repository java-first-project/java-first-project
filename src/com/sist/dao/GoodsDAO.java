package com.sist.dao;
import java.sql.*;
import java.util.*;

import com.sist.vo.BuyVO;
import com.sist.vo.GoodsVO;
public class GoodsDAO {
  // 전체적으로 사용 
  private Connection conn; // Socket => 연결 담당 
  private PreparedStatement ps; // BufferedReader , OutputStream 
  // 송(SQL문장) 수신(오라클에서 결과값 받기)
  private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
  private String[] tables={
		  "",
		  "goods_all",
		  "goods_best",
		  "goods_new",
		  "goods_special"
  };
  // 1. 드라이버 등록 
  public GoodsDAO()
  {
	  try
	  {
		  Class.forName("oracle.jdbc.driver.OracleDriver");
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
  }
  // 2. 오라클 연결 
  public void getConnection()
  {
	  try
	  {
		  conn=DriverManager.getConnection(URL,"hr",
				         "happy");
	  }catch(Exception ex) {}
  }
  // 3. 오라클 연결 해제 
  public void disConnection()
  {
	  try
	  {
		  if(ps!=null) ps.close();
		  if(conn!=null) conn.close();
	  }catch(Exception ex) {}
  }
  // 기능 설정 => 회원 가입 / 회원 탈퇴 / 로그인 
  // 상품 구매를 할 수 있다 
  // 구매 테이블을 만든다 
  // 관리 => 상품 구매가 많은 회원 => 등급 지정 => 관리자 
  // 구매 현황 , 회원 관리 
  // 1. 상품 목록 
  public List<GoodsVO> goodsListData(int type,int page)
  {
	  List<GoodsVO> list=
			     new ArrayList<GoodsVO>();
	  try
	  {
		  // 1. 연결
		  getConnection();
		  // 2. SQL문장 
		  String sql="SELECT no,goods_poster,goods_name,goods_price "
				    +"FROM "+tables[type] 
				    +" ORDER BY no ASC "
				    +"OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY";
		            // 12c => 페이지 나누기 => 인라인뷰 
		  // 3. 전송 
		  ps=conn.prepareStatement(sql);
		  // 4. ?에 값을 채운다 => ?가 없는 경우 통과 
		  ps.setInt(1, (page*12)-12); // 0번부터 시작 
		  // 결과값 받기 
		  ResultSet rs=ps.executeQuery();
		  // 실행된 결과를 메모리에 저장 => ResultSet 
		  // next() => 처음부터 => 마지막까지 
		  // previous() => 마지막 => 처음 
		  /*
		   *   데이터 출력 
		   *   getInt() / getString() / getDouble()
		   *   getDate() 
		   */
		  // no,poster,goods_name,goods_price
		  while(rs.next())
		  {
			  GoodsVO vo=new GoodsVO();
			  vo.setNo(rs.getInt(1));
			  vo.setGoods_poster(rs.getString(2));
			  vo.setGoods_name(rs.getString(3));
			  vo.setGoods_price(rs.getString(4));
			  list.add(vo);
		  }
		  rs.close(); // 실행시마다 => 데이터 저장이 다르다 => 지역변수 
		  
	  }catch(Exception ex)
	  {
		  // 오류 출력 
		  ex.printStackTrace();
	  }
	  finally
	  {
		  // 오라클 닫기 => 오류가 있던 없던 상관없이 
		  disConnection();
	  }
	  return list;
  }
  // 총페이지 
  public int goodsTotalPage(int type)
  {
	  int total=0;
	  try
	  {
		  getConnection();
		  String sql="SELECT CEIL(COUNT(*)/12.0) "
				    +"FROM "+tables[type];
		  ps=conn.prepareStatement(sql);
		  ResultSet rs=ps.executeQuery();
		  rs.next();
		  total=rs.getInt(1);
		  rs.close();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
	  finally
	  {
		  disConnection();
	  }
	  return total;
  }
  /*
   *    1. List => 조건없는 경우 / 페이지 나누기 
   *    2. VO => SELECT 컬럼,컬럼....
   *    3. 컬럼이 한개 => 해당 데이터형 
   *    -----------------------------------
   *    => pwd : 로그인 String
   */
  /*
   *   NO                NOT NULL NUMBER         
	GOODS_NAME        NOT NULL VARCHAR2(1000) 
	GOODS_SUB                  VARCHAR2(1000) 
	GOODS_PRICE       NOT NULL VARCHAR2(50)   
	GOODS_DISCOUNT             NUMBER         
	GOODS_FIRST_PRICE          VARCHAR2(20)   
	GOODS_DELIVERY    NOT NULL VARCHAR2(20)   
	GOODS_POSTER               VARCHAR2(260)  
	HIT                        NUMBER 
	1. 목록 (row가 여러개)
	          | VO => 여러개 저장 (List)
	2. 상세보기 => row (1개) 
	             --------- VO한개 
	SELECT no FROM goods=> int List<Integer> 
	       컬럼이 두개이상 => VO 
	
   */
  
  public GoodsVO goodsDetailData(int type,int gno)
  {
	  GoodsVO vo=new GoodsVO();
	  try
	  {
		  getConnection();
		  String sql="SELECT no,goods_name,goods_poster,goods_sub,"
				    +"goods_delivery,goods_discount,goods_price "
				    +"FROM "+tables[type]
				    +" WHERE no=?";
		  ps=conn.prepareStatement(sql);
		  ps.setInt(1, gno);
		  ResultSet rs=ps.executeQuery();
		  // no => 중복이 없다 => ROW한개 출력 
		  rs.next();
		  vo.setNo(rs.getInt(1));
		  vo.setGoods_name(rs.getString(2));
		  vo.setGoods_poster(rs.getString(3));
		  vo.setGoods_sub(rs.getString(4));
		  vo.setGoods_delivery(rs.getString(5));
		  vo.setGoods_discount(rs.getInt(6));
		  vo.setGoods_price(rs.getString(7));
		  rs.close();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
	  finally
	  {
		  disConnection();
	  }
	  return vo;
  }
  // 구매
  /*
   * 
   */
  public void goodsBuyData(BuyVO vo)
  {
	  try
	  {
	     getConnection();
	     String sql="INSERT INTO buy VALUES("
	    		   +"buy_no_seq.nextval,?,?,?,?,?,SYSDATE)";
	     ps=conn.prepareStatement(sql);
	     ps.setString(1, vo.getId());
	     ps.setInt(2,vo.getType());
	     ps.setInt(3,vo.getGno());
	     ps.setInt(4,vo.getAccount());
	     ps.setInt(5,vo.getPrice());
	     ps.executeUpdate();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
	  finally
	  {
		  disConnection();
	  }
  }
  // 조인
  /*
   *  고객  <구매>  도서
   *        | id , 도서번호
   *    =INNER (EQUI_JOIN) => INTERSECT (교집합)
   *    =OUTER => LEFT/RIGHT => INTERSECT + MINUS
   */
  public List<BuyVO> buyListData(String id)
  {
	  List<BuyVO> list=new ArrayList<BuyVO>();
	  try
	  {
		  getConnection();
		  String sql="SELECT b.no,goods_poster,goods_name,account,"
		  		+ "TO_CHAR(regdate,'YYYY-MM-DD'),price "
		  		+ "FROM buy  b JOIN goods_all g "
		  		+ "ON b.gno=g.no "
		  		+ "AND b.id=?"
		  		+ "ORDER BY regdate DESC";
		  // 본인만
		  ps=conn.prepareStatement(sql);
		  // ?에 값을 채운다
		  ps.setString(1,id);
		  // 실행
		  ResultSet rs=ps.executeQuery();
		  // 조인시 처리하는 방법
		  while(rs.next())
		  {
			  BuyVO vo=new BuyVO();
			  vo.setNo(rs.getInt(1));
			  vo.getGvo().setGoods_poster(rs.getString(2));
			  vo.getGvo().setGoods_name(rs.getString(3));
			  vo.setAccount(rs.getInt(4));
			  vo.setDbday(rs.getString(5));
			  vo.setPrice(rs.getInt(6));
			  list.add(vo);
			  
			  
		  }
		  rs.close();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
	  finally
	  {
		  disConnection();
	  }
	  return list;
  }
  public void buyDelete(int no)
  {
	  try
	  {
		  getConnection();
		  String sql="DELETE FROM buy "
				    +"WHERE no="+no;
		  ps=conn.prepareStatement(sql);
		  ps.executeUpdate();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
	  finally
	  {
		  disConnection();
	  }
  }
}