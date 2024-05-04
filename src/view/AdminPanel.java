package view;

import models.EmployeeModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import entity.Employee;
import enums.Degree;
import enums.Gender;
import enums.Role;
import hotel.Hotel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AdminPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public AdminPanel(Hotel hotel) {
		JTabbedPane adminPanes = new JTabbedPane();
		BorderLayout adminPanesLayout = new BorderLayout();
		setLayout(adminPanesLayout);
		add(adminPanes);
		//ui za dodavanje korisnika
		JPanel addUser = new EmployeeOptionsPanel(hotel);
		adminPanes.addTab("Upravljanje zaposlenima", addUser);
		
	}

}
