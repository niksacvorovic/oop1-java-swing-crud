package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Pricing;

public class PricingModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private String columnNames[] = {"ID", "Početak važenja", "Kraj važenja", "Cene"};
	private List<Pricing> data;
	
	public PricingModel(ArrayList<Pricing> data) {
		this.data = data;
	}
	public Pricing getData(int index) {
		return data.get(index);
	}
	public void addData(Pricing e) {
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
		Pricing e = data.get(rowIndex);
		return e.toCell()[columnIndex];
	}
}
