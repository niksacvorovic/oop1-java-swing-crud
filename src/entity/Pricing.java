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
	
	public Pricing(LocalDate start, LocalDate end, HashMap<RoomType, Double> roomPrices, HashMap<Extras, Double> extrasPrices) {
		this.startDate = start;
		this.endDate = end;
		this.roomPrices = roomPrices;
		this.extrasPrices = extrasPrices;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID() {
		ID = startDate.toString() + "-" + endDate.toString(); 
	}
}
