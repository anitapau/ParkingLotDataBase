package parkinglotdatabase;
/**
 * Class for the space 
 * @author anita paudel
 * @version 02/25/2017
 */
public class Space {
	
	// space number for this space
	private int mySpaceNumber;
	
	// space type for this space
	private String mySpaceType;
	
	// lot name for this space
	private String myLotName;
	// monthly rate for covered space
	private double monthlyRateCovered;
	


	/**
	 * get the spaceNumber
	 * 
	 * @return the space number
	 */
	/**
	 * Default constructor
	 */
	public Space() {
		
	}
	
	public Space(int theSpaceNum, String theSpaceType, String theLotname) {
		setMySpaceNumber(theSpaceNum);
		setMySpaceType(theSpaceType);
		setMyLotName(theLotname);
	}
	public int getMySpaceNumber() {
		return mySpaceNumber;
	}

	/**
	 * Set the spaceNumber
	 * 
	 * @param theSpaceNumber to be set
	 */
	public void setMySpaceNumber(int theSpaceNumber) {
		this.mySpaceNumber = theSpaceNumber;
	}

	/**
	 * get the spaceType
	 * 
	 * @return the space type
	 */
	public String getMySpaceType() {
		return mySpaceType;
	}

	/**
	 * Set the space type
	 * 
	 * @param theSpaceType space type for the space
	 */
	public void setMySpaceType(String theSpaceType) {
		this.mySpaceType = theSpaceType;
	}

	/**
	 * get the lotName
	 * 
	 * @return the lot Name
	 */
	public String getMyLotName() {
		return myLotName;
	}

	/**
	 * Set the lot name
	 * 
	 * @param theLotName lot name to be set
	 */
	public void setMyLotName(String theLotName) {
		this.myLotName = theLotName;
	}
	
	/**
	 * Getter for monthly rate.
	 * @return monthly rate
	 */
	public double getMonthlyRateCovered() {
		return monthlyRateCovered;
	}
	
	/**
	 * Setter for monthly Rate
	 * @param monthlyRateCovered
	 */
	public void setMonthlyRateCovered(double monthlyRateCovered) {
		this.monthlyRateCovered = monthlyRateCovered;
	}
}
