package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import entity.RoomFeature;
import hotel.Hotel;
import models.RoomFeatureModel;

public class RoomFeatureOptionsPanel extends JPanel {

	public RoomFeatureOptionsPanel(Hotel hotel) {
		GroupLayout featureOptionsLayout = new GroupLayout(this);
		featureOptionsLayout.setAutoCreateGaps(true);
		featureOptionsLayout.setAutoCreateContainerGaps(true);
		setLayout(featureOptionsLayout);
		JLabel nameLabel = new JLabel("Ime usluge:");
		JLabel priceLabel = new JLabel("Cena:");
		JTextField nameField = new JTextField();
		JTextField priceField = new JTextField();
		JButton addButton = new JButton("Dodajte uslugu");
		JButton deleteButton = new JButton("Obrišite uslugu");
		JButton changeButton = new JButton("Izmenite uslugu");
		JButton saveButton = new JButton("Sačuvajte izmene");
		RoomFeatureModel data = new RoomFeatureModel(hotel.rfm.roomFeatures);
		JTable featuresTable = new JTable(data);
		JScrollPane tableContainer = new JScrollPane(featuresTable);
		saveButton.setEnabled(false);
		featureOptionsLayout.setHorizontalGroup(featureOptionsLayout.createSequentialGroup()
				.addComponent(tableContainer)
				.addGroup(featureOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameLabel)
						.addComponent(priceLabel))
				.addGroup(featureOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameField)
						.addComponent(priceField)
						.addGroup(featureOptionsLayout.createSequentialGroup()
								.addComponent(addButton, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(deleteButton, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addGroup(featureOptionsLayout.createSequentialGroup()
								.addComponent(changeButton, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(saveButton, GroupLayout.DEFAULT_SIZE, 150, GroupLayout.PREFERRED_SIZE))
				));
		
		featureOptionsLayout.setVerticalGroup(featureOptionsLayout.createParallelGroup()
				.addComponent(tableContainer)
				.addGroup(featureOptionsLayout.createSequentialGroup()
						.addGroup(featureOptionsLayout.createSequentialGroup()
								.addGroup(featureOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(nameLabel)
										.addComponent(nameField))
								.addGroup(featureOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(priceLabel)
										.addComponent(priceField))
								.addGroup(featureOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(addButton)
										.addComponent(deleteButton))
								.addGroup(featureOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(changeButton)
										.addComponent(saveButton))
						)));
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = nameField.getText();
					double price = Double.parseDouble(priceField.getText());
					hotel.rfm.createRoomFeature(name, price);
					data.fireTableDataChanged();
					JOptionPane.showMessageDialog(null, "Usluga je uspešno dodata!");
					nameField.setText("");
					priceField.setText("");
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					hotel.rfm.deleteRoomFeature((String) data.getValueAt(featuresTable.getSelectedRow(), 0));
					data.fireTableDataChanged();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(featuresTable.getSelectedRow() != -1) {
					saveButton.setEnabled(true);
					RoomFeature r = data.getData(featuresTable.getSelectedRow());
					nameField.setText(r.getName());
					nameField.setEditable(false);
					priceField.setText(Double.toString(r.price));
				}else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				double price = Double.parseDouble(priceField.getText());
				hotel.rfm.updateRoomFeature(name, price);
				data.fireTableDataChanged();
				nameField.setEditable(true);
				saveButton.setEnabled(false);
				nameField.setText("");
				priceField.setText("");
				JOptionPane.showMessageDialog(null, "Izmene su uspešno sačuvane!");
			}
		});
	} 
}
