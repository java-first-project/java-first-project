package com.sist.dao;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;
import com.sist.vo.*;

public class BuyDAO {
    private Connection conn;
    private PreparedStatement ps;
    private String url, user, pwd;

    public BuyDAO() {
        try {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("db.properties");
            prop.load(fis);
            fis.close();
            this.url = prop.getProperty("db.url");
            this.user = prop.getProperty("db.user");
            this.pwd = prop.getProperty("db.password");
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch(Exception ex) { ex.printStackTrace(); }
    }

    public void getConnection() {
        try { conn = DriverManager.getConnection(url, user, pwd); }
        catch(Exception ex) {}
    }

    public void disConnection() {
        try {
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        } catch(Exception ex) {}
    }

    // 전체 구매 목록
    public List<BuyVO> buyAllData() {
        List<BuyVO> list = new ArrayList<BuyVO>();
        try {
            getConnection();
            String sql = "SELECT no, id, gno, account, price,regdate as dbday, status "
                       + "FROM buy "
                       + "ORDER BY no DESC";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                BuyVO vo = new BuyVO();
                vo.setNo(rs.getInt(1));
                vo.setId(rs.getString(2));
                vo.setGno(rs.getInt(3));
                vo.setAccount(rs.getInt(4));
                vo.setPrice(rs.getInt(5));
                vo.setDbday(rs.getString(6));
                vo.setStatus(rs.getString(7));
                list.add(vo);
            }
            rs.close();
        } catch(Exception ex) { ex.printStackTrace(); }
        finally { disConnection(); }
        return list;
    }
    
    // 특정 회원 구매 목록
    public List<BuyVO> buyListById(String id) {
        List<BuyVO> list = new ArrayList<BuyVO>();
        try {
            getConnection();
            String sql = "SELECT no, id, gno, account, price, dbday "
                       + "FROM buy "
                       + "WHERE id=? "
                       + "ORDER BY no DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                BuyVO vo = new BuyVO();
                vo.setNo(rs.getInt(1));
                vo.setId(rs.getString(2));
                vo.setGno(rs.getInt(3));
                vo.setAccount(rs.getInt(4));
                vo.setPrice(rs.getInt(5));
                vo.setDbday(rs.getString(6));
                list.add(vo);
            }
            rs.close();
        } catch(Exception ex) { ex.printStackTrace(); }
        finally { disConnection(); }
        return list;
    }
    
    //구매 상태 업데이트
    public void buyStatusUpdate(int no, String status)
    {
        try
        {
            getConnection();
            String sql = "UPDATE buy "
                       + "SET status=? "
                       + "WHERE no=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, no);
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
    
    //구매 통계
    public List<BuyStatisticsVO> buyStatisticsData() {
        List<BuyStatisticsVO> list = new ArrayList<>();
        try {
            getConnection();
            // ★ 중요: 실제 DB의 구매 테이블명(buy)과 컬럼명(id, price)으로 변경해야 해!
            String sql = "SELECT id, COUNT(*) as buy_count, SUM(price) as total_pay "
                       + "FROM buy "
                       + "GROUP BY id "
                       + "ORDER BY total_pay DESC"; // 구매 횟수가 높은 순서대로 내림차순 정렬
                       
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                BuyStatisticsVO vo = new BuyStatisticsVO();
                vo.setId(rs.getString(1));
                vo.setBuy_count(rs.getInt(2));
                vo.setTotal_pay(rs.getInt(3));
                list.add(vo);
            }
            rs.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            disConnection();
        }
        return list;
    }
    
    //사용자 구매 조회
    public List<BuyVO> buyUserData(String userId) {
    	List<BuyVO> list = new ArrayList<>();
        try {
            getConnection();
            String sql = "SELECT no, goods_name, price, regdate "
                       + "FROM buy "
                       + "WHERE id = ? "
                       + "ORDER BY no DESC";
                       
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                BuyVO vo = new BuyVO();
                vo.setNo(rs.getInt(1)); // 번호
                
                vo.getGvo().setGoods_name(rs.getString(2)); 
                
                vo.setPrice(rs.getInt(3)); // 가격
                vo.setRegdate(rs.getDate(4)); // 날짜
                
                list.add(vo);
            }
            rs.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            disConnection();
        }
        return list;
    }
    
    // 구매 정보 삽입
    public void goodsBuyData(BuyVO vo) {
        try {
            getConnection();
            String sql = "INSERT INTO buy (NO, ID, TYPE, GNO, ACCOUNT, PRICE, REGDATE, STATUS) "
                    + "VALUES (buy_no_seq.nextval, ?, ?, ?, ?, ?, SYSDATE, '결제완료')";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, vo.getId());
            ps.setInt(2, vo.getType());
            ps.setInt(3, vo.getGno());
            ps.setInt(4, vo.getAccount());
            ps.setInt(5, vo.getPrice());
            
            ps.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            disConnection();
        }
    }
}