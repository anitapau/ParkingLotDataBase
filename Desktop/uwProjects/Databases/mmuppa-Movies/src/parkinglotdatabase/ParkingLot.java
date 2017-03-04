package parkinglotdatabase;

/**
 * Class that refers to the parkinglot
 * 
 * @author anita paudel
 * @version 02/25/2017
 */
public class ParkingLot {
	/**
	 * Lotname of the lot
	 */
	private String myLotName;
	/**
	 * location of the parking lot
	 */
	private String myLocation;
	/**
	 * capacity to allow spaces use
	 */
	private int myCapacity;
	/**
	 * floors number
	 */
	private int myFloors;
	
	/**
	 * Default Constructor for the parking lot.
	 */
	public ParkingLot() {
		
	}
	/**
	 * Constructor to set the values for the fields
	 * @param theLotName lot name
	 * @param theLocation location name
	 * @param theCapacity capacity value
	 * @param theFloors floor number
	 * 	 */
	public ParkingLot(String theLotName, String theLocation, int theCapacity, int theFloors) {
		setMyLotName(theLotName);;
		setMyLocation(theLocation);
		setMyCapacity(theCapacity);
		setMyFloors(theFloors);
	}

	/**
	 * Get the lotName
	 * 
	 * @return theLotName
	 */
	public String getMyLotName() {
		return myLotName;
	}

	/**
	 * set the lotName
	 * 
	 * @param theLotName name of the lot
	 */
	public void setMyLotName(String theLotName) {
		this.myLotName = theLotName;
	}

	/**
	 * Get the location
	 * 
	 * @return the location
	 */
	public String getMyLocation() {
		return myLocation;
	}

	/**
	 * set the lotName
	 * 
	 * @param theLocation
	 *            location of the lot
	 */
	public void setMyLocation(String theLocation) {
		this.myLocation = theLocation;
	}

	/**
	 * Get the capacity
	 * 
	 * @return the capacity
	 */
	public int getMyCapacity() {
		return myCapacity;
	}

	/**
	 * set the capacity
	 * 
	 * @param theCapacity capacity of the lot
	 */
	public void setMyCapacity(int theCapacity) {
		this.myCapacity = theCapacity;
	}

	/**
	 * Get the floors
	 * 
	 * @return the floor number
	 */
	public int getMyFloors() {
		return myFloors;
	}

	/**
	 * set the floors
	 * 
	 * @param theFloors floors of the lot
	 */
	public void setMyFloors(int theFloors) {
		this.myFloors = theFloors;
	}

}
