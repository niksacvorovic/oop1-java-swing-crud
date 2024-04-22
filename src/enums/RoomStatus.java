package enums;

public enum RoomStatus {
	ZAUZETA(0),
	SPREMANJE(1),
	SLOBODNA(2);
	
	int status;
	private RoomStatus() {}
	private RoomStatus(int i) {this.status = i;}
	private String[] str = {"Zauzeta", "Spremanje", "Slobodna"};
	@Override
	public String toString() {
		return str[this.ordinal()];
	}
}
