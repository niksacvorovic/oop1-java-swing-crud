package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Guest;

public class GuestModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private String columnNames[] = {"Korisničko ime", "Lozinka", "Ime", "Prezime", "Pol", "Datum rođenja", "Broj telefona"};
	private List<Guest> data;
	
	public GuestModel(ArrayList<Guest> data) {
		this.data = data;
	}
	public Guest getData(int index) {
		return data.get(index);
	}
	public void addData(Guest e) {
		this.data.add(e);
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
		Guest e = data.get(rowIndex);
		return e.toCell()[columnIndex];
	}
}
