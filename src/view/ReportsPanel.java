package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import entity.Cleaner;
import entity.Employee;
import entity.Request;
import entity.Reservation;
import entity.Room;
import entity.RoomFeatureLink;
import enums.Status;
import hotel.Hotel;

public class ReportsPanel extends JPanel {
	public ReportsPanel(Hotel hotel) {
		BoxLayout reportLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(reportLayout);
		setBorder(BorderFactory.createTitledBorder("Izveštaji"));
		DefaultComboBoxModel roomlist = new DefaultComboBoxModel();
		for(Room i:hotel.rom.rooms) {
			roomlist.addElement(i.getRoomNumber());
		}
		JLabel startLabel = new JLabel("Početni datum");
		JLabel endLabel = new JLabel("Krajnji datum");
		JLabel roomLabel = new JLabel("Odabrana soba (za treći izveštaj)");
		JComboBox roomField = new JComboBox(roomlist);
		JTextField startField = new JTextField("yyyy-mm-dd");
		JTextField endField = new JTextField("yyyy-mm-dd");
		startField.setMaximumSize(new Dimension(150, 20));
		endField.setMaximumSize(new Dimension(150, 20));
		roomField.setMaximumSize(new Dimension(150, 20));
		ButtonGroup radioButtons = new ButtonGroup();
		JLabel reportsLabel = new JLabel("Odaberite izveštaj: ");
		JRadioButton firstReport = new JRadioButton("Izveštaj 1 (Prihodi, rashodi, sobarice, potvrđene rezervacije)");
		JRadioButton secondReport = new JRadioButton("Izveštaj 2 (Potvrđeni, otkazani i odbijeni zahtevi)");
		JRadioButton thirdReport = new JRadioButton("Izveštaj 3 (Podaci o sobama)");
		JButton generate = new JButton("Generiši izveštaj");
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
		generate.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					LocalDate startDate = LocalDate.parse(startField.getText());
					LocalDate endDate = LocalDate.parse(endField.getText());
					if(firstReport.isSelected()) {
						JFrame report = new JFrame();
						BoxLayout reportFrameLayout = new BoxLayout(report.getContentPane(), BoxLayout.Y_AXIS);
						report.getContentPane().setLayout(reportFrameLayout);
						DefaultTableModel incomeData = new DefaultTableModel();
						JTable incomeTable = new JTable(incomeData);
						JScrollPane incomes = new JScrollPane(incomeTable);
						incomes.setBorder(BorderFactory.createTitledBorder("Prilivi/Odlivi"));
						incomeData.addColumn("Datum");
						incomeData.addColumn("Priliv/Odliv");
						DefaultTableModel cleanerData = new DefaultTableModel();
						JTable cleanerTable = new JTable(cleanerData);
						JScrollPane cleanerContainer = new JScrollPane(cleanerTable);
						cleanerContainer.setBorder(BorderFactory.createTitledBorder("Rad higijeničara"));
						cleanerData.addColumn("Higijeničar");
						cleanerData.addColumn("Broj očiščenih soba");
						DefaultTableModel requestData = new DefaultTableModel();
						JTable requestTable = new JTable(requestData);
						JScrollPane requestTableContainer = new JScrollPane(requestTable);
						requestTableContainer.setBorder(BorderFactory.createTitledBorder("Potvrđeni zahtevi"));
						requestData.addColumn("Potvrđeni zahtev");
						requestData.addColumn("Tip sobe");
						double total = 0;
						for(Reservation i:hotel.rem.reservations) {
							if(i.begin.compareTo(startDate) > 0 && i.begin.compareTo(endDate) < 0) {
								total += i.price;
								incomeData.addRow(new Object[] {"Rezervacija " + i.begin.toString() + " - " + i.end.toString(), Double.toString(i.price)});
							}
						}
						int requestCount = 0;
						for(Request i:hotel.rem.requests) {
							if(i.begin.compareTo(startDate) > 0 && i.end.compareTo(endDate) < 0 && (i.status == Status.POTVRDJENA || i.status == Status.ZAVRSENA || i.status == Status.OTKAZANA)) {
								requestData.addRow(new Object[] {"Zahtev " + i.begin.toString() + " - " + i.end.toString(), i.type});
								requestCount++;
							}
						}
						String sep = System.getProperty("file.separator");
						List<String> userData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "cleanerlogs.csv"));
						HashMap<String, Integer> cleanCounter = new HashMap<String, Integer>();
						for(Employee i:hotel.um.employees) {
							if(i instanceof Cleaner) {
								cleanCounter.put(i.getUsername(), 0);
							}
						}
						for(String i:userData) {
							String data[] = i.split(",");
							LocalDate cleaningDate = LocalDate.parse(data[0]);
							if(cleaningDate.compareTo(startDate) > 0 && cleaningDate.compareTo(endDate) < 0) {
								cleanCounter.put(data[1], cleanCounter.get(data[1]) + 1);
							}
						}
						cleanCounter.forEach((key, value) -> {
							cleanerData.addRow(new Object[]  {key, value.toString()});
						});
						LocalDate paycheck = startDate;
						double totalPaycheck = 0;
						while(endDate.compareTo(paycheck) > 0) {
							if (paycheck.getDayOfMonth() == 25) {
								totalPaycheck = 0;
								for(Employee i:hotel.um.employees) {
									totalPaycheck -= i.getBaseSalary() * (1 + 0.04 * (LocalDate.now().getYear() - i.getEmploymentDate().getYear())); 
								}
								incomeData.addRow(new Object[] {"Isplata zaposlenima", Double.toString(totalPaycheck)});
								total += totalPaycheck;
							}
							paycheck = paycheck.plusDays(1);
						}
						JLabel incomeLabel = new JLabel("Ukupna zarada: " + Double.toString(total));
						JLabel requestLabel = new JLabel("Ukupan broj potvrđenih zahteva: " + Integer.toString(requestCount));
						report.setBounds(120, 120, 1000, 600);
						report.setTitle("Izveštaj 1");
						report.getContentPane().add(incomes);
						report.getContentPane().add(incomeLabel);
						report.getContentPane().add(requestTableContainer);
						report.getContentPane().add(requestLabel);
						report.getContentPane().add(cleanerContainer);
						report.setVisible(true);
					}else if(secondReport.isSelected()) {
						JFrame report = new JFrame();
						BoxLayout reportFrameLayout = new BoxLayout(report.getContentPane(), BoxLayout.Y_AXIS);
						report.getContentPane().setLayout(reportFrameLayout);
						DefaultListModel<String> accepted = new DefaultListModel<String>();
						JList acceptedList = new JList(accepted);
						JScrollPane acceptedPlaceholder = new JScrollPane(acceptedList);
						acceptedPlaceholder.setBorder(BorderFactory.createTitledBorder("Potvrđeni zahtevi"));
						DefaultListModel<String> rejected = new DefaultListModel<String>();
						JList rejectedList = new JList(rejected);
						JScrollPane rejectedPlaceholder = new JScrollPane(rejectedList);
						rejectedPlaceholder.setBorder(BorderFactory.createTitledBorder("Odbijeni zahtevi"));
						DefaultListModel<String> cancelled = new DefaultListModel<String>();
						JList cancelledList = new JList(cancelled);
						JScrollPane cancelledPlaceholder = new JScrollPane(cancelledList);
						cancelledPlaceholder.setBorder(BorderFactory.createTitledBorder("Otkazane rezervacije"));
						for(Reservation i:hotel.rem.reservations) {
							if(i.begin.compareTo(startDate) > 0 && i.end.compareTo(endDate) < 0 && i.status == Status.OTKAZANA) {
								String cancelledEntry = "Rezervacija " + i.begin.toString() + " - " + i.end.toString();
								cancelled.addElement(cancelledEntry);
							}
						}
						for(Request i:hotel.rem.requests) {
							if(i.begin.compareTo(startDate) > 0 && i.end.compareTo(endDate) < 0) {
								Status current = i.status;
								switch(current) {
								case POTVRDJENA:
									String acceptedEntry = "Zahtev " + i.begin.toString() + " - " + i.end.toString();
									accepted.addElement(acceptedEntry);
									break;
								case ODBIJENA:
									String rejectedEntry = "Zahtev " + i.begin.toString() + " - " + i.end.toString();
									rejected.addElement(rejectedEntry);
									break;
								default:
									break;
								}
							}
						}
						JLabel acceptedLabel = new JLabel("Broj potvrđenih zahteva: " + Integer.toString(accepted.getSize()));
						JLabel rejectedLabel = new JLabel("Broj odbijenih zahteva: " + Integer.toString(rejected.getSize()));
						JLabel cancelledLabel = new JLabel("Broj otkazanih rezervacija: " + Integer.toString(cancelled.getSize()));
						report.setBounds(120, 120, 1000, 600);
						report.setTitle("Izveštaj 2");
						report.getContentPane().add(acceptedPlaceholder);
						report.getContentPane().add(acceptedLabel);
						report.getContentPane().add(rejectedPlaceholder);
						report.getContentPane().add(rejectedLabel);
						report.getContentPane().add(cancelledPlaceholder);
						report.getContentPane().add(cancelledLabel);
						report.setVisible(true);
					}else if(thirdReport.isSelected()) {
						Room room = hotel.rom.readRoom((String) roomField.getSelectedItem());
						JFrame report = new JFrame();
						BoxLayout roomReportLayout = new BoxLayout(report.getContentPane(), BoxLayout.Y_AXIS);
						report.setLayout(roomReportLayout);
						DefaultTableModel roomData = new DefaultTableModel();
						JTable roomTable = new JTable(roomData);
						JScrollPane roomContainer = new JScrollPane(roomTable);
						roomData.addColumn("Rezervacije");
						roomData.addColumn("Prihod");
						int roomTotal = 0;
						for(Reservation i:room.reservations) {
							if(i.begin.compareTo(startDate) > 0 && i.begin.compareTo(endDate) < 0) {
								roomTotal += i.price;
								roomData.addRow(new Object[] {"Rezervacija " + i.begin.toString() + " - " + i.end.toString(), Double.toString(i.price)});
							}
						}
						report.setBounds(120, 120, 1000, 600);
						report.setTitle("Izveštaj 3");
						String features = "";
						for(RoomFeatureLink i:hotel.rfm.roomFeatureLinks) {
							if(i.room.equals(room)) {
								features += i.feature.getName() + ",";
							}
						}
						if(features.length() == 0) {
							features = "-";
						}else {
							features = features.substring(0, features.length() - 1);
						}
						String roomDescText = "<html><body>Broj sobe: " + room.getRoomNumber() + "<br>Tip sobe: " + room.type + "<br>Dodatne usluge sobe: " + features + "<br>Ukupan prihod iz sobe: " + Integer.toString(roomTotal) + "</body></html>";
						JLabel roomDesc = new JLabel(roomDescText);
						report.getContentPane().add(roomContainer);
						report.getContentPane().add(roomDesc);
						report.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Niste odabrali nijednu opciju!");
					}
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
				}
			}
		});
		radioButtons.add(firstReport);
		radioButtons.add(secondReport);
		radioButtons.add(thirdReport);
		startLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		startField.setAlignmentX(Component.LEFT_ALIGNMENT);
		endLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		endField.setAlignmentX(Component.LEFT_ALIGNMENT);
		roomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		roomField.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(startLabel);
		add(startField);
		add(endLabel);
		add(endField);
		add(roomLabel);
		add(roomField);
		add(reportsLabel);
		add(firstReport);
		add(secondReport);
		add(thirdReport);
		add(generate);
		setVisible(true);
	}
}
