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

/**
 * A class that consists of the database operations to insert and update the
 * Space information.
 * 
 * @author anita Paudel
 *
 */

public class SpaceDB {
	private static String userName = "anitapau"; // Change to yours
	private static String password = "dywodcoc";
	private static String serverName = "cssgate.insttech.washington.edu";
	private static Connection conn;
	private List<Space> list;

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
	 * Returns a list of space objects from the database.
	 * 
	 * @return list of space
	 * @throws SQLException
	 */
	public List<Space> getSpace() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select spaceNumber, spaceType, lotName " + "from anitapau.space ";
		list = new ArrayList<Space>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int spaceNum = rs.getInt("spaceNumber");
				String spaceType = rs.getString("spaceType");
				String lotName = rs.getString("lotName");
				Space space = new Space(spaceNum, spaceType, lotName);
				list.add(space);
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
	 * Filters the movie list to find the given spaceNumber. Returns a list with the
	 * Space objects that match the spaceNumber provided.
	 * @param theSpaceNumber
	 * @return list of space that contain the spaceNumber.
	 */
	public List<Space> getSpace(int theSpaceNumber) {
		List<Space> filterList = new ArrayList<Space>();
		try {
			list = getSpace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Space space : list) {
			if (space.getMySpaceNumber()==(theSpaceNumber)) {
				filterList.add(space);
			}
		}
		return filterList;
	}
	
	public List<Space> getAvailableSpaceByLotAndDate(String theLotName, String date) throws SQLException{
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String sql = "SELECT s.* FROM anitapau.space s WHERE  s.lotName = '%s' AND s.spaceNumber NOT IN (SELECT DISTINCT spaceNumber FROM anitapau.spaceBooking WHERE dateOfVisit = '%s');";
		sql = String.format(sql, theLotName, date);
		list = new ArrayList<Space>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int spaceNum = rs.getInt("spaceNumber");
				String spaceType = rs.getString("spaceType");
				String lotName = rs.getString("lotName");
				Space space = new Space(spaceNum, spaceType, lotName);
				list.add(space);
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
	 * Adds a new space to the table.
	 * @param theSpace
	 */
	public void addSpace(Space theSpace) {
		String sql = "insert into anitapau.space values " + "(?, ?, ?); ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, theSpace.getMySpaceNumber());
			preparedStatement.setString(2, theSpace.getMySpaceType());
			preparedStatement.setString(3, theSpace.getMyLotName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
	}

	/**
	 * Modifies the movie information corresponding to the index in the list.
	 * @param row index of the element in the list
	 * @param columnName attribute to modify
	 * @param data value to supply
	 */
	public void updateSpace(int row, String columnName, Object data) {
		
		Space space = list.get(row);
		int spaceNum = space.getMySpaceNumber();
		String sql = "update anitapau.space set " + columnName + " = ?  where spaceNumber = ? ";
		System.out.println(sql);
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			if (data instanceof String)
				preparedStatement.setString(1, (String) data);
			else if (data instanceof Integer)
				preparedStatement.setInt(1, (Integer) data);
			preparedStatement.setInt(2, spaceNum);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
		
	}
	
	
	
}
