package manage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import entity.Room;
import entity.RoomFeature;
import entity.RoomFeatureLink;
import exceptions.DuplicateIDException;
import exceptions.NonexistentEntityException;

public class RoomFeatureManager {
	public ArrayList<RoomFeature> roomFeatures;
	public ArrayList<RoomFeatureLink> roomFeatureLinks;
	
	public RoomFeatureManager() {
		this.roomFeatures = new ArrayList<RoomFeature>();
		this.roomFeatureLinks = new ArrayList<RoomFeatureLink>();
	}
	
	public void saveData() {
		String sep = System.getProperty("file.separator");
		ArrayList<String> featureBuffer = new ArrayList<String>();
		ArrayList<String> linkBuffer = new ArrayList<String>();
		for(RoomFeature r:roomFeatures) {
			featureBuffer.add(r.toFileString());
		}
		for(RoomFeatureLink l:roomFeatureLinks) {
			linkBuffer.add(l.toFileString());
		}
		try {
			Files.write(Paths.get("." + sep + "data" + sep + "roomfeatures.csv"), featureBuffer);
			Files.write(Paths.get("." + sep + "data" + sep + "roomfeaturelinks.csv"), linkBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void createRoomFeature(String name, double price) {
		for(RoomFeature i:roomFeatures) {
			if(i.getName().equals(name)) {
				throw new DuplicateIDException();
			}
		}
		RoomFeature r = new RoomFeature(name, price);
		roomFeatures.add(r);
	}
	
	public RoomFeature readRoomFeature(String name) {
		RoomFeature r = null;
		for(RoomFeature i:roomFeatures) {
			if(i.getName().equals(name)) {
				r = i;
				break;
			}
		}
		if(r == null) {
			throw new NonexistentEntityException();
		}
		return r;
	}
	
	public void updateRoomFeature(String name, double price) {
		RoomFeature r = readRoomFeature(name);
		r.price = price;
	}
	
	public void deleteRoomFeature(String name) {
		RoomFeature r = readRoomFeature(name);
		roomFeatures.remove(r);
	}
	
	public void addFeatureLink(Room room, RoomFeature feature) {
		RoomFeatureLink r = new RoomFeatureLink(room, feature);
		for(RoomFeatureLink i:roomFeatureLinks) {
			if(i.equals(r)) {
				throw new DuplicateIDException();
			}
		}
		roomFeatureLinks.add(r);
	}
	
	public void deleteFeatureLink(Room room, RoomFeature feature) {
		RoomFeatureLink r = new RoomFeatureLink(room, feature);
		boolean deleted = false;
		for(RoomFeatureLink i:roomFeatureLinks) {
			if(i.equals(r)) {
				roomFeatureLinks.remove(i);
				deleted = true;
				break;
			}
		}
		if(!deleted) {
			throw new NonexistentEntityException();
		}
	}
}
