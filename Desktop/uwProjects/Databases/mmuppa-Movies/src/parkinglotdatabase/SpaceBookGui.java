package parkinglotdatabase;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.DateFormatter;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


public class SpaceBookGui extends JFrame implements ActionListener, TableModelListener{

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		SpaceBookGui gui = new SpaceBookGui();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnList, btnSearch, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private JPanel pnlSearch;
	private JLabel lblBookId;;
	private JTextField txtStaffNum;
	private JButton btnstaffNumSearch;

	private JPanel pnlAdd;
	private JLabel parkingLot;
	private JLabel dateofVisit;
	private JLabel bookLabel;
	private JLabel spaceNum;
	private JLabel staffNum;
	private JLabel[] txfLabel = new JLabel[7];
	private JTextField[] txfField = new JTextField[7];
	private JButton btnAddStaff;
	private List<SpaceBooking> list;
	private String[] columnNames = { "bookingId", "spaceNumber", "staffNumber", "visitorLicense", "dateOfVisit"};
	private Object[][] data;
	private SpaceBookingDb myDb;
	
	private JComboBox<Integer> availableSpaces;
	JComboBox<Integer> staffNumbers;
	JDatePickerImpl datePicker;
	JComboBox<String> bookType;
	

	public SpaceBookGui() throws SQLException {
				myDb = new SpaceBookingDb();
		try {
			list = myDb.getBooking();
			data = new Object[list.size()][columnNames.length];
			for (int i = 0; i < list.size(); i++) {
				data[i][0] = list.get(i).getMyBookingId();
				data[i][1] = list.get(i).getMySpaceNumber();
				data[i][2] = list.get(i).getMyStaffNumber();
				data[i][3] = list.get(i).getMyVisitorLicense();
				data[i][4] = list.get(i).getMyDateofVisit();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		createComponents();
		setVisible(true);
		setSize(1000, 1000);
	}
	
	/**
	 * Creates panels for Movie list, search, add and adds the corresponding
	 * components to each panel.
	 * @throws SQLException 
	 */
	private void createComponents() throws SQLException {
		pnlButtons = new JPanel();
		btnList = new JButton("Booking List");
		btnList.addActionListener(this);

		btnSearch = new JButton("Booking Search");
		btnSearch.addActionListener(this);

		btnAdd = new JButton("Make Booking");
		btnAdd.addActionListener(this);

		pnlButtons.add(btnList);
		pnlButtons.add(btnSearch);
		pnlButtons.add(btnAdd);
		add(pnlButtons, BorderLayout.NORTH);

		// List Panel
		pnlContent = new JPanel();
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		table.getModel().addTableModelListener(this);

		// Search Panel
		pnlSearch = new JPanel();
		lblBookId = new JLabel("Enter bookingId: ");
		txtStaffNum = new JTextField(25);
		btnstaffNumSearch = new JButton("Search");
		btnstaffNumSearch.addActionListener(this);
		pnlSearch.add(lblBookId);
		pnlSearch.add(txtStaffNum);
		pnlSearch.add(btnstaffNumSearch);

		// Add Panel
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(8, 0));
		//labels select
		parkingLot = new JLabel("select Lot: ");
		pnlAdd.add(parkingLot);
		JComboBox<String> lotList = new JComboBox<>();
		pnlAdd.add(lotList);
		
		UtilDateModel model = new net.sourceforge.jdatepicker.impl.UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		dateofVisit = new JLabel("select Date: ");
		pnlAdd.add(dateofVisit);

		pnlAdd.add(datePicker);
	
		spaceNum = new JLabel("select space Number: ");
		pnlAdd.add(spaceNum);
		availableSpaces = new JComboBox<>();
		availableSpaces.setSize(10, 10);
		pnlAdd.add(availableSpaces);
		SpaceDB spaceDb = new SpaceDB();
		
		lotList.addActionListener(new ActionListener() {
			String selectedLot = null;
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedLot =  (String) lotList.getSelectedItem();
				datePicker.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						availableSpaces.removeAllItems();
						Date selectedValue = (Date) datePicker.getModel().getValue();
						SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-M-dd");
						String selectedDate = dFormat.format(selectedValue);
						try {
							List<Space> availableSpaceByDate = spaceDb.getAvailableSpaceByLotAndDate(selectedLot, selectedDate);
							for(Space s: availableSpaceByDate){
								availableSpaces.addItem(s.getMySpaceNumber());
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});;
				
			}
		});
		

		ParkingLotDB pdb = new ParkingLotDB();
		List<ParkingLot> parkingList = pdb.getLot();
		for(ParkingLot lot: parkingList){
			lotList.addItem(lot.getMyLotName());
		}
		
		staffNumbers = new JComboBox<>();
		staffNum = new JLabel("select staff Number: ");
		pnlAdd.add(staffNum);
		pnlAdd.add(staffNumbers);
		StaffDB staffDB = new StaffDB();
		List<Staff> staffList = staffDB.getStaff();
		for(Staff staff: staffList){
			staffNumbers.addItem(staff.getMyStaffNumber());
		}
		bookLabel = new JLabel("Type of Booking: ");
		pnlAdd.add(bookLabel);
		bookType = new JComboBox<>();
		bookType.addItem("STAFF");
		bookType.addItem("VISITER");
		pnlAdd.add(bookType);
		
		
		String labelNames[] = {"Enter vehicleLicenseNumber: "};
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}
		JPanel panel = new JPanel();
		btnAddStaff = new JButton("Add");
		btnAddStaff.addActionListener(this);
		panel.add(btnAddStaff);
		pnlAdd.add(panel);

