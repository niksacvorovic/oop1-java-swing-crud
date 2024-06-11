package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import entity.Room;
import hotel.Hotel;

public class ReportsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public ReportsPanel(Hotel hotel) {
		BoxLayout reportLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(reportLayout);
		DefaultComboBoxModel roomlist = new DefaultComboBoxModel();
		for(Room i:hotel.rom.rooms) {
			roomlist.addElement(i.getRoomNumber());
		}
		JLabel startLabel = new JLabel("Početni datum");
		JLabel endLabel = new JLabel("Krajnji datum");
		JLabel roomLabel = new JLabel("Odabrana soba (za treći izveštaj)");
		JComboBox roomField = new JComboBox(roomlist);
		JTextField startField = new JTextField("yyyy-mm-dd");
		JTextField endField = new JTextField("yyyy-mm-dd");
		startField.setMaximumSize(new Dimension(150, 20));
		endField.setMaximumSize(new Dimension(150, 20));
		roomField.setMaximumSize(new Dimension(150, 20));
		ButtonGroup radioButtons = new ButtonGroup();
		JRadioButton firstReport = new JRadioButton("Izveštaj 1 (Prihodi, rashodi, sobarice, potvrđene rezervacije)");
		JRadioButton secondReport = new JRadioButton("Izveštaj 2 (Potvrđeni, otkazani i odbijeni zahtevi)");
		JRadioButton thirdReport = new JRadioButton("Izveštaj 3 (Podaci o sobama)");
		JButton generate = new JButton("Generiši izveštaj");
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
		radioButtons.add(firstReport);
		radioButtons.add(secondReport);
		radioButtons.add(thirdReport);
		startLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		startField.setAlignmentX(Component.LEFT_ALIGNMENT);
		endLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		endField.setAlignmentX(Component.LEFT_ALIGNMENT);
		roomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		roomField.setAlignmentX(Component.LEFT_ALIGNMENT);
		setVisible(true);
		add(startLabel);
		add(startField);
		add(endLabel);
		add(endField);
		add(roomLabel);
		add(roomField);
		add(firstReport);
		add(secondReport);
		add(thirdReport);
		add(generate);
	}

}
