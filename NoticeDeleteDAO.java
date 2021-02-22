/*
 * 삭제 할 글 번호를 받아서 진행 
 */
package notice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;


public class NoticeDeleteDAO {

	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//필요한 객체 ~정보 
		//DB delete 처리를 함
		
		//필요한 객체 선언
		Connection con = null;
		PreparedStatement pstmt = null;
		
		//DB정보 
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "java00";
		String pw = "java00";
		//리턴되는 데이터 선언 : delete -int 
		int result = 0;
		
		try {
		//삭제 할 글번호 받기
		System.out.print("삭제 할 공지사항 번호를 입력-> ");
		String data = scanner.nextLine();
		long no = Long.parseLong(data);
		
		//DB처리 - DELETE : INSERT / UPDATE / DELETE -> 같은부류의 프로그램(사용객체2개, 리턴타입이 int)  select / insert,update,delete
		//1.드라이버 확인
		Class.forName(driver); // 메인메모리에 로딩됨 
		System.out.println("드라이버 확인 완료 ");
		//2.연결
		con = DriverManager.getConnection(url, id, pw);
		System.out.println("연결객체 생성완료");
		//3.쿼리 작성
		String sql = "DELETE FROM notice WHERE no = ?"; // ? : 값으로 대체 되는 문자 
		System.out.println("sql :" + sql);
		//4.실행 객체 생성
		pstmt = con.prepareStatement(sql);
		pstmt.setLong(1,no);
		
		System.out.println("실행객체와 데이터 셋팅 완료 ");
		//5.실행 
		result = pstmt.executeUpdate();
		System.out.println("실행완료 ");
		//6.출력
		if(result > 0) System.out.println("공지사항 글 번호 " + no + "번 삭제완료");
		System.out.println("출력 완료 ");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(con != null)con.close();
				if(pstmt != null)pstmt.close();
				System.out.println("닫기 완료 ");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}

}
