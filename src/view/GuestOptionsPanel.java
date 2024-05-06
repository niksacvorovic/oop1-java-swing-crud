package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import entity.Guest;
import enums.Gender;
import hotel.Hotel;
import models.GuestModel;

public class GuestOptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public GuestOptionsPanel(Hotel hotel) {
		GroupLayout guestOptionsLayout = new GroupLayout(this);
		guestOptionsLayout.setAutoCreateContainerGaps(true);
		guestOptionsLayout.setAutoCreateGaps(true);
		setLayout(guestOptionsLayout);
		GuestModel data = new GuestModel(hotel.um.guests);
		JTable userTable = new JTable(data);
		JScrollPane tableContainer = new JScrollPane(userTable);
		JLabel usernameLabel = new JLabel("Korisničko ime:");
		JLabel passwordLabel = new JLabel("Lozinka:");
		JLabel nameLabel = new JLabel("Ime:");
		JLabel surnameLabel = new JLabel("Prezime:");
		JLabel genderLabel = new JLabel("Pol:");
		JLabel birthDateLabel = new JLabel("Datum rođenja:");
		JLabel phoneNumLabel = new JLabel("Broj telefona:");
		JButton addButton = new JButton("Dodajte gosta");
		JButton changeButton = new JButton("Izmenite gosta");
		JButton saveButton = new JButton("Sačuvajte izmene");
		saveButton.setEnabled(false);
		JButton deleteButton = new JButton("Obrišite gosta");
		JTextField usernameField = new JTextField();
		JTextField passwordField = new JTextField();
		JTextField nameField = new JTextField();
		JTextField surnameField = new JTextField();
		JComboBox genderField = new JComboBox(Gender.values());
		JTextField birthDateField = new JTextField();
		birthDateField.setText("yyyy-mm-dd");
		JTextField phoneNumField = new JTextField();
		guestOptionsLayout.setHorizontalGroup(guestOptionsLayout.createSequentialGroup()
				.addGroup(guestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(tableContainer)
						.addGroup(guestOptionsLayout.createSequentialGroup()
								.addGroup(guestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(usernameLabel)
										.addComponent(passwordLabel)
										.addComponent(nameLabel)
										.addComponent(surnameLabel)
										.addComponent(genderLabel)
										.addComponent(birthDateLabel)
										.addComponent(phoneNumLabel))
								.addGroup(guestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(usernameField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(passwordField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(nameField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(surnameField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(genderField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(birthDateField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(phoneNumField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(guestOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(addButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(changeButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(saveButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(deleteButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								)
						)
				);
		guestOptionsLayout.setVerticalGroup(guestOptionsLayout.createSequentialGroup()
				.addComponent(tableContainer)
				.addGroup(guestOptionsLayout.createSequentialGroup()
						.addGroup(guestOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(usernameLabel)
								.addComponent(usernameField)
								.addComponent(addButton))
						.addGroup(guestOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(passwordLabel)
								.addComponent(passwordField)
								.addComponent(changeButton))
						.addGroup(guestOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(nameLabel)
								.addComponent(nameField)
								.addComponent(saveButton))
						.addGroup(guestOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(surnameLabel)
								.addComponent(surnameField)
								.addComponent(deleteButton))
						.addGroup(guestOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(genderLabel)
								.addComponent(genderField))
						.addGroup(guestOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(birthDateLabel)
								.addComponent(birthDateField))
						.addGroup(guestOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(phoneNumLabel)
								.addComponent(phoneNumField))
						)
				);
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String username = usernameField.getText();
					String password = passwordField.getText();
					String name = nameField.getText();
					String surname = surnameField.getText();
					Gender gender = (Gender) genderField.getSelectedItem();
					LocalDate birthDate = LocalDate.parse(birthDateField.getText());
					String phoneNumber = phoneNumField.getText();
					hotel.um.createGuest(username, password, name, surname, gender, birthDate, phoneNumber);
					data.fireTableDataChanged();
					JOptionPane.showMessageDialog(null, "Korisnik je uspešno dodat!");
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
				}
				usernameField.setText("");
				passwordField.setText("");
				nameField.setText("");
				surnameField.setText("");
				phoneNumField.setText("");
				birthDateField.setText("yyyy-mm-dd");
			}
		});
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(userTable.getSelectedRow() != -1) {
					saveButton.setEnabled(true);
					Guest em = data.getData(userTable.getSelectedRow());
					usernameField.setText(em.getUsername());
					passwordField.setText(em.getPassword());
					nameField.setText(em.getName());
					surnameField.setText(em.getLastName());
					genderField.setSelectedItem(em.getGender());
					birthDateField.setText(em.getBirthDate().toString());
					phoneNumField.setText(em.getPhoneNumber());
					usernameField.setEditable(false);
				}
				else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = passwordField.getText();
				String name = nameField.getText();
				String surname = surnameField.getText();
				Gender gender = (Gender) genderField.getSelectedItem();
				LocalDate birthDate = LocalDate.parse(birthDateField.getText());
				String phoneNumber = phoneNumField.getText();
				hotel.um.updateGuest(username, password, name, surname, gender, birthDate, phoneNumber);
				data.fireTableDataChanged();
				saveButton.setEnabled(false);
				usernameField.setEditable(true);
				usernameField.setText("");
				passwordField.setText("");
				nameField.setText("");
				surnameField.setText("");
				phoneNumField.setText("");
				birthDateField.setText("yyyy-mm-dd");
				JOptionPane.showMessageDialog(null, "Izmene su uspešno sačuvane!");
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					hotel.um.deleteUser((String) data.getValueAt(userTable.getSelectedRow(), 0));
					data.fireTableDataChanged();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		birthDateField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                birthDateField.setText("");
            }
        });
		add(tableContainer);
		add(usernameLabel);
		add(passwordLabel);
		add(nameLabel);
		add(surnameLabel);
		add(genderLabel);
		add(birthDateLabel);
		add(phoneNumLabel);
		add(usernameField);
		add(passwordField);
		add(nameField);
		add(surnameField);
		add(genderField);
		add(birthDateField);
		add(addButton);
		add(changeButton);
		add(saveButton);
		add(deleteButton);
		setVisible(true);
	}
}
