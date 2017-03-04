package parkinglotdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StaffDB {
	private static String userName = "anitapau"; // Change to yours
	private static String password = "dywodcoc";
	private static String serverName = "cssgate.insttech.washington.edu";
	private static Connection conn;
	private List<Staff> list;

	/**
	 * Creates a sql connection to MySQL using the properties for userid,
	 * password and server information.
	 * 
	 * @throws SQLException
	 */
	public static void createConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		conn = DriverManager.getConnection("jdbc:" + "mysql" + "://" + serverName + "/", connectionProps);

		System.out.println("Connected to database");
	}


	/**
	 * Filters the staff list to find the given staffNumber. Returns a list with the
	 * staff objects that match the staffNumber provided.
	 * @param theStaffNumber
	 * @return list of staff that contain the staffNumber.
	 */
	public List<Staff> getStaff(int theStaffNumber) {
		List<Staff> filterList = new ArrayList<Staff>();
		try {
			list = getStaff();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Staff staff : list) {
			if (staff.getMyStaffNumber()==(theStaffNumber)) {
				filterList.add(staff);
			}
		}
		return filterList;
	}
	
	/**
	 * Returns a list of staff objects from the database.
	 * 
	 * @return list of staff
	 * @throws SQLException
	 */
	public List<Staff> getStaff() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select staffNumber, telephoneExt, vehicleLicenceNumber " + "from anitapau.staff ";
		list = new ArrayList<Staff>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int staffNum = rs.getInt("staffNumber");
				String tel = rs.getString("telephoneExt");
				String vehL = rs.getString("vehicleLicenceNumber");
				Staff staff = new Staff(staffNum, tel, vehL);
				list.add(staff);			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}
	
	/**
	 * Adds a new staff to the table.
	 * @param staff 
	 */
	public void addStaff(Staff staff) {
		String sql = "insert into anitapau.staff values " + "(?, ?, ?); ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, staff.getMyStaffNumber());
			preparedStatement.setString(2, staff.getMyTelephoneExt());
			preparedStatement.setString(3, staff.getMyVehicleLicenseNumber());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
	}


	public void updateStaff(int row, String columnName, Object data) {
		// TODO Auto-generated method stub
		Staff staff = list.get(row);
		int staffNum = staff.getMyStaffNumber();
		String sql = "update anitapau.staff set " + columnName + " = ?  where staffNumber = ? ";
		System.out.println(sql);
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			if (data instanceof String)
				preparedStatement.setString(1, (String) data);
			else if (data instanceof Integer)
			preparedStatement.setInt(1, (Integer) data);
			preparedStatement.setInt(2, staffNum);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
	}


}
