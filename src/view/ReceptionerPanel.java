package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import hotel.Hotel;

public class ReceptionerPanel extends JPanel {
	public ReceptionerPanel(Hotel hotel) {
		JTabbedPane receptionerPanes = new JTabbedPane();
		BorderLayout receptionerPanesLayout = new BorderLayout();
		setLayout(receptionerPanesLayout);
		add(receptionerPanes);
		JPanel guestPanel = new GuestOptionsPanel(hotel);
		receptionerPanes.addTab("Upravljanje gostima", guestPanel);
	}
}
