package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Reservation;

public class ReservationModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"ID", "Gost", "Soba", "Početak", "Kraj", "Status", "Cena", "Dodatne usluge"};
	private List<Reservation> data;
	
	public ReservationModel(ArrayList<Reservation> data) {
		this.data = data;
	}

	public Reservation getData(int index) {
		return data.get(index);
	}
	
	public void addData(Reservation r) {
		this.data.add(r);
	}
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Reservation r = data.get(rowIndex);
		return r.toCell()[columnIndex];
	}

}
