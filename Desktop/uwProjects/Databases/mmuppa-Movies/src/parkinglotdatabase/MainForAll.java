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

public class MainForAll extends JFrame implements ActionListener {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnLot, btnSpace, btnStaff;
	private JPanel pnlButtons;
	private JButton btnSpaceBook;

	public MainForAll() {
		createComponents();
		setVisible(true);
		setResizable(false);
		setSize(500, 500);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainForAll mai = new MainForAll();
		mai.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Creates panels for Movie list, search, add and adds the corresponding
	 * components to each panel.
	 */
	private void createComponents() {
		pnlButtons = new JPanel();
		btnLot = new JButton("Lot");
		btnLot.addActionListener(this);

		btnSpace = new JButton("Space");
		btnSpace.addActionListener(this);

		btnStaff = new JButton("Staff");
		btnStaff.addActionListener(this);
		btnSpaceBook = new JButton("SpaceBooking");
		btnSpaceBook.addActionListener(this);
		pnlButtons.add(btnLot);
		pnlButtons.add(btnSpace);
		pnlButtons.add(btnSpaceBook);
		pnlButtons.add(btnStaff);
		add(pnlButtons, BorderLayout.NORTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (e.getSource() == btnLot) {
				ParkingLotGui lot = new ParkingLotGui();
				/*btnLot.setEnabled(false);
				btnStaff.setEnabled(true);
				btnSpace.setEnabled(true);
				btnSpaceBook.setEnabled(true);*/
			} 
		
		else if (e.getSource() == btnStaff) {
			StaffGui gui = new StaffGui();
		/*	btnLot.setEnabled(true);
			btnSpace.setEnabled(true);
			btnStaff.setEnabled(false);
			btnSpaceBook.setEnabled(true);*/
		}
		else if (e.getSource() == btnSpace) {
			SpaceGui space = new SpaceGui();
			/*btnStaff.setEnabled(true);
			btnLot.setEnabled(true);
			btnSpace.setEnabled(false);
			btnSpaceBook.setEnabled(true);*/
		}
		else if (e.getSource() == btnSpaceBook) {
			try {
				SpaceBookGui booking = new SpaceBookGui();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/*btnStaff.setEnabled(true);
			btnLot.setEnabled(true);
			btnSpaceBook.setEnabled(false);
			btnSpace.setEnabled(true);*/
		}		
	}

}
