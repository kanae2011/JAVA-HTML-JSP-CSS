package grade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GradeListDAO {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "java00";
		String pw = "java00";
		
		List<GradeVO>list=null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,id,pw);
			String sql = "SELECT gradeNO,gradeNAME FROM GRADE ORDER BY GRADENO ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			System.out.println("sql :" + sql);
			
			if(rs!=null) {
				while(rs.next()) {
					if(list==null) list = new ArrayList<GradeVO>();
					GradeVO vo = new GradeVO();
					vo.setGradeNo(rs.getLong("gradeNo"));
					vo.setGradeName(rs.getString("gradeName"));
					
					list.add(vo);
				}
			}
				System.out.println(" + 등급번호 / 등급명 + ");
				
				if(list==null) {
					System.out.println("데이터가 존재하지 않습니다.");
				}else {
				for(GradeVO vo:list) {
					System.out.print("+ " + vo.getGradeNo());
					System.out.println("/ " + vo.getGradeName());
				}
				}
			System.out.println("출력완료");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
