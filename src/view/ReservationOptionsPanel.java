package view;

import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import entity.Guest;
import entity.Request;
import entity.Reservation;
import entity.Room;
import enums.Status;
import hotel.Hotel;
import models.RequestModel;
import models.ReservationModel;

public class ReservationOptionsPanel extends JPanel {
	public ReservationOptionsPanel(Hotel hotel) {
		GroupLayout reservationOptionsLayout = new GroupLayout(this);
		reservationOptionsLayout.setAutoCreateContainerGaps(true);
		reservationOptionsLayout.setAutoCreateGaps(true);
		ArrayList<Request> validRequests = new ArrayList<Request>();
		for(Request i:hotel.rem.requests) {
			if(i.status == Status.POTVRDJENA) {
				validRequests.add(i);
			}
		}
		RequestModel reqData = new RequestModel(validRequests);
		JTable requestTable = new JTable(reqData);
		JScrollPane reqTableContainer = new JScrollPane(requestTable);
		ReservationModel resData = new ReservationModel(hotel.rem.reservations);
		reqTableContainer.setBorder(BorderFactory.createTitledBorder("Potvrđeni zahtevi"));
		JTable reservationTable = new JTable(resData);
		JScrollPane resTableContainer = new JScrollPane(reservationTable);
		resTableContainer.setBorder(BorderFactory.createTitledBorder("Rezervacije"));
		JLabel IDLabel = new JLabel("ID:");
		JLabel guestLabel = new JLabel("Gost:");
		JLabel roomLabel = new JLabel("Soba:");
		JLabel beginLabel = new JLabel("Početak rezervacije:");
		JLabel endLabel = new JLabel("Kraj rezervacije:");
		JTextField IDField = new JTextField();
		IDField.setEditable(false);
		JTextField guestField = new JTextField();
		JTextField roomField = new JTextField();
		JTextField startField = new JTextField();
		JTextField endField = new JTextField();
		JPanel checkBoxContainer = new JPanel(new GridLayout(3, 4));
		ArrayList<JCheckBox> boxes = new ArrayList<JCheckBox>();
		for(String i:hotel.pm.services) {
			JCheckBox box = new JCheckBox(i);
			boxes.add(box);
			checkBoxContainer.add(box);
		}
		JButton addButton = new JButton("Check In");
		JButton deleteButton = new JButton("Poništite rezervaciju");
		JButton changeButton = new JButton("Izmenite rezervaciju");
		JButton saveButton = new JButton("Sačuvajte izmene");
		JButton checkOutButton = new JButton("Check Out");
		JButton refreshButton = new JButton("Osvežite tabelu");
		saveButton.setEnabled(false);
		startField.setText("yyyy-mm-dd");
		endField.setText("yyyy-mm-dd");
		reservationOptionsLayout.setHorizontalGroup(reservationOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(reqTableContainer)
				.addComponent(resTableContainer)
				.addComponent(refreshButton)
				.addGroup(reservationOptionsLayout.createSequentialGroup()
						.addGroup(reservationOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(IDLabel)
								.addComponent(guestLabel)
								.addComponent(roomLabel)
								.addComponent(beginLabel)
								.addComponent(endLabel))
						.addGroup(reservationOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(IDField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(guestField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(roomField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(startField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(endField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(checkBoxContainer)
						.addGroup(reservationOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(addButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(changeButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(saveButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(deleteButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(checkOutButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						)
				);
		reservationOptionsLayout.setVerticalGroup(reservationOptionsLayout.createSequentialGroup()
				.addComponent(reqTableContainer)
				.addComponent(resTableContainer)
				.addComponent(refreshButton)
				.addGroup(reservationOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addGroup(reservationOptionsLayout.createSequentialGroup()
								.addGroup(reservationOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(IDLabel)
										.addComponent(IDField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(reservationOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(guestLabel)
										.addComponent(guestField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(reservationOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(roomLabel)
										.addComponent(roomField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(reservationOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(beginLabel)
										.addComponent(startField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(reservationOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(endLabel)
										.addComponent(endField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								)
						.addComponent(checkBoxContainer)
						.addGroup(reservationOptionsLayout.createSequentialGroup()
								.addComponent(addButton)
								.addComponent(changeButton)
								.addComponent(saveButton)
								.addComponent(deleteButton)
								.addComponent(checkOutButton))
						)
				);
		setLayout(reservationOptionsLayout);
		
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (requestTable.getSelectedRow() != -1) {
						Request r = reqData.getData(requestTable.getSelectedRow());
						JDialog chooseRoom = new JDialog();
						FlowLayout dialogLayout = new FlowLayout(FlowLayout.RIGHT);
						chooseRoom.setLayout(dialogLayout);
						chooseRoom.setTitle("Odabir sobe");
						chooseRoom.setResizable(false);
						chooseRoom.setBounds(300, 300, 250, 75);
						JLabel roomLabel = new JLabel("Odaberite sobu:");
						DefaultComboBoxModel rooms = new DefaultComboBoxModel();
						ArrayList<Room> available = hotel.availableRooms(r.type, r.begin, r.end);
						for(Room i:available) {
							rooms.addElement(i.getRoomNumber());
						}
						JComboBox roomPicker = new JComboBox(rooms);
						JButton apply = new JButton("Primenite");
						apply.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								String roomNumber = (String) roomPicker.getSelectedItem();
								hotel.checkIn(r, hotel.rom.readRoom(roomNumber));
								resData.fireTableDataChanged();
								reqData.fireTableDataChanged();
								chooseRoom.dispose();
							}
						});
						chooseRoom.getContentPane().add(roomLabel);
						chooseRoom.getContentPane().add(roomPicker);
						chooseRoom.getContentPane().add(apply);
						chooseRoom.setModalityType(ModalityType.APPLICATION_MODAL);
						chooseRoom.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
					}
					
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
				}
			}
		});
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (reservationTable.getSelectedRow() != -1) {
					saveButton.setEnabled(true);
					Reservation r = resData.getData(reservationTable.getSelectedRow());
					IDField.setText(r.getID());
					guestField.setText(r.guest.getUsername());
					roomField.setText(r.room.getRoomNumber());
					startField.setText(r.begin.toString());
					endField.setText(r.end.toString());
					for(String i:r.services) {
						for(JCheckBox j:boxes) {
							if(j.getText().equals(i)) {
								j.setSelected(true);
							}
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Reservation change = hotel.rem.readReservation(IDField.getText());
					Guest guest = (Guest) hotel.um.readUser(guestField.getText());
					Room room = hotel.rom.readRoom(roomField.getText());
					LocalDate start = LocalDate.parse(startField.getText());
					LocalDate end = LocalDate.parse(endField.getText());
					ArrayList<String> services = new ArrayList<String>();
					for(JCheckBox i:boxes) {
						if (i.isSelected()) {
							services.add(i.getText());
						}
					}
					if (hotel.validateReservationChanges(change, room, start, end)){
						hotel.rem.updateReservation(change.getID(), guest, room, start, end, Status.POTVRDJENA, 0, services);
						hotel.applyPricing(change);
						resData.fireTableDataChanged();
						saveButton.setEnabled(false);
					}else {
						JOptionPane.showMessageDialog(null, "Rezervacija se ne može izmeniti datim parametrima!");
					}
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Uneti su neispravni podaci!");
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					hotel.rem.deleteRequest((String) resData.getValueAt(reservationTable.getSelectedRow(), 0));
					resData.fireTableDataChanged();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		checkOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (reservationTable.getSelectedRow() != -1) {
					Reservation r = resData.getData(reservationTable.getSelectedRow());
					if(r.status == Status.POTVRDJENA) {
						hotel.checkOut(r);
						resData.fireTableDataChanged();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validRequests.clear();
				for(Request i:hotel.rem.requests) {
					if(i.status == Status.POTVRDJENA) {
						validRequests.add(i);
					}
				}
				reqData.fireTableDataChanged();
				resData.fireTableDataChanged();
			}
		});
		startField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                startField.setText("");
            }
        });
		endField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                endField.setText("");
            }
        });
	}
}
