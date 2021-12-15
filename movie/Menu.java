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

	// �����ͺ��̽��� �����ϴ� Ŭ����
	public void DBConnecting() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "c##ino";
		String pwd = "1234";
		try {
			// Class.forname : jdbc���� ����ϴ� ����̹��� �ε��ϴ� �޼���
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� �ε� ����");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("����̹� ���� �غ�...");
			// DriverManager.getConnection : ������ ��� �޼���
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("�����ͺ��̽� ���� ����");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void menu() {
		while (true) {

			System.out.println("-----------------------------------------------");
			System.out.println("1. ��ȭǥ ��� ���� || 2. ��ȭǥ �����ϱ� || 3. �����ϱ�");
			System.out.println("-----------------------------------------------");
			try {
				int num = Integer.parseInt(br.readLine());
				if (num == 1) {
					list();
				} else if (num == 2) {
					manage();
				} else if (num == 3) {
					System.out.println("�����մϴ�.");
					br.close();
					System.exit(0);
				}
			} catch (NumberFormatException e) {
				System.out.println("�߸��� �Է��Դϴ�.");
			} catch (IOException e) {
				System.out.println("�߸��� �Է��Դϴ�.");
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
		System.out.println("1. ��ȭ �߰��ϱ� || 2. ��ȭ �����ϱ� || 3. ��ȭ �����ϱ�");
		int num = Integer.parseInt(br.readLine());
		if (num == 1) {
			int num2;
			String name;
			String genre;
			int price;
			int grade;
			try {
				System.out.println("��ȣ �Է�");
				num2 = Integer.parseInt(br.readLine());
				System.out.println("�̸� �Է�");
				name = br.readLine();
				System.out.println("�帣 �Է�");
				genre = br.readLine();
				System.out.println("���� �Է�");
				price = Integer.parseInt(br.readLine());
				System.out.println("���� �Է�");
				grade = Integer.parseInt(br.readLine());
				query = "insert into movie values(" + num2 + ",'" + name + "','" + genre + "'," + price + "," + grade
						+ ")";
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.executeUpdate();
				System.out.println("��ȭ�� �߰��Ǿ����ϴ�.");
				pstm.close();
			} catch (NumberFormatException e) {
				System.out.println("�߸��� �Է��Դϴ�.");
			}
		} else if (num == 2) {
			int num3;
			System.out.println("������ ��ȭ�� ��ȣ�� �Է����ּ���.");
			num3 = Integer.parseInt(br.readLine());
			String name;
			String genre;
			int price;
			int grade;
			System.out.println("�̸� �Է�");
			name = br.readLine();
			System.out.println("�帣 �Է�");
			genre = br.readLine();
			System.out.println("���� �Է�");
			price = Integer.parseInt(br.readLine());
			System.out.println("���� �Է�");
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
				System.out.println("��ȭ�� �����Ǿ����ϴ�.");
				pstm.close();
			} catch (SQLException e) {
				System.out.println("�߸��� �Է��Դϴ�.");
			}
		} else if (num == 3) {
			int num3;
			System.out.println("������ ��ȭ�� ��ȣ�� �Է����ּ���.");
			num3 = Integer.parseInt(br.readLine());
			try {
				query = "delete from movie where id=" + num3;
				PreparedStatement pstm = con.prepareStatement(query);
				pstm.executeUpdate();
				System.out.println("��ȭ�� �����Ǿ����ϴ�.");
				pstm.close();
			} catch (SQLException e) {
				System.out.println("�߸��� �Է��Դϴ�.");
			}
		}
	}

}
