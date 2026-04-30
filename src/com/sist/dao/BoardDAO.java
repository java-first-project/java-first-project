package com.sist.dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;
import com.sist.vo.*;

public class BoardDAO {
	// 전체적으로 사용
	private Connection conn; // Socket => 연결 담당
	private PreparedStatement ps; // BufferedReader , OutputStream

	private String url;
	private String user;
	private String pwd;

	private static BoardDAO dao;


	// 1. 드라이버 등록
	public BoardDAO() {
		try {
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream("db.properties");
			prop.load(fis);
			fis.close(); // 파일 읽기 후 닫기

			// B. 로드된 파일에서 정보 추출
			this.url = prop.getProperty("db.url");
			this.user = prop.getProperty("db.user");
			this.pwd = prop.getProperty("db.password");

			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 2. 오라클 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception ex) {
		}
	}

	// 3. 오라클 연결 해제
	public void disConnection() {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
		}
	}

	public List<BoardVO> board_list(int page) {
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			getConnection();
			// SQL문장을 오라클 전송
			String sql = "SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit "
					+ "FROM board " + "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";
			int start = (page * 10) - 10;
			// 전송
			ps = conn.prepareStatement(sql);
			// ? 값을 채운다
			ps.setInt(1, start);
			// 실행 => 결과값
			ResultSet rs = ps.executeQuery();
			while (rs.next()) // 출력된 첫번째 위치로 커서 이동
			{
				BoardVO vo = new BoardVO();
				vo.setNo(rs.getInt(1));
				// vo.setNo(rs.getInt("no"))
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}

	// 총페이지 ==> CEIL
	public int boardTotalPage() {
		int total = 0;
		try {
			// 1. 연결
			getConnection();
			// 2. SQL문장을 만든다
			String sql = "SELECT CEIL(COUNT(*)/10.0) as total FROM board";
			// 3. 오라클로 전송
			ps = conn.prepareStatement(sql);
			// 4. ?가 있는 경우 => 값을 채운다
			ResultSet rs = ps.executeQuery();
			// 5. 출력된 메모리 위치 커서를 이동
			rs.next();
			// 6. 해당 데이터형을 이용해서 데이터를 가지고 온다
			// NUMBER , VRACHAR2 , CLOB , DATE
			// getDate()
			// --------------- getString()
			// | => 정수 (getInt()) , 실수 (getDouble())
			total = rs.getInt("total");
			// -------- 컬럼 인덱스번호 , 컬럼명
			// MyBatis는 컬럼명으로 읽는다 (함수=>반드시 별칭)
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		return total;
	}

	// 데이터 추가
	public void board_insert(BoardVO vo) {
		try {
			// 1. 연결
			getConnection();
			// 2. SQL문장 만들기
			String sql = "INSERT INTO board VALUES(" + "board_seq.nextval,?,?,?,?," + "SYSDATE,0)";
			// 3. 전송
			ps = conn.prepareStatement(sql);
			// 4. 실행전에 ?에 값을 채운다
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.executeUpdate(); // 데이터베이스 변경
			// => COMMIT 포함 => 자바 AutoCommit()
			// INSERT / UPDATE / DELETE
			// SELECT => executeQuery()
			/*
			 * INSERT INSERT ==>error => 트랜잭션 INSERT
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
	}

	// 상세보기
	public BoardVO board_detail(int no) {
		BoardVO vo = new BoardVO();
		try {
			// 1. 연결
			getConnection();
			// 2. SQL문장
			String sql = "UPDATE board SET " + "hit=hit+1 " + "WHERE no=?";
			// 조회수 증가
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate(); // commit 수행

			// 실제 데이터 읽기
			sql = "SELECT no,name,subject,content,hit," + "TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS') " + "FROM board "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			// 값 채우기
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setHit(rs.getInt(5));
			vo.setDbday(rs.getString(6));
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}

	// 수정
	// 삭제 delete.jsp?no=1
	public boolean board_delete(int no, String pwd) {
		boolean bCheck = false; // => 비번
		try {
			// 연결
			getConnection();
			// SQL 전송
			String sql = "SELECT pwd FROM board " + "WHERE no=?";
			ps = conn.prepareStatement(sql);
			// ?을 값을 채운다
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();

			if (db_pwd.equals(pwd)) // 본인
			{
				bCheck = true;
				sql = "DELETE FROM board " + "WHERE no=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		return bCheck;
	}
	
	public boolean board_edit_check(int no, String pwd) {
		boolean bCheck = false; // => 비번
		try {
			// 연결
			getConnection();
			// SQL 전송
			String sql = "SELECT pwd FROM board WHERE no=?";
			ps = conn.prepareStatement(sql);
			// ?을 값을 채운다
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();

			if (db_pwd.equals(pwd)) // 본인
			{
				bCheck = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		return bCheck;
	}

	public void board_edit(BoardVO vo) {
		try {
			// 1. 연결
			getConnection();
			// 2. SQL문장 만들기
			String sql = "UPDATE board SET name = ?, subject = ?, content = ?, pwd = ? WHERE no = ?";
			// 3. 전송
			ps = conn.prepareStatement(sql);
			// 4. 실행전에 ?에 값을 채운다
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.setInt(5, vo.getNo());
			ps.executeUpdate(); // 데이터베이스 변경
			// => COMMIT 포함 => 자바 AutoCommit()
			// INSERT / UPDATE / DELETE
			// SELECT => executeQuery()
			/*
			 * INSERT INSERT ==>error => 트랜잭션 INSERT
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
	}

	public static BoardDAO newInstance() {
		if (dao == null)
			dao = new BoardDAO();
		return dao;
	}
}