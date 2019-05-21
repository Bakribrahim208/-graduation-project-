package database;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.userdata;

public class sqliteconnection {

	static Statement stmt = null;

	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:finalproject.sqlite");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
			// TODO: handle exception
		}
	}

	public static void Add(String ip, String name,String Path) throws SQLException {
		String quary = "INSERT INTO Users  ('Ip','Name','Path') VALUES ('" + ip + "','" + name + "','"+Path+"') ";
		System.out.println(quary);
		stmt = Connector().createStatement();
		stmt.executeUpdate(quary);
		System.out.println("sucess addes");
	}

	public static void delete(String ip) throws SQLException {
		String quary = " delete from Users  where Ip='" + ip + "'";
		System.out.println(quary);
		stmt = Connector().createStatement();
		stmt.executeUpdate(quary);
		System.out.println("sucess deleted");

	}

	ObservableList<userdata> data = FXCollections.observableArrayList();

	public ObservableList<userdata> dataget() {
		try {
			String quary = "select *  from Users ";
			Connection con = sqliteconnection.Connector();

			ResultSet result = con.createStatement().executeQuery(quary);
			data.clear();
			// System.out.println(data.size());
			while (result.next()) {
				data.add(new userdata(  result.getString(3), result.getString(2), result.getString(4)));
 			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}

}
