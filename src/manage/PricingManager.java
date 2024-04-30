package manage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	
	public void saveData() {
		ArrayList<String> buffer = new ArrayList<String>();
		String sep = System.getProperty("file.separator");
		for(Pricing i:pricings) {
			String save = i.getID() + "," + i.startDate.toString() + "," + i.endDate.toString();
			StringBuilder sb = new StringBuilder(save);
			i.servicePrices.forEach((key, value) -> {
				sb.append("," + key + "-" + Double.toString(value));
			});
			buffer.add(sb.toString());
		}
		try {
			Files.write(Paths.get("." + sep + "data" + sep + "pricings.csv"), buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createPricing(LocalDate start, LocalDate end, double... params) {
		if (params.length != services.size()) {
			throw new RuntimeException();
		}
		HashMap<String, Double> servicePrices = new HashMap<String, Double>();
		String ID = pricingID.toString();
		pricingID++;
		Pricing p = new Pricing(ID, start, end, servicePrices);
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
