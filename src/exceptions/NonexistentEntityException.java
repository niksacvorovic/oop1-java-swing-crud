package exceptions;

public class NonexistentEntityException extends Exception {
	public NonexistentEntityException(){
		super("Dati objekat ne postoji u bazi podataka!");
	}
}
