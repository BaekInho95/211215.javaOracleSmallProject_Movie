package movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Menu {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String query;

	Connection con;

	// 데이터베이스를 연결하는 클래스
	public void DBConnecting() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "c##ino";
		String pwd = "1234";
		try {
			// Class.forname : jdbc에서 사용하는 드라이버를 로드하는 메서드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("드라이버 연결 준비...");
			// DriverManager.getConnection : 연결을 얻는 메서드
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("데이터베이스 연결 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void menu() {
		while (true) {

			System.out.println("-----------------------------------------------");
			System.out.println("1. 영화표 목록 보기 || 2. 영화표 관리하기 || 3. 종료하기");
			System.out.println("-----------------------------------------------");
			try {
				int num = Integer.parseInt(br.readLine());
				if (num == 1) {
					list();
				} else if (num == 2) {
					manage();
				} else if (num == 3) {
					System.out.println("종료합니다.");
					br.close();
					System.exit(0);
				}
			} catch (NumberFormatException e) {
				System.out.println("잘못된 입력입니다.");
			} catch (IOException e) {
				System.out.println("잘못된 입력입니다.");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void list() throws Exception {
		query = "select * from movie";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		System.out.println("NO. \t\tNAME \t\t\tGenre \tPRICE \tGRADE");
		while (rs.next()) {
			System.out.print(rs.getInt(1));
			System.out.print("\t\t" + rs.getString(2));
			System.out.print("\t\t\t" + rs.getString(3));
			System.out.print("\t" + rs.getInt(4));
			System.out.println("\t" + rs.getInt(5));
		}
	}

	public void manage() throws Exception {
		System.out.println("1. 영화 추가하기 || 2. 영화 수정하기 || 3. 영화 삭제하기");
		int num = Integer.parseInt(br.readLine());
		if (num == 1) {
			int num2;
			String name;
			String genre;
			int price;
			int grade;
			try {
				System.out.println("번호 입력");
				num2 = Integer.parseInt(br.readLine());
				System.out.println("이름 입력");
				name = br.readLine();
				System.out.println("장르 입력");
				genre = br.readLine();
				System.out.println("가격 입력");
				price = Integer.parseInt(br.readLine());
				System.out.println("평점 입력");
				grade = Integer.parseInt(br.readLine());
				query = "insert into movie values(" + num2 + ",'" + name + "','" + genre + "'," + price + "," + grade
						+ ")";
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.executeUpdate();
				System.out.println("영화가 추가되었습니다.");
				pstm.close();
			} catch (NumberFormatException e) {
				System.out.println("잘못된 입력입니다.");
			}
		} else if (num == 2) {
			int num3;
			System.out.println("수정할 영화의 번호를 입력해주세요.");
			num3 = Integer.parseInt(br.readLine());
			String name;
			String genre;
			int price;
			int grade;
			System.out.println("이름 입력");
			name = br.readLine();
			System.out.println("장르 입력");
			genre = br.readLine();
			System.out.println("가격 입력");
			price = Integer.parseInt(br.readLine());
			System.out.println("평점 입력");
			grade = Integer.parseInt(br.readLine());
			try {
				query = "update movie set id = ?, name =?, genre =?, price =?, grade =?where id =?";
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.setInt(1, num3);
				pstm.setString(2, name);
				pstm.setString(3, genre);
				pstm.setInt(4, price);
				pstm.setInt(5, grade);
				pstm.setInt(6, num3);
				pstm.executeUpdate();
				System.out.println("영화가 수정되었습니다.");
				pstm.close();
			} catch (SQLException e) {
				System.out.println("잘못된 입력입니다.");
			}
		} else if (num == 3) {
			int num3;
			System.out.println("삭제할 영화의 번호를 입력해주세요.");
			num3 = Integer.parseInt(br.readLine());
			try {
				query = "delete from movie where id=" + num3;
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.executeUpdate();
				System.out.println("영화가 삭제되었습니다.");
				pstm.close();
			} catch (SQLException e) {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

}
