package entity;

import java.time.LocalDate;
import java.util.HashMap;

public class Pricing {
	private String ID;
	public LocalDate startDate;
	public LocalDate endDate;
	public HashMap<String, Double> servicePrices;
	
	public Pricing(LocalDate start, LocalDate end, HashMap<String, Double> servicePrices) {
		this.startDate = start;
		this.endDate = end;
		this.servicePrices = servicePrices;

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
}
