package ch16JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardListPageDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//BoardVO 객체 작성
		//필요한 객체 선언을 해둠 -> 7.닫기가 가능해짐 
		//연결 객체
		Connection con = null;
		//실행 객체 
		PreparedStatement pstmt = null;
		//저장 객체
		ResultSet rs = null;
		
		//리턴 돼서 나중에 넘겨줄 데이터 1.list-list 2.view-BoardVO 3.insert,update,delete -int 
		List<BoardVO>list = null;
		
		//DB 접속 정보 
		//드라이버 - 오라클회사에서 보는 크기 -> 큰 > 작은
		String driver ="oracle.jdbc.driver.OracleDriver";
		//서버 - 자바에서 보는 크기 -> 큰-> 작은
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		//아이디
		String id = "java00";
		//비밀번호 
		String pw = "java00";
		
		//페이지 처리를 위한 정보
		int startRow =1;
		int endRow =10;
		
		//1~7 사이의 처리 중 한 가지라도 오류가 발생하면 7번 아래로 탈출 해야함 -> try{1~6}catch{}finally{7}
		try {
			//try(자원){//정상처리 -> try catch (){}finally{7}}
			//1.오라클 드라이버(오라클과 연결해주는 프로그램)가 있는지 확인 
			Class.forName(driver); // static 선언된 것은 로딩해줌
			System.out.println("1.드라이버가 존재");
			//2.연결-서버,아이디,비밀번호 
			con=DriverManager.getConnection(url, id,pw);
			System.out.println("2.오라클에 연결 완료");
			//3.실행 할 쿼리 작성 (암기 내용) + 페이지 처리- select 3번 : 서브쿼리 사용 
			//1)원본 데이터 가져오기 
			String sql = "SELECT no,title,writer,writeDate,hit "
					+ " FROM board ORDER BY no DESC";
			System.out.println("3-1.원본 sql 작성 :" + sql);
			//2)from에 원본 데이터를 넣고 rownum 붙이기 
			sql = "SELECT ROWNUM RNUM,no,title,writer,writeDate,hit "
					+ " FROM ("+ sql + ")";
			System.out.println("3-2.순서 번호가 붙은 sql 작성 :" + sql);
			//3)페이지에 맞는 데이터를 가져온다 
			sql =  "SELECT RNUM,no,title,writer,writeDate,hit "
					+ " FROM ("+ sql + ") "
					+ " WHERE RNUM BETWEEN ? AND ? "  ; 
			System.out.println("3-3.페이지에 필요한 데이터를 가져오는 sql 작성 :" + sql);
			//4.실행 객체 -DB에 전달 할 데이터 셋팅 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			System.out.println("4.실행객체 준비완료 ");
			//5.실행-결과 저장
			//쿼리가 select - executeQuery()로 실행 : 결과는 ResultSet 발생
			//쿼리가 insert,update,delete - executeUpdate()로 실행 : 결과는 int 발생 
			rs = pstmt.executeQuery();
			System.out.println("5.실행완료");
			//6.실행된 결과 출력 
			//rs객체가 있으면 
			if(rs!=null) {
				//rs.next() : rs안에 다음 데이터가 있으면 다음 데이터로 넘겨주고 true return 하는 메소드
				while(rs.next()) {
					//다음 데이터가 있는 경우 반복처리 
					//데이터를 저장할 객체 list가 null 이면 생성->한 번만 생성 
					if(list==null) list = new ArrayList<BoardVO>();
					//한 줄의 데이터를 담을 BoardVO 객체 생성 - 데이터 있는 만큼 함 
					BoardVO vo = new BoardVO();
					//출력 대신 vo객체에 데이터를 담아라 -setter()
					// System.out.println(rs.getLong("no"));
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setWriter(rs.getString("writer"));
					vo.setWriteDate(rs.getString("writeDate"));
					vo.setHit(rs.getLong("hit"));
					
					//vo ->list에 담기 
					list.add(vo);
				}//end of while (rs.next())
			}//end of if(rs!=null)
			
			System.out.println("+-----------------------------------+");
			System.out.println("+   글번호/ 제목 / 작성자 / 작성일 / 조회수          +");
			System.out.println("+-----------------------------------+");
			
				for(BoardVO vo : list) {
					System.out.print(vo.getNo() + "/" );
					System.out.print(vo.getTitle() + "/");
					System.out.print(vo.getWriter() + "/");
					System.out.print(vo.getWriteDate() + "/");
					System.out.println(vo.getHit());
				}
					
			
			 System.out.println("6.출력완료");
			
		}catch(Exception e) {
			//개발자를 위한 예외출력
			e.printStackTrace();
		}finally {
			//반드시 처리 -> 정상적으로 실행돼도 닫기가 필요.예외가 발생되더라도 발생 된 시점까지의 객체를 닫아야함 
			//7.사용한 객체 닫기 	
			try{
				//if(객체가 열려있는가?) 열려져 있는 경우만 닫음 
				if(con!=null) con.close(); //연결 객체 닫기 
				if(pstmt!=null) pstmt.close(); //실행 객체 닫기
				if(rs!=null)rs.close(); // 저장 객체 닫기 
				System.out.println("7.닫기완료");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
