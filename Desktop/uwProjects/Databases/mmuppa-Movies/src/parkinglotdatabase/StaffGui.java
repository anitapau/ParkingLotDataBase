package parkinglotdatabase;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
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

public class StaffGui extends JFrame implements ActionListener, TableModelListener{
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnList, btnSearch, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private JPanel pnlSearch;
	private JLabel lblstaffnum;;
	private JTextField txtStaffNum;
	private JButton btnstaffNumSearch;

	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[7];
	private JTextField[] txfField = new JTextField[7];
	private JButton btnAddStaff;
	private List<Staff> list;
	private String[] columnNames = { "staffNumber", "telephoneExt", "vehicleLicenceNumber"};

	private Object[][] data;
	private StaffDB myDb;
	
	
	

	public StaffGui() {
				myDb = new StaffDB();
		try {
			list = myDb.getStaff();
			data = new Object[list.size()][columnNames.length];
			for (int i = 0; i < list.size(); i++) {
				data[i][0] = list.get(i).getMyStaffNumber();
				data[i][1] = list.get(i).getMyTelephoneExt();
				data[i][2] = list.get(i).getMyVehicleLicenseNumber();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		createComponents();
		setVisible(true);
		setSize(500, 500);
	}
	
	/**
	 * Creates panels for Movie list, search, add and adds the corresponding
	 * components to each panel.
	 */
	private void createComponents() {
		pnlButtons = new JPanel();
		btnList = new JButton("Staff List");
		btnList.addActionListener(this);

		btnSearch = new JButton("Staff Search");
		btnSearch.addActionListener(this);

		btnAdd = new JButton("Add Staff");
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
		lblstaffnum = new JLabel("Enter StaffNumber: ");
		txtStaffNum = new JTextField(25);
		btnstaffNumSearch = new JButton("Search");
		btnstaffNumSearch.addActionListener(this);
		pnlSearch.add(lblstaffnum);
		pnlSearch.add(txtStaffNum);
		pnlSearch.add(btnstaffNumSearch);

		// Add Panel
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(8, 0));
		String labelNames[] = { "Enter staffNumber: ", "Enter telephoneExt: ", "Enter vehicleLicenseNumber: "};
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
        if(!columnName.equals((String) "staffNumber")) {
        	Object data = model.getValueAt(row, column);
            myDb.updateStaff(row, columnName, data);
        }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnList) {
			try {
				list = myDb.getStaff();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			data = new Object[list.size()][columnNames.length];
			for (int i = 0; i < list.size(); i++) {
				data[i][0] = list.get(i).getMyStaffNumber();
				data[i][1] = list.get(i).getMyTelephoneExt();
				data[i][2] = list.get(i).getMyVehicleLicenseNumber();
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
			int staffNum = Integer.parseInt(txtStaffNum.getText());
			if (staffNum > 0) {
				list = myDb.getStaff(staffNum);
				data = new Object[list.size()][columnNames.length];
				for (int i = 0; i < list.size(); i++) {
					data[i][0] = list.get(i).getMyStaffNumber();
					data[i][1] = list.get(i).getMyTelephoneExt();
					data[i][2] = list.get(i).getMyVehicleLicenseNumber();
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
			Staff staff = new Staff(Integer.parseInt(txfField[0].getText()), txfField[1].getText(),
					txfField[2].getText());
			myDb.addStaff(staff);
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			for (int i = 0; i < txfField.length; i++) {
				txfField[i].setText("");
			}
		}
	}

}
