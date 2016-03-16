package homework.three;

public class WrongStudentException extends Exception {
	private String message;
	
	public WrongStudentException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage(){
		return "Studend didn't add. Wrong input string. " + message;
	}
}