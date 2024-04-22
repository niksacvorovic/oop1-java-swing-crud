package enums;

public enum Extras {
	DORUCAK(0),
	RUCAK(1),
	VECERA(2),
	BAZEN(3),
	SAUNA(4),
	TERETANA(5),
	SLANA_SOBA(6);
	
	int extra;
	private Extras() {}
	private Extras(int i) {this.extra = i;}
	private String[] str = {"Doručak", "Ručak", "Večera", "Bazen", "Sauna", "Teretana", "Slana soba"};
	@Override
	public String toString() {
		return str[this.ordinal()];
	}
}
