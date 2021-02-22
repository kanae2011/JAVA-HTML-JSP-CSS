/*
 *  공지사항 글보기 - 보여줄 글 번호 입력 -> 데이터를 가져와서 출력 
 */
package notice;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class NoticeViewDAO {

	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//필요한 객체 선언 - select : con , prmt, rs
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//DB 접속 정보 
		//드라이버 - 오라클회사에서 보는 크기 -> 큰 > 작은
		String driver ="oracle.jdbc.driver.OracleDriver";
		//서버 - 자바에서 보는 크기 -> 큰-> 작은
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		//아이디
		String id = "java00";
		//비밀번호 
		String pw = "java00";
		
		
		//결과로 나오는 데이터 선언 : list select List<NoticeVO>
		NoticeVO vo = null;
		
		
		
		//보여줄 공지 글번호 선택-입력
		System.out.println("<<공지 글번호의 번호 선택>>");
		System.out.print("공지사항 번호 -> ");
		String data = scanner.nextLine(); //공지 번호를 입력할 때 숫자로 잘입력해야 함 
		
		long no = Long.parseLong(data);
		
			
		
		//선택 되어진 데이터 가져오기 
		try { // 정상처리 1~6
			Class.forName(driver); //1-확인
			System.out.println("===드라이버 확인 완료===");
			con=DriverManager.getConnection(url,id,pw); //2.연결
			System.out.println("===연결 완료===");
			//3.쿼리작성  페이지 처리를 위한 1)원본 데이터 가져오기 
			String sql = "SELECT no,title,content,TO_CHAR(startDate,'YYYY.MM.DD')startDate,TO_CHAR(endDate,'YYYY.MM.DD')endDate,"
					+ "TO_CHAR(updateDate,'YYYY.MM.DD')updateDate FROM notice " 
					+ " WHERE no = ? ";
			
			System.out.println("sql : " + sql);
			
			//4.실행객체 & 데이터 셋팅
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			System.out.println("===실행객체 생성 & 데이터 셋팅 완료===");
			
			//5.실행
			///select : pstmt.executeQuery() - 결과 : ResultSet / 나머지 : int 로 받음
			rs = pstmt.executeQuery(); 
			System.out.println("===실행 완료===");
			
			//6.출력 (NoticeVO 출력) -데이터가 한 개 
			//데이터를 NoticeVO에 담기 
			if (rs != null && rs.next()) { //결과 저장 객체가 null이 아니고 데이터가 있어야 함 
					//rs->vo로 데이터 담기
					vo = new NoticeVO(); //생성 후 저장 
					//rs에서 꺼내서 vo에 담기 
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setContent(rs.getString("content"));
					vo.setStartDate(rs.getString("startDate"));
					vo.setEndDate(rs.getString("endDate"));
					vo.setUpdateDate(rs.getString("updateDate"));
					
				System.out.println("DB에서 가져온 데이터 확인 : " + vo);
			} //end of  if (rs != null && rs.next())
			
			// vo에 있는 데이터 출력하기 
			System.out.println("+----------------------------------------+");
			System.out.println("번호   /  제목  /  공지시작일  / 공지 종료일  /  최종수정일");
			System.out.println("+----------------------------------------+");
			//데이터가 없는 경우 - null 발생
			if(vo==null)
			System.out.println("데이터가 존재하지 않습니다.");
			else {//데이터가 있으면 있는만큼 출력	
				System.out.println("+ 공지번호 : " + vo.getNo());
				System.out.println("+ 제목 : " + vo.getTitle());
				System.out.println("+ 내용 : " + vo.getContent());
				System.out.println("+ 공지 시작일 : " + vo.getStartDate());
				System.out.println("+ 공지 종료일 : " + vo.getEndDate());
				System.out.println("+ 최종 수정일 : " + vo.getUpdateDate());
		
				}
				
			System.out.println("+----------------------------------------+");
			System.out.println("데이터 출력 완료");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try { //7.닫기 
					if(con !=null)con.close();
					if(pstmt !=null)pstmt.close();
					if(rs !=null)rs.close();
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
	}

}
