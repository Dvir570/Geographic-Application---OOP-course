package BuisnessLogic;

/** 
 * This is a very simple example representing how to work with MySQL 
 * using java JDBC interface;
 * The example mainly present how to read a table representing a set of WiFi_Scans
 * Note: for simplicity only two properties are stored (in the DB) for each AP:
 * the MAC address (mac) and the signal strength (rssi), the other properties (ssid and channel)
 * are omitted as the algorithms do not use the additional data.
 * 
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;

public class Sql {

	private static String _ip = "5.29.193.52";
	private static String _url = "jdbc:mysql://" + _ip + ":3306/oop_course_ariel";
	private static String _user = "oop1";
	private static String _password = "Lambda1();";
	private static Connection _con = null;

//	public static void main(String[] args) {
//		int max_id = test_ex4_db();
//		// insert_table1(max_id);
//	}

//	public static int test_101() {
//		Statement st = null;
//		ResultSet rs = null;
//		int max_id = -1;
//		// String ip = "localhost";
//		// String ip = "192.168.1.18";
//
//		try {
//			_con = DriverManager.getConnection(_url, _user, _password);
//			st = _con.createStatement();
//			rs = st.executeQuery("SELECT UPDATE_TIME FROM ");
//			if (rs.next()) {
//				System.out.println(rs.getString(1));
//			}
//
//			PreparedStatement pst = _con.prepareStatement("SELECT * FROM test101");
//			rs = pst.executeQuery();
//
//			while (rs.next()) {
//				int id = rs.getInt(1);
//				if (id > max_id) {
//					max_id = id;
//				}
//				System.out.print(id);
//				System.out.print(": ");
//				System.out.print(rs.getString(2));
//				System.out.print(" (");
//				double lat = rs.getDouble(3);
//				System.out.print(lat);
//				System.out.print(", ");
//				double lon = rs.getDouble(4);
//				System.out.print(lon);
//				System.out.println(") ");
//			}
//		} catch (SQLException ex) {
//			Logger lgr = Logger.getLogger(Sql.class.getName());
//			lgr.log(Level.SEVERE, ex.getMessage(), ex);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//				if (_con != null) {
//					_con.close();
//				}
//			} catch (SQLException ex) {
//
//				Logger lgr = Logger.getLogger(Sql.class.getName());
//				lgr.log(Level.WARNING, ex.getMessage(), ex);
//			}
//		}
//		return max_id;
//	}

	@SuppressWarnings("resource")
	public static int test_ex4_db(Connection con) {
		Statement st = null;
		ResultSet rs = null;
		int max_id = -1;

		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM ex4_db");
			pst.setQueryTimeout(5);
			pst.executeBatch();
			rs = pst.executeQuery();
			int ind = 0;
			while (rs.next()) {
				int size = rs.getInt(7);
				int len = 7 + 2 * size;
				if (ind % 100 == 0) {
					for (int i = 1; i <= len; i++) {
						System.out.print(ind + ") " + rs.getString(i) + ",");
					}
					System.out.println();
				}
				Row r = new Row();
				for (int i=0 ;i<size;i++) {
					r.add(new WiFi(rs.getString(3),rs.getString(8+2*i),null,null,rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(9+2*i),rs.getString(2)));
				}
				Database.database.add(r);
				ind++;
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Sql.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (_con != null) {
					_con.close();
				}
			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(Sql.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		return max_id;
	}

	public static void insert_table(int max_id) {
		Statement st = null;
		ResultSet rs = null;
		// String ip = "localhost";
		// String ip = "192.168.1.18";

		try {
			_con = DriverManager.getConnection(_url, _user, _password);
			st = _con.createStatement();
			Date now = null;
			for (int i = 0; i < 5; i++) {
				int curr_id = 1 + i + max_id;
				String str = "INSERT INTO test101 (ID,NAME,pos_lat,pos_lon, time, ap1, ap2, ap3) " + "VALUES ("
						+ curr_id + ",'test_name" + curr_id + "'," + (32 + curr_id) + ",35.01," + now + ",'mac1"
						+ curr_id + "', 'mac2', 'mac3')";
				PreparedStatement pst = _con.prepareStatement(str);
				pst.execute();
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Sql.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (_con != null) {
					_con.close();
				}
			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(Sql.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	public static void insert_table2(int max_id, ArrayList<Row> ar) {
		Statement st = null;
		ResultSet rs = null;

		try {
			_con = DriverManager.getConnection(_url, _user, _password);
			st = _con.createStatement();

			int size = ar.size();
			for (int i = 0; i < size; i++) {
				int curr_id = 1 + i + max_id;
				Row r = ar.get(i);
				String sql = creat_sql(r, curr_id);
				PreparedStatement pst = _con.prepareStatement(sql);
				System.out.println(sql);
				pst.execute();
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Sql.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (_con != null) {
					_con.close();
				}
			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(Sql.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	private static String creat_sql(Row r, int id) {
		if (r.size() == 0)
			return "";
		String ans = "INSERT INTO ex4_db (ID,time, device,lat,lon,alt, number_of_ap";
		String str1 = "", str2 = "";
		int n = r.size();
		String in = " VALUES (" + id + ",'" + r.getWiFi(0).getTime() + "','" + r.getWiFi(0).getModel() + "',"
				+ r.getWiFi(0).getLat() + "," + r.getWiFi(0).getLon() + "," + r.getWiFi(0).getAlt() + "," + n;
		for (int i = 0; i < n; i++) {
			str1 += ",mac" + i + ",rssi" + i;
			WiFi w = r.getWiFi(i);
			str2 += ",'" + w.getMac() + "'," + w.getSignal();
		}
		ans += str1 + ")" + in + str2 + ")";
		return ans;
	}
}