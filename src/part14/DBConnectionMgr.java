package part14;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;

public class DBConnectionMgr {
	boolean initialized = false;
	private static DBConnectionMgr instance ;

	
	public static Connection getInstance() {// new할 필요가 없기에 static
		Connection conn = null;
		String url = "jdbc:mysql://localhost/ssar?serverTimezone=UTC"; // 오라클url검색하면 나옴.
		String username = "root";
		String password = "1234";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// 모든 클래스 다 사용가능
			conn = DriverManager.getConnection(url, username, password);// 바이트 스트림에 연결
			System.out.println("DB연결 성공");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB연결 실패");
		}
		return null;
	}
	public static DBConnectionMgr getInstance2() {
        if (instance == null) {
            synchronized (DBConnectionMgr.class) {
                if (instance == null) {
                    instance = new DBConnectionMgr();
                }
            }
        }
        return instance;
	}

	public synchronized Connection getConnection() throws Exception {
		if (!initialized) {
			Class c = Class.forName("com.mysql.cj.jdbc.Driver");
			DriverManager.registerDriver((Driver) c.newInstance());
			initialized = true;
		}
		return null;

	}

	public void freeConnection(Connection c, PreparedStatement p, ResultSet r) {
		try {
			if (r != null)
				r.close();
			if (p != null)
				p.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void freeConnection(Connection c, Statement s, ResultSet r) {
		try {
			if (r != null)
				r.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
