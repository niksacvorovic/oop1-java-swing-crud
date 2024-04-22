package exceptions;

public class DuplicateIDException extends RuntimeException {
	public DuplicateIDException() {
		super("Objekat sa datim ID već postoji u bazi podataka!");
	}
}
