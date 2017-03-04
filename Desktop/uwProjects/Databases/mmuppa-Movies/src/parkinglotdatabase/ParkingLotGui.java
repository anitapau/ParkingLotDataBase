package parkinglotdatabase;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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

public class ParkingLotGui extends JFrame implements ActionListener, TableModelListener {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnList, btnSearch, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private JPanel pnlSearch;
	private JLabel lblLotName;;
	private JTextField txtlotName;
	private JButton btnLotNameSearch;

	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[5];
	private JTextField[] txfField = new JTextField[5];
	private JButton btnAddLot;
	private List<ParkingLot> list;
	private String[] columnNames = { "lotName", "location", "capacity", "floors" };

	private Object[][] data;
	private ParkingLotDB myDb;

	public ParkingLotGui() {
		myDb = new ParkingLotDB();
		try {
			list = myDb.getLot();
			data = new Object[list.size()][columnNames.length];
			for (int i = 0; i < list.size(); i++) {
				data[i][0] = list.get(i).getMyLotName();
				data[i][1] = list.get(i).getMyLocation();
				data[i][2] = list.get(i).getMyCapacity();
				data[i][3] = list.get(i).getMyFloors();

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
		btnList = new JButton("Lot List");
		btnList.addActionListener(this);

		btnSearch = new JButton("Lot Search");
		btnSearch.addActionListener(this);

		btnAdd = new JButton("Add Lot");
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
		lblLotName = new JLabel("Enter LotName: ");
		txtlotName = new JTextField(25);
		btnLotNameSearch = new JButton("Search");
		btnLotNameSearch.addActionListener(this);
		pnlSearch.add(lblLotName);
		pnlSearch.add(txtlotName);
		pnlSearch.add(btnLotNameSearch);

		// Add Panel
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(6, 0));
		String labelNames[] = { "Enter LotName: ", "Enter Location: ", "Enter Capacity: ", "Enter Floors: " };
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}
		JPanel panel = new JPanel();
		btnAddLot = new JButton("Add");
		btnAddLot.addActionListener(this);
		panel.add(btnAddLot);
		pnlAdd.add(panel);

		add(pnlContent, BorderLayout.CENTER);
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		String columnName = model.getColumnName(column);
		Object data = model.getValueAt(row, column);

		// myDb.updateParkingLot(row, columnName, data);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnList) {
			try {
				list = myDb.getLot();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			data = new Object[list.size()][columnNames.length];
			for (int i = 0; i < list.size(); i++) {
				data[i][0] = list.get(i).getMyLotName();
				data[i][1] = list.get(i).getMyLocation();
				data[i][2] = list.get(i).getMyCapacity();
				data[i][3] = list.get(i).getMyFloors();
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

		} else if (e.getSource() == btnLotNameSearch) {
			String lotName = txtlotName.getText();
			if (lotName.length() > 0) {
				list = myDb.getLot(lotName);
				data = new Object[list.size()][columnNames.length];
				for (int i = 0; i < list.size(); i++) {
					data[i][0] = list.get(i).getMyLotName();
					data[i][1] = list.get(i).getMyLocation();
					data[i][2] = list.get(i).getMyCapacity();
					data[i][3] = list.get(i).getMyFloors();
				}
				pnlContent.removeAll();
				table = new JTable(data, columnNames);
				table.getModel().addTableModelListener(this);
				scrollPane = new JScrollPane(table);
				pnlContent.add(scrollPane);
				pnlContent.revalidate();
				this.repaint();
			}
		} else if (e.getSource() == btnAddLot) {
			ParkingLot lot = new ParkingLot(txfField[0].getText(), txfField[1].getText(),
					Integer.parseInt(txfField[2].getText()), Integer.parseInt(txfField[3].getText()));
			myDb.addLot(lot);
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			for (int i = 0; i < txfField.length; i++) {
				txfField[i].setText("");
			}
		}
	}
	
	

}
