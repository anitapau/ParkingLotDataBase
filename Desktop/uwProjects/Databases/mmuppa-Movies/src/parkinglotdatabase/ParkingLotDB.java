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
 * * A class that consists of the database operations to insert and update the
 * ParkingLot information.
 * 
 * @author anita paudel
 *
 */
public class ParkingLotDB {
	private static String userName = "anitapau"; // Change to yours
	private static String password = "dywodcoc";
	private static String serverName = "cssgate.insttech.washington.edu";
	private static Connection conn;
	private List<ParkingLot> list;

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

		System.out.println("Connected to database");
	}

	/**
	 * Returns a list of parkingLot objects from the database.
	 * 
	 * @return list of lots
	 * @throws SQLException
	 */
	public List<ParkingLot> getLot() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select lotName, location, capacity, floors " + "from anitapau.lot ";

		list = new ArrayList<ParkingLot>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String lotName = rs.getString("lotName");
				String location = rs.getString("location");
				int capacity = rs.getInt("capacity");
				int floors = rs.getInt("floors");
				ParkingLot lot = new ParkingLot(lotName, location, capacity, floors);
				list.add(lot);
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
	 * Filters the lotname list to find the given lotName. Returns a list with the
	 * parkingLot objects that match the lotName provided.
	 * @param theLotName
	 * @return list of lotName that contain the title.
	 */
	public List<ParkingLot> getLot(String theLotname) {
		List<ParkingLot> filterList = new ArrayList<ParkingLot>();
		try {
			list = getLot();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (ParkingLot lotName : list) {
			if (lotName.getMyLotName().toLowerCase().contains(theLotname.toLowerCase())) {
				filterList.add(lotName);
			}
		}
		return filterList;
	}
	
	
	/**
	 * Adds a new parking lot to the table.
	 * @param lot 
	 */
	public void addLot(ParkingLot lot) {
		String sql = "insert into anitapau.lot values " + "(?, ?, ?, ?); ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, lot.getMyLotName());
			preparedStatement.setString(2, lot.getMyLocation());
			preparedStatement.setInt(3, lot.getMyCapacity());
			preparedStatement.setInt(4, lot.getMyFloors());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
	}
	
	/**
	 * Modifies the parkinglot information corresponding to the index in the list.
	 * @param row index of the element in the list
	 * @param columnName attribute to modify
	 * @param data value to supply
	 */
	public void updateparkingLot(int row, String columnName, Object data) {
		
		ParkingLot lot = list.get(row);
		String lotName = lot.getMyLotName();
		String sql = "update anitapau.lot set " + columnName + " = ?  where lotName= ? ";
		System.out.println(sql);
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			if (data instanceof String)
				preparedStatement.setString(1, (String) data);
			else if (data instanceof Integer)
			preparedStatement.setInt(1, (Integer) data);
			preparedStatement.setString(2, lotName);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
		
	}

}
