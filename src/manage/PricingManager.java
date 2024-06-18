package manage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import entity.Pricing;
import exceptions.NonexistentEntityException;

public class PricingManager {
	public ArrayList<Pricing> pricings;
	public ArrayList<String> services;
	public ArrayList<String> roomTypes;
	private static Integer pricingID;
	
	public PricingManager() {
		this.pricings = new ArrayList<Pricing>();
		this.services = new ArrayList<String>();
		this.roomTypes = new ArrayList<String>();
	}
	
	public static String getPricingID() {
		return pricingID.toString();
	}
	
	public static void setPricingID(String s){
		PricingManager.pricingID = Integer.parseInt(s);
	}
	
	public void saveData() {
		ArrayList<String> buffer = new ArrayList<String>();
		String sep = System.getProperty("file.separator");
		for(Pricing i:pricings) {
			buffer.add(i.toFileString());
		}
		try {
			Files.write(Paths.get("." + sep + "data" + sep + "pricings.csv"), buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createPricing(LocalDate start, LocalDate end, double... params) {
		if (params.length != roomTypes.size() + services.size() || end.compareTo(start) < 0) {
			throw new RuntimeException();
		}
		LinkedHashMap<String, Double> servicePrices = new LinkedHashMap<String, Double>();
		pricingID++;
		String ID = pricingID.toString();
		Pricing p = new Pricing(ID, start, end, servicePrices);
		for(int i = 0; i < roomTypes.size(); i++) {
			servicePrices.put(roomTypes.get(i), params[i]);
		}
		for(int i = roomTypes.size(); i < roomTypes.size() + services.size(); i++) {
			servicePrices.put(services.get(i - roomTypes.size()), params[i]);
		}
		for(Pricing i:pricings) {
			if(p.startDate.compareTo(i.startDate) > 0 && p.endDate.compareTo(i.endDate) < 0) {
				Pricing split = new Pricing(i);
				pricingID++;
				split.setID(pricingID.toString());
				i.endDate = p.startDate;
				split.startDate = p.endDate;
				pricings.add(split);
			}/*else if(i.endDate.compareTo(p.startDate) > 0 && i.endDate.compareTo(p.endDate) < 0) {
				i.endDate = p.startDate;
			}else if(i.startDate.compareTo(p.startDate) > 0 && i.startDate.compareTo(p.endDate) < 0) {
				i.startDate = p.endDate;
			}*/
		}
		pricings.add(p);
	}
	
	public void createPricing(LocalDate start, LocalDate end, LinkedHashMap<String, Double> prices) {
		if (prices.size() != roomTypes.size() + services.size() || end.compareTo(start) < 0) {
			throw new RuntimeException();
		}
		String ID = pricingID.toString();
		pricingID++;
		Pricing p = new Pricing(ID, start, end, prices);
		for(Pricing i:pricings) {
			if(p.startDate.compareTo(i.startDate) > 0 && p.endDate.compareTo(i.endDate) < 0) {
				Pricing split = new Pricing(i);
				split.setID(pricingID.toString());
				pricingID++;
				i.endDate = p.startDate;
				split.startDate = p.endDate;
				pricings.add(split);
			}/*else if(i.endDate.compareTo(p.startDate) > 0 && i.endDate.compareTo(p.endDate) < 0) {
				i.endDate = p.startDate;
			}else if(i.startDate.compareTo(p.startDate) > 0 && i.startDate.compareTo(p.endDate) < 0) {
				i.startDate = p.endDate;
			}*/
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
		
	public void updatePricing(String ID, LocalDate start, LocalDate end, LinkedHashMap<String, Double> servicePrices) {
		if(end.compareTo(start) < 0) {
			throw new RuntimeException();
		}
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
