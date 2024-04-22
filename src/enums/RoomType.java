package enums;

public enum RoomType {
	JEDNOKREVETNA(0),
	DVOKREVETNA(1),
	DVOKREVETNA_BRACNI(2),
	TROKREVETNA(3),
	TROKREVETNA_BRACNI(4);
	
	int type;
	private RoomType() {}
	private RoomType(int i) {this.type = i;}
	private String[] str = {"Jednokrevetna", "Dvokrevetna", "Dvokrevetna s bračnim", "Trokrevetna", "Trokrevetna s bračnim"};
	@Override
	public String toString() {
		return str[this.ordinal()];
	}
}