		add(pnlContent, BorderLayout.CENTER);
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        myDb.updateBooking(row, columnName, data);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnList) {
			try {
				list = myDb.getBooking();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			data = new Object[list.size()][columnNames.length];
			for (int i = 0; i < list.size(); i++) {
				data[i][0] = list.get(i).getMyBookingId();
				data[i][1] = list.get(i).getMySpaceNumber();
				data[i][2] = list.get(i).getMyStaffNumber();
				data[i][3] = list.get(i).getMyVisitorLicense();
				data[i][4] = list.get(i).getMyDateofVisit();
			}
			pnlContent.removeAll();
			table = new JTable(data, columnNames);
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			pnlContent.add(scrollPane);
			pnlContent.revalidate();
			this.repaint();

		} else if (e.getSource() == btnSearch) {
			pnlContent.removeAll();
			pnlContent.add(pnlSearch);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAdd) {
			pnlContent.removeAll();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
			this.repaint();

		} else if (e.getSource() == btnstaffNumSearch) {
			String bookingId = txtStaffNum.getText();
			if (bookingId!= null || bookingId != "") {
				list = myDb.getBooking(bookingId);
				data = new Object[list.size()][columnNames.length];
				for (int i = 0; i < list.size(); i++) {
					data[i][0] = list.get(i).getMyBookingId();
					data[i][1] = list.get(i).getMySpaceNumber();
					data[i][2] = list.get(i).getMyStaffNumber();
					data[i][3] = list.get(i).getMyVisitorLicense();
					data[i][4] = list.get(i).getMyDateofVisit();
				}
				pnlContent.removeAll();
				table = new JTable(data, columnNames);
				table.getModel().addTableModelListener(this);
				scrollPane = new JScrollPane(table);
				pnlContent.add(scrollPane);
				pnlContent.revalidate();
				this.repaint();
			}
		} else if (e.getSource() == btnAddStaff) {
			
			Integer space = (Integer) availableSpaces.getSelectedItem();
			Integer staff = (Integer) staffNumbers.getSelectedItem();
			String vLicense = (String)txfField[0].getText();
			System.out.println("VVV"+vLicense);
			Date selectedValue = (Date) datePicker.getModel().getValue();
			SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-M-dd");
			String selectedDate = dFormat.format(selectedValue);
			String bType = (String)bookType.getSelectedItem();
			
			if(bType.equals("VISITER")&& (vLicense.isEmpty()|| vLicense==null)){
				JOptionPane.showMessageDialog(null, "Visitor License number is Required!");
			}else{
				SpaceBooking booking = new SpaceBooking(txfField[0].getText(), space,staff, vLicense, selectedDate);
				myDb.makeBooking(booking);
				JOptionPane.showMessageDialog(null, "Added Successfully!");
				for (int i = 0; i < txfField.length; i++) {
					txfField[i].setText("");
				}
			}
			
			
		}
	}


}
