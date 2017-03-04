package parkinglotdatabase;
/**
 * A class that is made for staff booking
 * @author anita paudel
 *
 */
public class SpaceBooking {
	/**
	 * Booking id for the space
	 */
	private String myBookingId;
	/**
	 * Space Number related to booking
	 */
	private int mySpaceNumber;
	/**
	 * staff number who is making the booking
	 */
	private int myStaffNumber;
	/**
	 * visitor license number
	 */
	private String myVisitorLicense;
	/**
	 * Date of the visit to the parking space
	 */
	private String myDateofVisit;
	/**
	 * Default constructor
	 */
	public SpaceBooking() {}
	/**
	 * Parameterized constructor
	 */
	public SpaceBooking(String bookingId, int spaceNum, int staffNum, String vehicleLicNum, String dateVisit) {
		myBookingId = bookingId;
		mySpaceNumber = spaceNum;
		myStaffNumber = staffNum;
		myVisitorLicense = vehicleLicNum;
		myDateofVisit = dateVisit;
	}
	/**
	 * Booking id made by the staff
	 * 
	 * @return booking id
	 */
	public String getMyBookingId() {
		return myBookingId;
	}

	/**
	 * Set the booking id
	 * 
	 * @param theBookingId to be set
	 */
	public void setMyBookingId(String theBookingId) {
		this.myBookingId = theBookingId;
	}

	/**
	 * get the space number
	 * 
	 * @return space number that exists
	 */
	public int getMySpaceNumber() {
		return mySpaceNumber;
	}

	/**
	 * Set the space number
	 * 
	 * @param theSpaceNumber that is booked
	 */
	public void setMySpaceNumber(int theSpaceNumber) {
		this.mySpaceNumber = theSpaceNumber;
	}

	/**
	 * get the staffNumber
	 * 
	 * @return the staffNumber
	 */
	public int getMyStaffNumber() {
		return myStaffNumber;
	}

	/**
	 * Set the staff number
	 * 
	 * @param theStaffNumber staff number
	 */
	public void setMyStaffNumber(int theStaffNumber) {
		this.myStaffNumber = theStaffNumber;
	}

	/**
	 * Get the visitor license number
	 * 
	 * @return the visitor license number
	 */
	public String getMyVisitorLicense() {
		return myVisitorLicense;
	}

	/**
	 * set the theVisitorLicense license number
	 */
	public void setMyVisitorLicense(String theVisitorLicense) {
		this.myVisitorLicense = theVisitorLicense;
	}

	/**
	 * get the date of visit
	 * 
	 * @return the visit date
	 */
	public String getMyDateofVisit() {
		return myDateofVisit;
	}

	/**
	 * set the date of visit
	 * 
	 * @param theDateofVisit the date of visit
	 */
	public void setMyDateofVisit(String theDateofVisit) {
		this.myDateofVisit = theDateofVisit;
	}
}
