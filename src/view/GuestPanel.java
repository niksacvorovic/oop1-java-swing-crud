package view;

import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import entity.Guest;
import entity.Request;
import entity.Reservation;
import entity.RoomFeature;
import entity.RoomFeatureLink;
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
		ArrayList<Request> requests = new ArrayList<Request>();
		double moneySpent = 0;
		for(Object o:g.userInputs) {
			if(o instanceof Reservation) {
				Reservation r = (Reservation) o;
				accepted.add(r);
				moneySpent += r.price;
			}else {
				Request req = (Request) o;
				requests.add(req);
				if(req.status == Status.POTVRDJENA || req.status == Status.OTKAZANA) {
					moneySpent += req.price;
				}
			}
		}
		JLabel spending = new JLabel("Ukupan trošak svih rezervacija: " + Double.toString(moneySpent));
		spending.setFont(new Font("Tahoma", Font.BOLD, 11));
		RequestModel reqData = new RequestModel(requests); 
		ReservationModel resData = new ReservationModel(accepted);
		JTable reservationTable = new JTable(resData);
		JTable requestTable = new JTable(reqData);
		JScrollPane reqTableContainer = new JScrollPane(requestTable);
		reqTableContainer.setBorder(BorderFactory.createTitledBorder("Zahtevi"));
		JScrollPane resTableContainer = new JScrollPane(reservationTable);
		resTableContainer.setBorder(BorderFactory.createTitledBorder("Rezervacije"));
		JLabel typeLabel = new JLabel("Željeni tip sobe:");
		JLabel beginLabel = new JLabel("Željeni početak:");
		JLabel featureLabel = new JLabel("Dodatne usluge sobe:");
		JLabel endLabel = new JLabel("Željeni kraj:");
		DefaultComboBoxModel roomTypes = new DefaultComboBoxModel();
		JComboBox typeField = new JComboBox(roomTypes);
		JTextField startField = new JTextField();
		JTextField endField = new JTextField();
		int gridLength = hotel.rfm.roomFeatures.size();
		JPanel featureContainer = new JPanel(new GridLayout(gridLength, 1));
		JPanel checkBoxContainer = new JPanel(new GridLayout(2, 3));
		checkBoxContainer.setBorder(BorderFactory.createTitledBorder("Dodatne usluge"));
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
			featureContainer.add(box);
		}
		JButton availableButton = new JButton("Dostupni tipovi soba");
		JButton addButton = new JButton("Dodajte zahtev");
		JButton deleteButton = new JButton("Otkažite zahtev");
		JButton cancelButton = new JButton("Otkažite rezervaciju");
		startField.setText("yyyy-mm-dd");
		endField.setText("yyyy-mm-dd");
		requestOptionsLayout.setHorizontalGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(reqTableContainer)
				.addComponent(resTableContainer)
				.addComponent(spending)
				.addGroup(requestOptionsLayout.createSequentialGroup()
						.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(beginLabel)
								.addComponent(endLabel)
								.addComponent(featureLabel)
								.addComponent(typeLabel))
						.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(typeField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(startField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(featureContainer)
								.addComponent(availableButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(endField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(checkBoxContainer)
						.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(addButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(deleteButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cancelButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						)
				);
		requestOptionsLayout.setVerticalGroup(requestOptionsLayout.createSequentialGroup()
				.addComponent(reqTableContainer)
				.addComponent(resTableContainer)
				.addComponent(spending)
				.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addGroup(requestOptionsLayout.createSequentialGroup()
								.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(beginLabel)
										.addComponent(startField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(endLabel)
										.addComponent(endField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(featureLabel)
										.addComponent(featureContainer))
								.addComponent(availableButton)
								.addGroup(requestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(typeLabel)
										.addComponent(typeField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								)
						.addComponent(checkBoxContainer)
						.addGroup(requestOptionsLayout.createSequentialGroup()
								.addComponent(addButton)
								.addComponent(deleteButton)
								.addComponent(cancelButton))
						)					
				);
		availableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					LocalDate start = LocalDate.parse(startField.getText());
					LocalDate end = LocalDate.parse(endField.getText());
					ArrayList<RoomFeature> features = new ArrayList<RoomFeature>();
					for(JCheckBox i:featureBoxes) {
						if(i.isSelected()) {
							features.add(hotel.rfm.readRoomFeature(i.getText()));
						}
					}
					ArrayList<String> available = hotel.showAvailable(start, end, features);
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
					Request newreq = hotel.rem.createRequest(g, roomType, begin, end, services);
					hotel.applyPricing(newreq);
					requests.add(newreq);
					/*requests.clear();
					for(Object o:g.userInputs) {
						if(o instanceof Request) {
							Request req = (Request) o;
							if(req.status == Status.NA_CEKANJU) {
								requests.add(req);
							}
						}
					}*/
					reqData.fireTableDataChanged();
					JOptionPane.showMessageDialog(null, "Zahtev je uspešno poslat!");
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
				}
				typeField.setSelectedIndex(-1);
				for(JCheckBox i:serviceBoxes) {
					i.setSelected(false);
				}
				for(JCheckBox i:featureBoxes) {
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
				if(requestTable.getSelectedRow() != -1) {
					Request r = hotel.rem.readRequest((String) reqData.getValueAt(requestTable.getSelectedRow(), 0));
					if(r.status == Status.NA_CEKANJU) {
						hotel.rem.deleteRequest(r.getID());
					}else if(r.status == Status.POTVRDJENA){
						String[] options = {"Otkažite", "Odustanite"};
						int option = JOptionPane.showOptionDialog(null, "Poništavanjem potvrđenog zahteva nemate pravo na povraćaj novca. Da li ste sigurni?",
								"Potvrda otkazivanja", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
								options, null);
						if(option == JOptionPane.OK_OPTION) {
							r.status = Status.OTKAZANA;
							JOptionPane.showMessageDialog(null, "Zahtev je uspešno otkazan!");
						}
					}
					reqData.fireTableDataChanged();
				}else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(reservationTable.getSelectedRow() != -1) {
					Reservation r = hotel.rem.readReservation((String) resData.getValueAt(reservationTable.getSelectedRow(), 0));
					String[] options = {"Otkažite", "Odustanite"};
					int option = JOptionPane.showOptionDialog(null, "Poništavanjem rezervacije nemate pravo na povraćaj novca. Da li ste sigurni?",
							"Potvrda otkazivanja", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
							options, null);
					if(option == JOptionPane.OK_OPTION) {
						r.status = Status.OTKAZANA;
						accepted.remove(r);
						resData.fireTableDataChanged();
						JOptionPane.showMessageDialog(null, "Rezervacija je uspešno otkazana!");
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
