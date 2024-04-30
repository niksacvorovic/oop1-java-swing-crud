package manage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Pricing;
import enums.RoomType;
import exceptions.NonexistentEntityException;

public class PricingManager {
	public ArrayList<Pricing> pricings;
	public ArrayList<String> services;
	private static Integer pricingID;
	
	public PricingManager() {
		this.pricings = new ArrayList<Pricing>();
		this.services = new ArrayList<String>();
		for(RoomType i:RoomType.values()) {
			services.add(i.toString());
		}
	}
	
	public static void setPricingID(String s){
		PricingManager.pricingID = Integer.parseInt(s);
	}
	
	public void createPricing(LocalDate start, LocalDate end, double... params) {
		if (params.length != services.size()) {
			throw new RuntimeException();
		}
		HashMap<String, Double> servicePrices = new HashMap<String, Double>();
		Pricing p = new Pricing(start, end, servicePrices);
		p.setID(pricingID.toString());
		pricingID++;
		for(int i = 0; i < services.size(); i++) {
			servicePrices.put(services.get(i), params[i]);
		}
		for(Pricing i:pricings) {
			if(p.startDate.compareTo(i.startDate) > 0 && p.endDate.compareTo(i.endDate) < 0) {
				Pricing split = new Pricing(i);
				split.startDate = p.endDate;
				i.endDate = p.startDate;
				pricings.add(split);
			}else if(i.endDate.compareTo(p.startDate) > 0 && i.endDate.compareTo(p.endDate) < 0) {
				i.endDate = p.startDate;
			}else if(i.startDate.compareTo(p.startDate) > 0 && i.startDate.compareTo(p.endDate) < 0) {
				i.startDate = p.endDate;
			}
		}
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
	
	public void updateOnePrice(String ID, String service, double price)  {
		Pricing p = readPricing(ID);
		p.servicePrices.replace(service, price);
	}
		
	public void updatePricing(String ID, LocalDate start, LocalDate end, HashMap<String, Double> servicePrices) {
		Pricing p = readPricing(ID);
		p.startDate = start;
		p.endDate = end;
		p.servicePrices = servicePrices;
	}
	
	public void deletePricing(String ID) {
		Pricing p = readPricing(ID);
		pricings.remove(p);
	}
}
