package ar.com.cipres.util;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class JDBCBatchOperation {
	static String DB_DRIVER = "com.mysql.jdbc.Driver";
	static String DB_CONNECTION = "jdbc:mysql://localhost:3306/";
	static String DB_USER = "root";
	static String DB_PASSWORD = "root";

	private static Connection getConnection(String bd) {
		Connection connection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection = (Connection) DriverManager.getConnection(DB_CONNECTION + bd , DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public static boolean BulkInsert(String sqlQuery, List<List<Map.Entry<Type, Object>>> parameters, String bd) {
		// String sqlQuery = "insert into CondTributariaAFIP values
		// (?,?,?,?,?,?,?,?)";
		int count = 0;
		int batchSize = 25000;
		Connection connection = getConnection(bd);
		PreparedStatement pstmt = null;
		try {
			int[] result = null;
			connection.setAutoCommit(false);
			pstmt = (PreparedStatement) connection.prepareStatement(sqlQuery);
			for (List<Map.Entry<Type, Object>> item : parameters) {
				for (Map.Entry<Type, Object> entry : item) {
					String type = entry.getKey().toString().substring(entry.getKey().toString().lastIndexOf('.') + 1);
					switch (type) {
					case "Integer":
						pstmt.setInt(item.indexOf(entry) + 1, (Integer) entry.getValue());
						break;
					case "BigDecimal":
						pstmt.setBigDecimal(item.indexOf(entry) + 1, (BigDecimal) entry.getValue());
						break;
					case "Long":
						pstmt.setLong(item.indexOf(entry) + 1, (Long) entry.getValue());
						break;
					case "Boolean":
						pstmt.setBoolean(item.indexOf(entry) + 1, (Boolean) entry.getValue());
						break;
					case "Date":
						pstmt.setDate(item.indexOf(entry) + 1, (Date) entry.getValue());
						break;
					case "Timestamp":
						pstmt.setTimestamp(item.indexOf(entry) + 1, (Timestamp) entry.getValue());
						break;
					case "String":
						pstmt.setString(item.indexOf(entry) + 1, (String) entry.getValue());
						break;
					default:
						pstmt.setString(item.indexOf(entry) + 1, (String) entry.getValue());
						break;
					}
				}
				pstmt.addBatch();
				count++;
				if (count % batchSize == 0 || count == parameters.size()) {
					System.out.println("Commit the batch");
					result = pstmt.executeBatch();
					System.out.println("Number of rows inserted: " + result.length);
					connection.commit();
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
