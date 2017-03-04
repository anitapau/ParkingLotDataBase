package parkinglotdatabase;

/**
 * Staff class including the staff information
 * 
 * @author anita paudel
 *
 */
public class Staff {
	/**
	 * Staff number for the staff.
	 */
	private int myStaffNumber;
	/**
	 * Telephone number
	 */
	private String myTelephoneExt;
	/**
	 * Vehicle license Number
	 */
	private String myVehicleLicenseNumber;
	
	/**
	 * Default constructor
	 */
	public Staff() {
		
	}
	public Staff(int staffNum, String tel, String vehL) {
		// TODO Auto-generated constructor stub
		myStaffNumber = staffNum;
		myTelephoneExt = tel;
		myVehicleLicenseNumber = vehL;
	}
	/**
	 * Staff number of the staff
	 * @return the staffNumber
	 */
	public int getMyStaffNumber() {
		return myStaffNumber;
	}
	public void setMyStaffNumber(int theStaffNumber) {
		this.myStaffNumber = theStaffNumber;
	}
	public String getMyTelephoneExt() {
		return myTelephoneExt;
	}
	public void setMyTelephoneExt(String theTelephoneExt) {
		this.myTelephoneExt = theTelephoneExt;
	}
	public String getMyVehicleLicenseNumber() {
		return myVehicleLicenseNumber;
	}
	public void setMyVehicleLicenseNumber(String theVehicleLicenseNumber) {
		this.myVehicleLicenseNumber = theVehicleLicenseNumber;
	}
	
	
}
