package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import entity.Cleaner;
import entity.Room;
import enums.RoomStatus;
import models.RoomModel;

public class CleanerPanel extends JPanel {
	public CleanerPanel(Cleaner c) {
		FlowLayout cleanerLayout = new FlowLayout(FlowLayout.LEFT);
		setLayout(cleanerLayout);
		RoomModel data = new RoomModel(c.rooms);
		JTable roomTable = new JTable(data);
		JButton cleanButton = new JButton("Očistite sobu");
		JScrollPane tableContainer = new JScrollPane(roomTable);
		tableContainer.setPreferredSize(new Dimension(600, 550));
		cleanButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(roomTable.getSelectedRow() != -1) {
					Room r = data.getData(roomTable.getSelectedRow());
					try {
						r.status = RoomStatus.SLOBODNA;
						r.cleaner = null;
						c.rooms.remove(r);
						String cleanlog = LocalDate.now().toString() + "," + r.getRoomNumber() + "," + c.getUsername();
						ArrayList<String> write = new ArrayList<String>();
						write.add(cleanlog);
						String sep = System.getProperty("file.separator");
						try {
							Files.write(Paths.get("." + sep + "data" + sep + "incomelogs.csv"), write);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Soba je očišćena!");
						data.fireTableDataChanged();
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
					}
				}
			}
		});
		add(tableContainer);
		add(cleanButton);
	}

}
