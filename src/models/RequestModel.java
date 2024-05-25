package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Request;

public class RequestModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"ID", "Gost", "Status", "Tip sobe", "Početak", "Kraj", "Dodatne usluge"};
	private List<Request> data;
	
	public RequestModel(ArrayList<Request> data) {
		this.data = data;
	}

	public Request getData(int index) {
		return data.get(index);
	}
	
	public void addData(Request r) {
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
		Request r = data.get(rowIndex);
		return r.toCell()[columnIndex];
	}

}
