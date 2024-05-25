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

import entity.Employee;
import enums.Degree;
import enums.Gender;
import enums.Role;
import hotel.Hotel;
import models.EmployeeModel;

public class EmployeeOptionsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public EmployeeOptionsPanel(Hotel hotel) {
		GroupLayout employeeOptionsLayout = new GroupLayout(this);
		employeeOptionsLayout.setAutoCreateContainerGaps(true);
		employeeOptionsLayout.setAutoCreateGaps(true);
		setLayout(employeeOptionsLayout);
		EmployeeModel data = new EmployeeModel(hotel.um.employees);
		JTable userTable = new JTable(data);
		JScrollPane tableContainer = new JScrollPane(userTable);
		JLabel usernameLabel = new JLabel("Korisničko ime:");
		JLabel passwordLabel = new JLabel("Lozinka:");
		JLabel nameLabel = new JLabel("Ime:");
		JLabel surnameLabel = new JLabel("Prezime:");
		JLabel genderLabel = new JLabel("Pol:");
		JLabel birthDateLabel = new JLabel("Datum rođenja:");
		JLabel phoneNumLabel = new JLabel("Broj telefona:");
		JLabel roleLabel = new JLabel("Radno mesto:");
		JLabel degreeLabel = new JLabel("Obrazovanje:");
		JLabel employmentLabel = new JLabel("Datum zaposlenja:");
		JLabel salaryLabel = new JLabel	("Osnovna plata:");
		JButton addButton = new JButton("Dodajte zaposlenog");
		JButton changeButton = new JButton("Izmenite zaposlenog");
		JButton saveButton = new JButton("Sačuvajte izmene");
		saveButton.setEnabled(false);
		JButton deleteButton = new JButton("Obrišite zaposlenog");
		JTextField usernameField = new JTextField();
		JTextField passwordField = new JTextField();
		JTextField nameField = new JTextField();
		JTextField surnameField = new JTextField();
		JComboBox genderField = new JComboBox(Gender.values());
		JTextField birthDateField = new JTextField();
		birthDateField.setText("yyyy-mm-dd");
		JTextField phoneNumField = new JTextField();
		JComboBox roleField = new JComboBox(Role.values());
		JComboBox degreeField = new JComboBox(Degree.values());
		JTextField employmentField = new JTextField();
		employmentField.setText("yyyy-mm-dd");
		JTextField salaryField = new JTextField();
		employeeOptionsLayout.setHorizontalGroup(employeeOptionsLayout.createSequentialGroup()
				.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(tableContainer)
						.addGroup(employeeOptionsLayout.createSequentialGroup()
								.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(usernameLabel)
										.addComponent(passwordLabel)
										.addComponent(nameLabel)
										.addComponent(surnameLabel)
										.addComponent(genderLabel)
										.addComponent(birthDateLabel)
										.addComponent(phoneNumLabel)
										.addComponent(roleLabel)
										.addComponent(degreeLabel)
										.addComponent(employmentLabel)
										.addComponent(salaryLabel))
								.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(usernameField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(passwordField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(nameField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(surnameField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(genderField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(birthDateField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(phoneNumField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(roleField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(degreeField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(employmentField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(salaryField, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(addButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(changeButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(saveButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(deleteButton, 200, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								)
						)
				);
		employeeOptionsLayout.setVerticalGroup(employeeOptionsLayout.createSequentialGroup()
				.addComponent(tableContainer)
				.addGroup(employeeOptionsLayout.createSequentialGroup()
						.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(usernameLabel)
								.addComponent(usernameField)
								.addComponent(addButton))
						.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(passwordLabel)
								.addComponent(passwordField)
								.addComponent(changeButton))
						.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(nameLabel)
								.addComponent(nameField)
								.addComponent(saveButton))
						.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(surnameLabel)
								.addComponent(surnameField)
								.addComponent(deleteButton))
						.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(genderLabel)
								.addComponent(genderField))
						.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(birthDateLabel)
								.addComponent(birthDateField))
						.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(phoneNumLabel)
								.addComponent(phoneNumField))
						.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(roleLabel)
								.addComponent(roleField))
						.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(degreeLabel)
								.addComponent(degreeField))
						.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(employmentLabel)
								.addComponent(employmentField))
						.addGroup(employeeOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(salaryLabel)
								.addComponent(salaryField))
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
					Role role = (Role) roleField.getSelectedItem();
					Degree degree = (Degree) degreeField.getSelectedItem();
					LocalDate employment = LocalDate.parse(employmentField.getText());
					Double salary = Double.parseDouble(salaryField.getText());
					hotel.um.createEmployee(username, password, name, surname, gender, birthDate, phoneNumber, role, degree, employment, salary);
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
				salaryField.setText("");
				birthDateField.setText("yyyy-mm-dd");
				employmentField.setText("yyyy-mm-dd");
			}
		});
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(userTable.getSelectedRow() != -1) {
					saveButton.setEnabled(true);
					Employee em = data.getData(userTable.getSelectedRow());
					usernameField.setText(em.getUsername());
					passwordField.setText(em.getPassword());
					nameField.setText(em.getName());
					surnameField.setText(em.getLastName());
					genderField.setSelectedItem(em.getGender());
					birthDateField.setText(em.getBirthDate().toString());
					phoneNumField.setText(em.getPhoneNumber());
					roleField.setSelectedItem(em.getRole());
					degreeField.setSelectedItem(em.getDegree());
					employmentField.setText(em.getEmploymentDate().toString());
					salaryField.setText(Double.toString(em.getBaseSalary()));
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
				try {
					String username = usernameField.getText();
					String password = passwordField.getText();
					String name = nameField.getText();
					String surname = surnameField.getText();
					Gender gender = (Gender) genderField.getSelectedItem();
					LocalDate birthDate = LocalDate.parse(birthDateField.getText());
					String phoneNumber = phoneNumField.getText();
					Degree degree = (Degree) degreeField.getSelectedItem();
					LocalDate employment = LocalDate.parse(employmentField.getText());
					Double salary = Double.parseDouble(salaryField.getText());
					hotel.um.updateEmployee(username, password, name, surname, gender, birthDate, phoneNumber, degree, employment, salary);
					data.fireTableDataChanged();
					saveButton.setEnabled(false);
					usernameField.setEditable(true);
					usernameField.setText("");
					passwordField.setText("");
					nameField.setText("");
					surnameField.setText("");
					phoneNumField.setText("");
					salaryField.setText("");
					birthDateField.setText("yyyy-mm-dd");
					employmentField.setText("yyyy-mm-dd");
					JOptionPane.showMessageDialog(null, "Izmene su uspešno sačuvane!");
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Uneti su neispravni podaci!");
				}
				
				
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
		employmentField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                employmentField.setText("");
            }
        });
	}
}
