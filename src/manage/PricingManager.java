package manage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Pricing;
import enums.Extras;
import enums.RoomType;
import exceptions.NonexistentEntityException;

public class PricingManager {
	public ArrayList<Pricing> pricings;
	
	public PricingManager() {
		this.pricings = new ArrayList<Pricing>();
	}
	
	public void createPricing(double[] prices, LocalDate start, LocalDate end) {
		for(Pricing i:pricings) {
			if (!((i.startDate.compareTo(end) > 0) || (i.endDate.compareTo(start) < 0))) {
				throw new RuntimeException();
			}
		}
		int counter = 0;
		if (prices.length != 12) {
			throw new RuntimeException();
		}
		HashMap<RoomType, Double> roomPrices = new HashMap<RoomType, Double>();
		HashMap<Extras, Double> extrasPrices = new HashMap<Extras, Double>();
		for(RoomType e:RoomType.values()) {
			roomPrices.put(e, prices[counter]);
			counter++;
		}
		for(Extras e:Extras.values()) {
			extrasPrices.put(e, prices[counter]);
			counter++;
		}
		Pricing p = new Pricing(start, end, roomPrices, extrasPrices);
		p.setID();
		pricings.add(p);
	}
	
	public Pricing readPricing(String ID) {
		Pricing p = null;
		for(Pricing i:pricings) {
			if(i.getID().equals(ID)) {
				p = i;
				break;
			}
		}
		if(p == null) {
			throw new NonexistentEntityException();
		}
		return p;
	}
	
	public void updateOnePrice(String ID, RoomType room, double price)  {
		Pricing p = readPricing(ID);
		p.roomPrices.replace(room, price);
	}
	
	public void updateOnePrice(String ID, Extras extra, double price)  {
		Pricing p = readPricing(ID);
		p.extrasPrices.replace(extra, price);
	}
	
	public void updatePricing(String ID, LocalDate start, LocalDate end, HashMap<RoomType, Double> roomPrices, 
			HashMap<Extras, Double> extrasPrices) {
		Pricing p = readPricing(ID);
		p.startDate = start;
		p.endDate = end;
		p.roomPrices = roomPrices;
		p.extrasPrices = extrasPrices;
		p.setID();
	}
	public void updatePricing(String ID, double[] prices, LocalDate start, LocalDate end) {
		Pricing p = readPricing(ID);
		int counter = 0;
		if (prices.length != 12) {
			throw new RuntimeException();
		}
		HashMap<RoomType, Double> roomPrices = new HashMap<RoomType, Double>();
		HashMap<Extras, Double> extrasPrices = new HashMap<Extras, Double>();
		for(RoomType e:RoomType.values()) {
			roomPrices.put(e, prices[counter]);
			counter++;
		}
		for(Extras e:Extras.values()) {
			extrasPrices.put(e, prices[counter]);
			counter++;
		}
		p.startDate = start;
		p.endDate = end;
		p.roomPrices = roomPrices;
		p.extrasPrices = extrasPrices;
		p.setID();
	}
	
	public void deletePricing(String ID) {
		Pricing p = readPricing(ID);
		pricings.remove(p);
	}
}
