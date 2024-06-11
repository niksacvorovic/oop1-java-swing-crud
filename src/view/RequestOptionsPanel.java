package view;

import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

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

import entity.Guest;
import entity.Request;
import entity.Room;
import enums.Status;

import javax.swing.JCheckBox;

import hotel.Hotel;
import models.RequestModel;

public class RequestOptionsPanel extends JPanel {
	public RequestOptionsPanel(Hotel hotel) {
		GroupLayout requestOptionsLayout = new GroupLayout(this);
		requestOptionsLayout.setAutoCreateContainerGaps(true);
		requestOptionsLayout.setAutoCreateGaps(true);
		setLayout(requestOptionsLayout);
		RequestModel data = new RequestModel(hotel.rem.requests);
		JTable requestTable = new JTable(data);
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
		ArrayList<JCheckBox> boxes = new ArrayList<JCheckBox>();
		for(String i:hotel.pm.services) {
			JCheckBox box = new JCheckBox(i);
			boxes.add(box);
			checkBoxContainer.add(box);
		}
		JButton addButton = new JButton("Dodajte zahtev");
		JButton deleteButton = new JButton("Obrišite zahtev");
		JButton validateButton = new JButton("Validirajte zahtev");
		JButton rejectButton = new JButton("Odbijte zahtev");
		JButton refreshButton = new JButton("Osvežite tabelu");
		startField.setText("yyyy-mm-dd");
		endField.setText("yyyy-mm-dd");
		requestOptionsLayout.setHorizontalGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(tableContainer)
				.addComponent(refreshButton)
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
				.addComponent(tableContainer)
				.addComponent(refreshButton)
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
					for(JCheckBox i:boxes) {
						if(i.isSelected()) {
							services.add(i.getText());
						}
					}
					hotel.rem.createRequest(guest, roomType, begin, end, services);
					data.fireTableDataChanged();
					JOptionPane.showMessageDialog(null, "Zahtev je uspešno dodat!");
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
				}
				guestField.setText("");
				typeField.setSelectedIndex(0);
				for(JCheckBox i:boxes) {
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
					Request fetch = data.getData(requestTable.getSelectedRow());
					hotel.validateRequest(fetch);
					if(fetch.status == Status.ODBIJENA) {
						JOptionPane.showMessageDialog(null, "Nema dostupnih soba. Zahtev je odbijen!");
					}
					data.fireTableDataChanged();
				}else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		rejectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (requestTable.getSelectedRow() != -1) {
					Request r = data.getData(requestTable.getSelectedRow());
					r.status = Status.ODBIJENA;
					data.fireTableDataChanged();
				}else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					hotel.rem.deleteRequest((String) data.getValueAt(requestTable.getSelectedRow(), 0));
					data.fireTableDataChanged();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				data.fireTableDataChanged();
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
