package exceptions;

public class DuplicateIDException extends Exception {
	public DuplicateIDException() {
		super("Objekat sa datim ID već postoji u bazi podataka!");
	}
}
