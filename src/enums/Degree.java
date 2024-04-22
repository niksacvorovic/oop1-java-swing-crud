package enums;

public enum Degree {
	OSNOVNA_SKOLA(0),
	TROGODISNJA_SREDNJA(1),
	CETVOROGODISNJA_SREDNJA(2),
	OSNOVNE_STRUKOVNE(3),
	MASTER_STRUKOVNE(4),
	OSNOVNE_AKADEMSKE(5),
	MASTER_AKADEMSKE(6),
	DOKTORSKE(7);
	
	int degree;
	private Degree() {}
	private Degree(int i) {this.degree = i;}
	
	private String[] str = {"Osnovna škola", "Trogodišnja srednja škola", "Četvorogodišnja srednja škola", "Osnovne strukovne studije",
			"Master strukovne studije", "Osnovne akademske studije", "Master akademske studije", "Doktorske studije"};
	@Override
	public String toString() {
		return str[this.ordinal()];
	}
}
