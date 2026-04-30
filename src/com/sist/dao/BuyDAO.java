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
            String sql = "SELECT no, id, gno, account, price,regdate as dbday "
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
}