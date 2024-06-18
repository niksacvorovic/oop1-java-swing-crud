package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.RoomFeature;

public class RoomFeatureModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String columnNames[] = {"Usluga", "Cena"};
	private List<RoomFeature> data;
	
	public RoomFeatureModel(ArrayList<RoomFeature> data) {
		this.data = data;
	}
	public RoomFeature getData(int index) {
		return data.get(index);
	}
	public void addData(RoomFeature e) {
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
		RoomFeature e = data.get(rowIndex);
		return e.toCell()[columnIndex];
	}
}
