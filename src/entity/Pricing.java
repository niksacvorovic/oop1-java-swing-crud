package entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Pricing {
	private String ID;
	public LocalDate startDate;
	public LocalDate endDate;
	public LinkedHashMap<String, Double> servicePrices;
	
	public Pricing(String ID, LocalDate start, LocalDate end, LinkedHashMap<String, Double> servicePrices) {
		this.ID = ID;
		this.startDate = start;
		this.endDate = end;
		this.servicePrices = servicePrices;
	}
	
	public Pricing(Pricing p) {
		this.startDate = p.startDate;
		this.endDate = p.endDate;
		this.servicePrices = p.servicePrices;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID; 
	}
	
	@Override
	public String toString() {
		String str = "ID: " + this.ID + "\nDatum početka važenja: " + this.startDate.toString() + "\nDatum kraja važenja: " + this.endDate; 
		StringBuilder sb = new StringBuilder(str);
		this.servicePrices.forEach((key, value) -> {
			sb.append("\n" + key.toString() + ": " + value);
		});
		return sb.toString();
	}
	
	public String toFileString() {
		String save = this.getID() + "," + this.startDate.toString() + "," + this.endDate.toString();
		StringBuilder sb = new StringBuilder(save);
		this.servicePrices.forEach((key, value) -> {
			sb.append("," + key + "-" + Double.toString(value));
		});
		return sb.toString();
	}
	
	public Object[] toCell() {
		Object data[] = {this.getID(), this.startDate.toString(), this.endDate.toString(), "Kliknite dvaput za pregled"};
		return data;
	}
}
