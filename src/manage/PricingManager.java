package manage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Pricing;
import enums.Extras;
import enums.RoomType;
import exceptions.DuplicateIDException;
import exceptions.NonexistentEntityException;

public class PricingManager {
	public ArrayList<Pricing> pricings;
	
	public PricingManager() {
		this.pricings = new ArrayList<Pricing>();
	}
	
	public void createPricing(String ID, double[] prices, LocalDate start, LocalDate end) throws Exception{
		for(Pricing i:pricings) {
			if(i.getID() == ID) {
				throw new DuplicateIDException();
			}
		}
		int counter = 0;
		if (prices.length != 12) {
			throw new Exception();
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
		Pricing p = new Pricing(ID, start, end, roomPrices, extrasPrices);
		pricings.add(p);
	}
	
	public Pricing readPricing(String ID) throws Exception{
		Pricing p = null;
		for(Pricing i:pricings) {
			if(i.getID() == ID) {
				p = i;
				break;
			}
		}
		if(p == null) {
			throw new NonexistentEntityException();
		}
		return p;
	}
	
	public void updatePricing(String ID, double[] prices, LocalDate start, LocalDate end) throws Exception{
		Pricing p = readPricing(ID);
		int counter = 0;
		if (prices.length != 12) {
			throw new Exception();
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
	}
	
	public void deletePricing(String ID) throws Exception{
		Pricing p = readPricing(ID);
		pricings.remove(p);
	}
}
