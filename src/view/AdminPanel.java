package view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import entity.Request;
import enums.Status;
import hotel.Hotel;
import models.RequestModel;
import models.ReservationModel;


public class AdminPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public AdminPanel(Hotel hotel) {
		JTabbedPane adminPanes = new JTabbedPane();
		BorderLayout adminPanesLayout = new BorderLayout();
		setLayout(adminPanesLayout);
		add(adminPanes);
		RequestModel reqData = new RequestModel(hotel.rem.requests);
		ReservationModel resData = new ReservationModel(hotel.rem.reservations);
		JPanel employeePanel = new EmployeeOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje zaposlenima", employeePanel);
		JPanel guestPanel = new GuestOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje gostima", guestPanel);
		JPanel roomPanel = new RoomOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje sobama", roomPanel);
		JPanel roomFeaturesPanel = new RoomFeatureOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje uslugama sobe", roomFeaturesPanel);
		JPanel requestPanel = new RequestOptionsPanel(hotel, reqData);
		adminPanes.addTab("Upravljanje zahtevima", requestPanel);
		JPanel reservationPanel = new ReservationOptionsPanel(hotel, reqData, resData);
		adminPanes.addTab("Upravljanje rezervacijama", reservationPanel);
		JPanel pricingPanel = new PricingOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje cenovnicima", pricingPanel);
		JPanel reportsPanel = new ReportsPanel(hotel);
		adminPanes.addTab("Izveštaji", reportsPanel);
	}
}
