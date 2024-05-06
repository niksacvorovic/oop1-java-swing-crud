package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Room;

public class RoomModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private String columnNames[] = {"Broj sobe", "Tip sobe", "Status sobe", "Zaduženi higijeničar"};
	private List<Room> data;
	
	public RoomModel(ArrayList<Room> data) {
		this.data = data;
	}
	public Room getData(int index) {
		return data.get(index);
	}
	public void addData(Room e) {
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
		Room e = data.get(rowIndex);
		return e.toCell()[columnIndex];
	}

}
