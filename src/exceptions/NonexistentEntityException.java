package exceptions;

public class NonexistentEntityException extends Exception {
	public NonexistentEntityException(String s){
		super(s);
	}
}
