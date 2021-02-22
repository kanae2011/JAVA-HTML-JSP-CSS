/** 별도의 도큐멘트 작성 가능- 마우스를 올리면 보임 
 * BoardViewDAO : 게시판의 글보기 작성 
 */
package ch16JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BoardViewDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//필요한 객체 선언 /연결객체
		Connection con = null;
		//실행객체
		PreparedStatement pstmt = null;
		//결과저장 객체 
		ResultSet rs = null;
		//DB 서버 연결시 사용되는 정보 
		String driver ="oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "java00";
		String pw = "java00";
		
		
		long no = 1; // update 의 물음표에 넣을 값(예시)
		//리턴 저장 객체 선언
		BoardVO vo =null;
		
		//프로그램 패턴 
		try {//정상처리
			//1.확인
			Class.forName(driver);
			//2.연결
			con = DriverManager.getConnection(url, id, pw);
			
			/**글보기를 하기 위한 데이터를 가져오기 전에  조회수를 1증가 시키는 처리를 함**/
			 //쿼리작성
			 String sql = "UPDATE board SET hit = hit +1 WHERE no=?";
			  //실행객체&데이터 셋팅
			 pstmt =con.prepareStatement(sql);
			 pstmt.setLong(1,no);
			  //실행
			
			 int result = pstmt.executeUpdate();
			 System.out.println(result);
			  //출력
			 if(result !=0) System.out.println("조회수  1증가 ");
			 else {
				 System.out.println("조회수가 1증가 되지 않음->글 번호가 존재하지 않음");
				 throw new Exception("조회수 1증가 안됨->글 번호가 존재하지 않음");
			 }
			  //닫기
			  pstmt.close();
			  /* 조회수 1 증가의 끝*/
			  
			//3.쿼리작성
			 sql = "SELECT no,title,content,writer,writeDate,hit "
					+ " FROM board "
					+ " WHERE no=?"; // ? : 값을 대체해야 하는 부분의 대체 문자  
			//4.실행객체 & 데이터 셋팅 
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			//5.실행 select -> executeQuery() / 그 외 -> executeUpdate() 결과 int 
			rs=pstmt.executeQuery();
			//6.출력 --데이터가 한 개 나오거나 또는 안나옴 
			if(rs !=null && rs.next()) { // 데이터를 가져옴 
				vo = new BoardVO(); //저장할 객체 생성
				vo.setNo(rs.getLong("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setWriter(rs.getString("writer"));
				vo.setWriteDate(rs.getString("writeDate"));
				vo.setHit(rs.getLong("hit"));
				
			}
			System.out.println("+----------------------------------+");
			System.out.println("번호 : " + vo.getNo());
			System.out.println("제목 : " + vo.getTitle());
			System.out.println("내용: " + vo.getContent());
			System.out.println("작성자 : " + vo.getWriter());
			System.out.println("작성일: " + vo.getWriteDate());
			System.out.println("조회수 : " + vo.getHit());
			System.out.println("+----------------------------------+");
			
		}catch(Exception e) {//예외처리
			e.printStackTrace();
		}finally {//반드시 처리
			try {//7.닫기를 위한 정상처리  
				if(con !=null)con.close();
				if(pstmt !=null)pstmt.close();
				if(rs !=null)rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
