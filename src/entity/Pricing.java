package entity;

import java.time.LocalDate;
import java.util.HashMap;
import enums.RoomType;
import enums.Extras;

public class Pricing {
	private String ID;
	public LocalDate startDate;
	public LocalDate endDate;
	public HashMap<RoomType, Double> roomPrices;
	public HashMap<Extras, Double> extrasPrices;
	
	//implementacija cenovnika: cenovnik sadrži samo id i datume, stavke implementiramo kao odvojene objekte koji imaju
	//u sebi id cenovnika, uslugu i cenu usluge
	
	public Pricing(LocalDate start, LocalDate end, HashMap<RoomType, Double> roomPrices, HashMap<Extras, Double> extrasPrices) {
		this.startDate = start;
		this.endDate = end;
		this.roomPrices = roomPrices;
		this.extrasPrices = extrasPrices;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID; 
	}
	
	@Override
	public String toString() {
		String str = "ID: " + this.ID + "\nDatim početka važenja: " + this.startDate.toString() + "\nDatum kraja važenja: " + this.endDate; 
		StringBuilder sb = new StringBuilder(str);
		this.roomPrices.forEach((key, value) -> {
			sb.append("\n" + key.toString() + ": " + value);
		});
		this.extrasPrices.forEach((key, value) -> {
			sb.append("\n" + key.toString() + ": " + value);
		});
		return sb.toString();
	}
}
