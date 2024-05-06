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
		RoomModel data = new RoomModel(hotel.rom.rooms);
		JTable roomTable = new JTable(data);
		JScrollPane tableContainer = new JScrollPane(roomTable);
		JLabel roomNumberLabel = new JLabel("Broj sobe:");
		JLabel roomTypeLabel = new JLabel("Tip sobe:");
		JLabel typesListLabel = new JLabel("Tipovi soba:");
		JButton addButton = new JButton("Dodajte sobu");
		JButton changeButton = new JButton("Izmenite sobu");
		JButton saveButton = new JButton("Sačuvajte izmene");
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
		add(tableContainer);
		add(roomNumberLabel);
		add(roomTypeLabel);
		add(roomNumberField);
		add(roomTypeField);
		add(addButton);
		add(changeButton);
		add(saveButton);
		add(deleteButton);
		add(typesListLabel);
		add(roomTypesList);
		setVisible(true);
	}

}
