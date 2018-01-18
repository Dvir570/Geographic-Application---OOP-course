package BuisnessLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoteTable {
	private Connection connection;
	private String database;
	private String table;
	private String lastUpdate = null;

	public RemoteTable(String url, String user, String password, String database, String table) {
		this.database = database;
		this.table = table;
	}
	
	@SuppressWarnings("resource")
	public int importRemoteTable() {
		Statement st = null;
		ResultSet rs = null;
		int max_id = -1;

		try {
			PreparedStatement pst = this.connection.prepareStatement("SELECT * FROM " + this.table);
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
				for (int i = 0; i < size; i++) {
					r.add(new WiFi(rs.getString(3), rs.getString(8 + 2 * i), null, null, rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getString(9 + 2 * i), rs.getString(2)));
				}
				Database.database.add(r);
				ind++;
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(RemoteTable.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(RemoteTable.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		return max_id;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public String getLastUpdate() {
		return this.lastUpdate;
	}
	
	public String getDatabaseName() {
		return this.database;
	}
	
	public String getTableName() {
		return this.table;
	}
}
