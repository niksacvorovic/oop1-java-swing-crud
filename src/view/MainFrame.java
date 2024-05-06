package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import entity.User;
import entity.Guest;
import entity.Administrator;
import entity.Receptioner;
import entity.Cleaner;
import hotel.Hotel;
import manage.UserManager;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private User loggedin;
	private Hotel hotel;
	private JPanel contentPane;

	public MainFrame(Hotel hotel) {
		this.loggedin = null;
		this.hotel = hotel;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(MainFrame.this);
		LoginDialog(hotel.um);
		addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	        	hotel.saveData();
	            System.exit(0);
	        }
	    });
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
	}
	
	public void LoginDialog(UserManager um) {
		JDialog d = new JDialog();
		JLabel usernameLabel = new JLabel("Korisničko ime: ");
		JLabel passwordLabel = new JLabel("Lozinka: ");
		JTextField usernameField = new JTextField();
		JTextField passwordField = new JPasswordField();
		JButton login = new JButton("Ulogujte se");
		JButton close = new JButton("Odustanite");
		FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);
		d.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		d.setLayout(layout);
		d.setTitle("Prijava na sistem");
		d.setResizable(false);
		d.setBounds(200, 200, 275, 125);
		usernameField.setPreferredSize(new Dimension(150, 20));
		passwordField.setPreferredSize(new Dimension(150, 20));
		login.setBounds(0, 0, 50, 30);
		close.setBounds(0, 0, 50, 30);
		d.getContentPane().add(usernameLabel);
		d.getContentPane().add(usernameField);
		d.getContentPane().add(passwordLabel);
		d.getContentPane().add(passwordField);
		d.getContentPane().add(close);
		d.getContentPane().add(login);
		d.setVisible(true);
		
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login(usernameField, passwordField, um, d);
			}
		});
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 10 && !usernameField.getText().equals("") && !passwordField.getText().equals("")) {
					login(usernameField, passwordField, um, d);
				}
				if(e.getKeyCode() == 40) {
					passwordField.requestFocus();
				}
			}
		});
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 10 && !usernameField.getText().equals("") && !passwordField.getText().equals("")) {
					login(usernameField, passwordField, um, d);
				}
				if(e.getKeyCode() == 38) {
					usernameField.requestFocus();
				}
			}
		});
	}
	
	public void login(JTextField usernameField, JTextField passwordField, UserManager um, JDialog d) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		try {
			if(um.readUser(username).getPassword().equals(password)) {
				loggedin = um.readUser(username);
			}
		}catch (Exception ex) {}
		if(loggedin instanceof Guest) {
			GuestFrame();
			d.dispose();
		}else if(loggedin instanceof Administrator) {
			AdminFrame(hotel);
			d.dispose();
		}else if(loggedin instanceof Receptioner) {
			ReceptionerFrame();
			d.dispose();
		}else if(loggedin instanceof Cleaner) {
			CleanerFrame();
			d.dispose();
		}else{
			JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
		}
	}
	
	public void AdminFrame(Hotel hotel) {
		setTitle("Hotel SIIT - Administracija");
		contentPane = new AdminPanel(hotel);
		setContentPane(contentPane);
		setVisible(true);
	}
	
	public void ReceptionerFrame() {
		
	}
	
	public void CleanerFrame() {
		
	}
	
	public void GuestFrame() {
		
	}
}
