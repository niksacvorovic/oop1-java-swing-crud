package enums;

public enum ReservationStatus {
	NA_CEKANJU(0),
	POTVDJENA(1),
	ODBIJENA(2),
	OTKAZANA(3);
	
	int status;
	private ReservationStatus() {}
	private ReservationStatus(int i) {this.status = i;}
	private String[] str = {"Na čekanju", "Potvrđena", "Odbijena", "Otkazana"};
	
	@Override
	public String toString() {
		return str[this.ordinal()];
	}
}
