package enums;

public enum Role {
	ADMINISTRATOR(0),
	RECEPCIONAR(1),
	HIGIJENICAR(2);
	
	int role;
	private Role() {}
	private Role(int i) {this.role = i;}
	private String[] str = {"Administrator", "Recepcionar", "Higijeničar"};
	@Override
	public String toString() {
		return str[this.ordinal()];
	}
}
