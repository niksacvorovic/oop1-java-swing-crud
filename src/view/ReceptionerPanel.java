package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import hotel.Hotel;
import models.RequestModel;
import models.ReservationModel;

public class ReceptionerPanel extends JPanel {
	public ReceptionerPanel(Hotel hotel) {
		JTabbedPane receptionerPanes = new JTabbedPane();
		BorderLayout receptionerPanesLayout = new BorderLayout();
		setLayout(receptionerPanesLayout);
		add(receptionerPanes);
		RequestModel reqData = new RequestModel(hotel.rem.requests);
		ReservationModel resData = new ReservationModel(hotel.rem.reservations);
		JPanel guestPanel = new GuestOptionsPanel(hotel);
		receptionerPanes.addTab("Upravljanje gostima", guestPanel);
		JPanel requestPanel = new RequestOptionsPanel(hotel, reqData);
		receptionerPanes.addTab("Upravljanje zahtevima", requestPanel);
		JPanel reservationPanel = new ReservationOptionsPanel(hotel, reqData, resData);
		receptionerPanes.addTab("Upravljanje rezervacijama", reservationPanel);
		JPanel roomPanel = new RoomViewPanel(hotel);
		receptionerPanes.addTab("Pregled soba", roomPanel);
	}
}