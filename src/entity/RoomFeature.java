package entity;

public class RoomFeature {
	private String name;
	public double price;
	
	public RoomFeature(String name, double price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toFileString() {
		return this.name + "," + Double.toString(this.price);
	}
	
	public Object[] toCell() {
		Object data[] = {this.name, this.price};
		return data;
	}
}
