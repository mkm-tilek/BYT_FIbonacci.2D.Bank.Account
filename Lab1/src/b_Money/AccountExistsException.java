package b_Money;

public class AccountExistsException extends Exception {
	static final long serialVersionUID = 1L; 
	
	public String getMessage() {
		return "Error, account with such Id already exists";
	}
}
