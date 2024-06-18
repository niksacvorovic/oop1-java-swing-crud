package view;

import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultRowSorter;
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
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import entity.Guest;
import entity.Request;
import entity.Reservation;
import entity.Room;
import entity.RoomFeature;
import enums.Status;
import hotel.Hotel;
import models.RequestModel;
import models.ReservationModel;

public class ReservationOptionsPanel extends JPanel {
	public ReservationOptionsPanel(Hotel hotel, RequestModel reqData, ReservationModel resData) {
		GroupLayout reservationOptionsLayout = new GroupLayout(this);
		reservationOptionsLayout.setAutoCreateContainerGaps(true);
		reservationOptionsLayout.setAutoCreateGaps(true);
		ArrayList<Request> validRequests = new ArrayList<Request>();
		JTable requestTable = new JTable(reqData);
		JScrollPane reqTableContainer = new JScrollPane(requestTable);
		reqTableContainer.setBorder(BorderFactory.createTitledBorder("Potvrđeni zahtevi"));
		JTable reservationTable = new JTable(resData);
		JScrollPane resTableContainer = new JScrollPane(reservationTable);
		resTableContainer.setBorder(BorderFactory.createTitledBorder("Rezervacije"));
		requestTable.setAutoCreateRowSorter(true);
		reservationTable.setAutoCreateRowSorter(true);
		RowFilter<Object, Object> acceptedFilter = new RowFilter<Object, Object>() {
			public boolean include(Entry entry) {
				Status status = (Status) entry.getValue(2);
				return status == Status.POTVRDJENA;
				}
			};
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(reqData);
		sorter.setRowFilter(acceptedFilter);
		requestTable.setRowSorter(sorter);
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
		ArrayList<JCheckBox> serviceBoxes = new ArrayList<JCheckBox>();
		ArrayList<JCheckBox> featureBoxes = new ArrayList<JCheckBox>();
		for(String i:hotel.pm.services) {
			JCheckBox box = new JCheckBox(i);
			serviceBoxes.add(box);
			checkBoxContainer.add(box);
		}
		for(RoomFeature i:hotel.rfm.roomFeatures) {
			JCheckBox box = new JCheckBox(i.getName());
			featureBoxes.add(box);
			checkBoxContainer.add(box);
		}
		JButton addButton = new JButton("Check In");
		JButton deleteButton = new JButton("Poništite rezervaciju");
		JButton changeButton = new JButton("Izmenite rezervaciju");
		JButton saveButton = new JButton("Sačuvajte izmene");
		JButton filterButton = new JButton("Filtrirajte tabele");
		JButton checkOutButton = new JButton("Check Out");
		saveButton.setEnabled(false);
		startField.setText("yyyy-mm-dd");
		endField.setText("yyyy-mm-dd");
		reservationOptionsLayout.setHorizontalGroup(reservationOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(filterButton)
				.addComponent(reqTableContainer)
				.addComponent(resTableContainer)
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
				.addComponent(filterButton)
				.addComponent(reqTableContainer)
				.addComponent(resTableContainer)
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
						int index = requestTable.convertRowIndexToModel(requestTable.getSelectedRow());
						Request r = hotel.rem.readRequest((String) reqData.getValueAt(index, 0));
						JDialog chooseRoom = new JDialog();
						FlowLayout dialogLayout = new FlowLayout(FlowLayout.RIGHT);
						chooseRoom.setLayout(dialogLayout);
						chooseRoom.setTitle("Odabir sobe");
						chooseRoom.setResizable(false);
						chooseRoom.setBounds(300, 300, 250, 75);
						JLabel roomLabel = new JLabel("Odaberite sobu:");
						DefaultComboBoxModel rooms = new DefaultComboBoxModel();
						ArrayList<Room> available = hotel.availableRooms(r.type, r.begin, r.end, r.services);
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
					int index = reservationTable.convertRowIndexToModel(reservationTable.getSelectedRow());
					saveButton.setEnabled(true);
					Reservation r = resData.getData(index);
					IDField.setText(r.getID());
					guestField.setText(r.guest.getUsername());
					roomField.setText(r.room.getRoomNumber());
					startField.setText(r.begin.toString());
					endField.setText(r.end.toString());
					for(String i:r.services) {
						for(JCheckBox j:serviceBoxes) {
							if(j.getText().equals(i)) {
								j.setSelected(true);
							}
						}
						for(JCheckBox j:featureBoxes) {
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
					ArrayList<String> features = new ArrayList<String>();
					for(JCheckBox i:serviceBoxes) {
						if (i.isSelected()) {
							services.add(i.getText());
						}
					}
					for(JCheckBox i:featureBoxes) {
						if (i.isSelected()) {
							features.add(i.getText());
						}
					}
					if (hotel.validateReservationChanges(change, room, start, end, features)){
						services.addAll(features);
						hotel.rem.updateReservation(change.getID(), guest, room, start, end, Status.POTVRDJENA, 0, services);
						hotel.applyPricing(change);
						resData.fireTableDataChanged();
						saveButton.setEnabled(false);
						IDField.setText("");
						guestField.setText("");
						roomField.setText("");
						startField.setText("yyyy-mm-dd");
						endField.setText("yyyy-mm-dd");
						for(JCheckBox i:serviceBoxes) {
							i.setSelected(false);
						}
						for(JCheckBox i:featureBoxes) {
							i.setSelected(false);
						}
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
					int index = reservationTable.convertRowIndexToModel(reservationTable.getSelectedRow());
					Reservation cancel = hotel.rem.readReservation((String) resData.getValueAt(index, 0));
					cancel.status = Status.OTKAZANA;
					resData.fireTableDataChanged();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		filterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog filterDialog = new JDialog();
				BoxLayout filterDialogLayout = new BoxLayout(filterDialog.getContentPane(), BoxLayout.PAGE_AXIS);
				filterDialog.getContentPane().setLayout(filterDialogLayout);
				filterDialog.setBounds(200, 200, 200, 350);
				
				JLabel filterRoomTypeLabel = new JLabel("Odaberite tip sobe: ");
				DefaultComboBoxModel<String> roomTypesComboBoxModel = new DefaultComboBoxModel<String>();
				for(String i:hotel.rom.roomTypes) {
					roomTypesComboBoxModel.addElement(i);
				}
				DefaultComboBoxModel<String> roomComboBoxModel = new DefaultComboBoxModel<String>();
				for(Room i:hotel.rom.rooms) {
					roomComboBoxModel.addElement(i.getRoomNumber());
				}
				JComboBox filterRoomTypeField = new JComboBox(roomTypesComboBoxModel);
				JComboBox filterRoomField = new JComboBox(roomComboBoxModel);
				JTextField lowestPriceField = new JTextField();
				JTextField highestPriceField = new JTextField();
				JButton applyButton = new JButton("Primenite");
				lowestPriceField.setMaximumSize(new Dimension(150, 20));
				highestPriceField.setMaximumSize(new Dimension(150, 20));
				filterRoomTypeField.setMaximumSize(new Dimension(150, 20));
				filterRoomField.setMaximumSize(new Dimension(150, 20));
				filterRoomTypeField.setSelectedIndex(-1);
				filterRoomField.setSelectedIndex(-1);
				filterRoomTypeField.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						String selected = (String) filterRoomTypeField.getSelectedItem();
						roomComboBoxModel.removeAllElements();
						for(Room i:hotel.rom.rooms) {
							if(i.type.equals(selected)) {
								roomComboBoxModel.addElement(i.getRoomNumber());
							}
						}
						filterRoomField.setSelectedIndex(-1);
					}
				});
				JLabel filterRoomLabel = new JLabel("Odaberite sobu: ");
				JLabel filterServicesLabel = new JLabel("Odaberite dodatne usluge: ");
				JLabel lowestPriceLabel = new JLabel("Unesite najnižu cenu: ");
				JLabel highestPriceLabel = new JLabel("Unesite najvišu cenu: ");
				filterRoomTypeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
				filterRoomTypeField.setAlignmentX(Component.LEFT_ALIGNMENT);
				filterRoomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
				filterRoomField.setAlignmentX(Component.LEFT_ALIGNMENT);
				filterServicesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
				lowestPriceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
				lowestPriceField.setAlignmentX(Component.LEFT_ALIGNMENT);
				highestPriceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
				highestPriceField.setAlignmentX(Component.LEFT_ALIGNMENT);
				filterDialog.getContentPane().add(filterRoomTypeLabel);
				filterDialog.getContentPane().add(filterRoomTypeField);
				filterDialog.getContentPane().add(filterRoomLabel);
				filterDialog.getContentPane().add(filterRoomField);
				filterDialog.getContentPane().add(lowestPriceLabel);
				filterDialog.getContentPane().add(lowestPriceField);
				filterDialog.getContentPane().add(highestPriceLabel);
				filterDialog.getContentPane().add(highestPriceField);
				filterDialog.getContentPane().add(filterServicesLabel);
				ArrayList<JCheckBox> filterBoxes = new ArrayList<JCheckBox>();
				for(String i:hotel.pm.services) {
					JCheckBox filterBox = new JCheckBox(i);
					filterBoxes.add(filterBox);
					filterBox.setAlignmentX(Component.LEFT_ALIGNMENT);
					filterDialog.getContentPane().add(filterBox);
				}
				applyButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						RowFilter<Object, Object> requestTableFilter = new RowFilter<Object, Object>(){
							public boolean include(Entry entry) {
								boolean checkRoomType = true;
								boolean checkPrice = true;
								boolean checkServices = true;
								String chosenRoomType = null;
								ArrayList<String> chosenServices = new ArrayList<String>();
								if(filterRoomTypeField.getSelectedIndex() != -1) {
									chosenRoomType = (String) filterRoomTypeField.getSelectedItem();
									checkRoomType = chosenRoomType.equals(entry.getValue(3));
								}
								for(JCheckBox i:filterBoxes) {
									if(i.isSelected()){
										chosenServices.add(i.getText());
									}
								}
								double price = (double) entry.getValue(6);
								try {
									checkPrice = (Double.parseDouble(lowestPriceField.getText()) < price) && (price < Double.parseDouble(highestPriceField.getText()));
								}catch(Exception ex) {
									checkPrice = true;
								}
								String services[] = ((String) entry.getValue(7)).split(",");
								for(String i:chosenServices) {
									checkServices = false;
									for(String j:services) {
										if(j.equals(i)) {
											checkServices = true;
										}
									}
									if(!checkServices) {
										break;
									}
								}
								Status status = (Status) entry.getValue(2);
								boolean accepted = (status == Status.POTVRDJENA);
								return checkServices && checkPrice && checkRoomType && accepted;
							}
						};
						((TableRowSorter<TableModel>) requestTable.getRowSorter()).setRowFilter(requestTableFilter);
						RowFilter<Object, Object> reservationTableFilter = new RowFilter<Object, Object>(){
							public boolean include(Entry entry) {
								boolean checkRoomType = true;
								boolean checkRoom = true;
								boolean checkPrice = true;
								boolean checkServices = true;
								String chosenRoomType = null;
								String chosenRoom = null;
								ArrayList<String> chosenServices = new ArrayList<String>();
								if(filterRoomTypeField.getSelectedIndex() != -1) {
									chosenRoomType = (String) filterRoomTypeField.getSelectedItem();
									checkRoomType = chosenRoomType.equals(hotel.rom.readRoom((String) entry.getValue(3)).type.toString());
								}
								if(filterRoomField.getSelectedIndex() != -1) {
									chosenRoom = (String) filterRoomField.getSelectedItem();
									checkRoom = chosenRoom.equals(entry.getValue(3));
								}
								if(filterRoomTypeField.getSelectedIndex() != -1) {
									chosenRoomType = (String) filterRoomTypeField.getSelectedItem();
									checkRoomType = chosenRoomType.equals(entry.getValue(3));
								}
								double price = (double) entry.getValue(6);
								try {
									checkPrice = (Double.parseDouble(lowestPriceField.getText()) < price) && (price < Double.parseDouble(highestPriceField.getText()));
								}catch(Exception ex) {
									checkPrice = true;
								}
								for(JCheckBox i:filterBoxes) {
									if(i.isSelected()){
										chosenServices.add(i.getText());
									}
								}
								String services[] = ((String) entry.getValue(7)).split(",");
								for(String i:chosenServices) {
									checkServices = false;
									for(String j:services) {
										if(j.equals(i)) {
											checkServices = true;
										}
									}
									if(!checkServices) {
										break;
									}
								}
								return checkRoomType && checkRoom && checkPrice && checkServices;
							}
						};
						TableRowSorter<TableModel> reservationSorter = new TableRowSorter<TableModel>(resData);
						reservationSorter.setRowFilter(reservationTableFilter);
						reservationTable.setRowSorter(reservationSorter);
						filterDialog.dispose();
					}
				});
				filterDialog.setModalityType(ModalityType.APPLICATION_MODAL);
				filterDialog.setTitle("Filtracija podataka");
				filterDialog.getContentPane().add(applyButton);
				filterDialog.setVisible(true);
			}
		});
		checkOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (reservationTable.getSelectedRow() != -1) {
					int index = reservationTable.convertRowIndexToModel(reservationTable.getSelectedRow());
					Reservation r = resData.getData(index);
					if(r.status == Status.POTVRDJENA) {
						hotel.checkOut(r);
						resData.fireTableDataChanged();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
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
