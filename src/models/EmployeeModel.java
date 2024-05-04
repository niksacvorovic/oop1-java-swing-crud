package models;

import java.util.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.Employee;

public class EmployeeModel extends AbstractTableModel {
	private String columnNames[] = {"Korisničko ime", "Lozinka", "Ime", "Prezime", "Pol", "Datum rođenja", "Broj telefona", "Radno mesto",
			"Obrazovanje", "Datum zaposlenja", "Osnovna plata"};
	private List<Employee> data;
	
	public EmployeeModel(ArrayList<Employee> data) {
		this.data = data;
	}
	public Employee getData(int index) {
		return data.get(index);
	}
	public void addData(Employee e) {
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
		Employee e = data.get(rowIndex);
		return e.toCell()[columnIndex];
	}
	public void removeRow(int index) {
		data.remove(index);
	}
}
