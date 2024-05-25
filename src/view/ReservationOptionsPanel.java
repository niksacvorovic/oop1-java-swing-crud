package view;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import hotel.Hotel;
import models.ReservationModel;

public class ReservationOptionsPanel extends JPanel {
	public ReservationOptionsPanel(Hotel hotel) {
		GroupLayout reservationOptionsLayout = new GroupLayout(this);
		ReservationModel data = new ReservationModel(hotel.rem.reservations);
		JTable reservationTable = new JTable(data);
		JScrollPane tableContainer = new JScrollPane(reservationTable);
		reservationOptionsLayout.setHorizontalGroup(reservationOptionsLayout.createSequentialGroup()
				.addComponent(tableContainer));
		reservationOptionsLayout.setVerticalGroup(reservationOptionsLayout.createSequentialGroup()
				.addComponent(tableContainer));
	}
}
