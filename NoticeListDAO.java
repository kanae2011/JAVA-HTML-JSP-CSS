/*
 *  공지사항 리스트 - 전체, 현재 , 지난, 예약 공지 
 */
package notice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NoticeListDAO {

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
		List<NoticeVO>list = null;
		
		//페이지 처리를 위한 변수 1페이지에 해당되는 데이터로 셋팅  
		long startRow = 1;
		long endRow = 10;
		
		//공지 쿼리 뒤에 붙는 조건문
		String condition = "";
		
		//공지 리스트의 종류 선택-입력
		while(true) {
			System.out.println("<<공지리스트 종류 선택>>");
			System.out.println("1.전체공지 2.현재공지 3.지난공지 4.예약공지");
			System.out.print("공지사항 선택(1~4)-> ");
			String menu = scanner.nextLine();
			
			if(menu.equals("1")) {
				//조건이 없으면 전체공지가 됨
				break;
			} else if(menu.equals("2")) { //현재공지 : 현재 날짜가 공지 시작일과 종료일 사이에 있다 
				condition = " WHERE startDate < SYSDATE AND endDate > TRUNC(SYSDATE) ";
				break;
			} else if (menu.equals("3")) { //지난공지 : 종료일이 현재 날짜보다 작다 
				condition = " WHERE endDate < TRUNC(SYSDATE) ";
				break;
			} else if (menu.equals("4")) { // 예약공지 : 시작일이 현재 날짜보다 크다 
				condition = " WHERE startDate < SYSDATE ";
				break;
			}else System.out.println("잘못된 메뉴 선택");
		}//end of while(true)
			System.out.println("공지사항 처리를 위한 조건문: " + condition);
		
		//선택 되어진 데이터 가져오기 
			try { // 정상처리 1~6
				Class.forName(driver); //1-확인
				System.out.println("===드라이버 확인 완료===");
				con=DriverManager.getConnection(url,id,pw); //2.연결
				System.out.println("===드라이버 연결 완료===");
				//3.쿼리작성  페이지 처리를 위한 1)원본 데이터 가져오기 
				String sql = "SELECT no,title,startDate,endDate,updateDate FROM notice " + condition 
						+ " ORDER BY startDate DESC ";
				//2) 순서번호 붙이기 (ROWNUM rnum)
				sql="SELECT ROWNUM RNUM, no,title,startDate,endDate,updateDate FROM ("+ sql + ")";
				//3)순서 번호를 붙인 데이터에서 페이지에 맞는 데이터 가져오기
				sql = "SELECT RNUM, no,title,TO_CHAR(startDate,'YYYY.MM.DD')startDate,"
						+ "TO_CHAR(endDate,'YYYY.MM.DD')endDate,TO_CHAR(updateDate,'YYYY.MM.DD')updateDate FROM (" + sql + ")"
						+ "WHERE RNUM BETWEEN ? AND ?"; //페이지 조건
				System.out.println("sql : " + sql);
				
				//4.실행객체 & 데이터 셋팅
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, startRow);
				pstmt.setLong(2, endRow);
				System.out.println("===실행객체 생성 & 데이터 셋팅 완료===");
				
				//5.실행
				///select : pstmt.executeQuery() - 결과 : ResultSet / 나머지 : int 로 받음
				rs = pstmt.executeQuery(); 
				System.out.println("===실행 완료===");
				
				//6.출력 (list 출력) -데이터가 여러개 -> 반복문
				if (rs != null) {
					while(rs.next()) {//다음 데이터가 있으면 반복처리 
						//rs->vo로 데이터 담기 -> list에 담는다 
						//데이터 담기위해서 list가 null이면 안된다 : 한 번만 생성 - 생성이 안되어 있으면 ㅕ생성
						if(list==null) list = new ArrayList<NoticeVO>();
						NoticeVO vo = new NoticeVO(); //생성 후 저장 
						vo.setNo(rs.getLong("no"));
						vo.setTitle(rs.getString("title"));
						vo.setStartDate(rs.getString("startDate"));
						vo.setEndDate(rs.getString("endDate"));
						vo.setUpdateDate(rs.getString("updateDate"));
						//vo -> list
						list.add(vo);
						
					}//end of while(rs.next())
					System.out.println("DB에서 가져온 데이터 확인 : " + list);
				}
				
				// list에 있는 데이터 출력하기 
				System.out.println("+----------------------------------------+");
				System.out.println("번호   /  제목  /  공지시작일  / 공지 종료일  /  최종수정일");
				System.out.println("+----------------------------------------+");
				//데이터가 없는 경우 - null 발생
				if(list==null)
				System.out.println("데이터가 존재하지 않습니다.");
				else {//데이터가 있으면 있는만큼 출력
					for(NoticeVO vo : list ) {
						System.out.print("+ " + vo.getNo());
						System.out.print("/ " + vo.getTitle());
						System.out.print("/ " + vo.getStartDate());
						System.out.print("/ " + vo.getEndDate());
						System.out.print("/ " + vo.getUpdateDate());
						System.out.println(); //줄바꿈 
					}
					
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
