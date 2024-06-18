package entity;

public class RoomFeatureLink {
	public Room room;
	public RoomFeature feature;
	
	public RoomFeatureLink(Room room, RoomFeature feature) {
		this.room = room;
		this.feature = feature;
	}
	
	public boolean equals(RoomFeatureLink link) {
		return this.room.getRoomNumber().equals(link.room.getRoomNumber()) && this.feature.getName().equals(link.feature.getName());
	}
	
	public String toFileString() {
		return this.room.getRoomNumber() + "," + this.feature.getName();
	}
}
