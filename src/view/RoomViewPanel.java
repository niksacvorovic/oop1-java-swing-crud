package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import entity.Room;
import hotel.Hotel;
import models.RoomModel;

public class RoomViewPanel extends JPanel {
	public RoomViewPanel(Hotel hotel) {
		GroupLayout roomOptionsLayout = new GroupLayout(this);
		DefaultListModel<String> roomTypesListModel = new DefaultListModel<String>();
		DefaultComboBoxModel<String> roomTypesComboBoxModel = new DefaultComboBoxModel<String>();
		for(String i:hotel.rom.roomTypes) {
			roomTypesListModel.addElement(i);
			roomTypesComboBoxModel.addElement(i);
		}
		roomOptionsLayout.setAutoCreateContainerGaps(true);
		roomOptionsLayout.setAutoCreateGaps(true);
		setLayout(roomOptionsLayout);
		RoomModel data = new RoomModel(hotel.rom.rooms);
		JTable roomTable = new JTable(data);
		JScrollPane tableContainer = new JScrollPane(roomTable);
		
		roomOptionsLayout.setHorizontalGroup(roomOptionsLayout.createSequentialGroup()
				.addComponent(tableContainer));
		
		roomOptionsLayout.setVerticalGroup(roomOptionsLayout.createParallelGroup()
				.addComponent(tableContainer));
	}
}
