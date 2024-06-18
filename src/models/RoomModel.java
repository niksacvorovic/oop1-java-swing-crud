package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Room;
import entity.RoomFeatureLink;

public class RoomModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private String columnNames[] = {"Broj sobe", "Tip sobe", "Status sobe", "Dodatne usluge", "Zaduženi higijeničar"};
	private List<Room> data;
	private List<RoomFeatureLink> featureData;
	
	public RoomModel(ArrayList<Room> data, ArrayList<RoomFeatureLink> featureData) {
		this.data = data;
		this.featureData = featureData;
	}
	public Room getData(int index) {
		return data.get(index);
	}
	public void addData(Room e) {
		this.data.add(e);
	}
	public Object[] toCell(Room r) {
		String cleanerString = null;
		if (r.cleaner == null) {
			cleanerString = "-";
		}else {
			cleanerString = r.cleaner.getUsername();
		}
		String features = "";
		for(RoomFeatureLink i:featureData) {
			if(i.room.equals(r)) {
				features += i.feature.getName() + ",";
			}
		}
		if(features.length() == 0) {
			features = "-";
		}else {
			features = features.substring(0, features.length() - 1);
		}
		Object data[] = {r.getRoomNumber(), r.type, r.status, features, cleanerString};
		return data;
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
		return toCell(e)[columnIndex];
	}

}
