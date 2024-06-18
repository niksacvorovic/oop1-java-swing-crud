package view;

import java.util.ArrayList;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.Entry;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import entity.Guest;
import entity.Request;
import entity.Room;
import entity.RoomFeature;
import enums.Status;

import javax.swing.JCheckBox;

import hotel.Hotel;
import models.RequestModel;

public class RequestOptionsPanel extends JPanel {
	public RequestOptionsPanel(Hotel hotel, RequestModel reqData) {
		GroupLayout requestOptionsLayout = new GroupLayout(this);
		requestOptionsLayout.setAutoCreateContainerGaps(true);
		requestOptionsLayout.setAutoCreateGaps(true);
		setLayout(requestOptionsLayout);
		JTable requestTable = new JTable(reqData);
		JScrollPane tableContainer = new JScrollPane(requestTable);
		JLabel guestLabel = new JLabel("Gost:");
		JLabel typeLabel = new JLabel("Željeni tip sobe:");
		JLabel beginLabel = new JLabel("Željeni početak:");
		JLabel endLabel = new JLabel("Željeni kraj:");
		JTextField guestField = new JTextField();
		DefaultComboBoxModel roomTypes = new DefaultComboBoxModel();
		for(String i:hotel.rom.roomTypes) {
			roomTypes.addElement(i);
		}
		JComboBox typeField = new JComboBox(roomTypes);
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
		JButton addButton = new JButton("Dodajte zahtev");
		JButton deleteButton = new JButton("Obrišite zahtev");
		JButton validateButton = new JButton("Validirajte zahtev");
		JButton rejectButton = new JButton("Odbijte zahtev");
		JButton filterButton = new JButton("Filtrirajte tabelu");
		startField.setText("yyyy-mm-dd");
		endField.setText("yyyy-mm-dd");
		requestTable.setAutoCreateRowSorter(true);
		requestOptionsLayout.setHorizontalGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(filterButton)
				.addComponent(tableContainer)
				.addGroup(requestOptionsLayout.createSequentialGroup()
						.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(guestLabel)
								.addComponent(typeLabel)
								.addComponent(beginLabel)
								.addComponent(endLabel))
						.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(guestField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(typeField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(startField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(endField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(checkBoxContainer)
						.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(addButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(validateButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(rejectButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(deleteButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						)
				);
		requestOptionsLayout.setVerticalGroup(requestOptionsLayout.createSequentialGroup()
				.addComponent(filterButton)
				.addComponent(tableContainer)
				.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addGroup(requestOptionsLayout.createSequentialGroup()
								.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(guestLabel)
										.addComponent(guestField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(typeLabel)
										.addComponent(typeField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(beginLabel)
										.addComponent(startField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(endLabel)
										.addComponent(endField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								)
						.addComponent(checkBoxContainer)
						.addGroup(requestOptionsLayout.createSequentialGroup()
								.addComponent(addButton)
								.addComponent(validateButton)
								.addComponent(rejectButton)
								.addComponent(deleteButton))
						)					
				);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Guest guest = (Guest) hotel.um.readUser(guestField.getText());
					String roomType = (String) typeField.getSelectedItem(); 
					LocalDate begin = LocalDate.parse(startField.getText());
					LocalDate end = LocalDate.parse(endField.getText());
					ArrayList<String> services = new ArrayList<String>();
					for(JCheckBox i:serviceBoxes) {
						if(i.isSelected()) {
							services.add(i.getText());
						}
					}
					for(JCheckBox i:featureBoxes) {
						if(i.isSelected()) {
							services.add(i.getText());
						}
					}
					Request newreq = hotel.rem.createRequest(guest, roomType, begin, end, services);
					hotel.applyPricing(newreq);
					reqData.fireTableDataChanged();
					JOptionPane.showMessageDialog(null, "Zahtev je uspešno dodat!");
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
				}
				guestField.setText("");
				typeField.setSelectedIndex(0);
				for(JCheckBox i:serviceBoxes) {
					i.setSelected(false);
				}
				for(JCheckBox i:featureBoxes) {
					i.setSelected(false);
				}
				startField.setText("yyyy-mm-dd");
				endField.setText("yyyy-mm-dd");
			}
		});
		validateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (requestTable.getSelectedRow() != -1) {
					int index = requestTable.convertRowIndexToModel(requestTable.getSelectedRow());
					Request fetch = reqData.getData(index);
					hotel.validateRequest(fetch);
					if(fetch.status == Status.ODBIJENA) {
						JOptionPane.showMessageDialog(null, "Nema dostupnih soba. Zahtev je odbijen!");
					}
					reqData.fireTableDataChanged();
				}else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		rejectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (requestTable.getSelectedRow() != -1) {
					int index = requestTable.convertRowIndexToModel(requestTable.getSelectedRow());
					Request r = reqData.getData(index);
					r.status = Status.ODBIJENA;
					r.price = 0; 
					reqData.fireTableDataChanged();
				}else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int index = requestTable.convertRowIndexToModel(requestTable.getSelectedRow());
					hotel.rem.deleteRequest((String) reqData.getValueAt(index, 0));
					reqData.fireTableDataChanged();
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
								return checkServices && checkPrice && checkRoomType;
							}
						};
						TableRowSorter<TableModel> requestSorter = new TableRowSorter<TableModel>(reqData);
						requestSorter.setRowFilter(requestTableFilter);
						requestTable.setRowSorter(requestSorter);
						filterDialog.dispose();
					}
				});
				filterDialog.setModalityType(ModalityType.APPLICATION_MODAL);
				filterDialog.setTitle("Filtracija podataka");
				filterDialog.getContentPane().add(applyButton);
				filterDialog.setVisible(true);
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
