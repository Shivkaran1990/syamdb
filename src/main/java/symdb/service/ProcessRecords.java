package symdb.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.util.StringUtils;

import symdb.cofiguration.DatabaseConnection;

public class ProcessRecords {

	public void startInserts(String fileLocaitn) throws IOException {
		String line = "";
		String splitBy = ",";
		int i = 0;
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(fileLocaitn));
			while ((line = br.readLine()) != null) {
				if (i >= 1) {
					String[] record = line.split(splitBy);
					String values = getData(record);

					StringBuilder insert = new StringBuilder();
					insert.append(Contants.Insert).append(values).append(")");
					System.out.println(insert.toString());
					executeInsert(insert.toString());
				}
				i++;

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void executeInsert(String insertQuery) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DatabaseConnection.getDBConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(insertQuery);
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	private String getData(String[] record) {
		StringBuilder data = new StringBuilder();
		for (int i = 0; i < record.length; i++) {
			String val = record[i];
			if (StringUtils.isNumber(val)) {
				data.append(val).append(",");
			} else {
				data.append("'").append(val).append("'").append(",");
			}
		}
		String finaldata = data.toString();
		return finaldata.substring(0, finaldata.length() - 1);
	}
}
