package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import entity.Reservation;
import entity.Room;
import entity.RoomFeature;
import entity.RoomFeatureLink;
import hotel.Hotel;
import models.RoomFeatureModel;
import models.RoomModel;

public class RoomOptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public RoomOptionsPanel(Hotel hotel) {
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
		RoomModel data = new RoomModel(hotel.rom.rooms, hotel.rfm.roomFeatureLinks);
		JTable roomTable = new JTable(data);
		JScrollPane tableContainer = new JScrollPane(roomTable);
		JLabel roomNumberLabel = new JLabel("Broj sobe:");
		JLabel roomTypeLabel = new JLabel("Tip sobe:");
		JLabel typesListLabel = new JLabel("Tipovi soba:");
		JButton addFeatureButton = new JButton("Dodajte osobinu sobe");
		JButton addButton = new JButton("Dodajte sobu");
		JButton changeButton = new JButton("Izmenite sobu");
		JButton saveButton = new JButton("Sačuvajte izmene");
		JButton featuresButton = new JButton("Uređenje usluga sobe...");
		saveButton.setEnabled(false);
		JButton deleteButton = new JButton("Obrišite sobu");
		JList roomTypesList = new JList(roomTypesListModel);
		JButton addTypeButton = new JButton("Dodajte tip sobe");
		JButton deleteTypeButton = new JButton("Obrišite tip sobe");
		JTextField roomNumberField = new JTextField();
		JTextField newTypeField = new JTextField();
		JComboBox roomTypeField = new JComboBox(roomTypesComboBoxModel);
		roomOptionsLayout.setHorizontalGroup(roomOptionsLayout.createSequentialGroup()
				.addComponent(tableContainer)
				.addGroup(roomOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(roomNumberLabel)
						.addComponent(roomTypeLabel)
						.addComponent(typesListLabel))
				.addGroup(roomOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(roomNumberField)
						.addComponent(roomTypeField)
						.addGroup(roomOptionsLayout.createSequentialGroup()
								.addComponent(addButton, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(deleteButton, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addGroup(roomOptionsLayout.createSequentialGroup()
								.addComponent(changeButton, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(saveButton, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addComponent(featuresButton)
						.addGroup(roomOptionsLayout.createSequentialGroup()
								.addComponent(roomTypesList, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addGroup(roomOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(newTypeField, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addComponent(addTypeButton, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addComponent(deleteTypeButton, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE)))
				));
		
		roomOptionsLayout.setVerticalGroup(roomOptionsLayout.createParallelGroup()
				.addComponent(tableContainer)
				.addGroup(roomOptionsLayout.createSequentialGroup()
						.addGroup(roomOptionsLayout.createSequentialGroup()
								.addGroup(roomOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(roomNumberLabel)
										.addComponent(roomNumberField))
								.addGroup(roomOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(roomTypeLabel)
										.addComponent(roomTypeField))
								.addGroup(roomOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(addButton)
										.addComponent(deleteButton))
								.addGroup(roomOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(changeButton)
										.addComponent(saveButton))
								.addComponent(featuresButton)
						.addGroup(roomOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(typesListLabel)
								.addComponent(roomTypesList)
								.addGroup(roomOptionsLayout.createSequentialGroup()
										.addComponent(newTypeField, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(addTypeButton)
										.addComponent(deleteTypeButton)))
						)));
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String roomNumber = roomNumberField.getText();
					String roomType = (String) roomTypeField.getSelectedItem();
					hotel.rom.createRoom(roomNumber, roomType);
					data.fireTableDataChanged();
					JOptionPane.showMessageDialog(null, "Soba je uspešno dodata!");
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
				}
				roomNumberField.setText("");
			}
		});
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(roomTable.getSelectedRow() != -1) {
					saveButton.setEnabled(true);
					Room r = data.getData(roomTable.getSelectedRow());
					roomNumberField.setText(r.getRoomNumber());
					roomTypeField.setSelectedItem(r.type);
					roomNumberField.setEditable(false);
				}
				else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String roomNumber = roomNumberField.getText();
				String roomType = (String) roomTypeField.getSelectedItem();
				hotel.rom.updateRoom(roomNumber, roomType);
				data.fireTableDataChanged();
				saveButton.setEnabled(false);
				roomNumberField.setEditable(true);
				JOptionPane.showMessageDialog(null, "Izmene su uspešno sačuvane!");
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					hotel.rom.deleteRoom((String) data.getValueAt(roomTable.getSelectedRow(), 0));
					data.fireTableDataChanged();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		addTypeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newType = newTypeField.getText();
				hotel.rom.roomTypes.add(newType);
				roomTypesListModel.addElement(newType);
				roomTypesComboBoxModel.addElement(newType);
				newTypeField.setText("");
			}
		});
		featuresButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(roomTable.getSelectedRow() != -1) {
					Room r = data.getData(roomTable.getSelectedRow());
					JDialog featuresDialog = new JDialog();
					featuresDialog.setBounds(200, 200, 300, 200);
					BoxLayout featuresLayout = new BoxLayout(featuresDialog.getContentPane(), BoxLayout.Y_AXIS);
					featuresDialog.getContentPane().setLayout(featuresLayout);
					JLabel featuresLabel = new JLabel("Odaberite usluge koje ova soba nudi:");
					ArrayList<JCheckBox> featureBoxes = new ArrayList<JCheckBox>(); 
					featuresDialog.getContentPane().add(featuresLabel);
					for(RoomFeature i:hotel.rfm.roomFeatures) {
						JCheckBox featureCheck = new JCheckBox(i.getName());
						featureBoxes.add(featureCheck);
						featuresDialog.getContentPane().add(featureCheck);
					}
					JButton applyButton = new JButton("Primenite");
					for(RoomFeatureLink i:hotel.rfm.roomFeatureLinks) {
						if(i.room.getRoomNumber().equals(r.getRoomNumber())) {
							for(JCheckBox j:featureBoxes) {
								if(j.getText().equals(i.feature.getName())) {
									j.setSelected(true);
									break;
								}
							}
						}
					}
					applyButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							LinkedList<RoomFeatureLink> links = new LinkedList<RoomFeatureLink>();
							boolean exists = false;
							for(RoomFeatureLink i:hotel.rfm.roomFeatureLinks) {
								if(i.room.getRoomNumber().equals(r.getRoomNumber())) {
									links.add(i);
								}
							}
							for(JCheckBox i:featureBoxes) {
								if(i.isSelected()) {
									exists = false;
									for(RoomFeatureLink j:links) {
										if(j.feature.getName().equals(i.getText())) {
											exists = true;
											links.remove(j);
										}
									}
									if(!exists) {
										hotel.rfm.addFeatureLink(r, hotel.rfm.readRoomFeature(i.getText()));
									}
								}
							}
							if(!links.isEmpty()) {
								for(RoomFeatureLink i:links) {
									hotel.rfm.roomFeatureLinks.remove(i);
								}
							}
							featuresDialog.dispose();
						}
					});
					applyButton.setMaximumSize(new Dimension(150, 20));
					featuresDialog.getContentPane().add(applyButton);
					featuresDialog.setTitle("Uređenje usluga sobe");
					featuresDialog.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		deleteTypeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String removeType = (String) roomTypesList.getSelectedValue();
					hotel.rom.roomTypes.remove(removeType);
					roomTypesListModel.removeElement(removeType);
					roomTypesComboBoxModel.removeElement(removeType);
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan element liste!");
				}	
			}
		});
		roomTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(roomTable.getSelectedRow() != -1 && e.getClickCount() == 2) {
					Room check = data.getData(roomTable.getSelectedRow());
					if(check.reservations.size() == 0) {
						JOptionPane.showMessageDialog(null, "Soba " + check.getRoomNumber() + " nema zakazane rezervacije");
					}else {
						String text = "Soba " + check.getRoomNumber() + " je rezervisana tokom sledećih perioda: ";
						for(Reservation i:check.reservations) {
							text += "\n" + i.begin.toString() + " - " + i.end.toString();
						}
						JOptionPane.showMessageDialog(null, text);
					}
				}
			}
		});
	}

}
