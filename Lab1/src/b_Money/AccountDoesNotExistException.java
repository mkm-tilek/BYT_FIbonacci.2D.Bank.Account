package b_Money;

public class AccountDoesNotExistException extends Exception {
	static final long serialVersionUID = 1L; 
	
	public String getMessage() {
		return "Error, account doesn't exist";
	}
}
