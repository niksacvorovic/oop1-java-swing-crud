package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

import entity.Reservation;
import entity.Room;
import enums.Status;
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
		roomTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(roomTable.getSelectedRow() != -1 && e.getClickCount() == 2) {
					Room check = data.getData(roomTable.getSelectedRow());
					boolean isReserved = false;
					for(Reservation i:check.reservations) {
						if(i.status == Status.POTVRDJENA) {
							isReserved = true;
						}
					}
					if(check.reservations.size() == 0 || !isReserved) {
						JOptionPane.showMessageDialog(null, "Soba " + check.getRoomNumber() + " nema zakazane rezervacije");
					}else {
						String text = "Soba " + check.getRoomNumber() + " je rezervisana tokom sledećih perioda: ";
						for(Reservation i:check.reservations) {
							if(i.status == Status.POTVRDJENA) {
								text += "\n" + i.begin.toString() + " - " + i.end.toString();
							}	
						}
						JOptionPane.showMessageDialog(null, text);
					}
				}
			}
		});
	}
}
