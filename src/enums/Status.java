package enums;

public enum Status {
	NA_CEKANJU(0),
	POTVRDJENA(1),
	ODBIJENA(2),
	OTKAZANA(3),
	U_TOKU(4),
	ZAVRSENA(5);
	
	int status;
	private Status() {}
	private Status(int i) {this.status = i;}
	private String[] str = {"Na čekanju", "Potvrđena", "Odbijena", "Otkazana", "U toku", "Završena"};
	
	@Override
	public String toString() {
		return str[this.ordinal()];
	}
}
