package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


import hotel.Hotel;


public class AdminPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public AdminPanel(Hotel hotel) {
		JTabbedPane adminPanes = new JTabbedPane();
		BorderLayout adminPanesLayout = new BorderLayout();
		setLayout(adminPanesLayout);
		add(adminPanes);
		JPanel employeePanel = new EmployeeOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje zaposlenima", employeePanel);
		JPanel guestPanel = new GuestOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje gostima", guestPanel);
		JPanel roomPanel = new RoomOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje sobama", roomPanel);
		JPanel requestPanel = new RequestOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje zahtevima", requestPanel);
		JPanel reservationPanel = new ReservationOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje rezervacijama", reservationPanel);
		JPanel pricingPanel = new PricingOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje cenovnicima", pricingPanel);
	}
}
