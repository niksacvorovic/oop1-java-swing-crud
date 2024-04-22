package enums;

public enum Gender {
	MUSKI(0),
	ZENSKI(1),
	OSTALO(2);
	
	int gender;
	private Gender() {}
	private Gender(int i) {this.gender = i;}
	private String[] str = {"Muški", "Ženski", "Ostalo"};
	@Override
	public String toString() {
		return str[this.ordinal()];
	}
}
