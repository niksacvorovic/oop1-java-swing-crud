package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

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
		String sep = System.getProperty("file.separator");
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
		JRadioButton fourthReport = new JRadioButton("Izveštaj 4 (Grafikoni)");
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
					if(firstReport.isSelected()) {
						LocalDate startDate = LocalDate.parse(startField.getText());
						LocalDate endDate = LocalDate.parse(endField.getText());
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
						LocalDate startDate = LocalDate.parse(startField.getText());
						LocalDate endDate = LocalDate.parse(endField.getText());
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
						LocalDate startDate = LocalDate.parse(startField.getText());
						LocalDate endDate = LocalDate.parse(endField.getText());
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
					}else if(fourthReport.isSelected()) {
						JFrame report = new JFrame();
						JTabbedPane reportPanes = new JTabbedPane();
						BorderLayout reportLayout = new BorderLayout();
						report.setLayout(reportLayout);
						LocalDate today = LocalDate.now();
						//generisanje prvog grafikona
						XYChart earningsChart = new XYChartBuilder().title("Zarada u prethodnih 12 meseci").build();
						JPanel earningsPane = new XChartPanel(earningsChart);
						LocalDate trackingDate = today.withDayOfMonth(1).minusYears(1).plusMonths(1);
						double earnings[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
						double typeEarned[] = null;
						double current[] = null;
						HashMap<String, double[]> earningsPerType = new HashMap<String, double[]>();
						for(String i:hotel.rom.roomTypes) {
							typeEarned = new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
							earningsPerType.put(i, typeEarned);
						}
						for(Request i:hotel.rem.requests) {
							if(i.begin.compareTo(trackingDate) >= 0 && i.end.compareTo(today) <= 0 && (i.status == Status.POTVRDJENA || i.status == Status.OTKAZANA)) {
								earnings[i.begin.getMonthValue() - 1] += i.price;
								current = earningsPerType.get(i.type);
								current[i.begin.getMonthValue() - 1] += i.price;
							}
						}
						for(Reservation i:hotel.rem.reservations) {
							if(i.begin.compareTo(trackingDate) >= 0 && i.end.compareTo(today) <= 0 && i.status != Status.ODBIJENA) {
								earnings[i.begin.getMonthValue() - 1] += i.price;
								current = earningsPerType.get(i.room.type);
								current[i.begin.getMonthValue() - 1] += i.price;
							}
						}
						double yData[] = new double[12];
						int month = trackingDate.getMonthValue();
						for(int i = 0; i < 12; i++) {
							yData[i] = earnings[month - 1];
							month = month % 12 + 1;
						}
						earningsChart.addSeries("Prihod", yData);
						for(String i:hotel.rom.roomTypes) {
							yData = new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
							month = trackingDate.getMonthValue();
							current = earningsPerType.get(i);
							for(int j = 0; j < 12; j++) {
								yData[j] = current[month - 1];
								month = month % 12 + 1;
							}
							earningsChart.addSeries(i, yData);
						}
						String[] monthNames = {"Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust",
								"Septembar", "Oktobar", "Novembar", "Decembar"};
						Font currentFont = earningsChart.getStyler().getAxisTickLabelsFont();
						earningsChart.getStyler().setxAxisTickLabelsFormattingFunction(x -> monthNames[(x.intValue() + today.getMonthValue() - 1) % 12]).setAxisTickLabelsFont(new Font(currentFont.getName(), currentFont.getStyle(), 10));
						reportPanes.add("Zarada", earningsPane);
						//generisanje drugog izveštaja
						PieChart cleanerChart = new PieChartBuilder().title("Opterećenje higijenske službe u prethodnih 30 dana").build();
						JPanel cleanerPane = new XChartPanel(cleanerChart);
						HashMap<String, Integer> roomsPerCleaner = new HashMap<String, Integer>();
						for(Employee i:hotel.um.employees) {
							if(i instanceof Cleaner) {
								roomsPerCleaner.put(i.getUsername(), 0);
							}
						}
						List<String> buffer = Files.readAllLines(Paths.get("." + sep + "data" + sep + "cleanerlogs.csv"));
						String[] data = null;
						for(String i:buffer) {
							data = i.split(",");
							if(today.compareTo(LocalDate.parse(data[0])) <= 30){
								roomsPerCleaner.replace(data[1], roomsPerCleaner.get(data[1]) + 1);
							}
						}
						roomsPerCleaner.forEach((key, value) -> {
							cleanerChart.addSeries(key + ":" + value.toString(), value);
						});
						reportPanes.add("Higijenska služba", cleanerPane);
						//generisanje trećeg izveštaja
						PieChart statusChart = new PieChartBuilder().title("Status zahteva i rezervacija u prethodnih 30 dana").build();
						JPanel statusPane = new XChartPanel(statusChart);
						HashMap<Status, Integer> resPerStatus = new HashMap<Status, Integer>();
						for(Status i:Status.values()) {
							resPerStatus.put(i, 0);
						}
						for(Request i:hotel.rem.requests) {
							if(today.compareTo(i.creationDate) <= 30) {
								resPerStatus.replace(i.status, resPerStatus.get(i.status) + 1);
							}
						}
						for(Reservation i:hotel.rem.reservations) {
							if(today.compareTo(i.creationDate) <= 30) {
								resPerStatus.replace(i.status, resPerStatus.get(i.status) + 1);
							}
						}
						resPerStatus.forEach((key, value) -> {
							statusChart.addSeries(key.toString() + ":" + value.toString(), value);
						});
						reportPanes.add("Statusi", statusPane);
						report.setBounds(120, 120, 1000, 600);
						report.setTitle("Izveštaj 4");
						report.getContentPane().add(reportPanes);
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
		radioButtons.add(fourthReport);
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
		add(fourthReport);
		add(generate);
		setVisible(true);
	}
}
