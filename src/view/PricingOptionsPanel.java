package view;

import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import entity.Pricing;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import hotel.Hotel;
import models.PricingModel;

public class PricingOptionsPanel extends JPanel {
	public PricingOptionsPanel(Hotel hotel) {
		GroupLayout pricingLayout = new GroupLayout(this);
		pricingLayout.getAutoCreateContainerGaps();
		pricingLayout.getAutoCreateGaps();
		PricingModel data = new PricingModel(hotel.pm.pricings);
		JTable pricingTable = new JTable(data);
		JScrollPane tableContainer = new JScrollPane(pricingTable);
		JLabel serviceLabel = new JLabel("Dodatne usluge:");
		DefaultListModel<String> servicesListModel = new DefaultListModel<String>();
		JTextField serviceField = new JTextField();
		for(String i:hotel.pm.services) {
			servicesListModel.addElement(i);
		}
		JButton addButton = new JButton("Dodajte novi cenovnik");
		JButton changeButton = new JButton("Izmenite postojeći cenovnik");
		JButton deleteButton = new JButton("Obrišite cenovnik");
		JButton addServiceButton = new JButton("Dodajte uslugu");
		JButton deleteServiceButton = new JButton("Obrišite uslugu");
		pricingLayout.setHorizontalGroup(pricingLayout.createSequentialGroup()
				.addComponent(tableContainer)
				.addGroup(pricingLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(addButton, GroupLayout.DEFAULT_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						.addComponent(changeButton, GroupLayout.DEFAULT_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						.addComponent(deleteButton, GroupLayout.DEFAULT_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						)
				);
		pricingLayout.setVerticalGroup(pricingLayout.createParallelGroup()
				.addComponent(tableContainer)
				.addGroup(pricingLayout.createSequentialGroup()
						.addComponent(addButton)
						.addComponent(changeButton)
						.addComponent(deleteButton)
						)
				);
		setLayout(pricingLayout);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog addPricingDialog = new JDialog();
				GridLayout dialogLayout = new GridLayout(hotel.pm.roomTypes.size() + hotel.pm.services.size() + 3, 2);
				dialogLayout.setVgap(10);
				dialogLayout.setHgap(10);
				JLabel startLabel = new JLabel("Početni datum");
				JTextField startField = new JTextField("yyyy-mm-dd");
				JLabel endLabel = new JLabel("Krajnji datum");
				JTextField endField = new JTextField("yyyy-mm-dd");
				addPricingDialog.add(startLabel);
				addPricingDialog.add(startField);
				addPricingDialog.add(endLabel);
				addPricingDialog.add(endField);
				HashMap<String, JTextField> priceFields = new HashMap<String, JTextField>();
				for(String i:hotel.pm.roomTypes) {
					JLabel label = new JLabel(i);
					JTextField field = new JTextField();
					field.setSize(150, 20);
					priceFields.put(i, field);
					addPricingDialog.add(label);
					addPricingDialog.add(field);
				}
				for(String i:hotel.pm.services) {
					JLabel label = new JLabel(i);
					JTextField field = new JTextField();
					field.setSize(150, 20);
					priceFields.put(i, field);
					addPricingDialog.add(label);
					addPricingDialog.add(field);
				}
				JLabel empty = new JLabel();
				JButton confirm = new JButton("Dodajte novi cenovnik");
				addPricingDialog.add(empty);
				addPricingDialog.add(confirm);
				confirm.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							LinkedHashMap<String, Double> prices = new LinkedHashMap<String, Double>();
							LocalDate startDate = LocalDate.parse(startField.getText());
							LocalDate endDate = LocalDate.parse(endField.getText());
							priceFields.forEach((key, value) ->{
								Double price = Double.parseDouble(value.getText());
								prices.put(key, price);
							});
							hotel.pm.createPricing(startDate, endDate, prices);
							data.fireTableDataChanged();
							addPricingDialog.dispose();
							JOptionPane.showMessageDialog(null, "Cenovnik je uspešno dodat!");
						}catch(Exception ex) {
							JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
						}
					}
				});
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
				addPricingDialog.setLayout(dialogLayout);
				addPricingDialog.pack();
				addPricingDialog.setTitle("Dodavanje cenovnika");
				addPricingDialog.setResizable(false);
				addPricingDialog.setBounds(200, 200, 400, 450);
				addPricingDialog.setModalityType(ModalityType.APPLICATION_MODAL);
				addPricingDialog.setVisible(true);			
			}
		});
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Pricing p = hotel.pm.readPricing((String) data.getValueAt(pricingTable.getSelectedRow(), 0));
				JDialog addPricingDialog = new JDialog();
				GridLayout dialogLayout = new GridLayout(hotel.pm.roomTypes.size() + hotel.pm.services.size() + 3, 2);
				dialogLayout.setVgap(10);
				dialogLayout.setHgap(10);
				JLabel startLabel = new JLabel("Početni datum");
				JTextField startField = new JTextField(p.startDate.toString());
				JLabel endLabel = new JLabel("Krajnji datum");
				JTextField endField = new JTextField(p.endDate.toString());
				addPricingDialog.add(startLabel);
				addPricingDialog.add(startField);
				addPricingDialog.add(endLabel);
				addPricingDialog.add(endField);
				HashMap<String, JTextField> priceFields = new HashMap<String, JTextField>();
				for(String i:hotel.pm.roomTypes) {
					JLabel label = new JLabel(i);
					JTextField field = new JTextField(p.servicePrices.get(i).toString());
					field.setSize(150, 20);
					priceFields.put(i, field);
					addPricingDialog.add(label);
					addPricingDialog.add(field);
				}
				for(String i:hotel.pm.services) {
					JLabel label = new JLabel(i);
					JTextField field = new JTextField(p.servicePrices.get(i).toString());
					field.setSize(150, 20);
					priceFields.put(i, field);
					addPricingDialog.add(label);
					addPricingDialog.add(field);
				}
				JLabel empty = new JLabel();
				JButton confirm = new JButton("Izmenite cenovnik");
				addPricingDialog.add(empty);
				addPricingDialog.add(confirm);
				confirm.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(pricingTable.getSelectedRow() != -1) {
							try {
								LinkedHashMap<String, Double> prices = new LinkedHashMap<String, Double>();
								LocalDate startDate = LocalDate.parse(startField.getText());
								LocalDate endDate = LocalDate.parse(endField.getText());
								priceFields.forEach((key, value) ->{
									Double price = Double.parseDouble(value.getText());
									prices.put(key, price);
								});
								hotel.pm.updatePricing(p.getID(), startDate, endDate, prices);
								data.fireTableDataChanged();
								addPricingDialog.dispose();
								JOptionPane.showMessageDialog(null, "Cenovnik je uspešno izmenjen!");
							}catch(Exception ex) {
								JOptionPane.showMessageDialog(null, "Neispravni podaci! Pokušajte opet");
							}
						}else {
							JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
						}
					}
				});
				addPricingDialog.setLayout(dialogLayout);
				addPricingDialog.pack();
				addPricingDialog.setTitle("Dodavanje cenovnika");
				addPricingDialog.setResizable(false);
				addPricingDialog.setBounds(200, 200, 400, 450);
				addPricingDialog.setModalityType(ModalityType.APPLICATION_MODAL);
				addPricingDialog.setVisible(true);
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pricingTable.getSelectedRow() != -1) {
					hotel.pm.deletePricing((String) data.getValueAt(pricingTable.getSelectedRow(), 0));
					data.fireTableDataChanged();
				}else {
					JOptionPane.showMessageDialog(null, "Niste selektovali nijedan red u tabeli!");
				}
			}
		});
		pricingTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(pricingTable.getSelectedRow() != -1 && e.getClickCount() == 2) {
					Pricing select = data.getData(pricingTable.getSelectedRow());
					StringBuilder sb = new StringBuilder();
					sb.append(String.format("Cenovnik za period od %s do %s\n", select.startDate.toString(),  select.endDate.toString()));
					select.servicePrices.forEach((key, value) ->{
						sb.append(String.format("%s: %s\n", key, value.toString()));
					});
					JOptionPane.showMessageDialog(null, sb.toString());
				}
			}
		});
	}
}
