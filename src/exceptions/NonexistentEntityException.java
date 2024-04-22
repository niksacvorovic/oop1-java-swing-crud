package exceptions;

public class NonexistentEntityException extends RuntimeException {
	public NonexistentEntityException(){
		super("Dati objekat ne postoji u bazi podataka!");
	}
}
