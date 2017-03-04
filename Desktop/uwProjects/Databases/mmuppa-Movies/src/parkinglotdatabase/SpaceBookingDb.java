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

public class SpaceBookingDb {
	private static String userName = "anitapau"; // Change to yours
	private static String password = "dywodcoc";
	private static String serverName = "cssgate.insttech.washington.edu";
	private static Connection conn;
	private List<SpaceBooking> list;
	private List<Integer> spaceBookedNum;

	/**
	 * Get the spaceBooked number
	 * 
	 * @return the list of space number that is booked
	 */
	public List<Integer> getSpaceBookedNum() {
		return spaceBookedNum;
	}

	/**
	 * Set the space number booked
	 * 
	 * @param spaceBookedNum
	 *            to be booked
	 */

	public void setSpaceBookedNum(List<Integer> spaceBookedNum) {
		this.spaceBookedNum = spaceBookedNum;
	}

	/**
	 * Create the connection to the database
	 * 
	 * @throws SQLException
	 */
	public static void createConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		conn = DriverManager.getConnection("jdbc:" + "mysql" + "://" + serverName + "/", connectionProps);
	}

	/**
	 * get all the bookings in a list
	 * 
	 * @return list of bookings
	 * @throws SQLException
	 */
	public List<SpaceBooking> getBooking() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select bookingId, spaceNumber, staffNumber, visitorLicense, dateOfVisit "
				+ "from anitapau.spaceBooking ";

		list = new ArrayList<SpaceBooking>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String bookId = rs.getString("bookingId");
				int spaceNum = rs.getInt("spaceNumber");
				int staffNum = rs.getInt("staffNumber");
				String vehiclNum = rs.getString("visitorLicense");
				String dateVisit = rs.getString("dateOfVisit");
				SpaceBooking booking = new SpaceBooking(bookId, spaceNum, staffNum, vehiclNum, dateVisit);
				list.add(booking);
			}
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
	 * Get Booking by specific booking Id
	 * 
	 * @param thebookingId
	 * @return list of bookings
	 */
	public List<SpaceBooking> getBooking(String theBookingId) {
		List<SpaceBooking> filterList = new ArrayList<SpaceBooking>();
		try {
			list = getBooking();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (SpaceBooking booking : list) {
			if (booking.getMyBookingId().equalsIgnoreCase(theBookingId)) {
				filterList.add(booking);
			}
		}
		return filterList;
	}

	/**
	 * Adds a new staff to the table.
	 * 
	 * @param staff
	 */
	public void makeBooking(SpaceBooking booking) {
		String sql = "insert into anitapau.spaceBooking values " + "(?, ?, ?, ?, ?); ";

		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, booking.getMyBookingId());
			preparedStatement.setInt(2, booking.getMySpaceNumber());
			preparedStatement.setInt(3, booking.getMyStaffNumber());
			preparedStatement.setString(4, booking.getMyVisitorLicense());
			preparedStatement.setString(5, booking.getMyDateofVisit());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void updateBooking(int row, String columnName, Object data) {
		// TODO Auto-generated method stub
		SpaceBooking booking = list.get(row);
		String bookingId = booking.getMyBookingId();
		String sql = "update anitapau.spaceBooking set " + columnName + " = ?  where bookingId = ? ";
		System.out.println(sql);
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			if (data instanceof String)
				preparedStatement.setInt(1, (Integer) data);
			else if (data instanceof Integer)
				preparedStatement.setInt(1, (Integer) data);
			preparedStatement.setString(2, bookingId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

}
