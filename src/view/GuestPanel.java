package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import entity.Guest;
import entity.Request;
import entity.Reservation;
import enums.Status;
import hotel.Hotel;
import models.RequestModel;
import models.ReservationModel;

public class GuestPanel extends JPanel {
	public GuestPanel(Hotel hotel, Guest g) {
		GroupLayout requestOptionsLayout = new GroupLayout(this);
		requestOptionsLayout.setAutoCreateContainerGaps(true);
		requestOptionsLayout.setAutoCreateGaps(true);
		setLayout(requestOptionsLayout);
		ArrayList<Reservation> accepted = new ArrayList<Reservation>();
		ArrayList<Request> pending = new ArrayList<Request>();
		for(Object o:g.userInputs) {
			if(o instanceof Reservation) {
				Reservation r = (Reservation) o;
				if(r.status == Status.POTVRDJENA) {
					accepted.add(r);
				}
			}else {
				Request req = (Request) o;
				if(req.status == Status.NA_CEKANJU) {
					pending.add(req);
				}
			}
		}
		RequestModel reqData = new RequestModel(pending); 
		ReservationModel data = new ReservationModel(accepted);
		JTable reservationTable = new JTable(data);
		JTable requestTable = new JTable(reqData);
		JScrollPane reqTableContainer = new JScrollPane(requestTable);
		JScrollPane tableContainer = new JScrollPane(reservationTable);
		JLabel typeLabel = new JLabel("Željeni tip sobe:");
		JLabel beginLabel = new JLabel("Željeni početak:");
		JLabel endLabel = new JLabel("Željeni kraj:");
		DefaultComboBoxModel roomTypes = new DefaultComboBoxModel();
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
		JButton availableButton = new JButton("Dostupni tipovi soba");
		JButton addButton = new JButton("Dodajte zahtev");
		JButton deleteButton = new JButton("Otkažite zahtev");
		startField.setText("yyyy-mm-dd");
		endField.setText("yyyy-mm-dd");
		requestOptionsLayout.setHorizontalGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(reqTableContainer)
				.addComponent(tableContainer)
				.addGroup(requestOptionsLayout.createSequentialGroup()
						.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(beginLabel)
								.addComponent(endLabel)
								.addComponent(typeLabel))
						.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(typeField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(startField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(availableButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(endField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(checkBoxContainer)
						.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(addButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(deleteButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						)
				);
		requestOptionsLayout.setVerticalGroup(requestOptionsLayout.createSequentialGroup()
				.addComponent(reqTableContainer)
				.addComponent(tableContainer)
				.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addGroup(requestOptionsLayout.createSequentialGroup()
								.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(beginLabel)
										.addComponent(startField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(endLabel)
										.addComponent(endField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(availableButton)
								.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(typeLabel)
										.addComponent(typeField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								)
						.addComponent(checkBoxContainer)
						.addGroup(requestOptionsLayout.createSequentialGroup()
								.addComponent(addButton)
								.addComponent(deleteButton))
						)					
				);
		availableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					LocalDate start = LocalDate.parse(startField.getText());
					LocalDate end = LocalDate.parse(endField.getText());
					ArrayList<String> available = hotel.showAvailable(start, end);
					roomTypes.removeAllElements();
					roomTypes.addAll(available);
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
				}
			}
		});
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String roomType = (String) typeField.getSelectedItem(); 
					LocalDate begin = LocalDate.parse(startField.getText());
					LocalDate end = LocalDate.parse(endField.getText());
					ArrayList<String> services = new ArrayList<String>();
					for(JCheckBox i:boxes) {
						if(i.isSelected()) {
							services.add(i.getText());
						}
					}
					hotel.rem.createRequest(g, roomType, begin, end, services);
					data.fireTableDataChanged();
					JOptionPane.showMessageDialog(null, "Zahtev je uspešno poslat!");
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
				}
				typeField.setSelectedIndex(0);
				for(JCheckBox i:boxes) {
					i.setSelected(false);
				}
				startField.setText("yyyy-mm-dd");
				endField.setText("yyyy-mm-dd");
				roomTypes.removeAllElements();
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					hotel.rem.deleteRequest((String) data.getValueAt(reservationTable.getSelectedRow(), 0));
					data.fireTableDataChanged();
				}catch(Exception ex) {
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
